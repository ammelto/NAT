package com.nerdery.umbrella.views.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;
import com.nerdery.umbrella.base.BaseActivity;
import com.nerdery.umbrella.views.settings.SettingsActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class HomeActivity extends BaseActivity implements HomeView{

    @Inject
    HomePresenter homePresenter;

    ActionBar actionBar;

    @BindView(R.id.appbar)
    AppBarLayout appBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Umbrella.getInstance().getAppGraph().inject(this);

        actionBar = getSupportActionBar();
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
    public void setActionBarColor(int color) {
        //Using deprecated method since minimum support version is lower than the version which deprecated this function
        int resource = getResources().getColor(R.color.weather_cool);
        ColorDrawable colorDrawable = new ColorDrawable(resource);

        actionBar.setBackgroundDrawable(colorDrawable);
        appBar.setBackgroundColor(resource);
    }

    @Override
    public void setAreaName(String name) {
        if(actionBar != null){
            actionBar.setTitle(name);
        }
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
        if(item.getItemId() == R.id.toolbar_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
