package com.nerdery.umbrella.data.model;

/**
 * Created by Alexander Melton on 2/22/2017.
 */

public class ForecastHour {

    // Make these private, and only accessible by getter and setter
    public String temperature;
    public String imageUrl;
    public String hour;
    public int temperatureValue;
    public Boolean isLocalDailyMax = false;
    public Boolean isLocalDailyMin = false;
}
