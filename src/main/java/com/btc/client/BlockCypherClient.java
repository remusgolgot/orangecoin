package com.btc.client;

import com.btc.model.AddressDto;
import com.btc.api.model.Block;
import com.btc.api.model.Transaction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BlockCypherClient implements BlockClient{

    public AddressDto callAddressAPI(String addressString, long timeout) {
        try {
            Thread.sleep(timeout);
            String command =
                    "curl -X GET https://api.blockcypher.com/v1/btc/main/addrs/" + addressString + "/balance";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            String response = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .filter(line -> line.contains(addressString) || line.contains("\"balance") || line.contains("\"total_sent"))
                    .map(String::trim)
                    .collect(Collectors.joining("\n"));

            long balance = Long.parseLong(response.split(",")[2].trim().split(" ")[1].trim());
//            boolean sentZero = response.split(",")[1].trim().split(":")[1].trim().equalsIgnoreCase("0");
            return new AddressDto(addressString, (double) balance);
        } catch (Exception e) {
            // do nothing
        }
        return null;
    }

    @Override
    public Transaction callTransactionAPI(String transactionId, long timeout) {
        return null;
    }

    @Override
    public Block callBlockHeightAPI(int height, long timeout) {
        return null;
    }

    @Override
    public Block callBlockAPI(String blockHash, long timeout) {
        return null;
    }
}
