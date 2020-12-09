package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "item")
    public Item item;

    @Override
    public String toString() {
        return "Channel{" +
                "item=" + item +
                '}';
    }
}
