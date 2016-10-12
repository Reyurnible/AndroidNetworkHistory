package com.lifeistech.android.internetsample.repository;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lifeistech.android.internetsample.entities.Weather;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClientでの実装
 */
public class WeatherRepositoryImplHttpClient implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplHttpClient.class.getSimpleName();

    @Override
    public void getWeather(final RequestCallback callback) {
        new AsyncTask<String, String, String>() {
            // 処理の前に呼ばれるメソッド
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            // 処理を行うメソッド
            @Override
            protected String doInBackground(String... params) {
                final HttpClient httpClient = new DefaultHttpClient();
                final HttpGet httpGet = new HttpGet(uri.toString());
                final HttpResponse httpResponse;
                try {
                    httpResponse = httpClient.execute(httpGet);
                    return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                } catch (IOException e) {
                    return null;
                }
            }

            // 処理がすべて終わったら呼ばれるメソッド
            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                // 通信失敗として処理
                if (TextUtils.isEmpty(response)) {
                    callback.error(new IOException("OkHttp Request error"));
                } else {
                    Log.d(TAG, "result: " + response);
                    // 通信結果を表示
                    final Weather weather = new Gson().fromJson(response, Weather.class);
                    callback.success(weather);
                }
            }
        }.execute();

    }
}
