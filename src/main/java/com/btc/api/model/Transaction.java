package com.btc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {
    @JsonProperty("hash")
    String hash;
    @JsonProperty("index")
    String transactionIndex;
    @JsonProperty("block_height")
    int blockHeight;
    @JsonProperty("time")
    long time;
    @JsonProperty("inputs")
    List<TransactionInput> transactionInputList;
    @JsonProperty("out")
    List<TransactionOutput> transactionOutputList;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public List<TransactionInput> getTransactionInputList() {
        return transactionInputList;
    }

    public void setTransactionInputList(List<TransactionInput> transactionInputList) {
        this.transactionInputList = transactionInputList;
    }

    public List<TransactionOutput> getTransactionOutputList() {
        return transactionOutputList;
    }

    public void setTransactionOutputList(List<TransactionOutput> transactionOutputList) {
        this.transactionOutputList = transactionOutputList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
