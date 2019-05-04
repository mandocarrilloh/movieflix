package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("profile_path")
    @Expose
    String profilePath;

    private String profileUrl;

    private final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    Person() {
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getProfileUrl() {
        return TMDB_IMG_BASE_URL + "w300" + profilePath;
    }

    void setProfileUrl(String profilePath) {
        profileUrl = TMDB_IMG_BASE_URL + "w300" + profilePath;
    }
}
