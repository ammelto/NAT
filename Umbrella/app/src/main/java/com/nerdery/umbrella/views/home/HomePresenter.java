package com.nerdery.umbrella.views.home;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.mvp.BasePresenter;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.model.CurrentObservation;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.ForecastHour;
import com.nerdery.umbrella.data.model.WeatherData;
import com.nerdery.umbrella.widget.SharedPrefsManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    private SharedPrefsManager sharedPrefsManager;
    private Subscription weatherSubscription;
    private enum days {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
    }

    public HomePresenter(SharedPrefsManager sharedPrefsManager) {
        this.sharedPrefsManager = sharedPrefsManager;
    }

    @Override
    public void attachView(HomeView view) {
        super.attachView(view);
        getWeatherData();
    }


    @Override
    public void detachView() {
        super.detachView();
        weatherSubscription.unsubscribe();
        weatherSubscription = null;
    }

    private void getWeatherData(){
        String notFound = String.valueOf(SharedPrefsManager.ZIP_EXISTS);
        String zip = sharedPrefsManager.getValue(SharedPrefsManager.ZIP, notFound);

        int zipValue = zip.equals(notFound) ? SharedPrefsManager.ZIP_DEFAULT : Integer.parseInt(zip);

        weatherSubscription = ApiManager.getWeatherApi().getForecastForZip(zipValue)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::parseWeather, e ->{
                    Timber.e(e, "Could not fetch weather data");
                });
    }

    protected void parseWeather(WeatherData weatherData){
        List<ForecastCondition> forecastConditionList = weatherData.forecast;
        CurrentObservation currentObservation = weatherData.currentObservation;
        String unit = sharedPrefsManager.getValue(SharedPrefsManager.UNITS, "imperial");


        HomeView homeView = getView();
        if(currentObservation == null){
            homeView.onInvalidZip();
            return;
        }
        Umbrella.getInstance().setWeatherData(weatherData);
        homeView.setActionBarColor(currentObservation.tempFahrenheit);
        homeView.setAreaName(currentObservation.displayLocation.full);
        homeView.setCurrentTemperature(unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ?
                currentObservation.tempFahrenheit : currentObservation.tempCelsius);
        homeView.setFlavorText(currentObservation.weather);

        for(ForecastCondition condition : forecastConditionList){
            Timber.d(condition.calendar.get(Calendar.DAY_OF_WEEK) + "");
        }

        splitByDay(forecastConditionList, unit);
    }

    protected List<ForecastDay> splitByDay(List<ForecastCondition> conditions, String unit){
        Map<String, List<ForecastHour>> forecastHoursMap = new LinkedHashMap<>();
        List<ForecastHour> forecastHours;
        String day;
        for(ForecastCondition condition : conditions){
            day = condition.calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

            if(forecastHoursMap.containsKey(day)) forecastHours = forecastHoursMap.get(day);
            else forecastHours = new ArrayList<>();

            forecastHours.add(buildForecastHour(condition, unit));
            forecastHoursMap.put(day, forecastHours);
        }

        List<ForecastDay> forecastDays = new ArrayList<>();
        for(String key: forecastHoursMap.keySet()){
            forecastDays.add(new ForecastDay(key, forecastHoursMap.get(key)));
        }

        getView().onBindAdapter(forecastDays);

        return forecastDays;
    }

    private ForecastHour buildForecastHour(ForecastCondition condition,String unit){
        ForecastHour forecastHour = new ForecastHour();

        Float tempFloat = unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ? condition.tempFahrenheit : condition.tempCelsius;

        forecastHour.hour = condition.displayTime;
        forecastHour.temperature = String.format(Umbrella.getInstance().getResources().getString(R.string.temperature_current),
                Math.round(tempFloat));
        forecastHour.imageUrl = condition.icon;

        return forecastHour;
    }

}
