package com.btc.model;

public class SummaryDto {
    long totalAddresses;
    long nonEmptyAddresses;
    double totalBalance;
    double spentZeroBalance;
    double lowSpentBalance;
    double unspendable;
    long nrUtxos;
    long nrUnspentUtxos;
    long utxoTotal;
    int maxHeight;

    public SummaryDto(){

    }

    public long getTotalAddresses() {
        return totalAddresses;
    }

    public void setTotalAddresses(long totalAddresses) {
        this.totalAddresses = totalAddresses;
    }

    public long getNonEmptyAddresses() {
        return nonEmptyAddresses;
    }

    public void setNonEmptyAddresses(long nonEmptyAddresses) {
        this.nonEmptyAddresses = nonEmptyAddresses;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getSpentZeroBalance() {
        return spentZeroBalance;
    }

    public void setSpentZeroBalance(double spentZeroBalance) {
        this.spentZeroBalance = spentZeroBalance;
    }

    public double getLowSpentBalance() {
        return lowSpentBalance;
    }

    public void setLowSpentBalance(double lowSpentBalance) {
        this.lowSpentBalance = lowSpentBalance;
    }

    public double getUnspendable() {
        return unspendable;
    }

    public void setUnspendable(double unspendable) {
        this.unspendable = unspendable;
    }

    public long getNrUtxos() {
        return nrUtxos;
    }

    public void setNrUtxos(long nrUtxos) {
        this.nrUtxos = nrUtxos;
    }

    public long getNrUnspentUtxos() {
        return nrUnspentUtxos;
    }

    public void setNrUnspentUtxos(long nrUnspentUtxos) {
        this.nrUnspentUtxos = nrUnspentUtxos;
    }

    public long getUtxoTotal() {
        return utxoTotal;
    }

    public void setUtxoTotal(long utxoTotal) {
        this.utxoTotal = utxoTotal;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
