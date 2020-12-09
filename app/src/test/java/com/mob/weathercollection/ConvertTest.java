package com.mob.weathercollection;

import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.model.weather.kma.KmaWeather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

public class ConvertTest {
    @Test
    public void kma2weather() throws IOException {
        KmaService kmaService = RetrofitImpl.getKmaService();

        Call<KmaWeather> kmaCall = kmaService.getWeather("2644000000");
        Response<KmaWeather> response = kmaCall.execute();

        KmaWeather kmaWeather = response.body();
        String[] locationParts = kmaWeather.channel.item.category.split(" ");
        kmaWeather.channel.item.category = locationParts[locationParts.length - 1];

        Weather weather = new Weather(kmaWeather);

        assertEquals(weather.getLocation(), kmaWeather.channel.item.category);
    }
}
