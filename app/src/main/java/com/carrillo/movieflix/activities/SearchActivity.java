package com.carrillo.movieflix.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.carrillo.movieflix.R;
import com.carrillo.movieflix.adapters.GenresRecyclerViewAdapter;
import com.carrillo.movieflix.data.NetworkUtils;
import com.carrillo.movieflix.models.GenreResponse;
import com.carrillo.movieflix.models.Genres;
import com.carrillo.movieflix.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;
    private EditText etSearchBox;
    private RecyclerView rvGenresList;
    private ArrayList<Genres> Genres;

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
                    Intent proximasIntent = new Intent(getApplicationContext(), ProximasActivity.class);
                    startActivity(proximasIntent);
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

    private void submitSearch() {
        Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
        String TMDBQuery = etSearchBox.getText().toString();
        intent.putExtra("SearchQuery", TMDBQuery);
        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        makeTMDBSearchQuery();
    }

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_buscar);

        rvGenresList = findViewById(R.id.rv_genres_list);
        rvGenresList.setNestedScrollingEnabled(false);

        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.MULTIPLY);

        etSearchBox = findViewById(R.id.et_search_box);
        etSearchBox.setOnEditorActionListener(new DoneOnEditorActionListener());
    }

    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitSearch();
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

    private void makeTMDBSearchQuery() {
        if (Utils.isConnected(this)) {
            Call<GenreResponse> call = NetworkUtils.buildGenreUrl();
            call.enqueue(new Callback<GenreResponse>() {
                @Override
                public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                    pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            GenreResponse applicationResponse = response.body();
                            if (applicationResponse.getResults() != null) {
                                if (!applicationResponse.getResults().isEmpty()) {
                                    Genres = applicationResponse.getResults();
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
                public void onFailure(Call<GenreResponse> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void populateRecyclerView() {
        GenresRecyclerViewAdapter rvAdapter = new GenresRecyclerViewAdapter(getApplicationContext(), Genres);
        rvGenresList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvGenresList.setAdapter(rvAdapter);
    }

    private void showRecyclerView() {
        tvErrorMessage.setVisibility(View.INVISIBLE);
        rvGenresList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvGenresList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getText(R.string.error_message));
    }
}
