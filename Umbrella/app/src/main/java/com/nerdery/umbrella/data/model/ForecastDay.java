package com.nerdery.umbrella.data.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class ForecastDay {
    private List<ForecastHour> forecastHours;
    private String day;

    private class ForecastHourComparator implements Comparator<ForecastHour> {
        public int compare(ForecastHour a, ForecastHour b) {
            if (a.getTemperatureValue() > b.getTemperatureValue())
                return 1; // highest value first
            if (a.getTemperatureValue() == b.getTemperatureValue())
                return 0;
            return -1;
        }
    }

    public ForecastDay(String day, List<ForecastHour> forecastHours){
        this.day = day;
        this.forecastHours = forecastHours;

        ForecastHour max = Collections.max(forecastHours, new ForecastHourComparator());
        ForecastHour min = Collections.min(forecastHours, new ForecastHourComparator());
        max.setLocalDailyMax(true);
        min.setLocalDailyMin(true);
    }

    public List<ForecastHour> getForecastHours() {
        return forecastHours;
    }

    public String getDay() {
        return day;
    }

}
