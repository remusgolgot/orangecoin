package com.btc.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "price")
public class Price {
    // date,price,market_cap,total_volume,variation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "price")
    private Double price;

    @Column(name = "marketCap")
    private Double marketCap;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "variation")
    private Double variation;

    @Column(name = "chg")
    private Double chg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getVariation() {
        return variation;
    }

    public void setVariation(Double variation) {
        this.variation = variation;
    }

    public Double getChg() {
        return chg;
    }

    public void setChg(Double chg) {
        this.chg = chg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Price(Long id, String date, Double price, Double marketCap, Double volume, Double variation, Double chg) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.marketCap = marketCap;
        this.volume = volume;
        this.variation = variation;
        this.chg = chg;
    }

    @Override
    public String toString() {
        return "Price{" +
                "date='" + date.split(" ")[0] + '\'' +
                ", price=" + price +
                ", variation=" + variation +
                ", chg=" + chg +
                '}';
    }
}