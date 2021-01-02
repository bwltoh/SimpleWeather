package com.example.simpleweather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simpleweather.model.CurrentWeatherConditions;
import com.example.simpleweather.model.Daily;
import com.example.simpleweather.model.HoulyForecasts;
import com.example.simpleweather.network.NetworkRepository;

import java.util.List;

public class CityViewModel extends AndroidViewModel {
    NetworkRepository networkRepository;
    public CityViewModel(@NonNull Application application) {
        super(application);
        networkRepository=new NetworkRepository(application);

    }

    public LiveData<CurrentWeatherConditions> getConditions(String locationKey,String apiKey,boolean details,int cityId){

        return networkRepository.getCurrentConditions(locationKey, apiKey, details,cityId);
    }


    public LiveData<List<HoulyForecasts>> getForecastsHourly(String key,String apiKey,boolean details,boolean metric){
        return networkRepository.getHourlyForcasts(key,apiKey, details, metric);
    }

    public LiveData<Daily> getForecastsDaily(String key,String apiKey,boolean details,boolean metric){
        return networkRepository.getDailyForcasts(key,apiKey, details, metric);
    }


}
