package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class TimeZone {
    @SerializedName("Code")
    private String code;
    @SerializedName("Name")
    private String Name;
    @SerializedName("GmtOffset")
    private float    gmtOffset;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(float gmtOffset) {
        this.gmtOffset = gmtOffset;
    }
}
