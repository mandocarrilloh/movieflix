package com.carrillo.movieflix.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.carrillo.movieflix.adapters.MovieRecyclerViewAdapter;
import com.carrillo.movieflix.data.NetworkUtils;
import com.carrillo.movieflix.R;
import com.carrillo.movieflix.models.Movie;
import com.carrillo.movieflix.models.MovieResponse;
import com.carrillo.movieflix.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingActivity extends AppCompatActivity {

    private TextView tvErrorMessage;
    private Button btinfo;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    return true;
                case R.id.navigation_top:
                    Intent topIntent = new Intent(getApplicationContext(), TopActivity.class);
                    startActivity(topIntent);
                    return true;
                case R.id.navigation_proximas:
                    Intent proximasIntent = new Intent(getApplicationContext(), ProximasActivity.class);
                    startActivity(proximasIntent);
                    return true;
                case R.id.navigation_buscar:
                    Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                case R.id.navigation_favoritos:
                    Intent favoritesIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(favoritesIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        initView();
        initListener();
        makeTMDBTrendingQuery();
    }

    private void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_inicio);

        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        btinfo = findViewById(R.id.bt_info);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.MULTIPLY);
    }

    private void initListener(){
        btinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
            }
        });
    }

    private void makeTMDBTrendingQuery() {
        if (Utils.isConnected(this)) {
            Call<MovieResponse> call = NetworkUtils.buildTrendingUrl();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            MovieResponse applicationResponse = response.body();
                            if (applicationResponse.getResults() != null) {
                                if (!applicationResponse.getResults().isEmpty()) {
                                    movies = applicationResponse.getResults();
                                    showRecyclerView();
                                    populateRecyclerView();
                                }
                            }
                        } else {
                            showErrorMessage();
                            String error = response.raw().networkResponse().toString();
                            if (error != null)
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado en el servidor de autenticacion", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        showErrorMessage();
                        String error = response.raw().networkResponse().toString();
                        if (error != null)
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado en el servidor de autenticacion", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    showErrorMessage();
                    pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    if (t.getMessage() != null)
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado en el servidor de autenticacion", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            pbLoadingIndicator.setVisibility(View.INVISIBLE);
            showErrorMessage();
            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión a internet, seguirás trabajando en modo \"sin conexión\".", Toast.LENGTH_LONG).show();
        }
    }

    private void showRecyclerView() {
        tvErrorMessage.setVisibility(View.INVISIBLE);
        rvMovieList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvMovieList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getText(R.string.error_message));
    }

    private void populateRecyclerView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int spanCount = 2;
        if (width > 1400) {
            spanCount = 5;
        } else if (width > 700) {
            spanCount = 3;
        }
        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        rvMovieList.setAdapter(new MovieRecyclerViewAdapter(getApplicationContext(), movies));
    }
}