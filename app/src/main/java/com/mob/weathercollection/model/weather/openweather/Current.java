package com.mob.weathercollection.model.weather.openweather;

import java.util.List;

public class Current {
    public String temp;
    public List<Description> weather;

    @Override
    public String toString() {
        return "Current{" +
                "temp='" + temp + '\'' +
                ", weather=" + weather +
                '}';
    }
}
