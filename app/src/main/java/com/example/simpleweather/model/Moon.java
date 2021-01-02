package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Moon {

    @SerializedName("EpochRise")
    private long moonRiseTime;
    @SerializedName("EpochSet")
    private long moonSetTime;
    @SerializedName("Phase")
    private String moonPhase;
    @SerializedName("Age")
    private int moonAge;

    public long getMoonRiseTime() {
        return moonRiseTime;
    }

    public void setMoonRiseTime(long moonRiseTime) {
        this.moonRiseTime = moonRiseTime;
    }

    public long getMoonSetTime() {
        return moonSetTime;
    }

    public void setMoonSetTime(long moonSetTime) {
        this.moonSetTime = moonSetTime;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public int getMoonAge() {
        return moonAge;
    }

    public void setMoonAge(int moonAge) {
        this.moonAge = moonAge;
    }
}

