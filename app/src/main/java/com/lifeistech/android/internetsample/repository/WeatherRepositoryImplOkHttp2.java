package com.lifeistech.android.internetsample.repository;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.lifeistech.android.internetsample.entities.Weather;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * OkHttpでの実装
 */
public class WeatherRepositoryImplOkHttp2 implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplOkHttp2.class.getSimpleName();

    private android.os.Handler handler = new Handler();

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
            // 通信が失敗した時
            @Override
            public void onFailure(Request request, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.error(e);
                    }
                });
            }

            // 通信が成功した時
            @Override
            public void onResponse(final com.squareup.okhttp.Response response) throws IOException {
                // 通信結果をログに出力する
                Log.d(TAG, "result: " + response.body().toString());
                final Weather weather = new Gson().fromJson(response.body().toString(), Weather.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.success(weather);
                    }
                });
            }
        });
    }
}
