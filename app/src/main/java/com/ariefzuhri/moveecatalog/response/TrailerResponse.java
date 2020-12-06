package com.ariefzuhri.moveecatalog.response;

import com.ariefzuhri.moveecatalog.response.model.Trailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TrailerResponse {
    @SerializedName("results")
    @Expose
    ArrayList<Trailer> trailers;

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
