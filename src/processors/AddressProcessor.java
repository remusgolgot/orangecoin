package processors;

import client.BlockClient;
import client.BlockchainClient;
import database.Query;
import model.Address;
import utils.Utils;

import java.util.List;

public class AddressProcessor {

    private final BlockClient blockClient = new BlockchainClient();
    private final Query query = new Query();

    public void processAddress(String address) {
        try {
            System.out.println("Fetching address " + address);
            checkAddress(address);

        } catch (Exception ignored) {
        }
    }

    public void printStats() {
        System.out.println("Number of addresses : " + query.countAddresses());
        System.out.println("Total               : " + Utils.prettyBalance(query.balanceSum()));
        System.out.println("Total spent < 10%   : " + Utils.prettyBalance(query.noSpentLessThanBalanceSum(0.1)));
        System.out.println("Total unspent       : " + Utils.prettyBalance(query.noSpentBalanceSum()));
    }

    public void printMeta() {
        List<String> list = query.getMetaTagAddresses();
        for (String s : list) {
            String[] ss = s.split(",");
            String address = ss[0];
            if (address.length() < 29) {
                address = address + "       ";
            }
            System.out.print(address + " " + Utils.prettyBalance(Double.parseDouble(ss[2])) + " " + ss[1]);
            System.out.println();
        }
    }

    private void checkAddress(String addressString) {
        try {
            long timeout = 2200;
            Address address = blockClient.callAddressAPI(addressString, timeout);
            if (address == null) {
                System.out.println("SKIPPING " + addressString);
                return;
            }
            Integer addressFromDB = query.addressExists(address);
            address.setLastUpdate(System.currentTimeMillis());
            if (addressFromDB == null) {
                System.out.println("new address ... creating");
                query.insertAddress(address);
            } else {
                System.out.println("address exists ... updating");
                query.updateAddress(address);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
