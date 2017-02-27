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
 *
 * Adapter for the DailyForecast recycler view.
 */

public class AdapterDailyForecast extends BaseRecyclerAdapter<ForecastDay, ViewHolderDailyForecast>{

    /**
     *
     * @param v Current view used to inflate the viewholder
     * @return Returns the viewholder corresponding to the adapter
     */
    @Override
    public ViewHolderDailyForecast inflateViewHolder(View v) {
        return new ViewHolderDailyForecast(v);
    }

    /**
     * Binds data to the view holder when data is added or removed from the adapter
     *
     * @param holder Viewholder which this adapter is attached to
     * @param position Position in the list of elements which this adapter is handling
     */
    @Override
    public void onBindViewHolder(ViewHolderDailyForecast holder, int position) {
        holder.bind(get(position));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_daily_forecast;
    }
}
