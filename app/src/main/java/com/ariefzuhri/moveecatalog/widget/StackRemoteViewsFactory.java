package com.ariefzuhri.moveecatalog.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.ariefzuhri.moveecatalog.favorite.Favorite;
import com.ariefzuhri.moveecatalog.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.ariefzuhri.moveecatalog.helper.MappingHelper.mapCursorToArrayList;
import static com.ariefzuhri.moveecatalog.widget.MyFavoriteMoviesWidget.EXTRA_ITEM_INDEX;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Bitmap> widgetItems = new ArrayList<>();
    private ArrayList<Favorite> listFavorites = new ArrayList<>();

    private final Context context;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(){}

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        if (cursor.getCount() != 0){
            listFavorites.clear();
            listFavorites.addAll(mapCursorToArrayList(cursor));
            cursor.close();
        }

        if (listFavorites.size() != 0){
            Bitmap bitmap;
            widgetItems.clear();
            for (int i = 0; i < getCount(); i++){
                try {
                    bitmap = Glide.with(context)
                            .asBitmap()
                            .load("https://image.tmdb.org/t/p/w154" + listFavorites.get(i).getPoster())
                            .apply(RequestOptions.placeholderOf(R.drawable.loading_icon))
                            .error(R.drawable.no_pic)
                            .submit()
                            .get();
                } catch (ExecutionException | InterruptedException error) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_pic);
                    error.printStackTrace();
                }

                widgetItems.add(bitmap);
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy(){}

    @Override
    public int getCount() {
        return listFavorites.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        if (widgetItems != null){
            remoteViews.setImageViewBitmap(R.id.image_view, widgetItems.get(position));

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(EXTRA_ITEM_INDEX, position);
            remoteViews.setOnClickFillInIntent(R.id.image_view, fillInIntent);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
