package com.lifeistech.android.internetsample.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lifeistech.android.internetsample.entities.Weather;

/**
 * Ionでの実装
 * https://github.com/koush/ion
 */
public class WeatherRepositoryImplIon implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplIon.class.getSimpleName();

    private Context context;

    public WeatherRepositoryImplIon(Context context) {
        this.context = context;
    }

    @Override
    public void getWeather(final RequestCallback callback) {
        // クライアントオブジェクトを作成する
        // 新しいリクエストを行う
        Ion.with(context)
                .load(uri.toString())
                .as(new TypeToken<Weather>() {
                })
                .setCallback(new FutureCallback<Weather>() {
                    // 通信が終了した時
                    @Override
                    public void onCompleted(Exception e, Weather weather) {
                        // 通信が失敗した時
                        if (e != null) {
                            callback.error(e);
                            return;
                        }
                        // 通信結果をログに出力する
                        Log.d(TAG, "result: " + weather.toString());
                        callback.success(weather);
                    }
                });
    }
}
