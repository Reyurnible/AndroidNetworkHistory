package com.reyurnible.android.networkhistory.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.reyurnible.android.networkhistory.entities.Weather;

import org.json.JSONObject;

/**
 * Volleyでの実装
 * https://github.com/mcxiaoke/android-volley
 */
public class WeatherRepositoryImplVolley implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplVolley.class.getSimpleName();

    private Context context;

    public WeatherRepositoryImplVolley(Context context) {
        this.context = context;
    }

    @Override
    public void getWeather(final RequestCallback callback) {
        final RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest request =
                new JsonObjectRequest(uri.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "result: " + response.toString());
                        final Weather weather = new Gson().fromJson(response.toString(), Weather.class);
                        callback.success(weather);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.error(error);
                    }
                });
        queue.add(request);

    }
}
