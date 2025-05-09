package com.btc.deprecated;

import com.btc.client.BlockClient;
import com.btc.client.BlockchainClient;
import com.btc.database.Query;
import com.btc.model.AddressDto;
import com.btc.processors.AddressProcessor;
import com.btc.services.CSVExportService;
import com.btc.services.ExportService;

import java.util.List;

public class MainDBUpdater {

    static BlockClient blockClient = new BlockchainClient();
    static final long timeout = 1200;
    static final int LIMIT = 5000;
    static final String MODE = "OLDEST";

    public static void main(String[] args) {
        try {
            System.out.println("Updating DB ...");
            List<String> addresses = getByMode();
            System.out.println("addresses : " + addresses.size());
            for (String address : addresses) {
                checkAddress(address);
            }
            System.out.println("Exporting to csv");
            ExportService exportService = new CSVExportService();
            exportService.export("out");

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
            AddressDto address = blockClient.callAddressAPI(addressString, timeout);
            AddressProcessor addressProcessor = new AddressProcessor();
            if (address == null) {
                System.out.println("SKIPPING " + addressString);
                return;
            }
            addressProcessor.processAddress(address.getAddress());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
