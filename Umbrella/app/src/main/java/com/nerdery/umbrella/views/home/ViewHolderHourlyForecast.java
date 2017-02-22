package com.nerdery.umbrella.views.home;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.model.ForecastHour;
import com.squareup.picasso.Picasso;

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

    public ViewHolderHourlyForecast(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(ForecastHour forecastHour){
        cellHourTextView.setText(forecastHour.hour);
        cellTemperatureTextView.setText(forecastHour.temperature);
        Picasso.with(itemView.getContext())
                .load(ApiManager.getIconApi().getUrlForIcon(forecastHour.imageUrl, forecastHour.isLocalDailyMax || forecastHour.isLocalDailyMin))
                .error(R.drawable.ic_settings_white_24dp)
                .into(imageView);

        int color;
        if(forecastHour.isLocalDailyMin) color = R.color.weather_cool;
        else if(forecastHour.isLocalDailyMax) color = R.color.weather_warm;
        else color = R.color.text_default;

        color = Umbrella.getInstance().getResources().getColor(color);
        imageView.setColorFilter(color);
        cellHourTextView.setTextColor(color);
        cellTemperatureTextView.setTextColor(color);
        
    }
}
