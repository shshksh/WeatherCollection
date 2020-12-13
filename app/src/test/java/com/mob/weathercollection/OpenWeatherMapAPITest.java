package com.mob.weathercollection;

import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.model.weather.kakao.Coordinate;
import com.mob.weathercollection.model.weather.kakao.Documents;
import com.mob.weathercollection.model.weather.openweather.OpenWeather;
import com.mob.weathercollection.util.KakaoService;
import com.mob.weathercollection.util.OpenWeatherService;
import com.mob.weathercollection.util.RetrofitImpl;

import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class OpenWeatherMapAPITest {
    @Test
    public void testOneCallApi() throws IOException, InterruptedException {
        OpenWeatherService openWeatherService = RetrofitImpl.getOpenWeatherService();
        Call<OpenWeather> openWeatherCall = openWeatherService.getOpenWeather("35.1775025378104", "129.089533955455", "bd334a07508a1d0a9fd754fb6b0aca99");

        Response<OpenWeather> response = openWeatherCall.execute();
        OpenWeather openWeather = response.body();

        System.out.println(openWeather);

        Thread.sleep(5000);
    }

    @Test
    public void linkToKakaoSearch() throws IOException, InterruptedException {
        KakaoService kakaoService = RetrofitImpl.getKakaoService();
        Call<Coordinate> coordinateCall = kakaoService.getCoordinate("부산 강서구");

        Response<Coordinate> kakaoResponse = coordinateCall.execute();
        Coordinate coordinate = kakaoResponse.body();
        System.out.println(coordinate.documents);

        Documents document = coordinate.documents.get(0);

        OpenWeatherService openWeatherService = RetrofitImpl.getOpenWeatherService();
        Call<OpenWeather> openWeatherCall = openWeatherService.getOpenWeather(document.y, document.x, "bd334a07508a1d0a9fd754fb6b0aca99");

        Response<OpenWeather> response = openWeatherCall.execute();
        OpenWeather openWeather = response.body();

        System.out.println(openWeather);

        String[] location = document.address_name.split(" ");

        Weather weather = new Weather(openWeather, location[location.length - 1]);

        System.out.println(weather);

        Thread.sleep(5000);
    }

    @Test
    public void convertTimeTest() {
        Date date = new Date((long) (1607864400) * 1000);
        Date date2 = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(date);
        calendar2.setTime(date2);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar2.get(Calendar.YEAR));

        System.out.println(calendar.get(Calendar.MONDAY));
        System.out.println(calendar2.get(Calendar.MONDAY));

        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar2.get(Calendar.DATE));

        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar2.get(Calendar.HOUR_OF_DAY));
    }
}
