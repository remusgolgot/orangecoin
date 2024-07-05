package deprecated;

import client.BlockchainClient;
import model.Address;
import utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BlockToAddress {

    static BlockchainClient blockClient = new BlockchainClient();
    static final long timeout = 2200;

    public static void main(String[] args) throws Exception {
        List<Address> addresses = blockClient.callGetBlockAPI(701, 750, timeout);
        for (Address address : addresses) {
            System.out.print(address.getAddress());
            Address result = blockClient.callAddressAPI(address.getAddress(), timeout);

            Long timestamp = address.getLastUpdate();
            Date date = new java.util.Date(timestamp * 1000);
            String prettyDate = prettyDate(date);

            Double balance = result.getBalance();
            System.out.println("," + Utils.prettyBalance(balance) + "," + prettyDate);
        }
    }

    private static String prettyDate(Date myDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss a");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(myDate);
    }
}
