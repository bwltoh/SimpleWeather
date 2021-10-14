package com.example.simpleweather.model;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @Embedded
    @SerializedName("Direction")
    private Direction direction;
    @Embedded
    @SerializedName("Speed")
    private Speed     speed;

    public Direction getDirection() {
        return direction;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
}
