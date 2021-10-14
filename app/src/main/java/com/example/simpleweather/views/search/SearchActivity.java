package com.example.simpleweather.views.search;

import android.app.Activity;
import android.app.SearchManager;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.BaseColumns;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweather.R;
import com.example.simpleweather.adapter.SearchAdapter;
import com.example.simpleweather.model.City;
import com.example.simpleweather.model.CityAndCurrentConditionsRelation;
import com.example.simpleweather.utils.Constants;
import com.example.simpleweather.utils.SwipeToDeleteCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener ,SearchView.OnSuggestionListener, SearchAdapter.OnCityChoosen {

    RecyclerView    recyclerView;
    SearchViewModel searchViewModel;
    List<City>      cityList;

    SearchAdapter searchAdapter;

    List<CityAndCurrentConditionsRelation> conditionsRelations;
    SearchView     searchView;
    ArrayList<City> array;
    CursorAdapter  suggestionAdapter;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView=findViewById(R.id.search_recycler);
        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);

        cityList=new ArrayList<>();
        array=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        searchAdapter=new SearchAdapter(this,this);
        recyclerView.setAdapter(searchAdapter);

        array=new ArrayList<>();
        suggestionAdapter= new SimpleCursorAdapter(this,R.layout.search_layout,
                null,new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1,SearchManager.SUGGEST_COLUMN_TEXT_2},
                new int[]{R.id.text1,R.id.text2},
                0);

        getCitiesAndWeatherList();
        onNewIntent(getIntent());

        enableSwipeToDelete();


    }


    private void getCitiesAndWeatherList() {
        searchViewModel.getCityWeather().observe(this, cityAndCurrentConditionsRelations -> {
            if (cityAndCurrentConditionsRelations != null) {

                conditionsRelations = cityAndCurrentConditionsRelations;
                searchAdapter.setCities(conditionsRelations);

            }
        });
    }

    void getCity(String locationKey, String apiKey, boolean withDetails) {
        searchViewModel.getCityByLocationKey(locationKey, apiKey, withDetails).observe(this, city -> {
            switch (city.getStatus()) {
                case SUCCESS:
                    searchViewModel.insertCity(city.getData());
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
                case LOADING:
                    Toast.makeText(SearchActivity.this, city.getErrorMessage(), Toast.LENGTH_LONG).show();
                    break;
                case ERROR:
                    Toast.makeText(SearchActivity.this, city.getErrorMessage(), Toast.LENGTH_LONG).show();
                    break;
            }

        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        if (countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer=new CountDownTimer(500,500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                getSuggestions(newText);
            }
        };
        countDownTimer.start();

        return true;
    }

    @Override
    public boolean onSuggestionSelect(int position) {

        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {

       getCity(array.get(position).getKey(),Constants.API_KEY,true);

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setIconifiedByDefault(false);//do not iconify the widget expand it by defualt
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        return super.onCreateOptionsMenu(menu);

    }



    private void getSuggestions(String newText) {

        searchViewModel.getSearchResults(Constants.API_KEY, newText).observe(this, searchResults -> {
            switch (searchResults.getStatus()) {
                case SUCCESS:
                    array.clear();
                    cityList.clear();
                    List<City> cityList = searchResults.getData();

                    if (cityList != null) {
                        array.addAll(cityList);


                        String[] colums = {BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1,
                                SearchManager.SUGGEST_COLUMN_TEXT_2,
                                SearchManager.SUGGEST_COLUMN_INTENT_DATA};
                        MatrixCursor cursor = new MatrixCursor(colums);
                        for (int i = 0; i < array.size(); i++) {
                            String[] tmp = {Integer.toString(i),
                                    cityList.get(i).getCityLocalizedName(), cityList.get(i).getCountry().getLocalizedName(),
                                    cityList.get(i).getKey()};
                            cursor.addRow(tmp);
                        }
                        suggestionAdapter.swapCursor(cursor);
                    }
                    break;
                case ERROR:
                    Toast.makeText(SearchActivity.this, searchResults.getErrorMessage(), Toast.LENGTH_LONG).show();
                    break;
                case LOADING:
                    Toast.makeText(SearchActivity.this, searchResults.getErrorMessage(), Toast.LENGTH_LONG).show();
                    break;
            }

        });
    }

    @Override
    public void passCity(City city) {

        searchViewModel.deleteCity(city);

    }


    private void enableSwipeToDelete() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                searchAdapter.removeItem(position);


            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
