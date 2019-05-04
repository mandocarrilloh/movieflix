package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {

    @SerializedName("id")
    @Expose
    private int movieId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("backdrop_path")
    @Expose
    private String backDropLarge;

    @SerializedName("runtime")
    @Expose
    private Integer runTime;

    @SerializedName("vote_average")
    @Expose
    private double rating;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("videos")
    @Expose
    private Videos videos;

    @SerializedName("genres")
    @Expose
    private ArrayList<Genres> genres;

    @SerializedName("production_companies")
    @Expose
    private ArrayList<ProductionCompanies> productionCompanies;

    @SerializedName("casts")
    @Expose
    private Casts casts;

    private final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    private final static String YT_MOVIE_BASE_URL = "https://www.youtube.com/watch";

    public Movie() {
    }

    public Movie(int movieId, String posterPath) {
        this.movieId = movieId;
        this.posterPath = posterPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterSmall() {
        return TMDB_IMG_BASE_URL + "w200" + posterPath;
    }

    public String getPosterLarge() {
        return TMDB_IMG_BASE_URL + "w500" + posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDropLarge() {
        return TMDB_IMG_BASE_URL + "w500" + backDropLarge;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public ArrayList<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public ArrayList<Cast> getCast() {
        return casts.getCast();
    }

    public ArrayList<Crew> getCrew() {
        return casts.getCrew();
    }

    public String getTrailerUrl() {
        if (videos != null) {
            if (videos.getResults() != null) {
                if (!videos.getResults().isEmpty()) {
                    for (Videos.Result trailer : videos.getResults()) {
                        if (trailer.type.equals("Trailer")) {
                            return YT_MOVIE_BASE_URL + "?v=" + trailer.id;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void setBackDrops(String backDropPath) {
        if (backDropPath != null) {
            backDropLarge = TMDB_IMG_BASE_URL + "w500" + backDropPath;
        } else {
            backDropLarge = null;
        }
    }
}


