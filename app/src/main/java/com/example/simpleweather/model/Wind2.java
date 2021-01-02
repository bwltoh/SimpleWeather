package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Wind2 {

    @SerializedName("Direction")
    private Metric direction;
    @SerializedName("Speed")
    private Metric speed;

    public Metric getDirection() {
        return direction;
    }

    public Metric getSpeed() {
        return speed;
    }
}
