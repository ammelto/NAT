package com.nerdery.umbrella.dependencies;

import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.dependencies.modules.AppModule;
import com.nerdery.umbrella.dependencies.modules.NetworkModule;
import com.nerdery.umbrella.views.home.HomeActivity;
import com.nerdery.umbrella.views.home.ViewHolderHourlyForecast;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Application graph for Dagger2
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppGraph {
    void inject(Umbrella application);

    void inject(HomeActivity homeActivity);

    void inject(ViewHolderHourlyForecast viewHolderHourlyForecast);

}
