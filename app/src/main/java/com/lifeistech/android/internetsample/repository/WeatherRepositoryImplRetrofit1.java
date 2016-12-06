package com.lifeistech.android.internetsample.repository;

import android.net.Uri;
import android.util.Log;

import com.lifeistech.android.internetsample.entities.Weather;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Retrofitでの実装
 */
public class WeatherRepositoryImplRetrofit1 implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplRetrofit1.class.getSimpleName();

    private final WeatherService service;

    public WeatherRepositoryImplRetrofit1() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).build().toString())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        service = restAdapter.create(WeatherService.class);
    }

    @Override
    public void getWeather(final RequestCallback callback) {
        service.getWeather(130010, new retrofit.Callback<Weather>() {
            @Override
            public void success(Weather weather, retrofit.client.Response response) {
                Log.d(TAG, "result: " + weather.toString());
                callback.success(weather);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.error(error);
            }
        });
    }

    private interface WeatherService {
        @GET(PATH)
        void getWeather(@Query("city") int city, retrofit.Callback<Weather> callback);
    }

}
