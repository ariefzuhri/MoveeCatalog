package com.ariefzuhri.myfavoritemovies.helper;

import android.database.Cursor;

import com.ariefzuhri.myfavoritemovies.Favorite;

import java.util.ArrayList;

import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.ID;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.MEDIA_TYPE;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.RATE;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Favorite> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Favorite> favoritesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ID));
            String mediaType = cursor.getString(cursor.getColumnIndexOrThrow(MEDIA_TYPE));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE));
            favoritesList.add(new Favorite(id, mediaType, title, poster, rate));
        }

        return favoritesList;
    }
}
