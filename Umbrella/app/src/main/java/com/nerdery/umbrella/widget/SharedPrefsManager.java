package com.nerdery.umbrella.widget;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class SharedPrefsManager {
    public static final String ZIP = "zip";
    public static final String UNITS = "units";
    public static final int ZIP_EXISTS = -1;
    public static final int ZIP_DEFAULT = 74112;
    public static final String IMPERIAL_UNITS = "imperial";
    public static final String METRIC_UNITS = "metric";
    public static final int TEMPERATURE_THRESHOLD = 60;

    private SharedPreferences sharedPreferences;

    public SharedPrefsManager(Application application){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public String getValue(String key, String value){
        return sharedPreferences.getString(key,value);
    }

    public int getValue(String key, int value){
        return sharedPreferences.getInt(key,value);
    }

}
