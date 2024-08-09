package main;

import processors.AddressProcessor;

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
Number of addresses : 10336
Total               : 10862914.01828942
Total spent < 10%   :  7971046.97635041
Total unspent       :  7363417.49408094
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       67416.99698329 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5836.75856032 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99051994 burn
1111111111111111111114oLvT2                620.04797254 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.10995416 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35504659 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00649738 Germany
 */
}
