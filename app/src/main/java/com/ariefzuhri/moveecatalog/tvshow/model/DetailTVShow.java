package com.ariefzuhri.moveecatalog.tvshow.model;

import com.ariefzuhri.moveecatalog.response.model.Genre;
import com.ariefzuhri.moveecatalog.response.model.TVNetwork;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DetailTVShow {

    @SerializedName("first_air_date")
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

    @SerializedName("number_of_episodes")
    @Expose
    private String episode;

    @SerializedName("number_of_seasons")
    @Expose
    private String season;

    @SerializedName("networks")
    @Expose
    private ArrayList<TVNetwork> networks;

    @SerializedName("episode_run_time")
    @Expose
    private ArrayList<String> runtime;

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getEpisode() {
        return episode;
    }

    public String getSeason() {
        return season;
    }

    public ArrayList<TVNetwork> getNetworks() {
        return networks;
    }

    public ArrayList<String> getRuntime() {
        return runtime;
    }
}