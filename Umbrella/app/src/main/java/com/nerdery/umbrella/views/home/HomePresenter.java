package com.nerdery.umbrella.views.home;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.mvp.BasePresenter;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.data.model.CurrentObservation;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.ForecastHour;
import com.nerdery.umbrella.data.model.WeatherData;
import com.nerdery.umbrella.data.SharedPrefsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

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
    private WeatherApi weatherApi;

    @Inject
    public HomePresenter(SharedPrefsManager sharedPrefsManager, WeatherApi weatherApi) {
        this.sharedPrefsManager = sharedPrefsManager;
        this.weatherApi = weatherApi;
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
        int zip = sharedPrefsManager.getZip();
        weatherSubscription = weatherApi.getForecastForZip(zip)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::parseWeather, e ->{
                    Timber.e(e, "Could not fetch weather data");
                });
    }

    protected void parseWeather(WeatherData weatherData){
        List<ForecastCondition> forecastConditionList = weatherData.getForecast();
        CurrentObservation currentObservation = weatherData.getCurrentObservation();
        String unit = sharedPrefsManager.getUnits();

        if(currentObservation == null){
            getView().onInvalidZip();
            return;
        }
        getView().setActionBarColor(currentObservation.getTempFahrenheit());
        getView().setAreaName(currentObservation.getDisplayLocation().getFull());
        getView().setCurrentTemperature(unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ?
                currentObservation.getTempFahrenheit() : currentObservation.getTempCelsius());
        getView().setFlavorText(currentObservation.getWeather());

        splitByDay(forecastConditionList, unit);
    }

    protected List<ForecastDay> splitByDay(List<ForecastCondition> conditions, String unit){
        Map<String, List<ForecastHour>> forecastHoursMap = new LinkedHashMap<>();
        List<ForecastHour> forecastHours;

        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        int tomorrow = calendar.get(Calendar.DAY_OF_WEEK);
        String day;

        for(ForecastCondition condition : conditions){
            if(condition.getCalendar().get(Calendar.DAY_OF_WEEK) == today) day = "Today";
            else if(condition.getCalendar().get(Calendar.DAY_OF_WEEK) == tomorrow) day = "Tomorrow";
            else day = condition.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

            if(forecastHoursMap.containsKey(day)) forecastHours = forecastHoursMap.get(day);
            else forecastHours = new ArrayList<>();

            forecastHours.add(buildForecastHour(condition, unit));
            forecastHoursMap.put(day, forecastHours);
        }

        List<ForecastDay> forecastDays = new ArrayList<>();
        for(String key: forecastHoursMap.keySet()){
            forecastDays.add(new ForecastDay(key, forecastHoursMap.get(key)));
        }

        getView().fillForecast(forecastDays);

        return forecastDays;
    }

    private ForecastHour buildForecastHour(ForecastCondition condition,String unit){
        ForecastHour forecastHour = new ForecastHour();

        Float tempFloat = unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ? condition.getTempFahrenheit() : condition.getTempCelsius();

        forecastHour.setHour(condition.getDisplayTime());
        forecastHour.setTemperatureValue(Math.round(tempFloat));
        forecastHour.setTemperature(String.format(Umbrella.getInstance().getResources().getString(R.string.temperature_current),
                Math.round(tempFloat)));
        forecastHour.setImageUrl(condition.getIcon());

        return forecastHour;
    }

}
