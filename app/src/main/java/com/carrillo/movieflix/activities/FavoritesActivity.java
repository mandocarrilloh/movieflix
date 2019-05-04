package com.carrillo.movieflix.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import com.carrillo.movieflix.R;
import com.carrillo.movieflix.adapters.MovieRecyclerViewAdapter;
import com.carrillo.movieflix.data.DBModels;
import com.carrillo.movieflix.data.FavoritesDbHelper;
import com.carrillo.movieflix.models.Movie;


public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;
    private TextView tvNoFavorites;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    Intent trendingIntent = new Intent(getApplicationContext(), TrendingActivity.class);
                    startActivity(trendingIntent);
                    return true;
                case R.id.navigation_top:
                    Intent topIntent = new Intent(getApplicationContext(), TopActivity.class);
                    startActivity(topIntent);
                    return true;
                case R.id.navigation_proximas:
                    Intent proximaIntent = new Intent(getApplicationContext(), ProximasActivity.class);
                    startActivity(proximaIntent);
                    return true;
                case R.id.navigation_buscar:
                    Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                case R.id.navigation_favoritos:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initView();
    }

    private void initView() {

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_favoritos);

        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);

        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Cursor cursorFavorites = getFavorites();
        initMovies(cursorFavorites);
        populateRecyclerView();

        tvNoFavorites = findViewById(R.id.tv_no_favorites);
        if (movies.size() > 0) {
            tvNoFavorites.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursorFavorites = getFavorites();
        initMovies(cursorFavorites);
        populateRecyclerView();
    }

    private Cursor getFavorites() {
        return mDatabase.query(
                DBModels.FavoritesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DBModels.FavoritesEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    private void populateRecyclerView() {
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int spanCount = 2;
        if (width > 1400) {
            spanCount = 4;
        } else if (width > 700) {
            spanCount = 3;
        }

        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        rvMovieList.setAdapter(rvAdapter);
    }

    private void initMovies(Cursor cursorMovies) {
        try {
            movies = new ArrayList<>();
            while (cursorMovies.moveToNext()) {
                int movieId = cursorMovies.getInt(cursorMovies.getColumnIndex("movieId"));
                String posterPath = cursorMovies.getString(cursorMovies.getColumnIndex("posterPath"));
                movies.add(new Movie(movieId, posterPath));
            }
        } finally {
            cursorMovies.close();
        }
    }
}