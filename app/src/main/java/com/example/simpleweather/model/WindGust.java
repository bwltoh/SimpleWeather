package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class WindGust {
    @SerializedName("Speed")
    private Speed speed;

    public Speed getSpeed() {
        return speed;
    }



}
