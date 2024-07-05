package main;

import processors.AddressProcessor;

public class Main {

    static String s = "1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP";

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
    }

/*
Number of addresses : 10729
Total               : 11217724.63364196
Total spent < 10%   :  8270632.07076279
Total unspent       :  7662395.60449751
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP 69036.62664731 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo  5800.7580666 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr  2130.99017004 burn
1111111111111111111114oLvT2          605.3760422 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa   100.05817448 genesis
1BitcoinEaterAddressDontSendf59kuE    13.3548837 burn
 */
}
