package com.carrillo.movieflix.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.carrillo.movieflix.R;
import com.carrillo.movieflix.adapters.TopRecyclerViewAdapter;
import com.carrillo.movieflix.data.NetworkUtils;
import com.carrillo.movieflix.models.Movie;
import com.carrillo.movieflix.models.MovieResponse;
import com.carrillo.movieflix.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopActivity extends AppCompatActivity {

    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvTopList;
    private ArrayList<Movie> top;

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
                    Intent proximaIntent = new Intent(getApplicationContext(), ProximasActivity.class);
                    startActivity(proximaIntent);
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
        setContentView(R.layout.activity_top);
        initView();
        makeTMDBTopQuery();
    }

    private void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_top);

        rvTopList = findViewById(R.id.rv_top_list);
        rvTopList.setNestedScrollingEnabled(false);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.MULTIPLY);
    }

    private void makeTMDBTopQuery() {
        if (Utils.isConnected(this)) {
            Call<MovieResponse> call = NetworkUtils.buildTopUrl();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            MovieResponse applicationResponse = response.body();
                            if (applicationResponse.getResults() != null) {
                                if (!applicationResponse.getResults().isEmpty()) {
                                    top = applicationResponse.getResults();
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
        rvTopList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvTopList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getText(R.string.error_message));
    }

    private void populateRecyclerView() {
        rvTopList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvTopList.setAdapter(new TopRecyclerViewAdapter(getApplicationContext(), top));
    }
}