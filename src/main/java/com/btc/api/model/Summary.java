package com.btc.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "summary")
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nrAddresses")
    private Long nrAddresses;

    @Column(name = "nonEmptyAddresses")
    private Long nonEmptyAddresses;

    @Column(name = "totalBalance")
    private Double totalBalance;

    @Column(name = "spentZeroBalance")
    private Double spentZeroBalance;

    @Column(name = "lowSpentBalance")
    private Double lowSpentBalance;

    @Column(name = "unspendable")
    private Double unspendable;

    @Column(name = "nrUtxos")
    private Long nrUtxos;

    @Column(name = "nrUnspentUtxos")
    private Long nrUnspentUtxos;

    @Column(name = "utxoTotal")
    private Long utxoTotal;

    @Column(name = "maxHeight")
    private Integer maxHeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNrAddresses() {
        return nrAddresses;
    }

    public void setNrAddresses(Long nrAddresses) {
        this.nrAddresses = nrAddresses;
    }

    public Long getNonEmptyAddresses() {
        return nonEmptyAddresses;
    }

    public void setNonEmptyAddresses(Long nonEmptyAddresses) {
        this.nonEmptyAddresses = nonEmptyAddresses;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getSpentZeroBalance() {
        return spentZeroBalance;
    }

    public void setSpentZeroBalance(Double spentZeroBalance) {
        this.spentZeroBalance = spentZeroBalance;
    }

    public Double getLowSpentBalance() {
        return lowSpentBalance;
    }

    public void setLowSpentBalance(Double lowSpentBalance) {
        this.lowSpentBalance = lowSpentBalance;
    }

    public Double getUnspendable() {
        return unspendable;
    }

    public void setUnspendable(Double unspendable) {
        this.unspendable = unspendable;
    }

    public Long getNrUtxos() {
        return nrUtxos;
    }

    public void setNrUtxos(Long nrUtxos) {
        this.nrUtxos = nrUtxos;
    }

    public Long getNrUnspentUtxos() {
        return nrUnspentUtxos;
    }

    public void setNrUnspentUtxos(Long nrUnspentUtxos) {
        this.nrUnspentUtxos = nrUnspentUtxos;
    }

    public Long getUtxoTotal() {
        return utxoTotal;
    }

    public void setUtxoTotal(Long utxoTotal) {
        this.utxoTotal = utxoTotal;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }
}