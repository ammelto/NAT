package com.nerdery.umbrella.views.home;

import android.content.SharedPreferences;
import android.text.format.DateUtils;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.SharedPrefsManager;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.ForecastHour;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/26/2017.
 *
 * Test for HomePresenter.
 * In a production application, I'd shoot for about 70% test coverage but considering this is a smaller application
 * I'm only going to write this one test.
 */
public class HomePresenterTest {

    private List<ForecastCondition> conditions;
    private final int LIST_SIZE = 30;

    @Mock WeatherApi weatherApi;

    @Mock SharedPrefsManager sharedPrefsManager;

    @Before
    public void createConditionList(){
        conditions = new ArrayList<>();

        Float rand;
        for(int i = 0; i < this.LIST_SIZE; i++){
            rand = new Random().nextFloat();
            rand *= 100;
            conditions.add(createCondition(i, (rand - 32) * 5/9, rand));
        }
    }

    private ForecastCondition createCondition(int hour, float tempC, float tempF){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hour);

        ForecastCondition forecastCondition = new ForecastCondition();
        forecastCondition.setCalendar(calendar);
        forecastCondition.setTempCelsius(tempC);
        forecastCondition.setTempFahrenheit(tempF);
        forecastCondition.setDisplayTime(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));

        return forecastCondition;
    }

    @Test
    public void testBuildForecastDayList(){
        HomePresenter homePresenter = new HomePresenter(sharedPrefsManager, weatherApi);
        List<ForecastDay> forecastDaysC = homePresenter.buildForecastDayList(conditions, SharedPrefsManager.METRIC_UNITS);
        List<ForecastDay> forecastDaysF = homePresenter.buildForecastDayList(conditions, SharedPrefsManager.IMPERIAL_UNITS);

        ForecastDay forecastDayC;
        ForecastDay forecastDayF;

        Assert.assertEquals(forecastDaysC.size(), forecastDaysF.size());

        for(int i = 0; i < forecastDaysC.size(); i++){
            forecastDayC = forecastDaysC.get(i);
            forecastDayF = forecastDaysF.get(i);

            assertForecastDays(forecastDayC);
            assertForecastDays(forecastDayF);
        }
    }

    private void assertForecastDays(ForecastDay forecastDay){

        //Each forecast day should contain at most one day's worth of hours, therefore all forecast hour times should be unique
        Set<String> forecastHourTimeSet = new HashSet<>(forecastDay.getForecastHours().size());
        List<ForecastHour> forecastHourList = forecastDay.getForecastHours();
        for(ForecastHour forecastHour : forecastHourList){
            forecastHourTimeSet.add(forecastHour.getHour());
        }

        //Hashsets require unique values, this will cause the sizes to be different if the hours aren't unique
        Assert.assertEquals(forecastHourList.size(), forecastHourTimeSet.size());
        //Make sure we don't have more than 24 hours per day
        Assert.assertTrue(forecastHourList.size() < 24);
    }
}