package com.mob.weathercollection.model.weather.kma;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item {

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
