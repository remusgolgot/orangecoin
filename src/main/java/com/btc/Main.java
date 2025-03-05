package com.btc;

import com.btc.processors.AddressProcessor;
import com.btc.services.CSVExportService;
import com.btc.services.ExportService;

public class Main {

    static String s = "32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo";

    public static void main(String[] args) {

        AddressProcessor addressProcessor = new AddressProcessor();
        String[] split = s.split("\n");
        System.out.println("Processing " + split.length + " addresses ");
        for (int i = 0; i < split.length; i++) {
            addressProcessor.processAddress(split[i]);
        }
        addressProcessor.printStats();
        System.out.println();
        System.out.println("==============");
        System.out.println();
        addressProcessor.printMeta();

        ExportService exportService = new CSVExportService();
        exportService.export("out");
        exportService.exportMeta("meta");
    }

/*
Number of addresses : 12812
Total               : 11215803.603146605
Total spent < 10%   :  8354049.749380772
Total unspent       :  7563556.113392331
 */

}
