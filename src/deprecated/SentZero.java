package deprecated;

import client.BlockCypherClient;
import model.Address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

// https://www.blockcypher.com/dev/bitcoin/#derive-address-in-wallet-endpoint
// curl https://api.blockcypher.com/v1/btc/main/addrs/12tkqA9xSoowkzoERHMWNKsTey55YEBqkv/balance
// https://docs.google.com/spreadsheets/d/1AFBEr0iRhyZY1c5p4dxun8XJPnCO3_E2Ddq9ny5xq8k/edit#gid=755240172
public class SentZero {

    static BlockCypherClient blockCypherClient = new BlockCypherClient();
    static long timeout = 700;

    public static void main(String[] args) throws Exception {
        long k = 0;
        int limit = 120;

        System.out.println("Running for " + limit + " addresses ...");
        System.out.println("======================================================");

        URL path = SentZero.class.getResource("test.txt");
        File file = new File(path.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int addrNr = 0;
        double totalBalance = 0;
        String response = "";
        while (addrNr < limit && (st = br.readLine()) != null) {
            addrNr++;
            String addressString = "";
            if (st.startsWith("==")) {
                continue;
            }
            addressString = st;
            try {
                Address address = blockCypherClient.callAddressAPI(addressString, timeout);
                totalBalance += address.getBalance();
                if (address.isSentZero()) {
                    k++;
                }
                System.out.println(address);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(response);
            }
        }
        System.out.println();
        System.out.println(k == limit);
        totalBalance = totalBalance / 100000000;
        System.out.println("TOTAL Balance = " + totalBalance);
    }
}
