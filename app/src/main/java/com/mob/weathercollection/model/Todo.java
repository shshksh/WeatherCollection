package com.mob.weathercollection.model;

public class Todo {
    public String userId;
    public String id;
    public String title;

    @Override
    public String toString() {
        return "Todo{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
