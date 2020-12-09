package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "data", strict = false)
public class Data {

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
