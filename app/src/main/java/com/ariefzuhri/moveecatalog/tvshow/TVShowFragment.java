package com.ariefzuhri.moveecatalog.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.ViewAllActivity;
import com.ariefzuhri.moveecatalog.tvshow.adapter.OnAirTVShowAdapter;
import com.ariefzuhri.moveecatalog.tvshow.adapter.TVShowAdapter;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import com.ariefzuhri.moveecatalog.tvshow.viewmodel.TVShowViewModel;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TITLE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TYPE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.ID_ON_AIR;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;

public class TVShowFragment extends Fragment implements View.OnClickListener{
    private TVShowViewModel viewModel;

    private TVShowAdapter adapter;
    private OnAirTVShowAdapter adapterOnAir;

    private ProgressBar progressBar, progressBarLoadMore;
    private TextView tvLoadMore;

    private final static String EXTRA_STATE_LIST = "extra_state_list";
    private final static String EXTRA_STATE_PAGE = "extra_state_page";
    private int currentPage = 1;

    public TVShowFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar_tvs);
        progressBarLoadMore = view.findViewById(R.id.progress_bar_tvs_load_more);
        progressBar.setVisibility(View.GONE);
        progressBarLoadMore.setVisibility(View.GONE);

        tvLoadMore = view.findViewById(R.id.tv_tvs_load_more);

        RecyclerView rvTVShows = view.findViewById(R.id.rv_tvshows);
        rvTVShows.setHasFixedSize(true);
        rvTVShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TVShowAdapter();
        adapter.notifyDataSetChanged();
        rvTVShows.setAdapter(adapter);

        RecyclerView rvOnAirTVShows = view.findViewById(R.id.rv_tvs_on_air);
        rvOnAirTVShows.setHasFixedSize(true);
        rvOnAirTVShows.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterOnAir = new OnAirTVShowAdapter();
        adapterOnAir.notifyDataSetChanged();
        rvOnAirTVShows.setAdapter(adapterOnAir);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowViewModel.class);
        showLoading(progressBar,true);
        if (savedInstanceState == null){
            loadTrendingTVShows();
        } else{
            adapter.setData(savedInstanceState.<TVShow>getParcelableArrayList(EXTRA_STATE_LIST));
            currentPage = savedInstanceState.getInt(EXTRA_STATE_PAGE);
        }
        loadOnAirTVShows();

        // VIEW ALL
        TextView tvViewAllOnAir = view.findViewById(R.id.tv_tvs_view_all_on_air);
        tvViewAllOnAir.setOnClickListener(this);

        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(progressBarLoadMore, true);
                tvLoadMore.setVisibility(View.GONE);
                loadTrendingTVShows();
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
        if (view.getId() == R.id.tv_tvs_view_all_on_air){
            Intent intent = new Intent(getActivity(), ViewAllActivity.class);
            intent.putExtra(EXTRA_TYPE, ID_ON_AIR);
            intent.putExtra(EXTRA_TITLE, getResources().getString(R.string.on_air));
            startActivity(intent);
        }
    }

    private void loadTrendingTVShows(){
        viewModel.setTVShow(currentPage);
        viewModel.getTVShow().observe(getViewLifecycleOwner(), new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> tvshows) {
                if (tvshows != null) {
                    adapter.setData(tvshows);

                    currentPage++;
                    showLoading(progressBarLoadMore, false);
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadOnAirTVShows(){
        viewModel.setOnAirTVShow(1);
        viewModel.getOnAirTVShow().observe(getViewLifecycleOwner(), new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> onAirTVShows) {
                if (onAirTVShows != null) {
                    adapterOnAir.setData(onAirTVShows);
                    showLoading(progressBar, false);
                }
            }
        });
    }
}