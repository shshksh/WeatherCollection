package com.mob.weathercollection.model.weather.openweather;

public class Hourly {
    public Double temp;
    public long dt;

    @Override
    public String toString() {
        return "Hourly{" +
                "temp='" + temp + '\'' +
                ", dt='" + dt + '\'' +
                '}';
    }
}
