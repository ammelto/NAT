package com.nerdery.umbrella.data.model;

import android.content.Context;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.widget.SharedPrefsManager;

import java.util.List;
import java.util.Locale;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class ForecastDay {
    private List<ForecastHour> forecastHours;
    private String day;

    public ForecastDay(String day, List<ForecastHour> forecastHours){
        this.day = day;
        this.forecastHours = forecastHours;
    }

    public List<ForecastHour> getForecastHours() {
        return forecastHours;
    }

    public String getDay() {
        return day;
    }

}
