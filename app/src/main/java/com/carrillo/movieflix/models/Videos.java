package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Videos {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public class Result {
        @SerializedName("key")
        @Expose
        String id;

        @SerializedName("type")
        @Expose
        String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
