package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "body", strict = false)
public class Body {

    @ElementList(inline = true)
    public List<Data> data;

    @Override
    public String toString() {
        return "Body{" +
                "data=" + data +
                '}';
    }
}
