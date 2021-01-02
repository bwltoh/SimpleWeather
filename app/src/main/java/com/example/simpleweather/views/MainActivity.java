package com.example.simpleweather.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.simpleweather.R;
import com.example.simpleweather.adapter.CityAdapter;
import com.example.simpleweather.model.City;
import com.example.simpleweather.network.NetworkRepository;
import com.example.simpleweather.utils.Constants;
import com.example.simpleweather.viewmodel.MainViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {


    List<City>                  cityArrayList;
    LinearLayout                layout;
    ViewPager                   viewPager;
    MainViewModel               mainViewModel;
    TextView                    cityname;
    NetworkRepository           networkRepository;
    CityAdapter                 cityAdapter;
    int                         PERMISSIN_ID = 234;
    FusedLocationProviderClient fusedLocationProviderClient;
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            createIndicator(cityArrayList.size(), position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location lastLocation = locationResult.getLastLocation();

            String lat = String.valueOf(lastLocation.getLatitude());
            String lon = String.valueOf(lastLocation.getLongitude());
            networkRepository.getCityByGeoLocation(Constants.API_KEY, lat + "," + lon);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setFitsSystemWindows(false);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        );

        setContentView(R.layout.activity_main);


        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        networkRepository = new NetworkRepository(getApplication());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);// FusedLocationProviderClient(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cityArrayList = new ArrayList<>();//cities array
        FloatingActionButton fab = findViewById(R.id.fab);
        layout = findViewById(R.id.indecator);
        cityname = findViewById(R.id.city_name);
        viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(changeListener);

        cityAdapter = new CityAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(cityAdapter);
        getLastLocation();
        getCities();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, SearchActivity.class));

            }
        });
    }

    private void getCities() {

        mainViewModel.getCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities != null && !cities.isEmpty()) {
                    cityArrayList = cities;
                    cityAdapter.setCities(cityArrayList);
                    createIndicator(cityArrayList.size(), 0);
                }
            }
        });
    }

    private void createIndicator(int viewCount, int currentPosition) {

        cityname.setText(cityArrayList.get(currentPosition).getCityLocalizedName());
        layout.removeAllViews();

        for (int i = 0; i < viewCount; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,
                    20);
            params.rightMargin = 10;
            view.setLayoutParams(params);
            if (i == currentPosition)
                view.setBackgroundResource(R.drawable.selected_indecator);
            else
                view.setBackgroundResource(R.drawable.empty_indecator);

            layout.addView(view);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getLastLocation() {

        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            getNewLocationData();
                        } else {
                            String lat = String.valueOf(location.getLatitude());
                            String lon = String.valueOf(location.getLongitude());
                            networkRepository.getCityByGeoLocation(Constants.API_KEY, lat + "," + lon);

                        }
                    }
                });
            } else {
                Toast.makeText(this, "turn on your location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void getNewLocationData() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIN_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIN_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                finish();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    if (checkPermission())
            getLastLocation();*/
    }

    public int getStatusBarHeight() {
        //since in multi window mode your app may not has status bar
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0)
            result = getResources().getDimensionPixelSize(resId);

        return result;
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(changeListener);
        super.onDestroy();


    }


}
