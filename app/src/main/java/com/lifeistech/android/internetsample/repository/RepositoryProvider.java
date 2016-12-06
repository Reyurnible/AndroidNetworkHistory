package com.lifeistech.android.internetsample.repository;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Provider for repository
 */
public class RepositoryProvider {

    private static HashMap<Client, WeatherRepository> repositories;

    public static void initialize(Context context) {
        repositories = new HashMap<>();
        for (Client client : Client.values()) {
            repositories.put(client, client.createClient(context));
        }
    }

    @Nullable
    public static WeatherRepository provideWeatherRepository(Client client) {
        return repositories.get(client);
    }

    // Network Libraries
    public enum Client {
        HttpClient {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplHttpClient();
            }
        },
        Volley {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplVolley(context);
            }
        },
        Ion {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplIon(context);
            }
        },
        OkHttp2 {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplOkHttp2();
            }
        },
        OkHttp3 {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplOkHttp3();
            }
        },
        Retrofit1 {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplRetrofit1();
            }
        },
        Retrofit2 {
            @Override
            public WeatherRepository createClient(Context context) {
                return new WeatherRepositoryImplRetrofit2();
            }
        },;

        public abstract WeatherRepository createClient(Context context);

        @Override
        public String toString() {
            return name();
        }
    }


}
