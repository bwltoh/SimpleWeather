package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("Direction")
    private Direction direction;
    @SerializedName("Speed")
    private Speed speed;

    public Direction getDirection() {
        return direction;
    }

    public Speed getSpeed() {
        return speed;
    }
}
