package com.carrillo.movieflix.data;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.carrillo.movieflix.models.GenreResponse;
import com.carrillo.movieflix.models.Movie;
import com.carrillo.movieflix.models.MovieResponse;
import com.carrillo.movieflix.services.EndpointRetrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    public final static String TBASE_URL = "https://api.themoviedb.org/3/";
    private final static String VALUE_API_KEY = "4828cc7253d4b097a8b3064638af0c67";
    private final static String VALUE_APPEND_TO_RESPONSE = "videos,casts";

    public static Call<MovieResponse> buildSearchUrl(String TMDBSearchQuery) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.searchMovie(TMDBSearchQuery, VALUE_API_KEY, getLanguage());
    }

    public static Call<MovieResponse> buildTrendingUrl() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.getTrendingMovies(VALUE_API_KEY, getLanguage());
    }

    public static Call<MovieResponse> buildProximasUrl() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.getUpcomingMovies(VALUE_API_KEY, getLanguage());
    }

    public static Call<MovieResponse> buildTopUrl() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.getTopMovies(VALUE_API_KEY, getLanguage());
    }

    public static Call<Movie> buildMovieDetailUrl(int movieId) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.getDetailMovie(movieId, VALUE_API_KEY, VALUE_APPEND_TO_RESPONSE, getLanguage());
    }

    public static Call<GenreResponse> buildGenreUrl() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.TBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointRetrofit retrofitIR = retrofit.create(EndpointRetrofit.class);
        return retrofitIR.getTopGenres(VALUE_API_KEY, getLanguage());
    }

    private static String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (language.equals("nl")) {
            return "es";
        } else if (language.equals("de")) {
            return "es";
        }
        return "es";
    }
}