package com.mob.weathercollection.model.weather.openweather;

import java.util.List;

public class OpenWeather {
    public Current current;
    public List<Hourly> hourly;

    @Override
    public String toString() {
        return "OpenWeather{" +
                "current=" + current +
                ", hourly=" + hourly +
                '}';
    }
}
