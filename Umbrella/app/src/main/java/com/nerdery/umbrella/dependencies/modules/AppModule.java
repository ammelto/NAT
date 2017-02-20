package com.nerdery.umbrella.dependencies.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.dependencies.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

@Module
public class AppModule {
    private final Umbrella application;

    public AppModule(Umbrella app){
        this.application = app;
    }

    @Provides
    @AppScope
    Context providesApplicationContext(){
        return application;
    }

}
