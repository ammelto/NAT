package com.nerdery.umbrella.views.settings.preferences;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.data.SharedPrefsManager;

/**
 * Created by Alexander Melton on 2/20/2017.
 *
 * Referenced from https://developer.android.com/guide/topics/ui/settings.html
 * Google recently said that it's probably better to create your own fragment instead of using the preference fragment
 * but this preferences screen is fairly simple so we can get away with this without too much trouble.
 * In a production application, I'd roll my own preferences fragment instead of extending theirs.
 */

public class PreferencesFragment extends PreferenceFragment{

    /**
     * Builds the preferences fragment from the settings menu resource
     *
     * @param savedInstanceState Saved instances bundle!
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);

        setPreferenceListener(SharedPrefsManager.UNITS);
        setPreferenceListener(SharedPrefsManager.ZIP);
    }

    /**
     * Builds the preference listener for the SharedPreferences
     *
     * @param key Key defined in my SharedPrefsManager
     */
    private void setPreferenceListener(String key){
        Preference preference = findPreference(key);
        preference.setOnPreferenceChangeListener(this::onPreferenceChange);

        String value = PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "");
        onPreferenceChange(preference, value);
    }

    /**
     * Listener for the preference manager
     *
     * @param preference Preference which is changed
     * @param o Data object corresponding to the preference
     * @return Boolean callback
     */
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
