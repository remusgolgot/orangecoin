package com.btc;

import com.btc.processors.AddressProcessor;

public class Main {

    static String s = "1FAjSbgfrhGYkfSeSVYVfYyV8AytMLEHY5";

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
Number of addresses : 10328
Total               : 10883859.92898788
Total spent < 10%   :  8029274.57591052
Total unspent       :  7388297.66247290
 */

/*
1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP       73989.77441020 mr100
32ixEdVJWo3kmvJGMTZq5jAQVZZeuwnqzo        5885.75942385 elSalvador
1CounterpartyXXXXXXXXXXXXXXXUWLpVr        2130.99066346 burn
1111111111111111111114oLvT2                633.90127949 burn
1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa         100.16408702 genesis
1BitcoinEaterAddressDontSendf59kuE          13.35520319 burn
bc1qq0l4jgg9rcm3puhhfwaz4c9t8hdee8hfz6738z   0.00650284 Germany
 */
}
