package com.btc.client;

import com.btc.model.AddressDto;
import com.btc.api.model.Block;
import com.btc.api.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainClient implements BlockClient {

    public AddressDto callAddressAPI(String addressString, long timeout) {
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
                long balance = Long.parseLong(response.split(":")[2].trim().split(",")[0].trim());
                return new AddressDto(addressString, (double) balance);
            } else {
                return null;
            }
        } catch (Exception e) {
            // do nothing
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction callTransactionAPI(String transactionId, long timeout) {
        return null;
    }

    @Override
    public Block callBlockHeightAPI(int height, long timeout) {
        Block block = new Block();
        try {
            Thread.sleep(timeout);
            String command =
                    "curl -X GET https://blockchain.info/block-height/" + height + "?format=json";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            String response = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .map(String::trim)
                    .collect(Collectors.joining("\n"));

            //get hash
            String blockHash = "";
            blockHash = response.split(",")[0].split(":")[2];
            System.out.println(height + " " + blockHash);
            return callBlockAPI(blockHash, timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }

        return block;
    }

    @Override
    public Block callBlockAPI(String blockHash, long timeout) {
        Block block = new Block();

        try {
            String command =
                    "curl -X GET https://blockchain.info/rawblock/" + blockHash + "?format=json";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            String response = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .map(String::trim)
                    .collect(Collectors.joining("\n"));
            ObjectMapper objectMapper = new ObjectMapper();
            block = objectMapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }

        return block;

    }

    public List<AddressDto> callGetBlockAPI(int from, int to, long timeout) {
        List addresses = new ArrayList<AddressDto>();
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
//                    long timestamp = Long.parseLong(trim);

                    addresses.add(new AddressDto(address, 0.0));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }

        return addresses;
    }
}
