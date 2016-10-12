package com.lifeistech.android.internetsample.entities;

public class Description {
    public String text;
    public String publicTime;

    @Override
    public String toString() {
        return "Description{" +
                "publicTime='" + publicTime + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
