package com.btc.model;

public class Price {
    String date;
    Double price;
    Double marketCap;
    Double volume;

    public Price(String date, Double price, Double marketCap, Double volume) {
        this.date = date;
        this.price = price;
        this.marketCap = marketCap;
        this.volume = volume;
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
}
