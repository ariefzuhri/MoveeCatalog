package com.ariefzuhri.moveecatalog.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.favorite.Favorite;
import com.ariefzuhri.moveecatalog.movie.DetailMovieActivity;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.ariefzuhri.moveecatalog.helper.MappingHelper.mapCursorToArrayList;
import static com.ariefzuhri.moveecatalog.movie.DetailMovieActivity.EXTRA_MOVIE;
import static com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity.EXTRA_TVSHOW;

public class MyFavoriteMoviesWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.ariefzuhri.TOAST_ACTION";
    public static final String UPDATE_WIDGET = "com.ariefzuhri.UPDATE_WIDGET";
    public static final String EXTRA_ITEM = "com.ariefzuhri.EXTRA_ITEM";
    public static final String EXTRA_ITEM_INDEX = "com.ariefzuhri.EXTRA_ITEM_INDEX";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_favorite_movies_widget);
        remoteViews.setRemoteAdapter(R.id.stack_view, intent);
        remoteViews.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, MyFavoriteMoviesWidget.class);
        toastIntent.setAction(MyFavoriteMoviesWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);

        if (intent.getAction() != null){
            if (intent.getAction().equals(TOAST_ACTION)){
                ArrayList<Favorite> listFavorites = new ArrayList<>();
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
                if (cursor.getCount() != 0){
                    listFavorites.clear();
                    listFavorites.addAll(mapCursorToArrayList(cursor));
                    cursor.close();

                    int viewIndex = intent.getIntExtra(EXTRA_ITEM_INDEX, 0);
                    Intent intentDetail = new Intent();

                    if (listFavorites.get(viewIndex).getMediaType().equals("movie")){
                        Movie movie = new Movie(
                                listFavorites.get(viewIndex).getId(),
                                listFavorites.get(viewIndex).getTitle(),
                                null,
                                listFavorites.get(viewIndex).getRate(),
                                listFavorites.get(viewIndex).getPoster()
                        );

                        intentDetail.setClass(context, DetailMovieActivity.class);
                        intentDetail.putExtra(EXTRA_MOVIE, movie);
                    } else if (listFavorites.get(viewIndex).getMediaType().equals("tv")){
                        TVShow tvshow = new TVShow(
                                listFavorites.get(viewIndex).getId(),
                                listFavorites.get(viewIndex).getTitle(),
                                null,
                                listFavorites.get(viewIndex).getRate(),
                                listFavorites.get(viewIndex).getPoster()
                        );

                        intentDetail.setClass(context, DetailTVShowActivity.class);
                        intentDetail.putExtra(EXTRA_TVSHOW, tvshow);
                    }

                    intentDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intentDetail);
                }
            } else if (intent.getAction().equals(UPDATE_WIDGET)){
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName widget = new ComponentName(context, MyFavoriteMoviesWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
            }
        }
    }
}

