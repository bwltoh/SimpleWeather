package com.example.simpleweather.network;

import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CurrentWeatherConditions;
import com.example.simpleweather.model.Daily;
import com.example.simpleweather.model.DayForecasts;
import com.example.simpleweather.model.HoulyForecasts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Content-Type:application/json", "Cache-Control: no-cache"})
    @GET("locations/v1/cities/geoposition/search")
    Call<City> getLocationKeyByGeoposition(@Query("apikey") String apiKey, @Query("q") String lanlag);


    @Headers({"Content-Type:application/json", "Cache-Control: no-cache"})
    @GET("locations/v1/cities/autocomplete")
    Call<List<City>> getLocationKeySearch(@Query("apikey") String apiKey, @Query("q") String searchText);


    @Headers({"Content-Type:application/json"})
    @GET("locations/v1/{location_key}")
    Call<City> getCityByLocationKey(@Path("location_key") String locationey, @Query("apikey") String apiKey, @Query("details") boolean withDetails);


    @Headers({"Content-Type:application/json"})
    @GET("currentconditions/v1/{location_key}")
    Call<List<CurrentWeatherConditions>> getCurrentConditions(@Path("location_key") String locationey, @Query("apikey") String apiKey, @Query("details") boolean withDetails);


    @Headers({"Content-Type:application/json"})
    @GET("forecasts/v1/daily/5day/{location_key}")
    Call<Daily> get5DayForecasts(@Path("location_key") String locationey, @Query("apikey") String apiKey, @Query("details") boolean withDetails, @Query("metric") boolean withMetric);


    @Headers({"Content-Type:application/json"})
    @GET("forecasts/v1/hourly/12hour/{location_key}")
    Call<List<HoulyForecasts>> getHourlyForecasts(@Path("location_key") String locationey, @Query("apikey") String apiKey, @Query("details") boolean withDetails, @Query("metric") boolean withMetric);


    //not used
    @Headers({"Content-Type:application/json"})
    @GET("locations/v1/cities/search")
    Call<List<City>> getCities(@Query("apikey") String apiKey, @Query("q") String searchText, @Query("details") boolean withDetails);


    @Headers({"Content-Type:application/json"})
    @GET("forecasts/v1/daily/1day/{location_key}")
    Call<DayForecasts> getDailyForecasts(@Path("location_key") String locationey, @Query("apikey") String apiKey, @Query("details") boolean withDetails, @Query("metric") boolean withMetric);

}
