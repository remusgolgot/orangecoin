package com.btc.processors;

import com.btc.client.BlockClient;
import com.btc.client.BlockchainClient;
import com.btc.database.Query;
import com.btc.model.AddressDto;

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
        System.out.println("Total               : " + query.balanceSum() / 100000000.0) ;
        System.out.println("Total spent < 10%   : " + query.noSpentLessThanBalanceSum(0.1) / 100000000.0);
        System.out.println("Total unspent       : " + query.noSpentBalanceSum() / 100000000.0);
    }

    public void printMeta() {
        List<String> list = query.getMetaTagAddresses();
        double sum = 0.0;
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
            AddressDto address = blockClient.callAddressAPI(addressString, timeout);
            System.out.println(address);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
