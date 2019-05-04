package com.carrillo.movieflix.services;

import com.carrillo.movieflix.models.GenreResponse;
import com.carrillo.movieflix.models.Movie;
import com.carrillo.movieflix.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndpointRetrofit {

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("query") String query,
                                    @Query("api_key") String apiKey,
                                    @Query("language") String languaje);

    @GET("discover/movie")
    Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey,
                                          @Query("language") String languaje);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey,
                                          @Query("language") String languaje);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopMovies(@Query("api_key") String apiKey,
                                     @Query("language") String languaje);

    @GET("movie/{movie_id}")
    Call<Movie> getDetailMovie(@Path("movie_id") int movieId,
                               @Query("api_key") String apiKey,
                               @Query("append_to_response") String extra,
                               @Query("language") String languaje);

    @GET("genre/movie/list")
    Call<GenreResponse> getTopGenres(@Query("api_key") String apiKey,
                                     @Query("language") String languaje);
}
