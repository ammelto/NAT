package com.nerdery.umbrella;

import android.app.Application;

import com.nerdery.umbrella.data.model.WeatherData;
import com.nerdery.umbrella.dependencies.AppGraph;
import com.nerdery.umbrella.dependencies.DaggerAppGraph;
import com.nerdery.umbrella.dependencies.modules.AppModule;
import com.nerdery.umbrella.dependencies.modules.PresenterModule;

import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class Umbrella extends Application {

    private AppGraph appGraph;
    private WeatherData weatherData;
    private static Umbrella instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appGraph = DaggerAppGraph
                .builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule())
                .build();

        appGraph.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    // I usually just do a class cast in the base activity, but this is fine too.
    public static Umbrella getInstance(){
        return instance;
    }

    public AppGraph getAppGraph(){ return appGraph;}

    // This isn't the place for cacheing weather data in-memory. I recommend putting this in some
    // injectable class that also retrieves the weather data. Say, an intermediate step that takes
    // the network API and requests data, then caches the data. The application class should
    // ONLY be for initialization of the app, and referencing of application level graph.
    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

}
