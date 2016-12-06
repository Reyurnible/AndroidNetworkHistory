package com.lifeistech.android.internetsample;

import android.app.Application;

import com.lifeistech.android.internetsample.repository.RepositoryProvider;

/**
 * Application for Initialize repositories
 */
public class ProviderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RepositoryProvider.initialize(this);
    }
}
