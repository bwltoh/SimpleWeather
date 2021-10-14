package com.example.simpleweather.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleweather.R;
import com.example.simpleweather.model.DayForecasts;
import com.example.simpleweather.utils.TimeUtil;
import com.google.gson.Gson;

public class DayDetailsActivity extends AppCompatActivity {

    TextView day,tempMax,tempMin,precipitationProbabilityDay, thunderStormProbabilityDay,windDay,
    longPhrase,sunRise,sunSet,rainProbabilityDay,rainDay,snowProbabilityDay,snowDay,
            night,precipitationProbabilityNight,thunderStormProbabilityNight,
    windNight,longPhraseNight,moonRise,moonSet,moonPhase,moonAge,rainProbabilityNight,rainNight,
    snowProbabilityNight,snowNight;
    ImageView dayIcon,nightIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        initViews();

        DayForecasts dayForecasts;
        Bundle b=getIntent().getExtras();



            String s=b.getString("DAY");
            String timeZone=b.getString("Zone");
            Gson gson=new Gson();
            dayForecasts=gson.fromJson(s,DayForecasts.class);

            if (dayForecasts!=null) {
                setIcon(dayIcon, dayForecasts.getDay().getIcon());
                setIcon(nightIcon, dayForecasts.getNight().getIcon());


                day.setText(getResources().getString(R.string.day, TimeUtil.getDayName(getResources(), dayForecasts.getTime())));
                tempMax.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f C", dayForecasts.getDayTemperature().getMaxTemp().getValue()));
                precipitationProbabilityDay.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getDay().getPrecipitationProbability(), " %"));
                thunderStormProbabilityDay.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getDay().getThunderstormProbability(), " %"));
                windDay.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getDay().getWind().getSpeed().getValue(), dayForecasts.getDay().getWind().getSpeed().getUnit()));
                longPhrase.setText(dayForecasts.getDay().getLongPhrase());
                sunRise.setText(TimeUtil.getHour(getResources(), dayForecasts.getSun().getSunRiseTime(), timeZone));
                sunSet.setText(TimeUtil.getHour(getResources(), dayForecasts.getSun().getSunSetTime(), timeZone));
                rainProbabilityDay.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getDay().getRainProbability(), " %"));
                rainDay.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getDay().getRain().getValue(), dayForecasts.getDay().getRain().getUnit()));
                snowProbabilityDay.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getDay().getSnowProbability(), " %"));
                snowDay.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getDay().getSnow().getValue(), dayForecasts.getDay().getSnow().getUnit()));


                night.setText(getResources().getString(R.string.night, TimeUtil.getDayName(getResources(), dayForecasts.getTime())));
                tempMin.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f C", dayForecasts.getDayTemperature().getMinTemp().getValue()));
                precipitationProbabilityNight.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getNight().getPrecipitationProbability(), " %"));
                thunderStormProbabilityNight.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getNight().getThunderstormProbability(), " %"));
                windNight.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getNight().getWind().getSpeed().getValue(), dayForecasts.getNight().getWind().getSpeed().getUnit()));
                longPhraseNight.setText(dayForecasts.getNight().getLongPhrase());
                moonRise.setText(TimeUtil.getHour(getResources(), dayForecasts.getMoon().getMoonRiseTime(), timeZone));
                moonSet.setText(TimeUtil.getHour(getResources(), dayForecasts.getMoon().getMoonSetTime(), timeZone));
                moonAge.setText(String.format(TimeUtil.getLocale(getResources()), "%d", dayForecasts.getMoon().getMoonAge()));
                moonPhase.setText(dayForecasts.getMoon().getMoonPhase());
                rainProbabilityNight.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getNight().getRainProbability(), " %"));
                rainNight.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getNight().getRain().getValue(), dayForecasts.getNight().getRain().getUnit()));
                snowProbabilityNight.setText(String.format(TimeUtil.getLocale(getResources()), "%d %s", dayForecasts.getNight().getSnowProbability(), " %"));
                snowNight.setText(String.format(TimeUtil.getLocale(getResources()), "%.1f %s", dayForecasts.getNight().getSnow().getValue(), dayForecasts.getNight().getSnow().getUnit()));

            }
    }

   void initViews(){
       day=findViewById(R.id.day_forcast) ;
       tempMax=findViewById(R.id.temp_max) ;
       dayIcon=findViewById(R.id.day_icon) ;
       precipitationProbabilityDay=findViewById(R.id.precipitation_probability_day) ;
       thunderStormProbabilityDay=findViewById(R.id.thunderstorm_probability_day) ;
       windDay=findViewById(R.id.wind_day) ;
       longPhrase=findViewById(R.id.long_phrase) ;
       sunRise=findViewById(R.id.sun_rise) ;
       sunSet=findViewById(R.id.sun_set) ;
       rainProbabilityDay=findViewById(R.id.rain_probability_day) ;
       rainDay=findViewById(R.id.rain_day) ;
       snowProbabilityDay=findViewById(R.id.snow_probability_day) ;
       snowDay=findViewById(R.id.snow_day) ;

       night=findViewById(R.id.night_forcast) ;
       tempMin=findViewById(R.id.temp_min) ;
       nightIcon=findViewById(R.id.night_icon) ;
       precipitationProbabilityNight=findViewById(R.id.precipitation_probability_night) ;
       thunderStormProbabilityNight=findViewById(R.id.thunderstorm_probability_night) ;
       windNight=findViewById(R.id.wind_night) ;
       longPhraseNight=findViewById(R.id.long_phrase_night) ;
       moonRise=findViewById(R.id.moon_rise) ;
       moonSet=findViewById(R.id.moon_set) ;
       moonPhase=findViewById(R.id.moon_phase) ;
       moonAge=findViewById(R.id.moon_age) ;
       rainProbabilityNight=findViewById(R.id.rain_probability_night) ;
       rainNight=findViewById(R.id.rain_night) ;
       snowProbabilityNight=findViewById(R.id.snow_probability_night) ;
       snowNight=findViewById(R.id.snow_night) ;


   }

   void setIcon(ImageView imageView,int icon){
        int i=getResources().getIdentifier("p_"+icon,"drawable",getPackageName());
        imageView.setBackgroundResource(i);
   }

}
