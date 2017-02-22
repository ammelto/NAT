package com.nerdery.umbrella.views.home;

import com.nerdery.umbrella.base.mvp.BaseView;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;

import java.util.List;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Contract for the HomeActivity
 */

public interface HomeView extends BaseView{

    void setActionBarColor(float temp);

    void setAreaName(String name);

    void setCurrentTemperature(float temp);

    void setFlavorText(String flavor);

    void onInvalidZip();

    void onBindAdapter(List<ForecastDay> forecastDays);
}
