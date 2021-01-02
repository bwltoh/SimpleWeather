package com.example.simpleweather.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweather.R;
import com.example.simpleweather.adapter.DailyAdapter;
import com.example.simpleweather.adapter.HourlyAdapter;
import com.example.simpleweather.model.CurrentWeatherConditions;
import com.example.simpleweather.model.Daily;
import com.example.simpleweather.model.DayForecasts;
import com.example.simpleweather.model.HoulyForecasts;
import com.example.simpleweather.utils.Constants;
import com.example.simpleweather.utils.TimeUtil;
import com.example.simpleweather.viewmodel.CityViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class CityFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 ="param4";
    MaterialButtonToggleGroup group;
    NestedScrollView          nestedScrollView;
    private              int    position;
    private int city_id;
    private String        zoneId ;
    private CityViewModel mViewModel;
    private String        locationKey;
    private List<HoulyForecasts> houlyForecastsList;
    private List<DayForecasts>   dayForecastsList;
    private TextView             date, weatherText, curr_temp, wind, realFeelTemp, humidity,
            windGust, windChillTemp, precipitation, uvIndex, pressure;
    private ImageView icon, windDir;
    private RecyclerView  recyclerView;
    private HourlyAdapter hourlyAdapter;
    private DailyAdapter  dailyAdapter;

    public static CityFragment newInstance(String key,String zoneId, int pos,int cityId) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, key);
        args.putInt(ARG_PARAM2, pos);
        args.putString(ARG_PARAM3, zoneId);
        args.putInt(ARG_PARAM4,cityId);
        fragment.setArguments(args);
        return fragment;

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.city_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            locationKey = getArguments().getString(ARG_PARAM1);
            zoneId=getArguments().getString(ARG_PARAM3);
            city_id=getArguments().getInt(ARG_PARAM4);
            position = getArguments().getInt(ARG_PARAM2);

        }

        initView(view);
        initRec();

        hourlyAdapter = new HourlyAdapter(getActivity());
        dailyAdapter = new DailyAdapter(getActivity());
        recyclerView.setAdapter(hourlyAdapter);
        houlyForecastsList = new ArrayList<>();
        dayForecastsList = new ArrayList<>();

        group.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (group.getCheckedButtonId() == R.id.hourly_btn) {

                    recyclerView.setAdapter(hourlyAdapter);
                    hourlyAdapter.setHourlyForecastsList(houlyForecastsList);

                } else if (group.getCheckedButtonId() == R.id.daily_btn) {

                    recyclerView.setAdapter(dailyAdapter);
                    dailyAdapter.setDailyForecastsList(dayForecastsList);
                    dailyAdapter.setTimezone(zoneId);

                }
            }
        });


        mViewModel = new ViewModelProvider(this).get(String.valueOf(position),CityViewModel.class);

        //get Current weather conditions
        getCurrentWeatherConditions(locationKey, Constants.API_KEY, true,city_id);

        getHourlyWeatherForcasts(locationKey, Constants.API_KEY, true, true);

        getDailyWeatherForcasts(locationKey, Constants.API_KEY, true, true);

    }


    private void getCurrentWeatherConditions(String locationKey,String apiKey,boolean withDetails,int cityid){
        mViewModel.getConditions(locationKey, apiKey, withDetails,cityid).observe(getActivity(), new Observer<CurrentWeatherConditions>() {
            @Override
            public void onChanged(CurrentWeatherConditions currentWeatherConditions) {
                if (currentWeatherConditions != null) {


                    DefineBackgroundForWeatherConditions(currentWeatherConditions.getCloudCover(), currentWeatherConditions.isHasPrecipitation(),
                            currentWeatherConditions.getPrecipitationType(), currentWeatherConditions.isDayTime());
                    date.setText(TimeUtil.getDateWithDayName(currentWeatherConditions.getTime()));
                    weatherText.setText(currentWeatherConditions.getWeatherText());
                    curr_temp.setText(String.valueOf(currentWeatherConditions.getTemperature().getMetric().getValue()+" "+currentWeatherConditions.getTemperature().getMetric().getUnit()));
                    int weatherIcon = getResources().getIdentifier("p_" + String.valueOf(currentWeatherConditions.getIcon()), "drawable", getActivity().getPackageName());
                    icon.setBackgroundResource(weatherIcon);

                    int windDirection = currentWeatherConditions.getWind().getDirection().getDegrees();
                    windDir.setRotation((float) 180 + windDirection);

                    setTextValue(wind, currentWeatherConditions.getWind().getDirection().getEnglish() +
                            " " + currentWeatherConditions.getWind().getSpeed().getMetric().getValue() +
                            " " + currentWeatherConditions.getWind().getSpeed().getMetric().getUnit());

                    setTextValue(realFeelTemp, currentWeatherConditions.getRealFeelTemperature().getMetric().getValue() +
                            " " + currentWeatherConditions.getRealFeelTemperature().getMetric().getUnit());
                    setTextValue(humidity, currentWeatherConditions.getRelativeHumidity() + " %");
                    setTextValue(windGust, currentWeatherConditions.getWindGust().getSpeed().getMetric().getValue() +
                            " " + currentWeatherConditions.getWindGust().getSpeed().getMetric().getUnit());
                    setTextValue(precipitation, currentWeatherConditions.getPrecipitationType());
                    setTextValue(windChillTemp, currentWeatherConditions.getWindChillTemperature().getMetric().getValue() +
                            " " + currentWeatherConditions.getWindChillTemperature().getMetric().getUnit());
                    setTextValue(pressure, currentWeatherConditions.getPressure().getMetric().getValue() +
                            " " + currentWeatherConditions.getPressure().getMetric().getUnit());
                    setTextValue(uvIndex, currentWeatherConditions.getUvIndex() + " " + currentWeatherConditions.getUvIndexText());
                }

            }
        });
    }
    private void getHourlyWeatherForcasts(String locationKey,String apiKey,boolean withDetails,boolean isMetric){
        mViewModel.getForecastsHourly(locationKey, apiKey, withDetails, isMetric).observe(getActivity(), new Observer<List<HoulyForecasts>>() {
            @Override
            public void onChanged(List<HoulyForecasts> houlyForecasts) {
                if (houlyForecasts != null) {
                    houlyForecastsList = houlyForecasts;
                    hourlyAdapter.setHourlyForecastsList(houlyForecastsList);
                    hourlyAdapter.setTimezone(zoneId);
                }

            }
        });
    }

    private void getDailyWeatherForcasts(String locationKey,String apiKey,boolean withDetails,boolean isMetric){

        mViewModel.getForecastsDaily(locationKey, apiKey, withDetails, isMetric).observe(getActivity(), new Observer<Daily>() {
            @Override
            public void onChanged(Daily daily) {
                if (daily != null) {
                    dayForecastsList = daily.getDayForecastsList();

                }

            }
        });
    }

    private void setTextValue(TextView textView,String value){
        textView.setText(value);
    }

    private void initView(View view){

        date=view.findViewById(R.id.date);
        weatherText=view.findViewById(R.id.weather_text);
        curr_temp=view.findViewById(R.id.curr_temp);
        icon=view.findViewById(R.id.icon);
        windDir=view.findViewById(R.id.wind_dir);
        wind=view.findViewById(R.id.wind_value);
        realFeelTemp=view.findViewById(R.id.realfeel_vlaue);
        humidity=view.findViewById(R.id.humidity);
        windGust=view.findViewById(R.id.wind_gust_vlaue);
        precipitation=view.findViewById(R.id.precipitation);
        windChillTemp=view.findViewById(R.id.windchill_vlaue);
        pressure=view.findViewById(R.id.pressure_value);
        uvIndex=view.findViewById(R.id.uv_index);
        nestedScrollView=view.findViewById(R.id.scrollview);
        group=view.findViewById(R.id.toggle_group);
        recyclerView=view.findViewById(R.id.recycler_view);
    }
    private void initRec(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        //recyclerView.setAdapter();
    }



    @Override
    public void onResume() {
        super.onResume();



        if (group.getCheckedButtonId() == R.id.hourly_btn) {

            recyclerView.setAdapter(hourlyAdapter);
            hourlyAdapter.setHourlyForecastsList(houlyForecastsList);
            hourlyAdapter.setTimezone(zoneId);
        } else if (group.getCheckedButtonId() == R.id.daily_btn) {

            recyclerView.setAdapter(dailyAdapter);
            dailyAdapter.setDailyForecastsList(dayForecastsList);
            dailyAdapter.setTimezone(zoneId);

        }
    }



    private void DefineBackgroundForWeatherConditions(int cloudCover,boolean hasPrecipitation,String precipitationType ,boolean isDayTime){

        if (hasPrecipitation){

            if (precipitationType.equalsIgnoreCase("Rain")&&isDayTime){
            setWeatherImage("rain_day");
            }else if (precipitationType.equalsIgnoreCase("Rain")&&!isDayTime){
                setWeatherImage("rain_night");
            } if (precipitationType.equalsIgnoreCase("Snow")&&isDayTime){
                setWeatherImage("snow_day");
            }else if (precipitationType.equalsIgnoreCase("Snow")&&!isDayTime){
                setWeatherImage("snow_night");
            }
        }else if (cloudCover>40&&isDayTime){
            setWeatherImage("cloud");
        }else if (cloudCover>40&&!isDayTime){
            setWeatherImage("cloud_night");
        }else if (cloudCover<40&&!isDayTime){
            setWeatherImage("clear_sky_night");
        }else {
            setWeatherImage("clear_sky");
        }
    }

    private void setWeatherImage(String nameImageFromDrawable){

        int image=getActivity().getResources().getIdentifier("bg_"+nameImageFromDrawable,"drawable",getActivity().getPackageName());
        nestedScrollView.setBackgroundResource(image);


    }
}
