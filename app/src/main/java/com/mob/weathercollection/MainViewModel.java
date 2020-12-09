package com.mob.weathercollection;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mob.weathercollection.model.weather.kma.KmaWeather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<KmaWeather> kmaWeather;

    public MutableLiveData<KmaWeather> getKmaWeather() {
        if (kmaWeather == null) {
            kmaWeather = new MutableLiveData<KmaWeather>();
            loadWeather("2644000000");
        }
        return kmaWeather;
    }

    public void loadWeather(String location) {
        KmaService kmaService = RetrofitImpl.getKmaService();

        Call<KmaWeather> weather = kmaService.getWeather(location);
        weather.enqueue(new Callback<KmaWeather>() {
            @Override
            public void onResponse(Call<KmaWeather> call, Response<KmaWeather> response) {
                Log.d("KMA request", "OnResponse");
                if (response.isSuccessful()) {
                    Log.d("KMA request", "isSuccessful " + response.message());
                    Log.d("KMA request", response.body().toString());
                    KmaWeather kmaWeather = response.body();
                    String[] locationParts = kmaWeather.channel.item.category.split(" ");
                    kmaWeather.channel.item.category = locationParts[locationParts.length - 1];
                    MainViewModel.this.kmaWeather.setValue(kmaWeather);
                }
            }

            @Override
            public void onFailure(Call<KmaWeather> call, Throwable t) {
                Log.d("KMA request", t.getMessage());
            }
        });
    }
}
