package com.mob.weathercollection.model.weather;

public class TempPerHour {
    private String temp;
    private String hour;

    public TempPerHour(String temp, int hour) {
        this.temp = temp;
        this.hour = Integer.toString(hour);
    }

    public TempPerHour(String temp, String hour) {
        this.temp = temp;
        this.hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public String getHour() {
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
