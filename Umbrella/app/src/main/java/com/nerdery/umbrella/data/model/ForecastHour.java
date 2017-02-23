package com.nerdery.umbrella.data.model;

/**
 * Created by Alexander Melton on 2/22/2017.
 */

public class ForecastHour {
    private String temperature;
    private String imageUrl;
    private String hour;
    private int temperatureValue;
    private Boolean isLocalDailyMax = false;
    private Boolean isLocalDailyMin = false;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(int temperatureValue) {
        this.temperatureValue = temperatureValue;
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
