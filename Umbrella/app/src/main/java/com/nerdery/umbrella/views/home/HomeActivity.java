package com.nerdery.umbrella.views.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.BaseActivity;
import com.nerdery.umbrella.data.model.ForecastDay;
import com.nerdery.umbrella.data.SharedPrefsManager;
import com.nerdery.umbrella.views.settings.SettingsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Implementation of the view for the Home activity.
 * Strictly view related logic goes here. Everything else should be offloaded to the presenter
 */

public class HomeActivity extends BaseActivity implements HomeView{

    @Inject
    HomePresenter homePresenter;

    private ActionBar actionBar;

    private AdapterDailyForecast adapterDailyForecast;

    @BindView(R.id.appbar)
    AppBarLayout appBar;

    @BindView(R.id.temperature_current)
    TextView currentTemperatureView;

    @BindView(R.id.temperature_flavor)
    TextView textView;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.daily_forecast_recycler)
    RecyclerView dailyRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Umbrella.getInstance().getAppGraph().inject(this);
        actionBar = getSupportActionBar();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dailyRecycler.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dailyRecycler.getContext(),
                linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this ,R.drawable.card_gutter_12dp));
        dailyRecycler.addItemDecoration(dividerItemDecoration);

        adapterDailyForecast = new AdapterDailyForecast();
        dailyRecycler.setAdapter(adapterDailyForecast);

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

    /**
     * Compares the temperature to the threshold from the shared preferences manager then changes the toolbar color to match
     * The temperature threshold exists in the shared preferences right now incase in the future we want the option
     * for the user to change the temperature threshold.
     *
     * @param temp Temperature as a float
     */
    @Override
    public void setActionBarColor(float temp) {
        int color = Math.round(temp) < SharedPrefsManager.TEMPERATURE_THRESHOLD ? R.color.weather_cool : R.color.weather_warm;
        int resource = ContextCompat.getColor(this, color);
        ColorDrawable colorDrawable = new ColorDrawable(resource);

        actionBar.setBackgroundDrawable(colorDrawable);
        appBar.setBackgroundColor(resource);
    }

    /**
     * Sets the area name configured by the presenter
     *
     * @param name Full area name. This is [City, state abbreviation]
     */
    @Override
    public void setAreaName(String name) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(name);
        }
    }

    /**
     * Flavor text is the text which is to be displayed below the current temperature
     *
     * @param flavor String which is printed on the top appbar.
     */
    @Override
    public void setFlavorText(String flavor) {
        textView.setText(flavor);
    }

    /**
     * Sets the current temperature on the appbar.
     *
     * @param temp Current observation temperature
     */
    @Override
    public void setCurrentTemperature(float temp) {
        String temperature = String.format(getResources().getString(R.string.temperature_current), Math.round(temp));
        currentTemperatureView.setText(temperature);
    }

    /**
     * This is called when the zipcode is determined to be invalid by the presenter.
     * In that case, the view creates a snackbar which is displayed to the user
     */
    @Override
    public void onInvalidZip() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.invalid_zip_snack, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.invalid_zip_action, view -> openSettingsActivity());
        snackbar.show();
    }

    /**
     * Loads the organized ForecastDay list into the DailyForecast adapter.
     *
     * @param forecastDays List of ForecastDay objects
     */
    @Override
    public void fillForecast(List<ForecastDay> forecastDays) {
        adapterDailyForecast.swapData(forecastDays);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_settings) openSettingsActivity();
        return super.onOptionsItemSelected(item);
    }

    private void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
