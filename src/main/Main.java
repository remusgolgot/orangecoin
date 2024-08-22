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
Number of addresses : 10333
Total               : 10836956.68863630
Total spent < 10%   :  7945344.13843841
Total unspent       :  7336506.55554295
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       69654.94883602 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5849.75857124 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99053632 burn
1111111111111111111114oLvT2                625.09077054 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.11951209 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35508481 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00649738 Germany
 */
}
