package com.example.simpleweather.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CurrentWeatherConditions;

@Database(entities={City.class, CurrentWeatherConditions.class},version=1,exportSchema=false)
public abstract class WeatherDatabase extends RoomDatabase {


    private static final Object lock=new Object();
    private static final String DATABASE_NAME="weather.db";
    private static WeatherDatabase sInstance;

    public static WeatherDatabase getInstance(Context context){
        if(sInstance==null){
            synchronized (lock){
                sInstance= Room.databaseBuilder(
                        context.getApplicationContext(),
                        WeatherDatabase.class,
                        WeatherDatabase.DATABASE_NAME
                ).build();
            }
        }
        return sInstance;
    }

    public abstract  CityDao cityDao();
}
