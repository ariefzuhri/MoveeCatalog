package com.ariefzuhri.moveecatalog.movie.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("release_date")
    @Expose
    private String date;

    @SerializedName("vote_average")
    @Expose
    private String rate;

    @SerializedName("poster_path")
    @Expose
    private String poster;

    public Movie(String id, String title, String date, String rate, String poster) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rate = rate;
        this.poster = poster;
    }

    public Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        date = in.readString();
        rate = in.readString();
        poster = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
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

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}