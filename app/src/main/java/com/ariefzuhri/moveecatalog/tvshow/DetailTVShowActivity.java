package com.ariefzuhri.moveecatalog.tvshow;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.helper.AppHelper;
import com.ariefzuhri.moveecatalog.response.model.CastCredit;
import com.ariefzuhri.moveecatalog.response.adapter.CastCreditAdapter;
import com.ariefzuhri.moveecatalog.response.viewmodel.CreditViewModel;
import com.ariefzuhri.moveecatalog.response.model.CrewCredit;
import com.ariefzuhri.moveecatalog.response.adapter.CrewCreditAdapter;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import com.ariefzuhri.moveecatalog.tvshow.viewmodel.DetailTVShowViewModel;
import com.ariefzuhri.moveecatalog.tvshow.model.DetailTVShow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.ID;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.MEDIA_TYPE;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.RATE;
import static com.ariefzuhri.moveecatalog.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setDate;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showSnackbar;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.updateWidgetItem;

public class DetailTVShowActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_TVSHOW = "extra_tvshow";

    private CrewCreditAdapter crewAdapter;
    private CastCreditAdapter castAdapter;

    private Uri uri;

    private String mediaType, mediaId, mediaTitle, mediaPoster, mediaRate;
    private boolean isAdded = false;

    private ProgressBar progressBar;
    private FloatingActionButton btnBack, btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_tvshow);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TVShow tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        RecyclerView rvCrews = findViewById(R.id.rv_crews);
        rvCrews.setHasFixedSize(true);
        rvCrews.setLayoutManager(new LinearLayoutManager(DetailTVShowActivity.this, LinearLayoutManager.HORIZONTAL, false));
        crewAdapter = new CrewCreditAdapter();
        crewAdapter.notifyDataSetChanged();
        rvCrews.setAdapter(crewAdapter);

        RecyclerView rvCasts = findViewById(R.id.rv_casts);
        rvCasts.setHasFixedSize(true);
        rvCasts.setLayoutManager(new LinearLayoutManager(DetailTVShowActivity.this, LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new CastCreditAdapter();
        castAdapter.notifyDataSetChanged();
        rvCasts.setAdapter(castAdapter);

        progressBar = findViewById(R.id.progress_bar_tvs_detail);
        progressBar.setVisibility(View.GONE);

        btnBack = findViewById(R.id.fab_tvs_back);
        btnBack.setOnClickListener(this);
        btnFavorite = findViewById(R.id.fab_tvs_favorite);
        btnFavorite.setOnClickListener(this);

        TextView tvFullTitle = findViewById(R.id.tv_full_title);
        TextView tvFullTitleDetail = findViewById(R.id.tv_full_title_detail);
        tvFullTitle.setVisibility(View.GONE);
        tvFullTitleDetail.setVisibility(View.GONE);

        final TextView tvNoCast = findViewById(R.id.tv_no_cast);
        final TextView tvNoCrew = findViewById(R.id.tv_no_crew);

        TextView tvTitle = findViewById(R.id.tv_tvs_title_detail);
        final TextView tvGenre = findViewById(R.id.tv_tvs_genre_detail);
        final TextView tvOverview = findViewById(R.id.tv_overview_detail);
        final TextView tvDate = findViewById(R.id.tv_tvs_date_detail);
        final TextView tvRuntime = findViewById(R.id.tv_tvs_runtime_detail);
        TextView tvRate = findViewById(R.id.tv_tvs_rate_detail);
        final TextView tvEpiosde = findViewById(R.id.tv_tvs_episode_detail);
        final TextView tvSeason = findViewById(R.id.tv_tvs_season_detail);
        final TextView tvNetwork = findViewById(R.id.tv_tvs_network_detail);
        ImageView imgPoster = findViewById(R.id.img_tvs_poster_detail);
        final ImageView imgBackdrop = findViewById(R.id.img_tvs_backdrop_detail);

        // POSTER
        loadImage(DetailTVShowActivity.this, imgPoster, tvshow.getPoster(), "w154");

        // TITLE
        tvTitle.setText(AppHelper.setTitle(tvshow.getTitle()));
        if (tvshow.getTitle().length() > 45){
            tvFullTitle.setVisibility(View.VISIBLE);
            tvFullTitleDetail.setVisibility(View.VISIBLE);
            tvFullTitleDetail.setText(tvshow.getTitle());
        }

        // RATE
        tvRate.setText(tvshow.getRate());

        // CREW, CAST
        showLoading(progressBar, true);
        CreditViewModel creditViewModel = new ViewModelProvider(DetailTVShowActivity.this, new ViewModelProvider.NewInstanceFactory()).get(CreditViewModel.class);
        creditViewModel.setCrewCredit("tv", tvshow.getId());
        creditViewModel.getCrewCredit().observe(DetailTVShowActivity.this, new Observer<ArrayList<CrewCredit>>() {
            @Override
            public void onChanged(ArrayList<CrewCredit> credits) {
                if (credits != null) {
                    crewAdapter.setData(credits);
                    if (crewAdapter.getItemCount() > 0){
                        tvNoCrew.setVisibility(View.GONE);
                    } else{
                        tvNoCrew.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        creditViewModel.setCastCredit("tv", tvshow.getId());
        creditViewModel.getCastCredit().observe(DetailTVShowActivity.this, new Observer<ArrayList<CastCredit>>() {
            @Override
            public void onChanged(ArrayList<CastCredit> credits) {
                if (credits != null) {
                    castAdapter.setData(credits);
                    if (castAdapter.getItemCount() > 0){
                        tvNoCast.setVisibility(View.GONE);
                    } else{
                        tvNoCast.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        DetailTVShowViewModel detailTVShowViewModel = new ViewModelProvider(DetailTVShowActivity.this, new ViewModelProvider.NewInstanceFactory()).get(DetailTVShowViewModel.class);
        detailTVShowViewModel.setDetailTVShow(DetailTVShowActivity.this, tvshow.getId());
        detailTVShowViewModel.getDetailTVShow().observe(DetailTVShowActivity.this, new Observer<ArrayList<DetailTVShow>>() {
            @Override
            public void onChanged(ArrayList<DetailTVShow> detailTVShows) {
                if (detailTVShows != null) {
                    // BACKDROP, OVERVIEW, DATE
                    loadImage(DetailTVShowActivity.this, imgBackdrop, detailTVShows.get(0).getBackdrop(), "w1280");
                    tvOverview.setText(detailTVShows.get(0).getOverview());
                    tvDate.setText(setDate(detailTVShows.get(0).getDate()));

                    // GENRE
                    ArrayList<String> tvShowGenre = new ArrayList<>();
                    for (int i = 0; i < detailTVShows.get(0).getGenres().size(); i++){
                        tvShowGenre.add(detailTVShows.get(0).getGenres().get(i).getName());
                    }
                    tvGenre.setText(TextUtils.join(", ", tvShowGenre));

                    // EPISODE
                    tvEpiosde.setText(detailTVShows.get(0).getEpisode());
                    tvEpiosde.append(" " + getResources().getQuantityString(
                            R.plurals.episode,
                            Integer.parseInt(tvEpiosde.getText().toString())
                    ));

                    // SEASON
                    tvSeason.setText(detailTVShows.get(0).getSeason());
                    tvSeason.append(" " + getResources().getQuantityString(
                            R.plurals.season,
                            Integer.parseInt(tvSeason.getText().toString())
                    ));

                    // NETWORK
                    ArrayList<String> networks = new ArrayList<>();
                    for (int i = 0; i < detailTVShows.get(0).getNetworks().size(); i++){
                        networks.add(detailTVShows.get(0).getNetworks().get(i).getName());
                    }
                    tvNetwork.setText(TextUtils.join(", ", networks));

                    // RUNTIME
                    tvRuntime.setText("-");
                    if (detailTVShows.get(0).getRuntime().size() != 0) {
                        tvRuntime.setText(detailTVShows.get(0).getRuntime().get(0));
                        tvRuntime.append(" " + getResources().getQuantityString(
                                R.plurals.minuteperepisode,
                                Integer.parseInt(tvRuntime.getText().toString())
                        ));
                    }

                    showLoading(progressBar, false);
                }
            }
        });

        // DATABASE
        mediaType = "tv";
        mediaId = tvshow.getId();
        mediaTitle = tvshow.getTitle();
        mediaPoster = tvshow.getPoster();
        mediaRate = tvshow.getRate();
        if (tvshow.getPoster() == null){
            mediaPoster = "null";
        }

        initFavoriteIcon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_tvs_back:
                onBackPressed();
                break;

            case R.id.fab_tvs_favorite:
                if (isAdded){
                    removeFavorite();
                } else{
                    addFavorite();
                }

                updateWidgetItem(this);
                isAdded = !isAdded;
                setFavoriteIcon(btnFavorite, isAdded);
                break;
        }
    }

    private void initFavoriteIcon() {
        uri = Uri.parse(CONTENT_URI + "/" + mediaId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() != 0){
            cursor.close();
            isAdded = true;
        } else {
            isAdded = false;
        }

        setFavoriteIcon(btnFavorite, isAdded);
    }

    private void setFavoriteIcon(FloatingActionButton button, boolean isFavorite){
        if (isFavorite){
            button.setImageResource(R.drawable.ic_favorite);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                button.setTooltipText(getResources().getString(R.string.remove_favorite));
        } else{
            button.setImageResource(R.drawable.ic_favorite_border);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                button.setTooltipText(getResources().getString(R.string.add_favorite));
        }
    }

    private void addFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, mediaId);
        contentValues.put(MEDIA_TYPE, mediaType);
        contentValues.put(TITLE, mediaTitle);
        contentValues.put(POSTER, mediaPoster);
        contentValues.put(RATE, mediaRate);

        getContentResolver().insert(CONTENT_URI, contentValues);
        showSnackbar(findViewById(R.id.layout_detail_tvshow), getResources().getString(R.string.add_favorite_success));
    }

    private void removeFavorite() {
        getContentResolver().delete(uri, null, null);
        showSnackbar(findViewById(R.id.layout_detail_tvshow), getResources().getString(R.string.remove_favorite_success));
    }
}