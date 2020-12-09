package com.mob.weathercollection;

import com.mob.weathercollection.model.weather.TempPerHour;
import com.mob.weathercollection.model.weather.Weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert.*;

public class CrawlingTest {
    @Test
    public void jsoupTest() throws IOException {
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?query=연산+6동+날씨").get();
        Elements contents = doc.select("div.today_area div.main_info div.info_data");

        for (Element content : contents) {
            System.out.println(content.text());
        }
    }

    @Test
    public void getTodayTemp() throws IOException {
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?query=연산+6동+날씨").get();
        Elements todayArea = doc.select("div.today_area");
        Elements mainInfo = todayArea.select("div.main_info");
        Element todayTemp = mainInfo.select("div.info_data p.info_temperature span.todaytemp").get(0);

        System.out.println(todayTemp.text());
    }

    @Test
    public void getTempsPerHour() throws IOException {
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?query=연산+6동+날씨").get();
        Elements infoList = doc.select("div.today_area div.table_info div.info_list.weather_condition._tabContent");
        Elements items = infoList.select("ul.list_area li dl");
        Elements temps = items.select("dd.weather_item span:not(.blind):not(.dot_point)");
        Elements hours = items.select("dd.item_time span:not(.tomorrow):not(.division_line):not(.tomorrow_icon):not(.blind)");

        for (int i = 0; i < hours.size(); i++) {
            System.out.println(hours.get(i) + " " + temps.get(i));
        }
    }

    @Test
    public void data2weather() throws IOException {
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?query=연산+6동+날씨").get();
        Elements todayArea = doc.select("div.today_area");

        Elements infoData = todayArea.select("div.main_info, div.info_data");
        Element todayTemp = infoData.select("p.info_temperature span.todaytemp").get(0);
        Element castTxt = infoData.select("ul.info_list li p.cast_txt").get(0);

        Elements items = todayArea.select("div.table_info div.info_list.weather_condition._tabContent ul.list_area li dl");
        Elements temps = items.select("dd.weather_item span:not(.blind):not(.dot_point)");
        Elements hours = items.select("dd.item_time span:not(.tomorrow):not(.division_line):not(.tomorrow_icon):not(.blind)");

        List<TempPerHour> tempPerHourList = new ArrayList<>();
        for (int i = 0; i < hours.size(); i++) {
            tempPerHourList.add(new TempPerHour(hours.get(i).text(), temps.get(i).text()));
        }
        Weather weather = new Weather("연산 6동", "네이버 날씨", todayTemp.text(), castTxt.text(), tempPerHourList);

        Assert.assertEquals(weather.getMainTemp(), todayTemp.text());
    }
}
