package com.lifeistech.android.internetsample.repository;

import android.net.Uri;

import com.lifeistech.android.internetsample.entities.Weather;

/**
 * 天気予報取得のためのManagerクラス
 */
public interface WeatherRepository {
    // URL Const Vals
    String SCHEME = "http";
    String AUTHORITY = "weather.livedoor.com";
    String PATH = "/forecast/webservice/json/v1";
    // URI
    Uri uri = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path(PATH)
            .appendQueryParameter("city", "130010")
            .build();

    void getWeather(RequestCallback callback);

    interface RequestCallback {
        // 成功時のCallback
        void success(Weather weather);

        // 失敗次のCallback
        void error(Throwable throwable);
    }

}
