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

    /**
     * Dependencies are handled by Dagger2 so everything in the constructor here is injected when the provider builds the presenter
     *
     * @param sharedPrefsManager Singleton SharedPrefsManager
     * @param weatherApi WeatherApi singleton provided by Dagger2
     */
    @Inject
    public HomePresenter(SharedPrefsManager sharedPrefsManager, WeatherApi weatherApi) {
        this.sharedPrefsManager = sharedPrefsManager;
        this.weatherApi = weatherApi;
    }

    /**
     * Rebuilds the weather data from the weather API any time the view is created or resumed
     * @param view View which this is present is bound to
     */
    @Override
    public void attachView(HomeView view) {
        super.attachView(view);
        getWeatherData();
    }


    /**
     * Clears out the weather subscription to prevent memory leaks
     */
    @Override
    public void detachView() {
        super.detachView();
        weatherSubscription.unsubscribe();
        weatherSubscription = null;
    }

    /**
     * Subscription for the Weather observable using RxJava
     * Offloads the request to a new thread then passes the response to the buildHomeView function
     *
     * On error, prints timber error message. Future improvement would be to have the view launch a snackbar on error
     */
    private void getWeatherData(){
        int zip = sharedPrefsManager.getZip();
        weatherSubscription = weatherApi.getForecastForZip(zip)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::buildHomeView, e ->{
                    Timber.e(e, "Could not fetch weather data");
                });
    }

    /**
     * Most of the current observation data comes from the API in a presentable form, or close enough
     * where minimal logic is required to make it presentable.
     * As a result, we simply need to pass the data to the view.
     *
     * Otherwise, we pass the data to dedicated functions which modify the data further before passing it to the view
     *
     * @param weatherData WeatherData object built from the response of the API
     */
    private void buildHomeView(WeatherData weatherData){
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
        getView().fillForecast(buildForecastDayList(forecastConditionList, unit));
    }

    /**
     * Function to organize the list of ForecastConditions into their respective days.
     *
     * @param conditions List of conditions given from the WeatherData API response
     * @param unit Pass the unit from user prefs here too so we don't have to call it again
     * @return Returns the list of ForecastDays so the buildHomeView function can attach the data to the view just to make this a little more functional.
     */
    public List<ForecastDay> buildForecastDayList(List<ForecastCondition> conditions, String unit){
        //LinkedHashMap is used here because we want to maintain the order of the List.
        Map<String, List<ForecastHour>> forecastHoursMap = new LinkedHashMap<>();
        List<ForecastHour> forecastHours;

        //Calendar is used to figure out what 'Today' and 'Tomorrow' is relative to the current day
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        int tomorrow = calendar.get(Calendar.DAY_OF_WEEK);

        //The day string is the key for the map as well as the title of the DailyForecast card
        String day;

        for(ForecastCondition condition : conditions){
            //Attempts to set the string value with respect to the locale
            //This allows multilingual support in the future.
            if(condition.getCalendar().get(Calendar.DAY_OF_WEEK) == today) day = Umbrella.getInstance().getString(R.string.today);
            else if(condition.getCalendar().get(Calendar.DAY_OF_WEEK) == tomorrow) day = Umbrella.getInstance().getString(R.string.tomorrow);
            else day = condition.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

            //Creates a new ForecastHours list if this is the first item for that day
            //Otherwise, gets the existing list
            if(forecastHoursMap.containsKey(day)) forecastHours = forecastHoursMap.get(day);
            else forecastHours = new ArrayList<>();

            //Builds the ForecastHours so that we don't have to do any logic to present this in the view holder.
            //Just keeps all the formatting logic for this in one place.
            forecastHours.add(buildForecastHour(condition, unit));
            forecastHoursMap.put(day, forecastHours);
        }

        //Builds ForecastDays from the linked hash map
        List<ForecastDay> forecastDays = new ArrayList<>();
        for(String key: forecastHoursMap.keySet()){
            forecastDays.add(new ForecastDay(key, forecastHoursMap.get(key)));
        }

        return forecastDays;
    }

    /**
     * Formats the ForecastCondition in such a way that the viewholder can use.
     * This is done to keep all the forecast formatting logic in one place.
     *
     * @param condition ForecastCondition to be formatted into a ForecastHour cell
     * @param unit Units the user selected in user preferences
     * @return Returns the ForecastHour cell object
     */
    public ForecastHour buildForecastHour(ForecastCondition condition,String unit){
        Float tempFloat = unit.equals(SharedPrefsManager.IMPERIAL_UNITS) ? condition.getTempFahrenheit() : condition.getTempCelsius();
        String temp = String.format(Umbrella.getInstance().getResources().getString(R.string.temperature_current), Math.round(tempFloat));

        //Creates a new ForecastHour. These values should not change, so there's no reason to have setters for them
        return new ForecastHour(temp, Math.round(tempFloat), condition.getDisplayTime(), condition.getIcon());
    }

}
