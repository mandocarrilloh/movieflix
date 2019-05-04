package com.carrillo.movieflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew extends Person {

    @SerializedName("job")
    @Expose
    private String job;

    public Crew() {
    }

    public Crew(String name, String job, String profilePath) {
        this.name = name;
        this.job = job;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getJob() {
        return job;
    }
}
