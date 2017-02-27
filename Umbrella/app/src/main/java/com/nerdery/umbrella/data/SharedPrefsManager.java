package com.nerdery.umbrella.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alexander Melton on 2/18/2017.
 *
 * Manager fpr shared preferences
 * This holds all of the final string keys as well as default values for the shared preferences.
 * This also returns any of the values needed from the shared preferences and takes care of the boilerplate
 * so the actual implementation doesn't get muddied up with shared prefs code.
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

    public int getZip(){
        String notFound = String.valueOf(SharedPrefsManager.ZIP_EXISTS);
        String zip = sharedPreferences.getString(SharedPrefsManager.ZIP, notFound);
        return zip.equals(notFound) ? SharedPrefsManager.ZIP_DEFAULT : Integer.parseInt(zip);
    }


    public String getUnits(){
        return sharedPreferences.getString(SharedPrefsManager.UNITS, "imperial");
    }


}
