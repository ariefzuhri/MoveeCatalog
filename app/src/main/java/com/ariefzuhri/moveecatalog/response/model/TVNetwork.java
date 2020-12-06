package com.ariefzuhri.moveecatalog.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVNetwork {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
