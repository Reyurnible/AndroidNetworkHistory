package com.reyurnible.android.networkhistory.repository;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.reyurnible.android.networkhistory.entities.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HttpURLConnectionでの実装
 * https://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class WeatherRepositoryImplHttpURLConnection implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplHttpURLConnection.class.getSimpleName();

    @Override
    public void getWeather(final RequestCallback callback) {
        new AsyncTask<Void, Void, Weather>() {
            // 処理の前に呼ばれるメソッド
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            // 処理を行うメソッド
            @Override
            protected Weather doInBackground(Void... params) {
                final HttpURLConnection urlConnection;
                try {
                    URL url = new URL(uri.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                } catch (MalformedURLException e) {
                    return null;
                } catch (IOException e) {
                    return null;
                }
                final String buffer;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    buffer = reader.readLine();
                } catch (IOException e) {
                    return null;
                } finally {
                    urlConnection.disconnect();
                }
                if (TextUtils.isEmpty(buffer)) {
                    return null;
                }
                return new Gson().fromJson(buffer, Weather.class);
            }

            // 処理がすべて終わったら呼ばれるメソッド
            @Override
            protected void onPostExecute(Weather response) {
                super.onPostExecute(response);
                // 通信失敗として処理
                if (response == null) {
                    callback.error(new IOException("HttpURLConnection request error"));
                } else {
                    Log.d(TAG, "result: " + response.toString());
                    // 通信結果を表示
                    callback.success(response);
                }
            }
        }.execute();
    }
}
