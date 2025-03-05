package com.btc.model;

public class Occurrence {

    double priceAtCondition;
    double priceAtTarget;
    String date;

    public Occurrence(double priceAtCondition, double priceAtTarget, String date) {
        this.priceAtCondition = priceAtCondition;
        this.priceAtTarget = priceAtTarget;
        this.date = date;
    }

    public double getPriceAtCondition() {
        return priceAtCondition;
    }

    public void setPriceAtCondition(double priceAtCondition) {
        this.priceAtCondition = priceAtCondition;
    }

    public double getPriceAtTarget() {
        return priceAtTarget;
    }

    public void setPriceAtTarget(double priceAtTarget) {
        this.priceAtTarget = priceAtTarget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
