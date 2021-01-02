package com.example.simpleweather.model;

import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("Icon")
    private int icon;
    @SerializedName("IconPhrase")
    private String iconPhrase;
    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;
    @SerializedName("PrecipitationType")
    private String precipitationType;
    @SerializedName("ShortPhrase")
    private String shortPhrase;
    @SerializedName("LongPhrase")
    private String longPhrase;
    @SerializedName("PrecipitationProbability")
    private  int precipitationProbability;
    @SerializedName("ThunderstormProbability")
    private int thunderstormProbability;
    @SerializedName("RainProbability")
    private int rainProbability;
    @SerializedName("SnowProbability")
    private int snowProbability;
    @SerializedName("IceProbability")
    private  int iceProbability;
    @SerializedName("Wind")
    private Wind2 wind;
    @SerializedName("WindGust")
    private WindGust2 windGust;
    @SerializedName("Rain")
    private Metric rain;
    @SerializedName("Snow")
    private Metric snow;
    @SerializedName("Ice")
    private Metric ice;


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
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

    public String getShortPhrase() {
        return shortPhrase;
    }

    public void setShortPhrase(String shortPhrase) {
        this.shortPhrase = shortPhrase;
    }

    public String getLongPhrase() {
        return longPhrase;
    }

    public void setLongPhrase(String longPhrase) {
        this.longPhrase = longPhrase;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(int thunderstormProbability) {
        this.thunderstormProbability = thunderstormProbability;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }

    public int getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(int snowProbability) {
        this.snowProbability = snowProbability;
    }

    public int getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(int iceProbability) {
        this.iceProbability = iceProbability;
    }

    public Wind2 getWind() {
        return wind;
    }

    public void setWind(Wind2 wind) {
        this.wind = wind;
    }

    public WindGust2 getWindGust() {
        return windGust;
    }

    public void setWindGust(WindGust2 windGust) {
        this.windGust = windGust;
    }

    public Metric getRain() {
        return rain;
    }

    public void setRain(Metric rain) {
        this.rain = rain;
    }

    public Metric getSnow() {
        return snow;
    }

    public void setSnow(Metric snow) {
        this.snow = snow;
    }

    public Metric getIce() {
        return ice;
    }

    public void setIce(Metric ice) {
        this.ice = ice;
    }
}
