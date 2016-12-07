package com.reyurnible.android.networkhistory;

import android.app.Application;

import com.reyurnible.android.networkhistory.repository.RepositoryProvider;

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
