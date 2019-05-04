package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenreResponse {

    @SerializedName("genres")
    @Expose
    private ArrayList<Genres> results;

    public ArrayList<Genres> getResults() {
        return results;
    }

    public void setResults(ArrayList<Genres> results) {
        this.results = results;
    }

}

