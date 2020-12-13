package com.mob.weathercollection.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitImpl {
    private static Retrofit kmaRetrofit = new Retrofit.Builder()
            .baseUrl("http://www.kma.go.kr")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    private static Retrofit kakaoRetrofit = new Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static Retrofit openWeatherService = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static KmaService getKmaService() {
        return kmaRetrofit.create(KmaService.class);
    }

    public static KakaoService getKakaoService() {
        return kakaoRetrofit.create(KakaoService.class);
    }

    public static OpenWeatherService getOpenWeatherService() {
        return openWeatherService.create(OpenWeatherService.class);
    }
}