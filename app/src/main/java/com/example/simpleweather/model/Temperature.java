package com.example.simpleweather.model;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class Temperature {

    @Embedded
    @SerializedName("Metric")
    private Metric metric;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
