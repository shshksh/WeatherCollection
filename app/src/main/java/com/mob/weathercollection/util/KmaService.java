package com.mob.weathercollection.util;

import com.mob.weathercollection.model.weather.kma.KmaWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KmaService {
    @GET("/wid/queryDFSRSS.jsp")
    Call<KmaWeather> getWeather(@Query("zone") String locationCode);
}
