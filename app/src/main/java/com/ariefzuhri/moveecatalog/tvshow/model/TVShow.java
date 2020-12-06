package com.ariefzuhri.moveecatalog.tvshow.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShow implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String title;

    @SerializedName("first_air_date")
    @Expose
    private String date;

    @SerializedName("vote_average")
    @Expose
    private String rate;

    @SerializedName("poster_path")
    @Expose
    private String poster;

    public TVShow(String id, String title, String date, String rate, String poster) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rate = rate;
        this.poster = poster;
    }

    protected TVShow(Parcel in) {
        id = in.readString();
        title = in.readString();
        date = in.readString();
        rate = in.readString();
        poster = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(rate);
        dest.writeString(poster);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRate() {
        return rate;
    }

    public String getPoster() {
        return poster;
    }

}