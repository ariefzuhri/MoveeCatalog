package com.ariefzuhri.moveecatalog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns._ID;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.ID;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.RATE;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.MEDIA_TYPE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmoveecatalog";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            _ID,
            MEDIA_TYPE,
            ID,
            TITLE,
            POSTER,
            RATE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
