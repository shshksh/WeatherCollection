package com.mob.weathercollection.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class Weather {

    @Element(name = "channel")
    public Channel channel;

    @Override
    public String toString() {
        return "Weather{" +
                "channel=" + channel +
                '}';
    }
}

@Root(name = "channel", strict = false)
class Channel {

    @Element(name = "item")
    public Item item;

    @Override
    public String toString() {
        return "Channel{" +
                "item=" + item +
                '}';
    }
}

@Root(name = "item", strict = false)
class Item {

    @Element(name = "category")
    public String category;

    @Element(name = "description")
    public Description description;

    @Override
    public String toString() {
        return "Item{" +
                "category='" + category + '\'' +
                ", description=" + description +
                '}';
    }
}

@Root(name = "description", strict = false)
class Description {

    @Element(name = "body")
    public Body body;

    @Override
    public String toString() {
        return "Description{" +
                "body=" + body +
                '}';
    }
}

@Root(name = "body", strict = false)
class Body {

    @ElementList(inline = true)
    public List<Data> data;

    @Override
    public String toString() {
        return "Body{" +
                "data=" + data +
                '}';
    }
}

@Root(name = "data", strict = false)
class Data {

    @Element(name = "hour")
    public int hour;

    @Element(name = "temp")
    public String temp;

    @Element(name = "wfKor")
    public String wfKor;

    @Override
    public String toString() {
        return "Data{" +
                "hour=" + hour +
                ", temp=" + temp +
                ", wfKor='" + wfKor + '\'' +
                '}';
    }
}

