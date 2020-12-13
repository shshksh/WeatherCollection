package com.mob.weathercollection.model.weather;

import com.mob.weathercollection.model.weather.kma.Data;
import com.mob.weathercollection.model.weather.kma.KmaWeather;
import com.mob.weathercollection.model.weather.openweather.Hourly;
import com.mob.weathercollection.model.weather.openweather.OpenWeather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Weather {
    private String location;
    private String dataSource;
    private String mainTemp;
    private String description;
    private List<TempPerHour> tempList;

    public Weather(String location, String dataSource, String mainTemp, String description, List<TempPerHour> tempList) {
        this.location = location;
        this.dataSource = dataSource;
        this.mainTemp = mainTemp;
        this.description = description;
        this.tempList = tempList;
    }

    public Weather(KmaWeather kmaWeather) {
        this.dataSource = "기상청";
        String[] kmaLocation = kmaWeather.channel.item.category.split("  ");
        this.location = kmaLocation[kmaLocation.length - 1];
        this.mainTemp = kmaWeather.channel.item.description.body.data.get(0).temp;
        this.description = kmaWeather.channel.item.description.body.data.get(0).wfKor;
        this.tempList = data2temp(kmaWeather.channel.item.description.body.data);
    }

    public Weather(OpenWeather openWeather, String location) {
        this.location = location;
        this.dataSource = "OpenWeatherMap";
        this.mainTemp = openWeather.current.temp;
        this.description = openWeather.current.weather.get(0).description;
        this.tempList = hourly2temp(openWeather.hourly);
    }

    private List<TempPerHour> hourly2temp(List<Hourly> hourly) {
        List<TempPerHour> tempList = new ArrayList<>();
        for (Hourly hour : hourly) {
            Date date = new Date(hour.dt * 1000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            tempList.add(new TempPerHour(hour.temp, calendar.get(Calendar.HOUR_OF_DAY)));
        }
        return tempList;
    }

    private List<TempPerHour> data2temp(List<Data> dataList) {
        List<TempPerHour> tempList = new ArrayList<>();
        for (Data data : dataList) {
            tempList.add(new TempPerHour(data.temp, data.hour));
        }
        return tempList;
    }

    public String getLocation() {
        return location;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public String getDescription() {
        return description;
    }

    public List<TempPerHour> getTempList() {
        return tempList;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "location='" + location + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", mainTemp='" + mainTemp + '\'' +
                ", description='" + description + '\'' +
                ", tempList=" + tempList +
                '}';
    }
}
