package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class DayTemperature {

    @SerializedName("Minimum")
    private Metric minTemp;
    @SerializedName("Maximum")
    private Metric maxTemp;

    public Metric getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Metric minTemp) {
        this.minTemp = minTemp;
    }

    public Metric getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Metric maxTemp) {
        this.maxTemp = maxTemp;
    }
}
