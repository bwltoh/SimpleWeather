package com.example.simpleweather.views.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simpleweather.database.DatabaseRepository;
import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CityAndCurrentConditionsRelation;
import com.example.simpleweather.network.ApiResponse;
import com.example.simpleweather.network.NetworkRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    NetworkRepository networkRepository;
    DatabaseRepository databaseRepository;
    public SearchViewModel(@NonNull Application application) {
        super(application);

        networkRepository = NetworkRepository.getInstance(application);
        databaseRepository = DatabaseRepository.getInstance(application);
    }

    public LiveData<ApiResponse<List<City>>> getSearchResults(String apiKey, String searchText) {

        return networkRepository.getCityBySearch(apiKey, searchText);
    }

    public LiveData<ApiResponse<List<City>>> getCities(String apiKey, String searchText, boolean withDetails) {

        return networkRepository.getCities(apiKey, searchText, withDetails);
    }


    public LiveData<List<CityAndCurrentConditionsRelation>>  getCityWeather(){
        return databaseRepository.getCitiesAndWeatherConditions();
    }

    public LiveData<ApiResponse<City>> getCityByLocationKey(String locationKey, String apiKey, boolean withDetails) {
        return networkRepository.getCityDataByLocationKey(locationKey, apiKey, withDetails);
    }

    public void deleteCity(City city) {
        databaseRepository.deleteCity(city);
    }

    public void insertCity(City city) {
        databaseRepository.insertNewCity(city);
    }

}
