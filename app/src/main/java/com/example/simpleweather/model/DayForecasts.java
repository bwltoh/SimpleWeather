package com.example.simpleweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DayForecasts implements Parcelable {

    public static final Creator<DayForecasts> CREATOR = new Creator<DayForecasts>() {
        @Override
        public DayForecasts createFromParcel(Parcel in) {
            return new DayForecasts(in);
        }

        @Override
        public DayForecasts[] newArray(int size) {
            return new DayForecasts[size];
        }
    };
    @SerializedName("EpochDate")
    private long time;
    @SerializedName("Sun")
    private Sun sun;
    @SerializedName("Moon")
    private Moon moon;
    @SerializedName("Temperature")
    private DayTemperature dayTemperature;
    @SerializedName("RealFeelTemperature")
    private DayTemperature realFeelTemprature;
    @SerializedName("Day")
    private Day day;
    @SerializedName("Night")
    private Day night;

    protected DayForecasts(Parcel in) {
        time = in.readLong();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public DayTemperature getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(DayTemperature dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public DayTemperature getRealFeelTemprature() {
        return realFeelTemprature;
    }

    public void setRealFeelTemprature(DayTemperature realFeelTemprature) {
        this.realFeelTemprature = realFeelTemprature;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Day getNight() {
        return night;
    }

    public void setNight(Day night) {
        this.night = night;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);

    }
}
