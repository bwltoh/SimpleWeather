package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Direction {



    @SerializedName("Degrees")
    private  int degrees;
    @SerializedName("Localized")
    private String localized;
    @SerializedName("English")
    private String english;

    public int getDegrees() {
        return degrees;
    }

    public String getLocalized() {
        return localized;
    }

    public String getEnglish() {
        return english;
    }


    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public void setLocalized(String localized) {
        this.localized = localized;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
