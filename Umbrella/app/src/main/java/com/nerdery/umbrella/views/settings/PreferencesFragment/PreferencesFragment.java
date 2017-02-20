package com.nerdery.umbrella.views.settings.PreferencesFragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nerdery.umbrella.R;

import java.util.prefs.PreferenceChangeListener;

/**
 * Created by Alexander Melton on 2/20/2017.
 *
 * Referenced from https://developer.android.com/guide/topics/ui/settings.html
 */

public class PreferencesFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {

        return false;
    }
}
