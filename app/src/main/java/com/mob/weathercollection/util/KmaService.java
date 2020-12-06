package com.mob.weathercollection.util;

import com.mob.weathercollection.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KmaService {
    @GET("/wid/queryDFSRSS.jsp")
    Call<Weather> getWeather(@Query("zone") String locationCode);
}
