package deprecated;

import client.BlockClient;
import client.BlockchainClient;
import model.Address;
import utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class Checker {
    public static final String DELIMITER = ",";
    static BlockClient blockClient = new BlockchainClient();
    static final long timeout = 2200;

    static final long offset = 2280;
    static final int count = 100;

    static final boolean random = true;
    static final int MAX_STEP_SIZE = 23;

    static int diffs = 0;
    static int problems = 0;

    public static void main(String[] args) throws Exception {
        URL path = Checker.class.getResource("addresses.txt");
        File file = new File(path.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String response = "";
        for (int i = 0; i < offset; i++) {
            br.readLine();
        }
        int c = 0;
        while (c < count && (st = br.readLine()) != null) {
            String addressString = "";
            if (st.startsWith("==")) {
                continue;
            }
            c++;
            if (st.contains(DELIMITER)) {
                addressString = st.split(DELIMITER)[0];
                System.out.print("Checking " + addressString);
                try {
                    Address address = blockClient.callAddressAPI(addressString, timeout);
                    if (address != null && !address.isSentZero()) {
                        problems++;
                        System.out.print(" PROBLEM !!! ");
                    }
                    Double balance = address.getBalance();
                    double a = Double.parseDouble(st.split(DELIMITER)[1]) * 100000000;
                    if ((Math.abs(Math.round(balance) - Math.round(a))) > 0) {
                        diffs++;
                        System.out.print(" DIFF !!! " + Utils.prettyBalance(balance) + " " + Utils.prettyBalance(a));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(response);
                }
            }
            System.out.println();

            if (random) {
                // move fw k steps
                long steps = Math.round(MAX_STEP_SIZE * Math.random());
                int k = 0;
                while (k < steps) {
                    br.readLine();
                    k++;
                }
            }
        }
        System.out.println("=====================");
        System.out.println("DIFFS " + diffs);
        System.out.println("PROBLEMS " + problems);

    }
}
