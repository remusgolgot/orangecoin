package com.btc;

import com.btc.processors.AddressProcessor;

public class Main {

    static String s = "1P1iFLSHSJj3SgGGMeX76BJyxHDB8xg4P";

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
Number of addresses : 10537
Total               : 10905879.90340798
Total spent < 10%   :  7995039.55146489
Total unspent       :  7376308.83724454
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       73513.14094857 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5863.75939614 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99061822 burn
1111111111111111111114oLvT2                629.47806566 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.13192275 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35520865 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00649738 Germany
 */
}
