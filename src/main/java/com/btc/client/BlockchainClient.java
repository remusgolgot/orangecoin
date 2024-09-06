package com.btc.client;

import com.btc.model.Address;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainClient implements BlockClient {

    public Address callAddressAPI(String addressString, long timeout) {
        String response = "";
        try {
            Thread.sleep(timeout);
            String command =
                    "curl -X GET https://blockchain.info/balance?active=" + addressString;
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();

            response = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .filter(line -> line.contains(addressString) || line.contains("\"balance") || line.contains("\"total_sent"))
                    .map(String::trim)
                    .collect(Collectors.joining("\n"));

            if (response.length() > 0) {
                double balance = Long.parseLong(response.split(":")[2].trim().split(",")[0].trim());
                double received = Long.parseLong(response.split(",")[2].trim().split(":")[1].trim().split("}")[0]);
                return new Address(addressString, balance, received);
            } else {
                return null;
            }
        } catch (Exception e) {
            // do nothing
            e.printStackTrace();
            System.out.println("here");
        }
        return null;
    }

    public List<Address> callGetBlockAPI(int from, int to, long timeout) {
        List addresses = new ArrayList<Address>();
        try {
            Thread.sleep(timeout);
            for (int i = from; i <= to; i++) {

                String command =
                        "curl -X GET https://blockchain.info/block-height/" + i + "?format=json";
                Process process = Runtime.getRuntime().exec(command);
                InputStream inputStream = process.getInputStream();
                String response = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .filter(line -> line.contains("\"n_tx\":1"))
                        .map(String::trim)
                        .collect(Collectors.joining("\n"));
                if (response.equals("")) {
                    System.out.println("Multiple transactions at height " + i);
                } else {
                    String address = response.split("\"addr\":\"")[1].split("\"")[0].trim();
                    String trim = response.split("\"time\":")[1].split(",")[0].trim();
                    long timestamp = Long.parseLong(trim);

                    addresses.add(new Address(address, 0.0, timestamp));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }

        return addresses;
    }
}
