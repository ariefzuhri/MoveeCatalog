package com.ariefzuhri.moveecatalog.tvshow;

import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TVShowResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<TVShow> tvshows;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public ArrayList<TVShow> getTVShows() {
        return tvshows;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
