package com.btc.processors;

import com.btc.client.BlockClient;
import com.btc.client.BlockchainClient;
import com.btc.database.Query;
import com.btc.model.Address;

import java.math.BigDecimal;
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
        System.out.println("Total               : " + BigDecimal.valueOf(query.balanceSum()).toPlainString());
        System.out.println("Total spent < 10%   : " + query.noSpentLessThanBalanceSum(0.1));
        System.out.println("Total unspent       : " + query.noSpentBalanceSum());
    }

    public void printMeta() {
        List<String> list = query.getMetaTagAddresses();
        Double sum = 0.0;
        for (String s : list) {
            String[] ss = s.split(",");
            String address = ss[0];
            if (address.length() < 29) {
                address = address + "       ";
            }
            double v = Double.parseDouble(ss[2]);
            String balance = v + " " + ss[1];
            System.out.print(address + " " + balance);
            System.out.println();
            sum += v;
        }
        System.out.println("========================");
        System.out.println("Total " + sum);
        System.out.println("========================");
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
            address.setBalance(address.getBalance() / 100000000);
            address.setReceived(address.getReceived() / 100000000);
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
