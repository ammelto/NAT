package com.nerdery.umbrella.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Edit: Changed this to have getters and a setter for the icon.
 * Since the data is serialized from the API there's no reason to have setters.
 * This should be immutable once called, so I made the variables private and only provided getters.
 *
 * Represents the "current_observation" data returned from Weather Underground
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 * @author bherbst
 */
public class CurrentObservation {
    @SerializedName("display_location")
    private DisplayLocation displayLocation;

    @SerializedName("temp_f")
    private float tempFahrenheit;

    @SerializedName("temp_c")
    private float tempCelsius;

    private String weather;

    private String icon;

    public DisplayLocation getDisplayLocation() {
        return displayLocation;
    }

    public float getTempFahrenheit() {
        return tempFahrenheit;
    }

    public float getTempCelsius() {
        return tempCelsius;
    }

    public String getWeather() {
        return weather;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
