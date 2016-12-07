package com.reyurnible.android.networkhistory.entities;

import java.util.List;

public class Weather {
    public List<PinpointLocation> pinpointLocations;
    public AreaLocation location;
    public String title;
    public Description description;
    public String publicTime;

    public Weather() {
    }

    public Weather(List<PinpointLocation> pinpointLocations, AreaLocation location, String title, Description description, String publicTime) {
        this.pinpointLocations = pinpointLocations;
        this.location = location;
        this.title = title;
        this.description = description;
        this.publicTime = publicTime;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "pinpointLocations=" + pinpointLocations +
                ", location=" + location +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", publicTime='" + publicTime + '\'' +
                '}';
    }
}
