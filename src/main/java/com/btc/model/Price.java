package com.btc.model;

public class Price {
    String date;
    Double price;
    Double marketCap;
    Double volume;
    Double variation;
    Double chg;

    public Price(String date, Double price, Double marketCap, Double volume, Double variation, Double chg) {
        this.date = date;
        this.price = price;
        this.marketCap = marketCap;
        this.volume = volume;
        this.variation = variation;
        this.chg = chg;
    }

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
}
