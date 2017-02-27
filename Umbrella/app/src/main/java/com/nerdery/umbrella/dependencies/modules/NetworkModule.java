package com.nerdery.umbrella.dependencies.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nerdery.umbrella.BuildConfig;
import com.nerdery.umbrella.data.api.IconApi;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.data.api.parser.ForecastParser;
import com.nerdery.umbrella.data.model.ForecastCondition;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexander Melton on 2/22/2017.
 *
 * Module for all the network dependency injections
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit providesRetrofitAdapter(Gson gson){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return adapter;
    }

    @Provides
    @Singleton
    public Gson providesGsonParser(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ForecastCondition.class, new ForecastParser())
                .create();

        return gson;
    }

    @Provides
    @Singleton
    public WeatherApi providesWeatherApi(Retrofit adapter){
        return adapter.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    public IconApi providesIconApi(){
        return new IconApi();
    }
}
