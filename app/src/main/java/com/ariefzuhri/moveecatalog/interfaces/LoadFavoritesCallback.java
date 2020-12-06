package com.ariefzuhri.moveecatalog.interfaces;

import com.ariefzuhri.moveecatalog.favorite.Favorite;
import java.util.ArrayList;

public interface LoadFavoritesCallback {
    void preExecute();
    void postExecute(ArrayList<Favorite> favorites);
}