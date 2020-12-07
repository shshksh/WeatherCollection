package com.mob.weathercollection;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private Weather kmaWeather;

    public Weather getKmaWeather() {
        if (kmaWeather == null) {
            loadWeather("2644000000");
        }
        return kmaWeather;
    }

    private void loadWeather(String location) {
        KmaService kmaService = RetrofitImpl.getKmaService();

        Call<Weather> weather = kmaService.getWeather(location);
        weather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.d("KMA request", "OnResponse");
                if (response.isSuccessful()) {
                    Log.d("KMA request", "isSuccessful " + response.message());
                    Log.d("KMA request", response.body().toString());
                    kmaWeather = response.body();
                    kmaWeather.src = "기상청";
                    String[] locationParts = kmaWeather.channel.item.category.split(" ");
                    kmaWeather.channel.item.category = locationParts[locationParts.length - 1];
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d("KMA request", t.getMessage());
            }
        });
    }
}
