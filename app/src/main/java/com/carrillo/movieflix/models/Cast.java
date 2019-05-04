package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast extends Person {

    @SerializedName("character")
    @Expose
    private String character;

    public Cast() {
    }

    public Cast(String name, String character, String profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getCharacter() {
        return character;
    }
}