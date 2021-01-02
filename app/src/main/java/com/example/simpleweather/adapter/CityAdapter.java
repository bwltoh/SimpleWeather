package com.example.simpleweather.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.simpleweather.model.City;
import com.example.simpleweather.views.CityFragment;

import java.util.List;

public class CityAdapter extends FragmentStatePagerAdapter {

    private List<City> cities;


    public CityAdapter(@NonNull FragmentManager fm,int behavior) {
        super(fm, behavior);

    }

   public void setCities(List<City> cities){
        this.cities=cities;
        notifyDataSetChanged();
   }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        String locationKey=cities.get(position).getKey();
        String zoneId=cities.get(position).getTimeZone().getName();
        int cityId=cities.get(position).getId();
        return   CityFragment.newInstance(locationKey,zoneId,position,cityId);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        int index=cities.indexOf(object);
        if (index==-1)
        return PagerAdapter.POSITION_NONE;
        else
            return index;
    }

    @Override
    public int getCount() {
        if (cities==null) return 0;
        else
        return cities.size();
    }
}
