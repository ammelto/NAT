package com.nerdery.umbrella.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nerdery.umbrella.BuildConfig;
import com.nerdery.umbrella.data.api.parser.ForecastParser;
import com.nerdery.umbrella.data.model.ForecastCondition;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Manages access to the various APIs we are using
 *
 * @author bherbst
 */

// Stop using this immediately, and put this shit in a module somewhere
// Provide Retrofit instance and build APIs out in modules.
// Static classes aren't testable, and the minute you want to mock this out to
// MockWebServer, it will require a lot of refactoring
public class ApiManager {
    private static WeatherApi sApi;

    /**
     * Get a WeatherApi instance for access to Weather Underground's APIs
     */
    public static WeatherApi getWeatherApi() {
        if (sApi == null) {
            sApi = buildWeatherApi();
        }

        return sApi;
    }

    /**
     * Get an IconApi instance
     */
    public static IconApi getIconApi() {
        return new IconApi();
    }

    /**
     * Build a new WeatherApi
     */
    private static WeatherApi buildWeatherApi() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ForecastCondition.class, new ForecastParser())
                .create();

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return adapter.create(WeatherApi.class);
    }
}
