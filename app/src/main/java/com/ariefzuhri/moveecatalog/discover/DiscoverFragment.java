package com.ariefzuhri.moveecatalog.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.discover.adapter.DiscoverMovieAdapter;
import com.ariefzuhri.moveecatalog.discover.adapter.DiscoverTVShowAdapter;
import com.ariefzuhri.moveecatalog.discover.viewmodel.DiscoverViewModel;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;

public class DiscoverFragment extends Fragment {
    private DiscoverMovieAdapter adapterDiscoverMovie;
    private DiscoverTVShowAdapter adapterDiscoverTVShow;

    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView tvSearchResults;

    private static final String EXTRA_STATE_RESULT_MOVIE = "extra_state_result_movie";
    private static final String EXTRA_STATE_RESULT_TVSHOW = "extra_state_result_tvshow";
    private static final String EXTRA_STATE_RESULT_COUNT = "extra_state_result_count";

    public DiscoverFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar_discover);
        progressBar.setVisibility(View.GONE);

        tvSearchResults = view.findViewById(R.id.tv_search_results);

        RecyclerView rvDiscoverMovies = view.findViewById(R.id.rv_movies_result);
        rvDiscoverMovies.setHasFixedSize(true);
        rvDiscoverMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterDiscoverMovie = new DiscoverMovieAdapter();
        adapterDiscoverMovie.notifyDataSetChanged();
        rvDiscoverMovies.setAdapter(adapterDiscoverMovie);

        RecyclerView rvDiscoverTVShows = view.findViewById(R.id.rv_tvshows_result);
        rvDiscoverTVShows.setHasFixedSize(true);
        rvDiscoverTVShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterDiscoverTVShow = new DiscoverTVShowAdapter();
        adapterDiscoverTVShow.notifyDataSetChanged();
        rvDiscoverTVShows.setAdapter(adapterDiscoverTVShow);

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                showLoading(progressBar, true);
                startSearching(query);

                searchView.clearFocus();
                searchView.onActionViewCollapsed();

                tvSearchResults.setText(
                        getResources().getString(R.string.search_results)
                        + " " + getResources().getString(R.string.result_for) + " '" +
                        query + "'"
                );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if (savedInstanceState != null) {
            ArrayList<Movie> listMovies = savedInstanceState.getParcelableArrayList(EXTRA_STATE_RESULT_MOVIE);
            ArrayList<TVShow> listTVShows = savedInstanceState.getParcelableArrayList(EXTRA_STATE_RESULT_TVSHOW);

            adapterDiscoverMovie.setData(listMovies);
            adapterDiscoverTVShow.setData(listTVShows);

            tvSearchResults.setText(savedInstanceState.getString(EXTRA_STATE_RESULT_COUNT));
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_RESULT_MOVIE, adapterDiscoverMovie.getData());
        outState.putParcelableArrayList(EXTRA_STATE_RESULT_TVSHOW, adapterDiscoverTVShow.getData());
        outState.putString(EXTRA_STATE_RESULT_COUNT, tvSearchResults.getText().toString());
    }

    private void startSearching(String query){
        DiscoverViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(DiscoverViewModel.class);

        viewModel.setDiscoverMovie(query);
        viewModel.getDiscoverMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> resultMovies) {
                if (resultMovies != null) {
                    adapterDiscoverMovie.setData(resultMovies);
                }
            }
        });
        viewModel.setDiscoverTVShow(query);
        viewModel.getDiscoverTVShow().observe(getViewLifecycleOwner(), new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> resultTVShows) {
                if (resultTVShows != null) {
                    adapterDiscoverTVShow.setData(resultTVShows);
                    showLoading(progressBar, false);
                }
            }
        });
    }
}
