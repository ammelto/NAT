package com.nerdery.umbrella.data.model;

/**
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

    public void setFull(String full) {
        this.full = full;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
