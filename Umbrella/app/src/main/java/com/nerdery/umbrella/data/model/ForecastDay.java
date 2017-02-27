package com.nerdery.umbrella.data.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexander Melton on 2/21/2017.
 *
 * Data structure to hold a list of forecast hours corresponding to a specific day.
 * Also figures the minimum and maximum of each.
 */

public class ForecastDay {
    private List<ForecastHour> forecastHours;
    private String day;

    /**
     * Comparator used to find the highest and lowest hourly forecast in a list.
     */
    private class ForecastHourComparator implements Comparator<ForecastHour> {
        /**
         * @param a First ForecastHour object to compare from a collection of Forecast hours.
         * @param b Second ForecastHour object to compare from a collection of Forecast hours.
         * @return Return an int which reflects how we want the comparator to organize our data.
         */
        public int compare(ForecastHour a, ForecastHour b) {
            if (a.getTemperatureValue() > b.getTemperatureValue())
                return 1; // highest value first
            if (a.getTemperatureValue() == b.getTemperatureValue())
                return 0;
            return -1;
        }
    }

    /**
     * Constructor for our ForecastDay object. Forcing constructor arguments and only getters allows for this to be immutable.
     *
     * @param day String which will be displayed at the top of the card, corresponds to the common day among all ForecastHours
     * @param forecastHours List of ForecastHours which correspond to the given day
     */
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
