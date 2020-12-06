package com.ariefzuhri.moveecatalog.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    @Expose
    String id;

    @SerializedName("site")
    @Expose
    String site;

    @SerializedName("type")
    @Expose
    String type;

    public String getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
