package com.btc;

import com.btc.processors.AddressProcessor;

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

        long l = 1100000000000000L;
    }

/*
Number of addresses : 11098
Total               : 11173397.88676329
Total spent < 10%   :  8346652.27559443
Total unspent       :  7513456.93413989
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       71889.94554327 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5920.76647314 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99064552 burn
1111111111111111111114oLvT2                644.43046520 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.18877359 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35544881 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00650284 Germany
 */
}
