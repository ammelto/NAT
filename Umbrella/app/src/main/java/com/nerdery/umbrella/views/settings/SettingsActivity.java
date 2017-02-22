package com.nerdery.umbrella.views.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.BaseActivity;
import com.nerdery.umbrella.views.settings.preferences.PreferencesFragment;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class SettingsActivity extends BaseActivity implements SettingsView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Fragment fragment = new PreferencesFragment();
        attachFragment(R.id.container, fragment);

    }
}
