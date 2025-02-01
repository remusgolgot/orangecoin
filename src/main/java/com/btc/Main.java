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
Number of addresses : 12396
Total               : 11013851.69851401
Total spent < 10%   :  8187409.08623855
Total unspent       :  7407005.82779825
 */

}
