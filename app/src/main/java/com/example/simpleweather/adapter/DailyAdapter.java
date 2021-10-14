package com.example.simpleweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweather.R;
import com.example.simpleweather.model.DayForecasts;
import com.example.simpleweather.utils.TimeUtil;
import com.example.simpleweather.views.DayDetailsActivity;
import com.google.gson.Gson;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

    List<DayForecasts> dailyForecastsList;
    Context            context;
    String timezone;
    public DailyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DailyAdapter.DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_daily,parent,false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.DailyViewHolder holder, int position) {
      holder.onBind(dailyForecastsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (dailyForecastsList==null)
            return 0;
        else
            return dailyForecastsList.size();
    }


    public void setDailyForecastsList(List<DayForecasts> dailyForecastsList){
        this.dailyForecastsList=dailyForecastsList;
        notifyDataSetChanged();
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView day, tempMax, tempMin, phrase, rainProbability, day_date;
        ImageView    icon;
        DayForecasts dayForecasts;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            tempMax = itemView.findViewById(R.id.max_temp);
            tempMin = itemView.findViewById(R.id.min_temp);
            phrase = itemView.findViewById(R.id.phrase);
            day_date = itemView.findViewById(R.id.day_date);
            rainProbability = itemView.findViewById(R.id.rain_probability);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        public void onBind(DayForecasts dayForecasts) {

            this.dayForecasts = dayForecasts;

            day.setText(TimeUtil.getDayName(context.getResources(), dayForecasts.getTime()));
            tempMax.setText(String.format(TimeUtil.getLocale(context.getResources()), "%.1f %s", dayForecasts.getDayTemperature().getMaxTemp().getValue(), dayForecasts.getDayTemperature().getMaxTemp().getUnit()));
            tempMin.setText(String.format(TimeUtil.getLocale(context.getResources()), "%.1f %s", dayForecasts.getDayTemperature().getMinTemp().getValue(), dayForecasts.getDayTemperature().getMinTemp().getUnit()));
            phrase.setText(dayForecasts.getDay().getIconPhrase());
            day_date.setText(TimeUtil.getDate(context.getResources(), dayForecasts.getTime()));
            int i = context.getResources().getIdentifier("p_" + dayForecasts.getDay().getIcon(), "drawable", context.getPackageName());

            icon.setBackgroundResource(i);
            rainProbability.setText(String.format(TimeUtil.getLocale(context.getResources()), "%d", dayForecasts.getDay().getRainProbability()));
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(), DayDetailsActivity.class);
            Gson gson=new Gson();
            String s=gson.toJson(dayForecasts);
            intent.putExtra("DAY",s);
            intent.putExtra("Zone",timezone);
            context.startActivity(intent);
         }
    }
}
