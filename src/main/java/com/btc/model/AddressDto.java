package com.btc.model;

public class AddressDto {
    String address;
    Double balance;
    Double received;
    Long firstInput;
    Long lastInput;
    Long lastOutput;
    Long dormancy;
    Long age;
    Long toxicity;
    Long uxtoSpent;
    Long uxtoUnSpent;
    Long uxto;
    String meta;

    public AddressDto(){

    }

    public AddressDto(String address, Double balance, Double received) {
        this.address = address;
        this.balance = balance;
        this.received = received;
    }

    public AddressDto(String address, Double balance, Long firstInput, Long lastInput, Long lastOutput, String meta) {
        this.address = address;
        this.balance = balance;
        this.firstInput = firstInput;
        this.lastInput = lastInput;
        this.lastOutput = lastOutput;
        this.meta = meta;
    }

    public AddressDto(String address, Double balance) {
        this.address = address;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return fillWithSpaces(address) + " " + (balance);
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

    public Long getFirstInput() {
        return firstInput;
    }

    public void setFirstInput(Long firstInput) {
        this.firstInput = firstInput;
    }

    public Long getLastInput() {
        return lastInput;
    }

    public void setLastInput(Long lastInput) {
        this.lastInput = lastInput;
    }

    public Long getLastOutput() {
        return lastOutput;
    }

    public void setLastOutput(Long lastOutput) {
        this.lastOutput = lastOutput;
    }

    public Long getDormancy() {
        return dormancy;
    }

    public void setDormancy(Long dormancy) {
        this.dormancy = dormancy;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getToxicity() {
        return toxicity;
    }

    public void setToxicity(Long toxicity) {
        this.toxicity = toxicity;
    }

    public Long getUxtoSpent() {
        return uxtoSpent;
    }

    public void setUxtoSpent(Long uxtoSpent) {
        this.uxtoSpent = uxtoSpent;
    }

    public Long getUxtoUnSpent() {
        return uxtoUnSpent;
    }

    public void setUxtoUnSpent(Long uxtoUnSpent) {
        this.uxtoUnSpent = uxtoUnSpent;
    }

    public Long getUxto() {
        return uxto;
    }

    public void setUxto(Long uxto) {
        this.uxto = uxto;
    }

    private String fillWithSpaces(String address) {
        StringBuilder addressBuilder = new StringBuilder(address);
        if (address.length() < 34) {

            addressBuilder.append(" ".repeat(Math.max(0, 34 - addressBuilder.length())));
        }
        return addressBuilder.toString();
    }
}
