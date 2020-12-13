package com.mob.weathercollection;

import com.mob.weathercollection.model.weather.kakao.Coordinate;
import com.mob.weathercollection.util.KakaoService;
import com.mob.weathercollection.util.RetrofitImpl;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class KakaoAddressSearchTest {

    @Test
    public void getCoordinate() throws InterruptedException, IOException {
        KakaoService kakaoService = RetrofitImpl.getKakaoService();
        Call<Coordinate> coordinateCall = kakaoService.getCoordinate("연산6동");

        Response<Coordinate> response = coordinateCall.execute();
        Coordinate coordinate = response.body();

        System.out.println(coordinate.documents);

        Thread.sleep(5000);
    }
}
