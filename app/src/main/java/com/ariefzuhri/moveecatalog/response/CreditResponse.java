package com.ariefzuhri.moveecatalog.response;

import com.ariefzuhri.moveecatalog.response.model.CastCredit;
import com.ariefzuhri.moveecatalog.response.model.CrewCredit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CreditResponse {
    @SerializedName("cast")
    @Expose
    private ArrayList<CastCredit> casts;

    @SerializedName("crew")
    @Expose
    private ArrayList<CrewCredit> crews;

    public ArrayList<CrewCredit> getCrews() {
        return crews;
    }

    public ArrayList<CastCredit> getCasts() {
        return casts;
    }
}
