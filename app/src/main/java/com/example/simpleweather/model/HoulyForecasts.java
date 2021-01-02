package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class HoulyForecasts {


    @SerializedName("EpochDateTime")
    private long time;
    @SerializedName("WeatherIcon")
    private int icon;
    @SerializedName("IconPhrase")
    private String phrase;
    @SerializedName("IsDaylight")
    private boolean isDayLight;
    @SerializedName("Temperature")
    private Metric temprature;
    @SerializedName("Wind")
    private Wind2 wind;
    @SerializedName("RainProbability")
    private int rainProbability;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public boolean isDayLight() {
        return isDayLight;
    }

    public void setDayLight(boolean dayLight) {
        isDayLight = dayLight;
    }

    public Metric getTemprature() {
        return temprature;
    }

    public void setTemprature(Metric temprature) {
        this.temprature = temprature;
    }

    public Wind2 getWind() {
        return wind;
    }

    public void setWind(Wind2 wind) {
        this.wind = wind;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }
}
