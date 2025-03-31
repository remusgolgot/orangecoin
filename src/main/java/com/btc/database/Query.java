package com.btc.database;

import com.btc.api.model.Block;
import com.btc.api.model.PreviousOutput;
import com.btc.api.model.TransactionOutput;
import com.btc.model.AddressDto;
import com.btc.model.Price;
import com.btc.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    private final String connectionUrl = "jdbc:mysql://localhost:3306/bitcoin?serverTimezone=UTC";

    public void insertPrice(Price price) {

        String sql = "insert into price (date, price, marketCap, volume, variation, chg)"
                + " values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatementPrice(conn, sql, price)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

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

    public List<String> getAddressesWithLimitAndOffsetSorted(int limit, int offset, String sortBy, boolean zeroIncluded) {
        if (sortBy == null) {
            sortBy = "balance DESC";
        }
        String zeroIncludedAsString = zeroIncluded ? " WHERE balance >= 0 " : " WHERE balance > 0 ";
        String query = "select address, balance, received, meta, firstInput, lastOutput from address " + zeroIncludedAsString +
                "order by " + sortBy + " limit " + limit + " OFFSET " + offset;
        List<String> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= 6; i++) {
                    if (i == 1 || i == 4) {
                        String string = rs.getString(i);
                        string = string != null ? string : "";
                        row.append(string);
                    }
                    if (i == 2 || i == 3) {
                        Double d = rs.getDouble(i) / 100000000.0;
                        row.append(d);
                    }
                    if (i == 5 || i == 6) {
                        long timestamp = rs.getLong(i);
                        row.append(timestamp > 0 ? Utils.longTimeStampToDateString(timestamp) : "");
                    }
                    if (i < 6) {
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
        String query = "select address from address order by lastOutput asc limit " + nr;
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
        String query = "select * from address where meta != '' order by meta desc";
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

    public void insertAddress(AddressDto address) {

        String sql = "insert into address (address, balance, received, lastOutput, meta)"
                + " values (?, ?, ?, ?, ?)";
        address.setMeta("");
        address.setLastOutput(System.currentTimeMillis());

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatement(conn, sql, address)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void updateAddress(AddressDto address) {
        String sql = "update address set balance = " + address.getBalance() + ", received = " + address.getReceived() +
                ", lastOutput = " + System.currentTimeMillis() +
                " where address = '" + address.getAddress() + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void insertAddress(String address, double value, long time) {

        String sql = "insert into address (address, balance, received, firstInput, lastInput, meta)"
                + " values (?, ?, ?, ?, ?, ?)";
        AddressDto address1 = new AddressDto(address, value);
        address1.setMeta("");
        address1.setFirstInput(time);
        address1.setLastInput(time);
        address1.setReceived(value);

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatement(conn, sql, address1)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void updateAddressAdd(String address, long value, long time, long receiveValue) {
        String sql = "update address set balance = balance + " + value + ", lastInput = " + time + ", received = received + " + receiveValue +
                " where address = '" + address + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void updateAddressRemove(String address, long value, long time) {
        String sql = "update address set balance = balance - " + value + ", lastOutput = " + time +
                " where address = '" + address + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public Integer addressExists(AddressDto address) {
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

    public Integer addressExists(String address) {
        String sql = "select * from address where address = '" + address + "'";

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

    public void insertBlock(Block block) {

        String sql = "insert into block (hash, height, prevBlock, nrTx)"
                + " values (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatementBlock(conn, sql, block)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void insertUtxo(TransactionOutput transactionOutput) {

        String sql = "insert into utxo (address, txIndex, value, timestamp, spent)"
                + " values (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = preparedStatementUtxo(conn, sql, transactionOutput)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
    }

    public void updateUtxoSpent(PreviousOutput previousOutput) {

        String sql = "update utxo set spent = true where txIndex = " + previousOutput.getTransactionIndex() + " and address = '" + previousOutput.getAddress() + "'";
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "root");
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
        }
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

    private PreparedStatement preparedStatement(Connection connection, String sql, AddressDto address) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString(1, address.getAddress());
        preparedStmt.setDouble(2, address.getBalance());
        preparedStmt.setDouble(3, address.getReceived());
        preparedStmt.setLong(4, address.getFirstInput());
        preparedStmt.setLong(5, address.getLastInput());
        preparedStmt.setString(6, address.getMeta());

        return preparedStmt;
    }

    private PreparedStatement preparedStatementPrice(Connection connection, String sql, Price price) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString(1, price.getDate());
        preparedStmt.setDouble(2, price.getPrice());
        preparedStmt.setDouble(3, price.getMarketCap());
        preparedStmt.setDouble(4, price.getVolume());
        preparedStmt.setDouble(5, price.getVariation());
        preparedStmt.setDouble(6, price.getChg());

        return preparedStmt;
    }

    private PreparedStatement preparedStatementUtxo(Connection connection, String sql, TransactionOutput utxo) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString(1, utxo.getAddress());
        preparedStmt.setLong(2, utxo.getTransactionIndex());
        preparedStmt.setLong(3, utxo.getValue());
        preparedStmt.setLong(4, utxo.getTime());
        preparedStmt.setInt(5, 0);
        return preparedStmt;
    }

    private PreparedStatement preparedStatementBlock(Connection connection, String sql, Block block) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString(1, block.getHash());
        preparedStmt.setLong(2, block.getHeight());
        preparedStmt.setString(3, block.getPrevBlock());
        preparedStmt.setInt(4, block.getNrTx());
        return preparedStmt;
    }

}