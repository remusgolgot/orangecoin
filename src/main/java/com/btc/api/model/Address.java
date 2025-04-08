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
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "received")
    private Double received;

    @Column(name = "meta")
    private String meta;

    @Column(name = "lastOutput")
    private Long lastOutput;

    @Column(name = "lastInput")
    private Long lastInput;

    @Column(name = "firstInput")
    private Long firstInput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getLastOutput() {
        return lastOutput;
    }

    public void setLastOutput(Long lastOutput) {
        this.lastOutput = lastOutput;
    }

    public Long getLastInput() {
        return lastInput;
    }

    public void setLastInput(Long lastInput) {
        this.lastInput = lastInput;
    }

    public Long getFirstInput() {
        return firstInput;
    }

    public void setFirstInput(Long firstInput) {
        this.firstInput = firstInput;
    }
}