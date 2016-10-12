package com.lifeistech.android.internetsample.entities;

public class AreaLocation {
    public String city;
    public String area;
    public String prefecture;

    @Override
    public String toString() {
        return "AreaLocation{" +
                "prefecture='" + prefecture + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
