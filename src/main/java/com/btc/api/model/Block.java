package com.btc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "block")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "hash")
   @JsonProperty("hash")
   String hash;
   @Column(name = "prevBlock")
   @JsonProperty("prev_block")
   String prevBlock;
   @Column(name = "nrTx")
   @JsonProperty("n_tx")
   int nrTx;
   @Column(name = "height")
   @JsonProperty("height")
   int height;
   @JsonProperty("tx")
   List<Transaction> transactionList;

   public Block() {
   }

   public Block(String hash, String prevBlock, int nrTx, int height, List<Transaction> transactionList) {
      this.hash = hash;
      this.prevBlock = prevBlock;
      this.nrTx = nrTx;
      this.height = height;
      this.transactionList = transactionList;
   }

   public String getHash() {
      return hash;
   }

   public void setHash(String hash) {
      this.hash = hash;
   }

   public String getPrevBlock() {
      return prevBlock;
   }

   public void setPrevBlock(String prevBlock) {
      this.prevBlock = prevBlock;
   }

   public int getNrTx() {
      return nrTx;
   }

   public void setNrTx(int nrTx) {
      this.nrTx = nrTx;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public List<Transaction> getTransactionList() {
      return transactionList;
   }

   public void setTransactionList(List<Transaction> transactionList) {
      this.transactionList = transactionList;
   }
}
