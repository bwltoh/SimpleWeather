package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Sun {

    @SerializedName("EpochRise")
    private long sunRiseTime;
    @SerializedName("EpochSet")
    private long sunSetTime;

    public long getSunRiseTime() {
        return sunRiseTime;
    }

    public void setSunRiseTime(long sunRiseTime) {
        this.sunRiseTime = sunRiseTime;
    }

    public long getSunSetTime() {
        return sunSetTime;
    }

    public void setSunSetTime(long sunSetTime) {
        this.sunSetTime = sunSetTime;
    }
}
