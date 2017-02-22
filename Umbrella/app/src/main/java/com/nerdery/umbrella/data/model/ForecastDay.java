package com.nerdery.umbrella.data.model;

import java.util.List;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class ForecastDay {
    private List<ForecastCondition> forecastConditions;
    private String day;

    public ForecastDay(String day, List<ForecastCondition> forecastConditions){
        this.day = day;
        this.forecastConditions = forecastConditions;
    }

    public List<ForecastCondition> getForecastConditions() {
        return forecastConditions;
    }

    public String getDay() {
        return day;
    }
}
