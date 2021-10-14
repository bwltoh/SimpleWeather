package com.example.simpleweather.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.simpleweather.model.City;
import com.example.simpleweather.views.fragment.CityFragment;

import java.util.List;

public class CityAdapter extends FragmentStateAdapter {

    private List<City> cities;

    public CityAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String locationKey = cities.get(position).getKey();
        String zoneId = cities.get(position).getTimeZone().getName();
        int cityId = cities.get(position).getId();
        return CityFragment.newInstance(locationKey, zoneId, position, cityId);
    }

    @Override
    public int getItemCount() {
        if (cities == null) return 0;
        else
            return cities.size();
    }

}
