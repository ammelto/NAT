package com.nerdery.umbrella.dependencies;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.BaseActivity;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.api.WeatherApi;
import com.nerdery.umbrella.dependencies.modules.AppModule;
import com.nerdery.umbrella.dependencies.modules.PresenterModule;
import com.nerdery.umbrella.views.home.HomeActivity;
import com.nerdery.umbrella.views.home.ViewHolderHourlyForecast;

import dagger.Component;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

@AppScope
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppGraph {
    void inject(Umbrella application);

    void inject(HomeActivity homeActivity);

    void inject(ViewHolderHourlyForecast viewHolderHourlyForecast);
}
