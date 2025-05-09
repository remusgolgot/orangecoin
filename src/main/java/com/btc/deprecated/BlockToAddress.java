package com.btc.deprecated;

import com.btc.client.BlockchainClient;
import com.btc.model.AddressDto;
import com.btc.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BlockToAddress {

    static BlockchainClient blockClient = new BlockchainClient();
    static final long timeout = 2200;

    public static void main(String[] args) throws Exception {
        List<AddressDto> addresses = blockClient.callGetBlockAPI(701, 750, timeout);
        for (AddressDto address : addresses) {
            System.out.print(address.getAddress());
            AddressDto result = blockClient.callAddressAPI(address.getAddress(), timeout);

            Long timestamp = address.getLastOutput();
            Date date = new java.util.Date(timestamp * 1000);
            String prettyDate = prettyDate(date);

            Double balance = (double) result.getBalance();
            System.out.println("," + Utils.prettyBalance(balance) + "," + prettyDate);
        }
    }

    private static String prettyDate(Date myDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss a");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(myDate);
    }
}
