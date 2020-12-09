package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "description", strict = false)
public class Description {

    @Element(name = "body")
    public Body body;

    @Override
    public String toString() {
        return "Description{" +
                "body=" + body +
                '}';
    }
}
