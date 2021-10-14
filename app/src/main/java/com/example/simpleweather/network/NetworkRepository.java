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
    private static NetworkRepository SInstance;
    Application        application;
    DatabaseRepository databaseRepository;

    private NetworkRepository(Application application) {
        this.application = application;
        databaseRepository = DatabaseRepository.getInstance(application);
    }

    public static NetworkRepository getInstance(Application application) {
        if (SInstance == null) {
            synchronized (NetworkRepository.class) {
                if (SInstance == null) {
                    SInstance = new NetworkRepository(application);
                }
            }
        }
        return SInstance;
    }


    //get city based on user geographical location
    public void getCityByGeoLocation(String apiKey, String latlog) {


        APIClient.getInstance(application).getApi().getLocationKeyByGeoposition(apiKey, latlog).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {


                    databaseRepository.insertNewCity(response.body());


                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {


            }
        });


    }

    //get list of cities when using autocomplete
    public LiveData<ApiResponse<List<City>>> getCityBySearch(String apiKey, String searchText) {
        final MutableLiveData<ApiResponse<List<City>>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi().getLocationKeySearch(apiKey, searchText).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                if (response.isSuccessful()) {

                    mutableLiveData.setValue(ApiResponse.create(response.body()));

                } else {

                    mutableLiveData.setValue(ApiResponse.<List<City>>failure("Somthing went wrong!"));
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

                mutableLiveData.setValue(ApiResponse.<List<City>>failure(t.getMessage()));
            }
        });

        return mutableLiveData;
    }


    public LiveData<ApiResponse<City>> getCityDataByLocationKey(String locationKey, String apiKey, boolean withDetails) {
        final MutableLiveData<ApiResponse<City>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi()
                .getCityByLocationKey(locationKey, apiKey, withDetails).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(ApiResponse.create(response.body()));

                } else mutableLiveData.setValue(ApiResponse.failure("Something went wrong!"));

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                mutableLiveData.setValue(ApiResponse.failure(t.getMessage()));

            }
        });

        return mutableLiveData;
    }

    //get Current Weather conditions
    public LiveData<ApiResponse<CurrentWeatherConditions>> getCurrentConditions(String locationKey, String apiKey, boolean withDetails, final int cityId) {
        final MutableLiveData<ApiResponse<CurrentWeatherConditions>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi().getCurrentConditions(locationKey, apiKey, withDetails).enqueue(new Callback<List<CurrentWeatherConditions>>() {
            @Override
            public void onResponse(Call<List<CurrentWeatherConditions>> call, Response<List<CurrentWeatherConditions>> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        mutableLiveData.setValue(ApiResponse.create(response.body().get(0)));
                    }
                    List<CurrentWeatherConditions> list = response.body();
                    databaseRepository.insertWeatherConditions(cityId, list.get(0));


                } else mutableLiveData.setValue(ApiResponse.failure("Somthing went wrong!"));

            }

            @Override
            public void onFailure(Call<List<CurrentWeatherConditions>> call, Throwable t) {
                mutableLiveData.setValue(ApiResponse.failure(t.getMessage()));
            }
        });

        return mutableLiveData;
    }

    public LiveData<ApiResponse<Daily>> getDailyForcasts(String key, String apiKey, boolean details, boolean metric) {
        final MutableLiveData<ApiResponse<Daily>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi().get5DayForecasts(key, apiKey, details, metric).enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(ApiResponse.create(response.body()));


                } else mutableLiveData.setValue(ApiResponse.failure("Something went wrong!"));
            }

            @Override
            public void onFailure(Call<Daily> call, Throwable t) {
                mutableLiveData.setValue(ApiResponse.failure(t.getMessage()));
            }
        });

        return mutableLiveData;
    }

    public LiveData<ApiResponse<List<HoulyForecasts>>> getHourlyForcasts(String key, String apiKey, boolean details, boolean metric) {
        final MutableLiveData<ApiResponse<List<HoulyForecasts>>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi().getHourlyForecasts(key, apiKey, details, metric).enqueue(new Callback<List<HoulyForecasts>>() {
            @Override
            public void onResponse(Call<List<HoulyForecasts>> call, Response<List<HoulyForecasts>> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(ApiResponse.create(response.body()));
                } else mutableLiveData.setValue(ApiResponse.failure("Somthing went wrong!"));
            }

            @Override
            public void onFailure(Call<List<HoulyForecasts>> call, Throwable t) {
                mutableLiveData.setValue(ApiResponse.failure(t.getMessage()));
            }
        });

        return mutableLiveData;
    }


    //not used
    public LiveData<ApiResponse<List<City>>> getCities(String apiKey, String searchText, boolean withDetails) {
        final MutableLiveData<ApiResponse<List<City>>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(ApiResponse.loading());
        APIClient.getInstance(application).getApi().getCities(apiKey, searchText, withDetails).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful())
                    mutableLiveData.setValue(ApiResponse.create(response.body()));

                else mutableLiveData.setValue(ApiResponse.failure("Somthing went wrong!"));

            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                mutableLiveData.setValue(ApiResponse.failure(t.getMessage()));
            }
        });

        return mutableLiveData;
    }
}
