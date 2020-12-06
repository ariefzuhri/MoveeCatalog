package com.ariefzuhri.moveecatalog.movie.model;

import com.ariefzuhri.moveecatalog.response.model.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DetailMovie {
    @SerializedName("release_date")
    @Expose
    private String date;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("backdrop_path")
    @Expose
    private String backdrop;

    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres;

    @SerializedName("runtime")
    @Expose
    private String runtime;

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getRuntime() {
        return runtime;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }
}