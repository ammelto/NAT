package com.nerdery.umbrella.dependencies.modules;

import android.content.Context;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.SharedPrefsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Module for application level dependencies
 */

@Module
public class AppModule {
    private final Umbrella application;

    public AppModule(Umbrella app){
        this.application = app;
    }

    @Provides
    @Singleton
    Context providesApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    SharedPrefsManager providesSharedPrefsManager(){
        return new SharedPrefsManager(application);
    }

}
