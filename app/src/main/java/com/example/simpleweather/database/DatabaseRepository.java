package com.example.simpleweather.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CityAndCurrentConditionsRelation;
import com.example.simpleweather.model.CurrentWeatherConditions;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseRepository {



    private static DatabaseRepository SInstance;
    private final CityDao cityDao;
    private LiveData<List<City>> cities;

    private DatabaseRepository(Application application){
        WeatherDatabase database=WeatherDatabase.getInstance(application);
        cityDao=database.cityDao();
        cities=cityDao.getAllCities();

    }

    public static DatabaseRepository getInstance(Application application){
        if(SInstance==null){
            synchronized (DatabaseRepository.class){
                if (SInstance==null){
                    SInstance=new DatabaseRepository(application);
                }
            }
        }
        return SInstance;
    }

    public LiveData<List<City>> getAllCities(){
        return cities;
    }

    public LiveData<List<CityAndCurrentConditionsRelation>> getCitiesAndWeatherConditions(){
        return cityDao.getCityAndWeatherConditions();
    }

    public void insertNewCity (final City city){
        provideExecutor().execute(new Runnable() {
            @Override
            public void run() {
                cityDao.insertIfNotFound(city);
            }
        });

    }

    public void deleteCity(final City city) {
        provideExecutor().execute(new Runnable() {
            @Override
            public void run() {
                cityDao.deleteCity(city);
            }
        });
    }

    public LiveData<CurrentWeatherConditions> getCurrentWeatherConditions(int cityId) {
        return cityDao.getCurrentWeatherConditions(cityId);
    }

    public void insertWeatherConditions(final int cityId, final CurrentWeatherConditions conditions) {
        provideExecutor().execute(new Runnable() {
            @Override
            public void run() {
                cityDao.insertWeatherConditionsForCity(cityId, conditions);
            }
        });
    }

    private Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
