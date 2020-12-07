package com.mob.weathercollection.util;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitImpl {
    private static Retrofit kmaRetrofit = new Retrofit.Builder()
            .baseUrl("http://www.kma.go.kr")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public static KmaService getKmaService() {
        return kmaRetrofit.create(KmaService.class);
    }
}