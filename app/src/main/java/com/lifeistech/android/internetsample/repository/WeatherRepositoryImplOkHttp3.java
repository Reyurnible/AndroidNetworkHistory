package com.lifeistech.android.internetsample.repository;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.lifeistech.android.internetsample.entities.Weather;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp3での実装
 * https://github.com/square/okhttp
 */
public class WeatherRepositoryImplOkHttp3 implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplOkHttp3.class.getSimpleName();

    private Handler handler = new Handler();

    @Override
    public void getWeather(final RequestCallback callback) {
        final Request request = new Request.Builder()
                // URLを生成
                .url(uri.toString())
                .get()
                .build();
        // クライアントオブジェクトを作成する
        final OkHttpClient client = new OkHttpClient();
        // 新しいリクエストを行う
        client.newCall(request).enqueue(new Callback() {
            // 通信が成功した時
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 通信結果をログに出力する
                final String responseBody = response.body().string();
                Log.d(TAG, "result: " + responseBody);
                final Weather weather = new Gson().fromJson(responseBody, Weather.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.success(weather);
                    }
                });
            }

            // 通信が失敗した時
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.error(e);
                    }
                });
            }
        });
    }
}
