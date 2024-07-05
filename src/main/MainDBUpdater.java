package main;

import client.BlockClient;
import client.BlockchainClient;
import database.Query;
import model.Address;
import processors.AddressProcessor;
import utils.Utils;

import java.util.List;

public class MainDBUpdater {

    static BlockClient blockClient = new BlockchainClient();
    static final long timeout = 1200;
    static final int LIMIT = 2000;
    static final String MODE = "OLDEST";

    public static void main(String[] args) {
        try {
            System.out.println("Updating DB ...");
            List<String> addresses = getByMode();
            System.out.println("addresses : " + addresses.size());
            for (String address : addresses) {
                checkAddress(address);
            }

        } catch (Exception e) {
            //
        }
    }

    private static List<String> getByMode() {
        Query query = new Query();
        List<String> addresses;
        if (MainDBUpdater.MODE.equals("BIGGEST")) {
            addresses = query.getTopAddresses(LIMIT);
        } else {
            addresses = query.getOldestAddresses(LIMIT);
        }
        return addresses;
    }

    private static void checkAddress(String addressString) {
        try {
            Address address = blockClient.callAddressAPI(addressString, timeout);
            AddressProcessor addressProcessor = new AddressProcessor();
            if (address == null) {
                System.out.println("SKIPPING " + addressString);
                return;
            }
            addressProcessor.processAddress(address.getAddress());
            if (address.isSentZero()) {
                Double balance = address.getBalance();
                System.out.println(addressString + "," + Utils.prettyBalance(balance));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
