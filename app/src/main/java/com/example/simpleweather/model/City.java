package com.example.simpleweather.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "city")
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String  Key;
    private String  Type;
    private int     Rank;
    @SerializedName("LocalizedName")
    private String  cityLocalizedName;
    @Embedded
    private Country Country;
    @SerializedName("TimeZone")
    @Embedded
    private TimeZone timeZone;

    public City() {
    }

    @Ignore
    public City(String key, String type, int rank, String localizedName, com.example.simpleweather.model.Country country) {
        this.Key = key;
        this.Type = type;
        this.Rank = rank;
        this.cityLocalizedName = localizedName;
        this.Country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getCityLocalizedName() {
        return cityLocalizedName;
    }

    public void setCityLocalizedName(String cityLocalizedName) {
        this.cityLocalizedName = cityLocalizedName;
    }

    public com.example.simpleweather.model.Country getCountry() {
        return Country;
    }

    public void setCountry(com.example.simpleweather.model.Country country) {
        Country = country;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}

