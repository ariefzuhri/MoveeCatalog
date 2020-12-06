package com.ariefzuhri.moveecatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ariefzuhri.moveecatalog.movie.adapter.MovieAdapter;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.movie.viewmodel.MovieViewModel;
import com.ariefzuhri.moveecatalog.tvshow.adapter.TVShowAdapter;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import com.ariefzuhri.moveecatalog.tvshow.viewmodel.TVShowViewModel;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;

public class ViewAllActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_TITLE = "extra_title";

    public static final int ID_IN_THEATERS = 100;
    public static final int ID_RELEASE_TODAY = 200;
    public static final int ID_UPCOMING = 300;
    public static final int ID_ON_AIR = 400;

    private MovieViewModel viewModelMovie;
    private TVShowViewModel viewModelTVShow;

    private MovieAdapter adapterMovie;
    private TVShowAdapter adapterTVShow;

    private ProgressBar progressBar;
    private TextView tvLoadMore;

    private final static String EXTRA_STATE_LIST = "extra_state_list";
    private final static String EXTRA_STATE_PAGE = "extra_state_page";
    private int currentPage = 1;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra(EXTRA_TITLE));

        tvLoadMore = findViewById(R.id.tv_load_more_view_all);
        progressBar = findViewById(R.id.progress_bar_view_all);
        tvLoadMore.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        RecyclerView rvViewAll = findViewById(R.id.rv_view_all);
        rvViewAll.setHasFixedSize(true);
        rvViewAll.setLayoutManager(new LinearLayoutManager(this));

        type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        if (type == ID_IN_THEATERS || type == ID_RELEASE_TODAY || type == ID_UPCOMING){
            adapterMovie = new MovieAdapter();
            adapterMovie.notifyDataSetChanged();
            rvViewAll.setAdapter(adapterMovie);

            viewModelMovie = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
            if (savedInstanceState == null){
                switch (type){
                    case ID_IN_THEATERS:
                        loadInTheatersMovies();
                        break;

                    case ID_RELEASE_TODAY:
                        loadReleaseTodayMovies();
                        break;

                    case ID_UPCOMING:
                        loadUpcomingMovies();
                        break;

                    case ID_ON_AIR:
                        loadOnAirTVShows();
                        break;
                }
            } else{
                adapterMovie.setData(savedInstanceState.<Movie>getParcelableArrayList(EXTRA_STATE_LIST));
            }
        } else if (type == ID_ON_AIR){
            adapterTVShow = new TVShowAdapter();
            adapterTVShow.notifyDataSetChanged();
            rvViewAll.setAdapter(adapterTVShow);

            viewModelTVShow = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowViewModel.class);
            if (savedInstanceState == null){
                loadOnAirTVShows();
            } else{
                adapterTVShow.setData(savedInstanceState.<TVShow>getParcelableArrayList(EXTRA_STATE_LIST));
            }
        }

        if (savedInstanceState != null){
            currentPage = savedInstanceState.getInt(EXTRA_STATE_PAGE);
        }

        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(progressBar, true);
                tvLoadMore.setVisibility(View.GONE);

                switch (type){
                    case ID_IN_THEATERS:
                        loadInTheatersMovies();
                        break;

                    case ID_RELEASE_TODAY:
                        loadReleaseTodayMovies();
                        break;

                    case ID_UPCOMING:
                        loadUpcomingMovies();
                        break;

                    case ID_ON_AIR:
                        loadOnAirTVShows();
                        break;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (type == ID_IN_THEATERS || type == ID_RELEASE_TODAY || type == ID_UPCOMING){
            outState.putParcelableArrayList(EXTRA_STATE_LIST, adapterMovie.getData());
        } else if (type == ID_ON_AIR){
            outState.putParcelableArrayList(EXTRA_STATE_LIST, adapterTVShow.getData());
        }

        outState.putInt(EXTRA_STATE_PAGE, currentPage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void loadInTheatersMovies(){
        viewModelMovie.setInTheatersMovie(currentPage);
        viewModelMovie.getInTheatersMovie().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> inTheatersMovies) {
                if (inTheatersMovies != null) {
                    adapterMovie.setData(inTheatersMovies);

                    currentPage++;
                    showLoading(progressBar, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadReleaseTodayMovies(){
        viewModelMovie.setReleaseTodayMovie(currentPage);
        viewModelMovie.getReleaseTodayMovie().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> releaseTodayMovies) {
                if (releaseTodayMovies != null) {
                    adapterMovie.setData(releaseTodayMovies);

                    currentPage++;
                    showLoading(progressBar, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadUpcomingMovies(){
        viewModelMovie.setUpcomingMovie(currentPage);
        viewModelMovie.getUpcomingMovie().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> upcomingMovies) {
                if (upcomingMovies != null) {
                    adapterMovie.setData(upcomingMovies);

                    currentPage++;
                    showLoading(progressBar, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadOnAirTVShows(){
        viewModelTVShow.setOnAirTVShow(currentPage);
        viewModelTVShow.getOnAirTVShow().observe(this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> onAirTVShows) {
                if (onAirTVShows != null) {
                    adapterTVShow.setData(onAirTVShows);

                    currentPage++;
                    showLoading(progressBar, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
