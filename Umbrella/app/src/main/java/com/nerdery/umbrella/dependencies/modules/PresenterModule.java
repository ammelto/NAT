package com.nerdery.umbrella.dependencies.modules;

import com.nerdery.umbrella.widget.SharedPrefsManager;
import com.nerdery.umbrella.views.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

@Module
public class PresenterModule {

    // This module is unnecessary as the only necessary dependency is already provided
    // @Inject annotate the constructor of HomePresenter.
    @Provides
    HomePresenter providesHomePresenter(SharedPrefsManager sharedPrefsManager){
        return new HomePresenter(sharedPrefsManager);
    }
}

