package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Casts {

    @SerializedName("crew")
    @Expose
    private ArrayList<Crew> crew;

    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> cast;

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}
