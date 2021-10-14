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
import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CityAndCurrentConditionsRelation;
import com.example.simpleweather.utils.TimeUtil;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private       List<CityAndCurrentConditionsRelation> cities;
    private final Context                                context;
    private final OnCityChoosen                          onCityChoosen;

    public SearchAdapter(Context context, OnCityChoosen onCityChoosen) {
        this.context = context;
        this.onCityChoosen = onCityChoosen;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.onBind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        if (cities==null)
        return 0;
        else
            return cities.size();
    }

    public void setCities(List<CityAndCurrentConditionsRelation> cities)
    {
        this.cities=cities;
        notifyDataSetChanged();
    }


    public void removeItem(int position) {
       onCityChoosen.passCity(cities.get(position).city);
    }

    public interface OnCityChoosen{
        void passCity(City city);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder  {

        TextView cityName,countryName,temp,phrase;
        ImageView                        icon;
        CityAndCurrentConditionsRelation city;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName=itemView.findViewById(R.id.city);
            countryName=itemView.findViewById(R.id.country);
            temp=itemView.findViewById(R.id.temp);
            phrase=itemView.findViewById(R.id.phrase);
            icon=itemView.findViewById(R.id.icon);

        }

        public void onBind(CityAndCurrentConditionsRelation city) {
            this.city=city;

            cityName.setText(city.city.getCityLocalizedName());
            countryName.setText(city.city.getCountry().getLocalizedName());
            if (city.conditions==null)return;
            temp.setText(String.format(TimeUtil.getLocale(context.getResources()), "%.1f C", city.conditions.getTemperature().getMetric().getValue()));
            phrase.setText(city.conditions.getWeatherText());
            int ic=context.getResources().getIdentifier("p_"+city.conditions.getIcon(),"drawable",context.getPackageName());
            icon.setBackgroundResource(ic);
        }


    }
}
