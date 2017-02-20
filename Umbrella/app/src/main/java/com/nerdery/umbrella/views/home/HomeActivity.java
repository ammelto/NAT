package com.nerdery.umbrella.views.home;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class HomeActivity extends BaseActivity implements HomeView{

    @Inject
    HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Umbrella.getInstance().getAppGraph().inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        homePresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        homePresenter.detachView();
    }

    @Override
    public void setTheme(float tempF) {
        if(tempF > homePresenter.TEMPERATURE_THRESHOLD){
            super.setTheme(R.style.WarmTheme);
        }else{
            super.setTheme(R.style.ColdTheme);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }
}
