package com.example.simpleweather.model;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class WindGust {
    @Embedded
    @SerializedName("Speed")
    private Speed speed;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
}
