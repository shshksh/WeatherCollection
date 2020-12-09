package com.mob.weathercollection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

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
}
