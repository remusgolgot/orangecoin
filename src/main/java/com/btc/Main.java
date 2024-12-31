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
Number of addresses : 11439
Total               : 10474041.23659493
Total spent < 10%   :  7653753.89794245
Total unspent       :  6885981.45643243
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       58219.41152847 mr100
3D6kjgsypiAnGY7E1XHDQXapW4whUWaH1H       13797.77035568 Mara
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        6001.76660951 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99069234 burn
1111111111111111111114oLvT2                663.37917235 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.30271131 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35600303 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00650284 Germany
 */
}
