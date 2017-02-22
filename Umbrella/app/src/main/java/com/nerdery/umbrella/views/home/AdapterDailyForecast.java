package com.nerdery.umbrella.views.home;

import android.view.View;
import android.widget.BaseAdapter;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.BaseRecyclerAdapter;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.WeatherData;

import java.util.List;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class AdapterDailyForecast extends BaseRecyclerAdapter<ForecastDay, ViewHolderDailyForecast>{

    @Override
    public ViewHolderDailyForecast inflateViewHolder(View v) {
        return new ViewHolderDailyForecast(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderDailyForecast holder, int position) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_daily_forecast;
    }
}
