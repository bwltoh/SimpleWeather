package com.example.simpleweather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simpleweather.database.DatabaseRepository;
import com.example.simpleweather.model.City;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private DatabaseRepository databaseRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        databaseRepository=DatabaseRepository.getInstance(application);
    }


    public LiveData<List<City>> getCities(){

        return databaseRepository.getAllCities();
    }


}
