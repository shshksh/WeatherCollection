package com.mob.weathercollection.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Override
    public String toString() {
        return "Channel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
