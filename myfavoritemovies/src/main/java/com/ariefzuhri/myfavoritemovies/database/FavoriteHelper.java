package com.ariefzuhri.myfavoritemovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.ID;
import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    private FavoriteHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor querryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor query(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues contentValues){
        return database.insert(
                DATABASE_TABLE,
                null,
                contentValues
        );
    }

    public int delete(String id) {
        return database.delete(
                DATABASE_TABLE,
                ID + " = ?",
                new String[]{id}
        );
    }
}
