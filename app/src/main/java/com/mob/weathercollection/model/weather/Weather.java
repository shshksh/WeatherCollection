package com.mob.weathercollection.model.weather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Weather {

    public String src;

    @Element(name = "channel")
    public Channel channel;

    @Override
    public String toString() {
        return "Weather{" +
                "channel=" + channel +
                '}';
    }
}

