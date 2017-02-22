package com.nerdery.umbrella.views.home;

import android.view.View;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.BaseRecyclerAdapter;
import com.nerdery.umbrella.data.model.ForecastCondition;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class AdapterHourlyForecast extends BaseRecyclerAdapter<ForecastCondition, ViewHolderHourlyForecast> {

    @Override
    public ViewHolderHourlyForecast inflateViewHolder(View v) {
        return new ViewHolderHourlyForecast(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderHourlyForecast holder, int position) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_daily_forecast;
    }
}
