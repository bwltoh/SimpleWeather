package com.example.simpleweather.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "current_conditions",foreignKeys = { @ForeignKey(entity = City.class,
        parentColumns = "id",
        childColumns = "city_id",
        onDelete = ForeignKey.CASCADE)})
public class CurrentWeatherConditions {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private int city_id;
    @SerializedName("EpochTime")
    private long time;
    @SerializedName("WeatherText")
    private String weatherText;
    @SerializedName("WeatherIcon")
    private int icon;
    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;
    @SerializedName("PrecipitationType")
    private String precipitationType;
    @SerializedName("IsDayTime")
    private boolean isDayTime;
    @Embedded(prefix = "Temperature")
    @SerializedName("Temperature")
    private Temperature temperature;
    @Embedded(prefix = "RealFeelTemperature")
    @SerializedName("RealFeelTemperature")
    private Temperature realFeelTemperature;
    @SerializedName("RelativeHumidity")
    private int relativeHumidity;
    @Embedded(prefix = "Wind")
    @SerializedName("Wind")
    private Wind wind;
    @Embedded(prefix = "WindGust")
    @SerializedName("WindGust")
    private WindGust windGust;
    @Embedded(prefix = "WindChillTemperature")
    @SerializedName("WindChillTemperature")
    private Temperature windChillTemperature;
    @Embedded(prefix = "Pressure")
    @SerializedName("Pressure")
    private Pressure pressure;
    @SerializedName("UVIndex")
    private int uvIndex;
    @SerializedName("UVIndexText")
    private String uvIndexText;
    @SerializedName("CloudCover")
    private int cloudCover ;

    public CurrentWeatherConditions() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    public boolean isDayTime() {
        return isDayTime;
    }

    public void setDayTime(boolean dayTime) {
        isDayTime = dayTime;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(Temperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public WindGust getWindGust() {
        return windGust;
    }

    public void setWindGust(WindGust windGust) {
        this.windGust = windGust;
    }

    public Temperature getWindChillTemperature() {
        return windChillTemperature;
    }

    public void setWindChillTemperature(Temperature windChillTemperature) {
        this.windChillTemperature = windChillTemperature;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getUvIndexText() {
        return uvIndexText;
    }

    public void setUvIndexText(String uvIndexText) {
        this.uvIndexText = uvIndexText;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
