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

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    public final float TEMPERATURE_THRESHOLD = 60.0f;

    public HomePresenter() {

    }

    @Override
    public void attachView(HomeView view) {
        super.attachView(view);

        ApiManager.getWeatherApi().getForecastForZip(74127)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::parseWeather, e ->{
                    Timber.e(e, "Could not fetch weather data");
                });
    }

    public void parseWeather(WeatherData weatherData){
        List<ForecastCondition> forecastConditionList = weatherData.forecast;
        CurrentObservation currentObservation = weatherData.currentObservation;

        HomeView homeView = getView();
        homeView.setActionBarColor(getWeatherColor(currentObservation.tempFahrenheit));
        homeView.setAreaName(currentObservation.displayLocation.full);
    }

    private int getWeatherColor(float temp){
        return temp < this.TEMPERATURE_THRESHOLD ? R.color.weather_cool : R.color.weather_warm;
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
