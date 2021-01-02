package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class WindGust2 {

    @SerializedName("Speed")
    private Metric speed;

    public Metric getSpeed() {
        return speed;
    }
}
