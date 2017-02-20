package com.nerdery.umbrella.views.settings.PreferencesFragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.utils.SharedPrefsManager;

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

        setPreferenceListener(SharedPrefsManager.UNITS);
        setPreferenceListener(SharedPrefsManager.ZIP);
    }

    private void setPreferenceListener(String key){
        Preference preference = findPreference(key);
        preference.setOnPreferenceChangeListener(this);

        String value = PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "");
        onPreferenceChange(preference, value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String value = o.toString();
        
        switch (preference.getKey()){
            case SharedPrefsManager.UNITS:
                ListPreference listPreference = (ListPreference) preference;
                int i = listPreference.findIndexOfValue(value);
                preference.setSummary(listPreference.getEntries()[i]);
                break;
            case SharedPrefsManager.ZIP:
                preference.setSummary(value);
                break;
        }
        return true;
    }
}
