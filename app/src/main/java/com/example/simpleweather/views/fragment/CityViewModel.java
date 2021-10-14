package com.example.simpleweather.views.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.simpleweather.database.DatabaseRepository;
import com.example.simpleweather.model.CurrentWeatherConditions;
import com.example.simpleweather.model.Daily;
import com.example.simpleweather.model.DayForecasts;
import com.example.simpleweather.model.HoulyForecasts;
import com.example.simpleweather.network.ApiResponse;
import com.example.simpleweather.network.NetworkRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityViewModel extends AndroidViewModel {
    NetworkRepository  networkRepository;
    DatabaseRepository databaseRepository;

    public CityViewModel(@NonNull Application application) {
        super(application);
        networkRepository = NetworkRepository.getInstance(application);
        databaseRepository = DatabaseRepository.getInstance(application);
    }

    public LiveData<ApiResponse<CurrentWeatherConditions>> getConditions(String locationKey, String apiKey, boolean details, int cityId) {

        return networkRepository.getCurrentConditions(locationKey, apiKey, details, cityId);
    }


    public LiveData<ApiResponse<List<HoulyForecasts>>> getForecastsHourly(String key, String apiKey, boolean details, boolean metric) {
        //  filter hours that go past
        return Transformations.map(networkRepository.getHourlyForcasts(key, apiKey, details, metric), new Function<ApiResponse<List<HoulyForecasts>>, ApiResponse<List<HoulyForecasts>>>() {
            @Override
            public ApiResponse<List<HoulyForecasts>> apply(ApiResponse<List<HoulyForecasts>> input) {
                List<HoulyForecasts> filteredHoulyForecasts = new ArrayList<>();
                Date today = new Date();
                long nhour = today.getTime() / (1000 * 60 * 60);
                if (input.getData() != null) {
                    for (int i = 0; i < input.getData().size(); i++) {
                        long hour = input.getData().get(i).getTime() / (60 * 60);

                        if (hour >= nhour) {
                            filteredHoulyForecasts.add(input.getData().get(i));
                        }
                    }
                    input.setData(filteredHoulyForecasts);
                }

                return input;
            }
        });
    }

    public LiveData<ApiResponse<Daily>> getForecastsDaily(String key, String apiKey, boolean details, boolean metric) {
        //  filter days that go past
        return Transformations.map(networkRepository.getDailyForcasts(key, apiKey, details, metric), new Function<ApiResponse<Daily>, ApiResponse<Daily>>() {
            @Override
            public ApiResponse<Daily> apply(ApiResponse<Daily> input) {

                List<DayForecasts> filteredDailyForecasts = new ArrayList<>();
                Date today = new Date();
                long yesterday = today.getTime() / (1000 * 60 * 60 * 24) - 1;
                if (input.getData() != null) {
                    for (int d = 0; d < input.getData().getDayForecastsList().size(); d++) {
                        long day = input.getData().getDayForecastsList().get(d).getTime() / (60 * 60 * 24);
                        if (day >= yesterday)
                            filteredDailyForecasts.add(input.getData().getDayForecastsList().get(d));
                    }
                    input.getData().setDayForecastsList(filteredDailyForecasts);
                }
                return input;
            }
        });
    }


    public LiveData<CurrentWeatherConditions> getWeatherConditions(int cityId) {
        return databaseRepository.getCurrentWeatherConditions(cityId);
    }

}
