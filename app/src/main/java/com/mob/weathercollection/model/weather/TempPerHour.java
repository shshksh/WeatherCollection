package com.mob.weathercollection.model.weather;

public class TempPerHour {
    private String temp;
    private int hour;

    public TempPerHour(String temp, int hour) {
        this.temp = temp;
        this.hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return "TempPerHour{" +
                "temp='" + temp + '\'' +
                ", hour=" + hour +
                '}';
    }
}
