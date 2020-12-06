package com.ariefzuhri.moveecatalog.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ariefzuhri.moveecatalog.database.FavoriteHelper;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.AUTHORITY;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class FavoriteProvider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", FAVORITE_ID);
    }

    public FavoriteProvider(){}

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = favoriteHelper.querryAll();
                break;

            case FAVORITE_ID:
                cursor = favoriteHelper.query(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;

        if (uriMatcher.match(uri) == FAVORITE) added = favoriteHelper.insert(contentValues);
        else added = 0;

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;

        if (uriMatcher.match(uri) == FAVORITE_ID) deleted = favoriteHelper.delete(uri.getLastPathSegment());
        else deleted = 0;

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }
}
