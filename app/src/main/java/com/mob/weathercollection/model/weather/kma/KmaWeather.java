package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class KmaWeather {
    @Element(name = "channel")
    public Channel channel;

    @Override
    public String toString() {
        return "Weather{" +
                "channel=" + channel +
                '}';
    }
}

