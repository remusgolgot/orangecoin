package com.btc.model;

import java.util.Date;

public class SystemInput {

    private int streak;
    private Double min; // can be 0
    private Double max; // can be 0
    private int timespan;
    private int target;
    private Date from;
    private Date to;
    private String mode;

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public int getTimespan() {
        return timespan;
    }

    public void setTimespan(int timespan) {
        this.timespan = timespan;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
