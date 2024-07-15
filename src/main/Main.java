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
    }

/*
Number of addresses : 10631
Total               : 11152883.27853306
Total spent < 10%   :  8234364.29705249
Total unspent       :  7590922.52993899
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       71587.55503931 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5811.75842701 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99018642 burn
1111111111111111111114oLvT2                608.81575220 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.09689797 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35488916 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00612498 Germany
 */
}
