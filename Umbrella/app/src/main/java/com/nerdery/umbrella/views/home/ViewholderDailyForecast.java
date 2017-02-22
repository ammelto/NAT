package com.nerdery.umbrella.views.home;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.model.ForecastDay;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class ViewHolderDailyForecast extends RecyclerView.ViewHolder {

    private final int HOURLY_COLUMN_SPAN = 4;

    @BindView(R.id.daily_forecast_text)
    TextView textView;

    @BindView(R.id.hourly_forecast_recycler)
    RecyclerView recyclerView;

    private AdapterHourlyForecast adapterHourlyForecast;

    public ViewHolderDailyForecast(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), this.HOURLY_COLUMN_SPAN));

        adapterHourlyForecast = new AdapterHourlyForecast();
        recyclerView.setAdapter(adapterHourlyForecast);

    }

    public void bind(ForecastDay forecastDay){
        textView.setText(forecastDay.getDay());
        adapterHourlyForecast.swapData(forecastDay.getForecastHours());
    }
}
