package com.example.simpleweather.network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.simpleweather.database.DatabaseRepository;
import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CurrentWeatherConditions;
import com.example.simpleweather.model.Daily;
import com.example.simpleweather.model.HoulyForecasts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkRepository {

    Application application;
    DatabaseRepository databaseRepository;

    public NetworkRepository(Application application) {
        this.application=application;
        databaseRepository=DatabaseRepository.getInstance(application);
    }

    public LiveData<City> getCityByGeoLocation(String apiKey, String latlog){
        final MutableLiveData<City> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().getLocationKeyByGeoposition(apiKey, latlog).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());


                    databaseRepository.insertNewCity(response.body());

                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {


            }
        });

        return mutableLiveData;
    }

    //get list of cities when using autocomplete
    public LiveData<List<City>> getCityBySearch(String apiKey, String searchText){
        final MutableLiveData<List<City>> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().getLocationKeySearch(apiKey, searchText).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful())
                    mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }


    public LiveData<City> getCityDataByLocationKey(String locationKey, String apiKey, boolean withDetails){
        final MutableLiveData<City> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi()
                .getCityByLocationKey(locationKey, apiKey, withDetails).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());

                }

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {


            }
        });

        return mutableLiveData;
    }

    //get Current Weather conditions
    public LiveData<CurrentWeatherConditions> getCurrentConditions(String locationKey, String apiKey, boolean withDetails, final int cityId){
        final MutableLiveData<CurrentWeatherConditions> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().getCurrentConditions(locationKey, apiKey, withDetails).enqueue(new Callback<List<CurrentWeatherConditions>>() {
            @Override
            public void onResponse(Call<List<CurrentWeatherConditions>> call, Response<List<CurrentWeatherConditions>> response) {
                if (response.isSuccessful()) {
                    List<CurrentWeatherConditions> list=response.body();
                    mutableLiveData.setValue(list.get(0));
                    databaseRepository.insertWeatherConditions(cityId,list.get(0));
                }

            }

            @Override
            public void onFailure(Call< List<CurrentWeatherConditions>> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }

    public LiveData<Daily> getDailyForcasts(String key,String apiKey,boolean details,boolean metric){
        final MutableLiveData<Daily> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().get5DayForecasts(key,apiKey, details, metric).enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Daily> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }

    public LiveData<List<HoulyForecasts>> getHourlyForcasts(String key,String apiKey,boolean details,boolean metric ){
        final MutableLiveData<List<HoulyForecasts>> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().getHourlyForecasts(key,apiKey, details, metric).enqueue(new Callback<List<HoulyForecasts>>() {
            @Override
            public void onResponse(Call<List<HoulyForecasts>> call, Response<List<HoulyForecasts>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                 }
            }

            @Override
            public void onFailure(Call<List<HoulyForecasts>> call, Throwable t) {

             }
        });

        return mutableLiveData;
    }


    public LiveData<List<City>> getCities(String apiKey, String searchText,boolean withDetails){
        final MutableLiveData<List<City>> mutableLiveData=new MutableLiveData<>();
        APIClient.getInstance(application.getApplicationContext()).getApi().getCities(apiKey, searchText,withDetails).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful())
                    mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
             }
        });

        return mutableLiveData;
    }
}
