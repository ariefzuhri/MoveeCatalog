package com.ariefzuhri.moveecatalog.movie;

import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

// Kelas pojo semacam ini untuk mempresentasikan JSON
public class MovieResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
