package com.mob.weathercollection.util;

import com.mob.weathercollection.model.weather.kakao.Coordinate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface KakaoService {
    @Headers("Authorization: KakaoAK ceaf43679feac454c13bbc041078ddb8")
    @GET("/v2/local/search/address.json")
    Call<Coordinate> getCoordinate(@Query("query") String address);
}
