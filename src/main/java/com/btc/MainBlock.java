package com.btc;

import com.btc.api.model.*;
import com.btc.client.BlockClient;
import com.btc.client.BlockchainClient;
import com.btc.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainBlock {

    public static void main(String[] args) {

        final Query query = new Query();

        final BlockClient blockClient = new BlockchainClient();
        long timeout = 100;

        for (int i = 99000; i < 100000; i++) {
            System.out.println("processing block at height " + i);
            Block block = blockClient.callBlockHeightAPI(i, timeout);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            CompletableFuture.supplyAsync(() -> {
                query.insertBlock(block);
                return null;
            });
            System.out.println("Found " + block.getTransactionList().size() + " transactions");
            int txNr = 0;
            for (Transaction transaction : block.getTransactionList()) {
                txNr++;
                List<TransactionInput> transactionInputList = transaction.getTransactionInputList();
                List<TransactionOutput> transactionOutputList = transaction.getTransactionOutputList();
                List<String> inputAddresses = new ArrayList<>();
                for (TransactionInput transactionInput : transactionInputList) {
                    PreviousOutput previousOutput = transactionInput.getPreviousOutput();
                    if (previousOutput.getAddress() != null) {
                        inputAddresses.add(previousOutput.getAddress());
                        CompletableFuture.supplyAsync(() -> {
                            query.updateUtxoSpent(previousOutput); // here
                            return null;
                        });
                        query.updateAddressRemove(previousOutput.getAddress(), previousOutput.getValue(), transaction.getTime());
                    }
                }

                for (TransactionOutput transactionOutput : transactionOutputList) {
                    transactionOutput.setTime(transaction.getTime());
                    query.insertUtxo(transactionOutput);
                    String address = transactionOutput.getAddress();
                    if (query.addressExists(address) != null) {
                        if (inputAddresses.contains(address)) {
                            query.updateAddressAdd(address, transactionOutput.getValue(), transaction.getTime(), 0);
                        } else {
                            query.updateAddressAdd(address, transactionOutput.getValue(), transaction.getTime(), transactionOutput.getValue());
                        }
                    } else {
                        query.insertAddress(address, transactionOutput.getValue(), transaction.getTime());
                    }
                }
                System.out.println("processed transaction " + txNr);
            }
            System.out.println("===================");
        }
    }

}
