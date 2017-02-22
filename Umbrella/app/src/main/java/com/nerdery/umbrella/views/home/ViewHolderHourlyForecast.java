package com.nerdery.umbrella.views.home;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.data.api.ApiManager;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.model.ForecastHour;
import com.nerdery.umbrella.widget.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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
                .load(ApiManager.getIconApi().getUrlForIcon(forecastHour.imageUrl, false))
                .error(R.drawable.ic_settings_white_24dp)
                .into(imageView);
        //ApiManager.getIconApi().getUrlForIcon()
    }
}
