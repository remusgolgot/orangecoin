package com.btc.model;

public class SystemResult {

    private int nrOccurrences;
    private int upOccurrences;
    private int downOccurrences;
    private int timespan;
    private double averageUp;
    private double averageDown;
    private double overall;
    private double roi;

    public int getNrOccurrences() {
        return nrOccurrences;
    }

    public void setNrOccurrences(int nrOccurrences) {
        this.nrOccurrences = nrOccurrences;
    }

    public int getTimespan() {
        return timespan;
    }

    public void setTimespan(int timespan) {
        this.timespan = timespan;
    }

    public int getUpOccurrences() {
        return upOccurrences;
    }

    public void setUpOccurrences(int upOccurrences) {
        this.upOccurrences = upOccurrences;
    }

    public int getDownOccurrences() {
        return downOccurrences;
    }

    public void setDownOccurrences(int downOccurrences) {
        this.downOccurrences = downOccurrences;
    }

    public double getAverageUp() {
        return averageUp;
    }

    public void setAverageUp(double averageUp) {
        this.averageUp = averageUp;
    }

    public double getAverageDown() {
        return averageDown;
    }

    public void setAverageDown(double averageDown) {
        this.averageDown = averageDown;
    }

    public double getOverall() {
        return overall;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }
}
