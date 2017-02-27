package com.nerdery.umbrella.views.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.base.BaseActivity;
import com.nerdery.umbrella.base.mvp.BaseView;
import com.nerdery.umbrella.views.settings.preferences.PreferencesFragment;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * This is just used to show the preferences fragment
 */

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Fragment fragment = new PreferencesFragment();
        attachFragment(R.id.container, fragment);

    }
}
