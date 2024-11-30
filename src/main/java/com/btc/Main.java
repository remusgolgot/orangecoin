package com.btc;

import com.btc.processors.AddressProcessor;
import com.btc.services.CSVExportService;
import com.btc.services.ExportService;

public class Main {

    static String s = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";

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

        long l = 1100000000000000L;
    }

/*
Number of addresses : 11019
Total               : 11149013.69339084
Total spent < 10%   :  8267156.64763620
Total unspent       :  7466129.62508814
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       67899.93999806 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5941.76657924 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99065152 burn
1111111111111111111114oLvT2                649.44693569 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.25663394 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35572589 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00650284 Germany
 */
}
