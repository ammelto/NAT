package com.nerdery.umbrella.data.model;

/**
 * Edit: Only have getters wither private member variables here for the same reason as CurrentObservation
 * Represents a "display_location" returned from Weather Underground
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 * @author bherbst
 */
public class DisplayLocation {
    private String full;
    private String city;
    private String state;
    private String state_name;
    private String country;
    private String zip;

    public String getFull() {
        return full;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getState_name() {
        return state_name;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }
}
