package com.mob.weathercollection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mob.weathercollection.databinding.ActivityMainBinding;
import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        KmaService kmaService = RetrofitImpl.getKmaService();

        Call<Weather> weather = kmaService.getWeather("2647070000");
        weather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.d("KMA request", "OnResponse");
                if (response.isSuccessful()) {
                    Log.d("KMA request", "isSuccessful " + response.message());
                    Log.d("KMA request", response.body().toString());
                    Weather kmaWeather = response.body();
                    kmaWeather.src = "기상청";
                    kmaWeather.channel.item.category = kmaWeather.channel.item.category.split(" ")[2];
                    binding.setWeather(kmaWeather);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d("KMA request", t.getMessage());
            }
        });

        LinearLayout llItemMain = binding.layoutKma.llItammainTemperatureperhour;
        View itemTemp = getLayoutInflater().inflate(R.layout.item_tempperhours, llItemMain, false);
        llItemMain.addView(itemTemp);
    }
}