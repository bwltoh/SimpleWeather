package com.example.simpleweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweather.R;
import com.example.simpleweather.model.HoulyForecasts;
import com.example.simpleweather.utils.TimeUtil;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    List<HoulyForecasts> hourlyForecastsList;
    Context context;
    String timezone;
    public HourlyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_hourly,parent,false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        holder.onBind(hourlyForecastsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (hourlyForecastsList==null)
        return 0;
        else
            return hourlyForecastsList.size();
    }


    public void setHourlyForecastsList(List<HoulyForecasts> hourlyForecastsList){
        this.hourlyForecastsList=hourlyForecastsList;
        notifyDataSetChanged();


    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    class HourlyViewHolder extends RecyclerView.ViewHolder{


        TextView hour,temp,wind,rainProbability;
        ImageView icon;


        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hour=itemView.findViewById(R.id.hour);
            temp=itemView.findViewById(R.id.temp);
            wind=itemView.findViewById(R.id.wind);
            rainProbability=itemView.findViewById(R.id.rain_probability);
            icon=itemView.findViewById(R.id.icon);


        }

        public void onBind(HoulyForecasts houlyForecasts) {

            hour.setText(TimeUtil.getHour(context.getResources(), houlyForecasts.getTime(), timezone));
            temp.setText(String.format(TimeUtil.getLocale(context.getResources()), "%.1f %s", houlyForecasts.getTemprature().getValue(), houlyForecasts.getTemprature().getUnit()));
            wind.setText(String.format(TimeUtil.getLocale(context.getResources()), "%.1f %s", houlyForecasts.getWind().getSpeed().getValue(), houlyForecasts.getWind().getSpeed().getUnit()));
            rainProbability.setText(String.valueOf(houlyForecasts.getRainProbability()));
            int i = context.getResources().getIdentifier("p_" + houlyForecasts.getIcon(), "drawable", context.getPackageName());
            icon.setBackgroundResource(i);
        }
    }
}
