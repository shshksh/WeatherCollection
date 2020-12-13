package com.mob.weathercollection.util;

import com.mob.weathercollection.model.weather.openweather.OpenWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("/data/2.5/onecall?units=metric&lang=kr&exclude=minutely,daily,alerts")
    Call<OpenWeather> getOpenWeather(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String key);
}
