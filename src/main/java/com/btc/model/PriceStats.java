package com.btc.model;

public class PriceStats {
    Double averagePrice;
    Double medianPrice;
    Integer upDays;
    Integer downDays;
    String biggestDecrease;
    String biggestIncrease;

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getMedianPrice() {
        return medianPrice;
    }

    public void setMedianPrice(Double medianPrice) {
        this.medianPrice = medianPrice;
    }

    public Integer getUpDays() {
        return upDays;
    }

    public void setUpDays(Integer upDays) {
        this.upDays = upDays;
    }

    public Integer getDownDays() {
        return downDays;
    }

    public void setDownDays(Integer downDays) {
        this.downDays = downDays;
    }

    public String getBiggestDecrease() {
        return biggestDecrease;
    }

    public void setBiggestDecrease(String biggestDecrease) {
        this.biggestDecrease = biggestDecrease;
    }

    public String getBiggestIncrease() {
        return biggestIncrease;
    }

    public void setBiggestIncrease(String biggestIncrease) {
        this.biggestIncrease = biggestIncrease;
    }
}
