package com.nerdery.umbrella.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class SharedPrefsManager {
    private static final String ZIP_CODE = "umbrella.zip";
    private static final String UNITS = "umbrella.units";

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
