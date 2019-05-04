package com.carrillo.movieflix.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
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

public class SearchResultsActivity extends AppCompatActivity {

    private String searchQuery;
    private TextView tvErrorMessage;
    private TextView tvResultsTitle;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;

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
                    return true;
                case R.id.navigation_proximas:
                    return true;
                case R.id.navigation_buscar:
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
        setContentView(R.layout.activity_search_results);
        initView();
        makeTMDBSearchQuery();
    }

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_buscar);

        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.MULTIPLY);
        tvResultsTitle = findViewById(R.id.tv_results_title);
        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);

        Intent intent = getIntent();
        searchQuery = intent.getExtras().getString("SearchQuery");
    }

    private void makeTMDBSearchQuery() {
        if (Utils.isConnected(this)) {
            Call<MovieResponse> call = NetworkUtils.buildSearchUrl(searchQuery);
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
                                    tvResultsTitle.setText(movies.size() + " " + getResources().getString(R.string.results_heading) + " '" + searchQuery + "'");
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

    private void populateRecyclerView() {
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);
        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvMovieList.setAdapter(rvAdapter);
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
}