package com.example.simpleweather.model;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

public class Country{
    @SerializedName("ID")
    private String countryID;
    private String LocalizedName;


    public Country() {
    }

    @Ignore
    public Country(String ID, String localizedName) {
        this.countryID = ID;
        this.LocalizedName = localizedName;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getLocalizedName() {
        return LocalizedName;
    }

    public void setLocalizedName(String localizedName) {
        LocalizedName = localizedName;
    }
}
