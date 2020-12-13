package com.mob.weathercollection.model.weather.openweather;

public class Hourly {
    public String temp;
    public long dt;

    @Override
    public String toString() {
        return "Hourly{" +
                "temp='" + temp + '\'' +
                ", dt='" + dt + '\'' +
                '}';
    }
}
