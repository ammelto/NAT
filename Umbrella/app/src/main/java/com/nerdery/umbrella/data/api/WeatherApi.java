package com.nerdery.umbrella.data.api;

import com.nerdery.umbrella.BuildConfig;
import com.nerdery.umbrella.data.model.WeatherData;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit interface for fetching weather data
 *
 * @author bherbst
 */
public interface WeatherApi {

    // Observables! :)
    /**
     * Get the forecast for a given zip code
     *
     * Changed return type to Observable for use with RxJava
     */
    @GET("/api/" + BuildConfig.API_KEY + "/conditions/hourly/q/{zip}.json")
    Observable<WeatherData> getForecastForZip(@Path("zip") int zipCode);
}
