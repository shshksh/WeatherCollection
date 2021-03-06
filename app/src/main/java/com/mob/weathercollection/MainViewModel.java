package com.mob.weathercollection;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mob.weathercollection.model.weather.TempPerHour;
import com.mob.weathercollection.model.weather.Weather;
import com.mob.weathercollection.model.weather.kakao.Coordinate;
import com.mob.weathercollection.model.weather.kakao.Documents;
import com.mob.weathercollection.model.weather.kma.KmaWeather;
import com.mob.weathercollection.model.weather.openweather.OpenWeather;
import com.mob.weathercollection.util.KakaoService;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.OpenWeatherService;
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
    private MutableLiveData<String> locationName = new MutableLiveData<>();
    private String locationCode;
    private MutableLiveData<Weather> kmaWeather;
    private MutableLiveData<Weather> naverWeather;
    private MutableLiveData<Weather> openWeather;

    public void setLocation(String location) {
        Log.d("setLocation", location);
        String[] parts = location.split("_");
        if (!parts[0].equals("부산광역시")) {
            locationName.setValue("부산 " + parts[0]);
        } else {
            locationName.setValue(parts[0]);
        }
        locationCode = parts[1];
        Log.d("setLocation", locationName.getValue());
    }

    public MutableLiveData<String> getLocationName() {
        return locationName;
    }

    public MutableLiveData<Weather> getKmaWeather() {
        if (kmaWeather == null) {
            kmaWeather = new MutableLiveData<>();
            loadWeatherFromKma(locationCode);
        }
        return kmaWeather;
    }

    public MutableLiveData<Weather> getNaverWeather() {
        if (naverWeather == null) {
            naverWeather = new MutableLiveData<>();
            loadWeatherFromNaver(locationName.getValue());
        }
        return naverWeather;
    }

    public MutableLiveData<Weather> getOpenWeather() {
        if (openWeather == null) {
            openWeather = new MutableLiveData<>();
            loadWeatherFromOpenWeather(locationName.getValue());
        }
        return openWeather;
    }

    public void refreshAllWeather() {
        Log.d("mainViewModel", "refresh all weather");
        loadWeatherFromKma(locationCode);
        loadWeatherFromNaver(locationName.getValue());
        loadWeatherFromOpenWeather(locationName.getValue());
    }

    private void loadWeatherFromOpenWeather(String location) {
        KakaoService kakaoService = RetrofitImpl.getKakaoService();
        Call<Coordinate> coordinateCall = kakaoService.getCoordinate(location);

        OpenWeatherService openWeatherService = RetrofitImpl.getOpenWeatherService();

        coordinateCall.enqueue(new Callback<Coordinate>() {
            @Override
            public void onResponse(Call<Coordinate> call, Response<Coordinate> response) {
                if (response.isSuccessful()) {
                    Coordinate coordinate = response.body();
                    Documents documents = coordinate.documents.get(0);

                    String[] parts = documents.address_name.split(" ");

                    Call<OpenWeather> openWeatherCall =
                            openWeatherService.getOpenWeather(documents.y, documents.x,
                                    "bd334a07508a1d0a9fd754fb6b0aca99");

                    openWeatherCall.enqueue(new Callback<OpenWeather>() {
                        @Override
                        public void onResponse(Call<OpenWeather> call,
                                               Response<OpenWeather> response) {
                            if (response.isSuccessful()) {
                                OpenWeather responseWeather = response.body();
                                Weather weather = new Weather(responseWeather,
                                        parts[parts.length - 1]);
                                getOpenWeather().setValue(weather);
                            }
                        }

                        @Override
                        public void onFailure(Call<OpenWeather> call, Throwable t) {
                            Log.d("open weather map", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Coordinate> call, Throwable t) {
                Log.d("kakao address search", t.getMessage());
            }
        });
    }

    private void loadWeatherFromNaver(String location) {
        String[] tokens = location.split(" ");
        location = tokens[tokens.length - 1];
        String query = tokens[0];
        for (int i = 1; i < tokens.length; i++) {
            query += "+" + tokens[i];
        }

        new AsyncTask<String, Void, Weather>() {
            @Override
            protected Weather doInBackground(String... strings) {
                Log.d("naver query", "doInBackground: " + strings[0] + "+날씨");
                try {
                    Document doc =
                            Jsoup.connect("https://search.naver.com/search.naver?query=" + strings[0] + "+날씨").get();
                    Elements todayArea = doc.select("div.today_area");

                    Elements infoData = todayArea.select("div.main_info, div.info_data");
                    Element todayTemp = infoData.select("p.info_temperature span.todaytemp").get(0);
                    Element castTxt = infoData.select("ul.info_list li p.cast_txt").get(0);

                    Elements items = todayArea.select("div.table_info div.info_list" +
                            ".weather_condition._tabContent ul.list_area li dl");
                    Elements temps = items.select("dd.weather_item span:not(.blind):not(" +
                            ".dot_point)");
                    Elements hours = items.select("dd.item_time span:not(.tomorrow):not(" +
                            ".division_line):not(.tomorrow_icon):not(.blind):not(.more_bytime)" +
                            ":not(.ico)");

                    String description = castTxt.text();
                    description = description.split(", ")[0];

                    List<TempPerHour> tempPerHourList = new ArrayList<>();
                    Log.d("query", "doInBackground: test");
                    for (int i = 0; i < temps.size(); i++) {
                        tempPerHourList.add(new TempPerHour(temps.get(i).text() + ".0",
                                hours.get(i).text().substring(0, 2)));
                        Log.d("query", "doInBackground: " + tempPerHourList.get(i).toString());
                    }
                    Weather naverWeather = new Weather(strings[1], "네이버", todayTemp.text() + ".0",
                            description, tempPerHourList);

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
        }.execute(query, location);
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
