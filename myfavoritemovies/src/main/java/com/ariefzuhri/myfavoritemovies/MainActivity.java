package com.ariefzuhri.myfavoritemovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariefzuhri.myfavoritemovies.adapter.FavoriteMovieAdapter;
import com.ariefzuhri.myfavoritemovies.adapter.FavoriteTVShowAdapter;
import com.ariefzuhri.myfavoritemovies.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ariefzuhri.myfavoritemovies.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.ariefzuhri.myfavoritemovies.helper.AppHelper.showLoading;

public class MainActivity extends AppCompatActivity implements LoadFavoritesCallback{
    private ProgressBar progressBar;
    private TextView tvFavoriteMovies, tvFavoriteTVShows, tvNoFavorite;

    private FavoriteMovieAdapter adapterMovie;
    private FavoriteTVShowAdapter adapterTVShow;

    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";
    private static final String EXTRA_STATE_TVSHOW = "extra_state_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_favorite);
        progressBar.setVisibility(View.GONE);

        tvFavoriteMovies = findViewById(R.id.tv_favorite_movies);
        tvFavoriteTVShows = findViewById(R.id.tv_favorite_tvshows);
        tvNoFavorite = findViewById(R.id.tv_no_favorite);

        int columns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            columns = 3;
        } else{
            columns = 5;
        }

        RecyclerView rvMovies = findViewById(R.id.rv_favorite_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(this, columns));
        rvMovies.setHasFixedSize(true);
        adapterMovie = new FavoriteMovieAdapter();
        adapterMovie.notifyDataSetChanged();
        rvMovies.setAdapter(adapterMovie);

        RecyclerView rvTVShows = findViewById(R.id.rv_favorite_tvshows);
        rvTVShows.setLayoutManager(new GridLayoutManager(this, columns));
        rvTVShows.setHasFixedSize(true);
        adapterTVShow = new FavoriteTVShowAdapter();
        adapterTVShow.notifyDataSetChanged();
        rvTVShows.setAdapter(adapterTVShow);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null){
            new LoadFavoritesAsync(this, this).execute();
        } else {
            ArrayList<Favorite> favoriteMovies = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            ArrayList<Favorite> favoriteTVShows = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TVSHOW);

            adapterMovie.setData(favoriteMovies);
            adapterTVShow.setData(favoriteTVShows);

            setTextView(adapterMovie.getItemCount(), adapterTVShow.getItemCount());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, adapterMovie.getData());
        outState.putParcelableArrayList(EXTRA_STATE_TVSHOW, adapterTVShow.getData());
    }

    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoading(progressBar, true);
            }
        });
    }

    public void postExecute(ArrayList<Favorite> favorites) {
        ArrayList<Favorite> favoriteMovies = new ArrayList<>();
        ArrayList<Favorite> favoriteTVShows = new ArrayList<>();

        for (int i = 0; i < favorites.size(); i++){
            if (favorites.get(i).getMediaType().equals("movie")){
                favoriteMovies.add(favorites.get(i));
            } else if (favorites.get(i).getMediaType().equals("tv")){
                favoriteTVShows.add(favorites.get(i));
            }
        }

        showLoading(progressBar, false);
        setTextView(favoriteMovies.size(), favoriteTVShows.size());

        adapterMovie.setData(favoriteMovies);
        adapterTVShow.setData(favoriteTVShows);
    }

    private static class LoadFavoritesAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoritesCallback> weakCallback;

        private LoadFavoritesAsync(Context context, LoadFavoritesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            weakCallback.get().postExecute(favorites);
        }
    }

    private void setTextView(int favoriteMoviesCount, int favoriteTVShowsCount) {
        tvNoFavorite.setVisibility(View.VISIBLE);
        tvFavoriteMovies.setVisibility(View.GONE);
        tvFavoriteTVShows.setVisibility(View.GONE);

        if (favoriteMoviesCount + favoriteTVShowsCount > 0){
            tvNoFavorite.setVisibility(View.GONE);
            if (favoriteMoviesCount > 0){
                tvFavoriteMovies.setVisibility(View.VISIBLE);
            }
            if (favoriteTVShowsCount > 0){
                tvFavoriteTVShows.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        final LoadFavoritesCallback callback;

        DataObserver(Handler handler, Context context, LoadFavoritesCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavoritesAsync(context, callback).execute();
        }
    }
}
