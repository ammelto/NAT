package com.nerdery.umbrella.data.model;

/**
 * Created by Alexander Melton on 2/22/2017.
 */

public class ForecastHour {
    private final String temperature;
    private final String imageUrl;
    private final String hour;
    private final int temperatureValue;
    private Boolean isLocalDailyMax = false;
    private Boolean isLocalDailyMin = false;

    public ForecastHour(String temperature, int temperatureValue, String hour, String imageUrl) {
        this.temperature = temperature;
        this.temperatureValue = temperatureValue;
        this.hour = hour;
        this.imageUrl = imageUrl;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHour() {
        return hour;
    }

    public int getTemperatureValue() {
        return temperatureValue;
    }

    public Boolean getLocalDailyMax() {
        return isLocalDailyMax;
    }

    public void setLocalDailyMax(Boolean localDailyMax) {
        isLocalDailyMax = localDailyMax;
    }

    public Boolean getLocalDailyMin() {
        return isLocalDailyMin;
    }

    public void setLocalDailyMin(Boolean localDailyMin) {
        isLocalDailyMin = localDailyMin;
    }
}
