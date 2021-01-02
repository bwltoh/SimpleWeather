package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Speed {



    @SerializedName("Metric")
    private Metric metric;

    public Metric getMetric() {
        return metric;
    }


}
