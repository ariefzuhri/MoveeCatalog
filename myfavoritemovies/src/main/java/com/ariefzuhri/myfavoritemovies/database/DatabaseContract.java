package com.ariefzuhri.myfavoritemovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.ariefzuhri.moveecatalog";
    private static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns{
        public static String TABLE_NAME = "favorite";
        public static String MEDIA_TYPE = "type";
        public static String ID = "id";
        public static String TITLE = "title";
        public static String POSTER = "poster";
        public static String RATE = "rate";

        /**
         * Mendefinisikan content uri
         * content://com.ariefzuhri.moveecatalog
         */
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
