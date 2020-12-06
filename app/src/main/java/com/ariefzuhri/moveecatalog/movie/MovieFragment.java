package com.ariefzuhri.moveecatalog.movie;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.ViewAllActivity;
import com.ariefzuhri.moveecatalog.movie.adapter.MovieAdapter;
import com.ariefzuhri.moveecatalog.movie.adapter.InTheatersMovieAdapter;
import com.ariefzuhri.moveecatalog.movie.adapter.ReleaseTodayMovieAdapter;
import com.ariefzuhri.moveecatalog.movie.adapter.UpcomingMovieAdapter;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.movie.viewmodel.MovieViewModel;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TITLE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TYPE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.ID_IN_THEATERS;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.ID_RELEASE_TODAY;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.ID_UPCOMING;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;

public class MovieFragment extends Fragment implements View.OnClickListener{
    private MovieViewModel viewModel;

    private MovieAdapter adapter;
    private InTheatersMovieAdapter adapterInTheaters;
    private ReleaseTodayMovieAdapter adapterReleaseToday;
    private UpcomingMovieAdapter adapterUpcoming;

    private ProgressBar progressBar, progressBarLoadMore;
    private TextView tvNoReleaseToday, tvLoadMore, tvViewAllReleaseToday;

    private final static String EXTRA_STATE_LIST = "extra_state_list";
    private final static String EXTRA_STATE_PAGE = "extra_state_page";
    private int currentPage = 1;

    public MovieFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBarLoadMore = view.findViewById(R.id.progress_bar_load_more);
        progressBar.setVisibility(View.GONE);
        progressBarLoadMore.setVisibility(View.GONE);

        tvNoReleaseToday = view.findViewById(R.id.tv_no_release_today);
        tvViewAllReleaseToday = view.findViewById(R.id.tv_view_all_release_today);

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        rvMovies.setAdapter(adapter);

        RecyclerView rvInTheatersMovies = view.findViewById(R.id.rv_in_theaters);
        rvInTheatersMovies.setHasFixedSize(true);
        rvInTheatersMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterInTheaters = new InTheatersMovieAdapter();
        adapterInTheaters.notifyDataSetChanged();
        rvInTheatersMovies.setAdapter(adapterInTheaters);

        RecyclerView rvReleaseTodayMovies = view.findViewById(R.id.rv_release_today);
        rvReleaseTodayMovies.setHasFixedSize(true);
        rvReleaseTodayMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterReleaseToday = new ReleaseTodayMovieAdapter();
        adapterReleaseToday.notifyDataSetChanged();
        rvReleaseTodayMovies.setAdapter(adapterReleaseToday);

        RecyclerView rvUpcomingMovies = view.findViewById(R.id.rv_upcoming);
        rvUpcomingMovies.setHasFixedSize(true);
        rvUpcomingMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterUpcoming = new UpcomingMovieAdapter();
        adapterUpcoming.notifyDataSetChanged();
        rvUpcomingMovies.setAdapter(adapterUpcoming);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        showLoading(progressBar, true);
        if (savedInstanceState == null){
            loadTrendingMovies();
        } else{
            adapter.setData(savedInstanceState.<Movie>getParcelableArrayList(EXTRA_STATE_LIST));
            currentPage = savedInstanceState.getInt(EXTRA_STATE_PAGE);
        }
        loadInTheatersMovies();
        loadReleaseTodayMovies();
        loadUpcomingMovies();


        // VIEW ALL
        TextView tvViewAllInTheaters = view.findViewById(R.id.tv_view_all_in_theaters);
        TextView tvViewAllReleaseToday = view.findViewById(R.id.tv_view_all_release_today);
        TextView tvViewAllUpcoming = view.findViewById(R.id.tv_view_all_upcoming);

        tvViewAllInTheaters.setOnClickListener(this);
        tvViewAllReleaseToday.setOnClickListener(this);
        tvViewAllUpcoming.setOnClickListener(this);

        tvLoadMore = view.findViewById(R.id.tv_load_more);
        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(progressBarLoadMore, true);
                tvLoadMore.setVisibility(View.GONE);
                loadTrendingMovies();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_LIST, adapter.getData());
        outState.putInt(EXTRA_STATE_PAGE, currentPage);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ViewAllActivity.class);

        switch(view.getId()){
            case R.id.tv_view_all_in_theaters:
                intent.putExtra(EXTRA_TYPE, ID_IN_THEATERS);
                intent.putExtra(EXTRA_TITLE, getResources().getString(R.string.in_theaters));
                break;

            case R.id.tv_view_all_release_today:
                intent.putExtra(EXTRA_TYPE, ID_RELEASE_TODAY);
                intent.putExtra(EXTRA_TITLE, getResources().getString(R.string.release_today));
                break;

            case R.id.tv_view_all_upcoming:
                intent.putExtra(EXTRA_TYPE, ID_UPCOMING);
                intent.putExtra(EXTRA_TITLE, getResources().getString(R.string.upcoming));
                break;

            default:
                break;
        }

        startActivity(intent);
    }

    private void loadTrendingMovies() {
        viewModel.setMovie(getActivity(), currentPage);
        viewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    adapter.setData(movies);

                    currentPage++;
                    showLoading(progressBarLoadMore, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadInTheatersMovies(){
        viewModel.setInTheatersMovie(1);
        viewModel.getInTheatersMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> inTheatersMovies) {
                if (inTheatersMovies != null) {
                    adapterInTheaters.setData(inTheatersMovies);
                }
            }
        });
    }

    private void loadReleaseTodayMovies(){
        viewModel.setReleaseTodayMovie(1);
        viewModel.getReleaseTodayMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> releaseTodayMovies) {
                if (releaseTodayMovies != null) {
                    adapterReleaseToday.setData(releaseTodayMovies);

                    if (adapterReleaseToday.getItemCount() > 0){
                        tvNoReleaseToday.setVisibility(View.GONE);
                        tvViewAllReleaseToday.setVisibility(View.VISIBLE);
                    } else{
                        tvNoReleaseToday.setVisibility(View.VISIBLE);
                        tvViewAllReleaseToday.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void loadUpcomingMovies(){
        viewModel.setUpcomingMovie(1);
        viewModel.getUpcomingMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> upcomingMovies) {
                if (upcomingMovies != null) {
                    adapterUpcoming.setData(upcomingMovies);
                    showLoading(progressBar, false);
                }
            }
        });
    }
}