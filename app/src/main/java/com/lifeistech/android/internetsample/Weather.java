package com.lifeistech.android.internetsample;

import java.util.List;

/**
 * Created by shunhosaka on 2015/11/09.
 */
public class Weather {
    public static final String TAG = Weather.class.getSimpleName();
    public List<PinpointLocation> pinpointLocations;
    public Location location;
    public String title;
    public Description description;
    public String publicTime;

    public class PinpointLocation {
        public String name;
        public String link;
    }

    public class Location {
        public String city;
        public String area;
        public String prefecture;
    }

    public class Description {
        public String text;
        public String publicTime;
    }
}
