package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {

    @SerializedName("DailyForecasts")
    private List<DayForecasts> dayForecastsList;

    public List<DayForecasts> getDayForecastsList() {
        return dayForecastsList;
    }

    public void setDayForecastsList(List<DayForecasts> dayForecastsList) {
        this.dayForecastsList = dayForecastsList;
    }
}
