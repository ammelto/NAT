package com.nerdery.umbrella.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.BaseRecyclerAdapter;
import com.nerdery.umbrella.data.model.ForecastDay;

import java.util.List;
import java.util.PrimitiveIterator;

import timber.log.Timber;

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
        Timber.d("Binding" + position);
        holder.bind(get(position));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_daily_forecast;
    }
}
