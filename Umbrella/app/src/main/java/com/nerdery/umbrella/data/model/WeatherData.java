package com.nerdery.umbrella.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents weather information returned from the Weather Underground API
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 * @author bherbst
 */
public class WeatherData {
    @SerializedName("current_observation")
    private CurrentObservation currentObservation;

    @SerializedName("hourly_forecast")
    private List<ForecastCondition> forecast;

    public CurrentObservation getCurrentObservation() {
        return currentObservation;
    }

    public void setCurrentObservation(CurrentObservation currentObservation) {
        this.currentObservation = currentObservation;
    }

    public List<ForecastCondition> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastCondition> forecast) {
        this.forecast = forecast;
    }
}
