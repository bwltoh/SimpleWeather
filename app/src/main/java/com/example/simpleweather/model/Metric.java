package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Metric {
    @SerializedName("Value")
    private double value;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("UnitType")
    private int    unitType;



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}
