package com.carrillo.movieflix.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.carrillo.movieflix.adapters.CastRecyclerViewAdapter;
import com.carrillo.movieflix.adapters.CrewRecyclerViewAdapter;
import com.carrillo.movieflix.data.DBModels;
import com.carrillo.movieflix.data.NetworkUtils;
import com.carrillo.movieflix.R;
import com.carrillo.movieflix.data.FavoritesDbHelper;
import com.carrillo.movieflix.models.Genres;
import com.carrillo.movieflix.models.Movie;
import com.carrillo.movieflix.models.ProductionCompanies;
import com.carrillo.movieflix.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvMovieTitle;
    private TextView tvMovieGenres;
    private TextView tvMovieProductionCompanies;
    private TextView tvMovieRunTime;
    private TextView tvMovieOverview;
    private TextView tvMovieReleaseDate;
    private TextView tvCrew;
    private TextView tvCast;
    private TextView tvErrorMessage;
    private ImageView ivMoviePoster;
    private FloatingActionButton btnFavorites;
    private ProgressBar pbLoadingIndicator;
    private LinearLayout llMovieInfoHolder;
    private RatingBar rbRatingBar;
    private RecyclerView rvCast;
    private RecyclerView rvCrew;
    private Button btnTrailer;
    private int movieId;
    private Movie movie;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initView();
        initListener();
    }

    private void initView() {
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        ivMoviePoster = findViewById(R.id.iv_movie_poster);
        tvMovieGenres = findViewById(R.id.tv_movie_genres);
        tvMovieProductionCompanies = findViewById(R.id.tv_movie_production_companies);
        tvMovieRunTime = findViewById(R.id.tv_movie_runtime);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieOverview = findViewById(R.id.tv_movie_overview);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.MULTIPLY);
        llMovieInfoHolder = findViewById(R.id.ll_movie_info_holder);
        rbRatingBar = findViewById(R.id.rb_movie_rating);
        btnFavorites = findViewById(R.id.btn_favorites);
        rvCast = findViewById(R.id.rv_cast);
        rvCrew = findViewById(R.id.rv_crew);
        tvCrew = findViewById(R.id.tv_crew);
        tvCast = findViewById(R.id.tv_cast);
        btnTrailer = findViewById(R.id.btn_trailer);

        Intent intent = getIntent();
        movieId = intent.getExtras().getInt("MovieId");
        makeTMDBMovieDetailQuery(movieId);

        Drawable progress = rbRatingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, getResources().getColor(R.color.primaryLightColor));

        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        if (isInFavorites()) {
            btnFavorites.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        } else {
            btnFavorites.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryLightColor)));
        }

    }

    private void initListener() {
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            int duration = Toast.LENGTH_LONG;

            public void onClick(View v) {
                if (isInFavorites() && removeFromFavorites()) {
                    btnFavorites.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryLightColor)));
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.favorites_removed_1) + " '" + movie.getTitle() + "' " + getResources().getString(R.string.favorites_removed_2), duration);
                    toast.show();
                } else if (addToFavorites()) {
                    btnFavorites.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.favorites_added_1) + " '" + movie.getTitle() + "' " + getResources().getString(R.string.favorites_added_2), duration);
                    toast.show();
                }
            }
        });
    }

    private void makeTMDBMovieDetailQuery(int movieId) {
        if (Utils.isConnected(this)) {
            Call<Movie> call = NetworkUtils.buildMovieDetailUrl(movieId);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    tvCast.setVisibility(View.VISIBLE);
                    tvCrew.setVisibility(View.VISIBLE);
                    btnFavorites.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Movie applicationResponse = response.body();
                            if (applicationResponse != null) {
                                movie = applicationResponse;
                                bindMovieData();
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
                public void onFailure(Call<Movie> call, Throwable t) {
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

    private void showMovieDetails() {
        tvErrorMessage.setVisibility(View.GONE);
        llMovieInfoHolder.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        llMovieInfoHolder.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getText(R.string.error_message));
    }

    private void bindMovieData() {
        ImageView headerBackdrop = findViewById(R.id.header_backdrop);
        if (movie.getBackDropLarge() != null) {
            Picasso.get().load(movie.getBackDropLarge()).into(headerBackdrop);
        } else {
            Picasso.get().load(R.drawable.backdrop_fallback).into(headerBackdrop);
        }
        setTitle(movie.getTitle());
        tvMovieTitle.setText(movie.getTitle());
        if (movie.getRunTime() != null) {
            tvMovieRunTime.setText(String.valueOf(movie.getRunTime()) + " " + getResources().getString(R.string.minutes));
        } else {
            tvMovieRunTime.setText(R.string.unknown);
        }

        if (movie.getOverview() != null) {
            tvMovieOverview.setText(movie.getOverview());
        } else {
            tvMovieOverview.setText(R.string.no_description);
        }

        if (movie.getReleaseDate() != null) {
            tvMovieReleaseDate.setText(movie.getReleaseDate());
        } else {
            tvMovieReleaseDate.setText(R.string.unknown);
        }

        Picasso.get().load(movie.getPosterLarge()).into(ivMoviePoster);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation moveUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        ivMoviePoster.startAnimation(moveUpAnimation);
        headerBackdrop.startAnimation(fadeInAnimation);

        rbRatingBar.setRating((float) (movie.getRating() / 2));

        ArrayList<Genres> genres = movie.getGenres();
        tvMovieGenres.setText(getResources().getString(R.string.genres) + ": ");
        if (genres.size() == 0) {
            tvMovieGenres.append(getResources().getString(R.string.unknown));
        } else {
            for (int i = 0; i < genres.size(); i++) {
                if (i != 0) {
                    tvMovieGenres.append(", ");
                }
                tvMovieGenres.append(genres.get(i).getName());
            }
        }

        ArrayList<ProductionCompanies> productionCompanies = movie.getProductionCompanies();
        tvMovieProductionCompanies.setText(getResources().getString(R.string.producers) + ": ");
        if (productionCompanies.size() == 0) {
            tvMovieProductionCompanies.append(getResources().getString(R.string.unknown));
        } else {
            for (int i = 0; i < productionCompanies.size(); i++) {
                if (i != 0) {
                    tvMovieProductionCompanies.append(", ");
                }
                tvMovieProductionCompanies.append(productionCompanies.get(i).getName());
            }
        }

        if (movie.getCast().size() > 0) {
            CastRecyclerViewAdapter castAdapter = new CastRecyclerViewAdapter(getApplicationContext(), movie.getCast());
            LinearLayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvCast.setLayoutManager(castLayoutManager);
            rvCast.setAdapter(castAdapter);
        } else {
            tvCast.setVisibility(View.GONE);
            rvCast.setVisibility(View.GONE);
        }

        if (movie.getCrew().size() > 0) {
            CrewRecyclerViewAdapter crewAdapter = new CrewRecyclerViewAdapter(getApplicationContext(), movie.getCrew());
            LinearLayoutManager crewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvCrew.setLayoutManager(crewLayoutManager);
            rvCrew.setAdapter(crewAdapter);
        } else {
            tvCrew.setVisibility(View.GONE);
            rvCrew.setVisibility(View.GONE);
        }

        final String trailerUrl = movie.getTrailerUrl();
        if (trailerUrl != null) {
            btnTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(trailerUrl));
                    startActivity(i);
                }
            });
        } else {
            btnTrailer.setEnabled(false);
            btnTrailer.setText(R.string.no_trailer);
            btnTrailer.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_disabled));
        }
    }

    private Boolean isInFavorites() {
        Cursor mCursor = mDatabase.rawQuery(
                "SELECT * FROM " + DBModels.FavoritesEntry.TABLE_NAME +
                        " WHERE " + DBModels.FavoritesEntry.COLUMN_MOVIE_ID + "= " + movieId
                , null);

        return mCursor.moveToFirst();
    }

    private Boolean addToFavorites() {
        ContentValues values = new ContentValues();
        values.put(DBModels.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
        values.put(DBModels.FavoritesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        return mDatabase.insert(DBModels.FavoritesEntry.TABLE_NAME, null, values) > 0;
    }

    private Boolean removeFromFavorites() {
        return mDatabase.delete(DBModels.FavoritesEntry.TABLE_NAME, DBModels.FavoritesEntry.COLUMN_MOVIE_ID + " = " + movieId, null) > 0;
    }
}
