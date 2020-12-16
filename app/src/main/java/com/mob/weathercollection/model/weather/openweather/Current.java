package com.mob.weathercollection.model.weather.openweather;

import java.util.List;

public class Current {
    public Double temp;
    public List<Description> weather;

    @Override
    public String toString() {
        return "Current{" +
                "temp='" + temp + '\'' +
                ", weather=" + weather +
                '}';
    }
}
