package com.nerdery.umbrella;

import android.app.Application;

import com.nerdery.umbrella.dependencies.AppGraph;
import com.nerdery.umbrella.dependencies.DaggerAppGraph;
import com.nerdery.umbrella.dependencies.modules.AppModule;
import com.nerdery.umbrella.dependencies.modules.NetworkModule;

import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Umbrella Application
 * Gets the weather from a desired zipcode then displays it in Fahrenheit or Celsius
 */

public class Umbrella extends Application {

    private AppGraph appGraph;
    private static Umbrella instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appGraph = DaggerAppGraph
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();

        appGraph.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Umbrella getInstance(){
        return instance;
    }

    public AppGraph getAppGraph(){ return appGraph;}

}
