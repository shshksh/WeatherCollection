package com.mob.weathercollection;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mob.weathercollection.model.weather.TempPerHour;
import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.model.weather.kma.KmaWeather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Weather> kmaWeather;
    private MutableLiveData<Weather> naverWeather;

    public MutableLiveData<Weather> getKmaWeather() {
        if (kmaWeather == null) {
            kmaWeather = new MutableLiveData<>();
            loadWeatherFromKma("2644000000");
        }
        return kmaWeather;
    }

    public MutableLiveData<Weather> getNaverWeather() {
        if (naverWeather == null) {
            naverWeather = new MutableLiveData<>();
            loadWeatherFromNaver("부산 강서구");
        }
        return naverWeather;
    }

    private void loadWeatherFromNaver(String location) {
        String[] tokens = location.split(" ");
        String query = tokens[0];
        for (int i = 1; i < tokens.length; i++) {
            query += "+" + tokens[i];
        }

        new AsyncTask<String, Void, Weather>() {
            @Override
            protected Weather doInBackground(String... strings) {
                Log.d("naver query", "doInBackground: " + strings[0] + "+날씨");
                try {
                    Document doc = Jsoup.connect("https://search.naver.com/search.naver?query=" + strings[0] + "+날씨").get();
                    Elements todayArea = doc.select("div.today_area");

                    Elements infoData = todayArea.select("div.main_info, div.info_data");
                    Element todayTemp = infoData.select("p.info_temperature span.todaytemp").get(0);
                    Element castTxt = infoData.select("ul.info_list li p.cast_txt").get(0);

                    Elements items = todayArea.select("div.table_info div.info_list.weather_condition._tabContent ul.list_area li dl");
                    Elements temps = items.select("dd.weather_item span:not(.blind):not(.dot_point)");
                    Elements hours = items.select("dd.item_time span:not(.tomorrow):not(.division_line):not(.tomorrow_icon):not(.blind):not(.more_bytime):not(.ico)");

                    String description = castTxt.text();
                    description = description.split(", ")[0];

                    List<TempPerHour> tempPerHourList = new ArrayList<>();
                    Log.d("query", "doInBackground: test");
                    for (int i = 0; i < temps.size(); i++) {
                        tempPerHourList.add(new TempPerHour(temps.get(i).text(), hours.get(i).text()));
                        Log.d("query", "doInBackground: " + tempPerHourList.get(i).toString());
                    }
                    Weather naverWeather = new Weather(strings[0], "네이버", todayTemp.text(), description, tempPerHourList);

                    return naverWeather;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Weather weather) {
                super.onPostExecute(weather);
                naverWeather.setValue(weather);
            }
        }.execute(query);
    }

    public void loadWeatherFromKma(String location) {
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
                    getKmaWeather().setValue(new Weather(kmaWeather));
                }
            }

            @Override
            public void onFailure(Call<KmaWeather> call, Throwable t) {
                Log.d("KMA request", t.getMessage());
            }
        });
    }
}
