package com.nerdery.umbrella.views.home;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.mvp.BasePresenter;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.model.CurrentObservation;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.WeatherData;
import com.nerdery.umbrella.widget.SharedPrefsManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
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

        splitByDay(forecastConditionList);
    }

    protected void splitByDay(List<ForecastCondition> conditions){
        Map<String, List<ForecastCondition>> conditionMap = new LinkedHashMap<>();
        List<ForecastCondition> forecastConditionList;
        String day;
        for(ForecastCondition condition : conditions){
            day = condition.calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

            if(conditionMap.containsKey(day)) forecastConditionList = conditionMap.get(day);
            else forecastConditionList = new ArrayList<>();

            forecastConditionList.add(condition);
            conditionMap.put(day, forecastConditionList);
        }

        List<ForecastDay> forecastDays = new ArrayList<>();
        for(String key: conditionMap.keySet()){
            forecastDays.add(new ForecastDay(key, conditionMap.get(key)));
        }
        getView().onBindAdapter(forecastDays);
    }

}
