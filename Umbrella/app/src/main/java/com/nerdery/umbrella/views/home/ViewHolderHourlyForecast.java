package com.nerdery.umbrella.views.home;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.api.IconApi;
import com.nerdery.umbrella.data.model.ForecastHour;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class ViewHolderHourlyForecast extends RecyclerView.ViewHolder {

    @BindView(R.id.text_cell_hour)
    TextView cellHourTextView;

    @BindView(R.id.image_cell)
    ImageView imageView;

    @BindView(R.id.text_cell_temperature)
    TextView cellTemperatureTextView;

    @Inject
    IconApi iconApi;

    public ViewHolderHourlyForecast(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        Umbrella.getInstance().getAppGraph().inject(this);
    }

    public void bind(ForecastHour forecastHour){
        cellHourTextView.setText(forecastHour.getHour());
        cellTemperatureTextView.setText(forecastHour.getTemperature());
        Picasso.with(itemView.getContext())
                .load(iconApi.getUrlForIcon(forecastHour.getImageUrl(), forecastHour.getLocalDailyMax() || forecastHour.getLocalDailyMin()))
                .error(R.drawable.ic_settings_white_24dp)
                .into(imageView);

        int color = R.color.text_default;
        if(forecastHour.getLocalDailyMin()) color = R.color.weather_cool;
        else if(forecastHour.getLocalDailyMax()) color = R.color.weather_warm;

        color = ContextCompat.getColor(itemView.getContext(), color);
        imageView.setColorFilter(color);
        cellHourTextView.setTextColor(color);
        cellTemperatureTextView.setTextColor(color);

    }
}
