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

    @Column(name = "lastUpdate")
    private Long lastUpdate;
}