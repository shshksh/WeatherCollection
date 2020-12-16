package com.mob.weathercollection.model.weather;

public class TempPerHour {
    private String temp;
    private String hour;

    public TempPerHour(String temp, int hour) {
        this.temp = temp;
        String mHour = Integer.toString(hour);
        if (mHour.length() < 2) {
            mHour = "0" + mHour;
        }
        this.hour = mHour;
    }

    public TempPerHour(String temp, String hour) {
        this.temp = temp;
        this.hour = hour;
    }

    public TempPerHour(Double temp, int hour) {
        temp = Math.round(temp * 10) / 10.0;
        this.temp = Double.toString(temp);
        String mHour = Integer.toString(hour);
        if (mHour.length() < 2) {
            mHour = "0" + mHour;
        }
        this.hour = mHour;
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
