package com.btc.model;

public class Address {
    String address;
    Double balance;
    Double received;
    boolean sentZero;
    long lastUpdate;
    String meta;

    public Address(String address, Double balance, boolean sentZero) {
        this.address = address;
        this.balance = balance;
        this.sentZero = sentZero;
    }

    public Address(String address, Double balance, Double received) {
        this.address = address;
        this.balance = balance;
        this.received = received;
    }

    public Address(String address, Double balance, Double received, long lastUpdate, String meta) {
        this.address = address;
        this.balance = balance;
        this.received = received;
        this.lastUpdate = lastUpdate;
        this.meta = meta;
    }

    public Address(String address, Double balance, long time) {
        this.address = address;
        this.balance = balance;
        this.lastUpdate = time;
    }

    @Override
    public String toString() {
        return fillWithSpaces(address) + " " + (balance / 100000000) + (!sentZero ? " !!!!!!!!!!!!!! " : "");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean isSentZero() {
        if (received == null) {
            return sentZero;
        } else {
            return received.equals(balance);
        }
    }

    public void setSentZero(boolean sentZero) {
        this.sentZero = sentZero;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    private String fillWithSpaces(String address) {
        StringBuilder addressBuilder = new StringBuilder(address);
        if (address.length() < 34) {

            addressBuilder.append(" ".repeat(Math.max(0, 34 - addressBuilder.length())));
        }
        return addressBuilder.toString();
    }
}
