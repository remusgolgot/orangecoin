package database;

import model.Address;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    private final String connectionUrl = "jdbc:mysql://localhost:3306/bitcoin?serverTimezone=UTC";

    public int countAddresses() {

        String sqlCountAll = "SELECT count(*) AS recordCount FROM address";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(sqlCountAll);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                return rs.getInt("recordCount");
            }
        } catch (SQLException e) {
            // handle the exception
        }
        return 0;
    }

    public List<String> getAddressesWithLimitAndOffset(int limit, int offset) {
        return getAddressesWithLimitAndOffsetSorted(limit, offset, "balance");
    }

    public List<String> getAddressesWithLimitAndOffsetSorted(int limit, int offset, String sortBy) {
        if (sortBy == null) {
            sortBy = "balance";
        }
        String query = "select address,balance,received,meta,lastUpdate from address order by " + sortBy + " DESC limit " + limit + " OFFSET " + offset;
        List<String> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= 5; i++) {
                    if (i <= 1 || i == 4) {
                        String string = rs.getString(i);
                        string = string != null ? string : "";
                        row.append(string);
                    }
                    if (i == 2 || i == 3) {
                        Double d = rs.getDouble(i);
                        row.append(Utils.prettyBalance(d));
                    }
                    if (i == 5) {
                        long l = rs.getLong(i);
                        Date d = new Date(l);
                        row.append(d);
                    }
                    if (i < 5) {
                        row.append(",");
                    }
                }
                list.add(row.toString());
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<String> getTopAddresses(int nr) {
        String query = "select address from address order by balance desc limit " + nr;
        List<String> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("address"));
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<String> getOldestAddresses(int nr) {
        String query = "select address from address order by lastUpdate asc limit " + nr;
        List<String> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("address"));
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return list;

    }

    public double balanceSum() {

        String sqlCountAll = "SELECT sum(balance) AS sum FROM address";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(sqlCountAll);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                return rs.getDouble("sum");
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public double noSpentBalanceSum() {

        String sqlCountAll = "SELECT sum(balance) AS sum FROM address where balance=received";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(sqlCountAll);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                return rs.getDouble("sum");
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<String> getMetaTagAddresses() {
        String query = "select * from address where meta != '' order by balance desc";
        List<String> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("address") + "," + rs.getString("meta") + "," + rs.getString("balance"));
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return list;

    }

    public void insertAddress(Address address) {

        String sql = "insert into address (address, balance, received, lastUpdate, meta)"
                + " values (?, ?, ?, ?, ?)";
        address.setMeta("");
        address.setLastUpdate(System.currentTimeMillis());

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatement(conn, sql, address)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void updateAddress(Address address) {
        String sql = "update address set balance = " + address.getBalance() + ", received = " + address.getReceived() +
                ", lastUpdate = " + System.currentTimeMillis() +
                " where address = '" + address.getAddress() + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public Integer addressExists(Address address) {
        String sql = "select * from address where address = '" + address.getAddress() + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public double noSpentLessThanBalanceSum(double v) {

        String sqlCountAll = "SELECT sum(balance) AS sum FROM address where (received-balance)/received < " + v;

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(sqlCountAll);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                return rs.getDouble("sum");
            }
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private PreparedStatement preparedStatement(Connection connection, String sql, Address address) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString(1, address.getAddress());
        preparedStmt.setDouble(2, address.getBalance());
        preparedStmt.setDouble(3, address.getReceived());
        preparedStmt.setLong(4, address.getLastUpdate());
        preparedStmt.setString(5, address.getMeta());

        return preparedStmt;
    }
}