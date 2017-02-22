package com.nerdery.umbrella.dependencies.modules;

import android.content.Context;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.dependencies.AppScope;
import com.nerdery.umbrella.widget.SharedPrefsManager;

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

    @Provides
    @AppScope
    SharedPrefsManager providesSharedPrefsManager(){
        return new SharedPrefsManager(application);
    }


}
