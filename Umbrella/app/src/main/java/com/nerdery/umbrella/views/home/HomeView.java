package com.nerdery.umbrella.views.home;

import com.nerdery.umbrella.base.mvp.BaseView;

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
}
