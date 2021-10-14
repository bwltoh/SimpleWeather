package com.example.simpleweather.views.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simpleweather.database.DatabaseRepository;
import com.example.simpleweather.model.City;
import com.example.simpleweather.network.NetworkRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final DatabaseRepository databaseRepository;
    private final NetworkRepository  networkRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        databaseRepository = DatabaseRepository.getInstance(application);
        networkRepository = NetworkRepository.getInstance(application);
    }


    public LiveData<List<City>> getCities() {

        return databaseRepository.getAllCities();
    }


    public void getCityByGeoLocation(String apiKey, String latlag) {
        networkRepository.getCityByGeoLocation(apiKey, latlag);
    }
}
