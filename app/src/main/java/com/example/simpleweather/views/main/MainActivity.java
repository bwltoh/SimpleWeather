package com.example.simpleweather.views.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.simpleweather.R;
import com.example.simpleweather.adapter.CityAdapter;
import com.example.simpleweather.model.City;
import com.example.simpleweather.utils.Constants;
import com.example.simpleweather.views.search.SearchActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<City>                  cityArrayList;
    LinearLayout                layout;
    ViewPager2                  viewPager;
    CoordinatorLayout           coordinator;
    MainViewModel               mainViewModel;
    TextView                    cityname;
    CityAdapter                 cityAdapter;
    int                         PERMISSIN_ID = 234;
    FusedLocationProviderClient fusedLocationProviderClient;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        new Handler(getMainLooper()).postDelayed(
                                () -> viewPager.setCurrentItem(viewPager.getAdapter() != null ? viewPager.getAdapter().getItemCount() - 1 : 0),
                                200
                        );

                    }
                }

            }
    );

    ViewPager2.OnPageChangeCallback changeListener = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            createIndicator(cityArrayList.size(), position);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);
        setContentView(R.layout.activity_main);


        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        cityArrayList = new ArrayList<>();//cities array
        FloatingActionButton fab = findViewById(R.id.fab);
        layout = findViewById(R.id.indicator);
        cityname = findViewById(R.id.city_name);
        viewPager = findViewById(R.id.view_pager);
        coordinator = findViewById(R.id.coordinator);
        viewPager.registerOnPageChangeCallback(changeListener);


        cityAdapter = new CityAdapter(this);
        viewPager.setAdapter(cityAdapter);
        getLastLocation();


        fab.setOnClickListener(view -> activityResultLauncher.launch(new Intent(MainActivity.this, SearchActivity.class)));

        getCities();
        //apply bottom margin to coordinator layout so that its content does not do under system navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(coordinator, (v, insets) -> {
            Insets inset = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams marginLayout = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            marginLayout.bottomMargin = inset.bottom;
            v.setLayoutParams(marginLayout);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void getCities() {

        mainViewModel.getCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities != null && !cities.isEmpty()) {
                    cityArrayList = cities;
                    cityAdapter.setCities(cityArrayList);

                    layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            createIndicator(cityArrayList.size(), 0);
                            layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
                }
            }
        });
    }

    private void createIndicator(int viewCount, int currentPosition) {

        cityname.setText(cityArrayList.get(currentPosition).getCityLocalizedName());
        if (layout.getChildCount() > 0)
            layout.removeAllViews();

        for (int i = 0; i < viewCount; i++) {
            View view = new View(MainActivity.this);
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

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getLastLocation() {

        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location != null) {
                        String lat = String.valueOf(location.getLatitude());
                        String lon = String.valueOf(location.getLongitude());
                        mainViewModel.getCityByGeoLocation(Constants.API_KEY, lat + "," + lon);
                    }
                });
            } else {
                Toast.makeText(this, "turn on your device location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
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
                Toast.makeText(this, "This Permission is required", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

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
        viewPager.unregisterOnPageChangeCallback(changeListener);
        super.onDestroy();


    }


}
