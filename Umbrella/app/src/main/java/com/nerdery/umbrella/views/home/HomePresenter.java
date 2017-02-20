package com.nerdery.umbrella.views.home;

import android.util.Log;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.mvp.BasePresenter;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.api.IconApi;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.data.model.CurrentObservation;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.WeatherData;
import com.nerdery.umbrella.utils.SharedPrefsManager;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
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

    private void parseWeather(WeatherData weatherData){
        List<ForecastCondition> forecastConditionList = weatherData.forecast;
        CurrentObservation currentObservation = weatherData.currentObservation;
        String unit = sharedPrefsManager.getValue(SharedPrefsManager.UNITS, "imperial");


        HomeView homeView = getView();
        if(currentObservation == null){
            homeView.onInvalidZip();
            return;
        }
        homeView.setActionBarColor(currentObservation.tempFahrenheit);
        homeView.setAreaName(currentObservation.displayLocation.full);
        homeView.setCurrentTemperature(unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ?
                currentObservation.tempFahrenheit : currentObservation.tempCelsius);
        homeView.setFlavorText(currentObservation.weather);
    }

}
