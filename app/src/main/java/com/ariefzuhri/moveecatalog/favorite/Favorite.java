package com.ariefzuhri.moveecatalog.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private String id;
    private String mediaType;
    private String title;
    private String poster;
    private String rate;

    public Favorite(String id, String mediaType, String title, String poster, String rate) {
        this.id = id;
        this.mediaType = mediaType;
        this.title = title;
        this.poster = poster;
        this.rate = rate;
    }

    private Favorite(Parcel in) {
        id = in.readString();
        mediaType = in.readString();
        title = in.readString();
        poster = in.readString();
        rate = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mediaType);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(rate);
    }
}
