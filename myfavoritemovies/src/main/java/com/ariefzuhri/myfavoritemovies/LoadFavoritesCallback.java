package com.ariefzuhri.myfavoritemovies;

import java.util.ArrayList;

public interface LoadFavoritesCallback {
    void preExecute();
    void postExecute(ArrayList<Favorite> favorites);
}