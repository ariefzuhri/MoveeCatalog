package com.ariefzuhri.moveecatalog.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrewCredit {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("profile_path")
    @Expose
    private String photo;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getPhoto() {
        return photo;
    }
}
