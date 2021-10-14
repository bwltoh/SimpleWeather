package com.example.simpleweather.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CityAndCurrentConditionsRelation;
import com.example.simpleweather.model.CurrentWeatherConditions;

import java.util.List;

@Dao
public abstract class CityDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertNewCity(City city);

    @Query("SELECT * FROM city")
    abstract LiveData<List<City>> getAllCities();

    @Query("Select * FROM city where `Key` LIKE  :key")
    abstract List<City> getCity(String key);
    @Transaction
    void  insertIfNotFound(City city){
       List<City> list=getCity(city.getKey());

       if (list.isEmpty()){
           insertNewCity(city);
       }

    }

    @Delete()
    abstract void deleteCity(City city);
    @Transaction
    public void insertWeatherConditionsForCity(int cityId, CurrentWeatherConditions conditions){

        deleteWeatherConditionsForCity(cityId);
        conditions.setCity_id(cityId);
        insertWeatherConditions(conditions);

    }

    @Query("DELETE FROM current_conditions where city_id LIKE :cityId ")
    abstract void deleteWeatherConditionsForCity(int cityId);

    @Insert
    abstract void insertWeatherConditions(CurrentWeatherConditions conditions);

    @Transaction
    @Query("SELECT * FROM City")
    public abstract LiveData<List<CityAndCurrentConditionsRelation>> getCityAndWeatherConditions();

    @Query("Select * From current_conditions where city_id LIKE :cityId ")
    public abstract LiveData<CurrentWeatherConditions> getCurrentWeatherConditions(int cityId);
}
