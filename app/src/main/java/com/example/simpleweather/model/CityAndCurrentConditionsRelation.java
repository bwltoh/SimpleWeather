package com.example.simpleweather.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CityAndCurrentConditionsRelation {

    @Embedded
    public City city;
    @Relation(parentColumn = "id",
    entityColumn = "city_id")
    public CurrentWeatherConditions conditions;
}
