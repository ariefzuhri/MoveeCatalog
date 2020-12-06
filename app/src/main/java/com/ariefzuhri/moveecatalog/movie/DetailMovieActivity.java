package com.ariefzuhri.moveecatalog.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.helper.AppHelper;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.movie.viewmodel.DetailMovieViewModel;
import com.ariefzuhri.moveecatalog.movie.model.DetailMovie;
import com.ariefzuhri.moveecatalog.response.model.CastCredit;
import com.ariefzuhri.moveecatalog.response.adapter.CastCreditAdapter;
import com.ariefzuhri.moveecatalog.response.model.CrewCredit;
import com.ariefzuhri.moveecatalog.response.model.Trailer;
import com.ariefzuhri.moveecatalog.response.viewmodel.CreditViewModel;
import com.ariefzuhri.moveecatalog.response.adapter.CrewCreditAdapter;
import com.ariefzuhri.moveecatalog.response.viewmodel.TrailerViewModel;
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
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setRuntime;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showLoading;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showSnackbar;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.updateWidgetItem;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MOVIE = "extra_movie";

    private CrewCreditAdapter crewAdapter;
    private CastCreditAdapter castAdapter;

    private Uri uri;

    private String mediaType, mediaId, mediaTitle, mediaPoster, mediaRate;
    private boolean isAdded = false;
    private String movieTrailer;

    private ProgressBar progressBar;
    private FloatingActionButton btnBack, btnFavorite, btnTrailer;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_movie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        RecyclerView rvCrews = findViewById(R.id.rv_crews);
        rvCrews.setHasFixedSize(true);
        rvCrews.setLayoutManager(new LinearLayoutManager(DetailMovieActivity.this, LinearLayoutManager.HORIZONTAL, false));
        crewAdapter = new CrewCreditAdapter();
        crewAdapter.notifyDataSetChanged();
        rvCrews.setAdapter(crewAdapter);

        RecyclerView rvCasts = findViewById(R.id.rv_casts);
        rvCasts.setHasFixedSize(true);
        rvCasts.setLayoutManager(new LinearLayoutManager(DetailMovieActivity.this, LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new CastCreditAdapter();
        castAdapter.notifyDataSetChanged();
        rvCasts.setAdapter(castAdapter);

        progressBar = findViewById(R.id.progress_bar_detail);
        progressBar.setVisibility(View.GONE);

        btnBack = findViewById(R.id.fab_back);
        btnBack.setOnClickListener(this);
        btnFavorite = findViewById(R.id.fab_favorite);
        btnFavorite.setOnClickListener(this);
        btnTrailer = findViewById(R.id.fab_trailer);
        btnTrailer.setOnClickListener(this);
        btnTrailer.setVisibility(View.GONE);

        TextView tvFullTitle = findViewById(R.id.tv_full_title);
        TextView tvFullTitleDetail = findViewById(R.id.tv_full_title_detail);
        tvFullTitle.setVisibility(View.GONE);
        tvFullTitleDetail.setVisibility(View.GONE);

        final TextView tvNoCast = findViewById(R.id.tv_no_cast);
        final TextView tvNoCrew = findViewById(R.id.tv_no_crew);

        TextView tvTitle = findViewById(R.id.tv_title_detail);
        final TextView tvGenre = findViewById(R.id.tv_genre_detail);
        final TextView tvOverview = findViewById(R.id.tv_overview_detail);
        final TextView tvDate = findViewById(R.id.tv_date_detail);
        final TextView tvRuntime = findViewById(R.id.tv_runtime_detail);
        TextView tvRate = findViewById(R.id.tv_rate_detail);
        ImageView imgPoster = findViewById(R.id.img_poster_detail);
        final ImageView imgBackdrop = findViewById(R.id.img_backdrop_detail);

        // POSTER
        loadImage(DetailMovieActivity.this, imgPoster, movie.getPoster(), "w154");

        // TITLE
        tvTitle.setText(AppHelper.setTitle(movie.getTitle()));
        if (movie.getTitle().length() > 45){
            tvFullTitle.setVisibility(View.VISIBLE);
            tvFullTitleDetail.setVisibility(View.VISIBLE);
            tvFullTitleDetail.setText(movie.getTitle());
        }

        // RATE
        tvRate.setText(movie.getRate());

        // CREW, CAST
        CreditViewModel creditViewModel = new ViewModelProvider(DetailMovieActivity.this, new ViewModelProvider.NewInstanceFactory()).get(CreditViewModel.class);
        showLoading(progressBar, true);
        creditViewModel.setCrewCredit("movie", movie.getId());
        creditViewModel.getCrewCredit().observe(DetailMovieActivity.this, new Observer<ArrayList<CrewCredit>>() {
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
        creditViewModel.setCastCredit("movie", movie.getId());
        creditViewModel.getCastCredit().observe(DetailMovieActivity.this, new Observer<ArrayList<CastCredit>>() {
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

        DetailMovieViewModel detailMovieViewModel = new ViewModelProvider(DetailMovieActivity.this, new ViewModelProvider.NewInstanceFactory()).get(DetailMovieViewModel.class);
        detailMovieViewModel.setDetailMovie(DetailMovieActivity.this, movie.getId());
        detailMovieViewModel.getDetailMovie().observe(DetailMovieActivity.this, new Observer<ArrayList<DetailMovie>>() {
            @Override
            public void onChanged(ArrayList<DetailMovie> detailMovie) {
                if (detailMovie != null) {
                    // BACKDROP, OVERVIEW, DATE
                    loadImage(DetailMovieActivity.this, imgBackdrop, detailMovie.get(0).getBackdrop(), "w1280");
                    tvOverview.setText(detailMovie.get(0).getOverview());
                    tvDate.setText(setDate(detailMovie.get(0).getDate()));

                    // GENRE
                    ArrayList<String> movieGenre = new ArrayList<>();
                    for (int i = 0; i < detailMovie.get(0).getGenres().size(); i++){
                        movieGenre.add(detailMovie.get(0).getGenres().get(i).getName());
                    }
                    tvGenre.setText(TextUtils.join(", ", movieGenre));

                    // RUNTIME
                    tvRuntime.setText(setRuntime(DetailMovieActivity.this, detailMovie.get(0).getRuntime()));

                    showLoading(progressBar, false);
                }
            }
        });

        // DATABASE
        mediaType = "movie";
        mediaId = movie.getId();
        mediaTitle = movie.getTitle();
        mediaPoster = movie.getPoster();
        mediaRate = movie.getRate();
        if (movie.getPoster() == null){
            mediaPoster = "null";
        }

        // TRAILER
        TrailerViewModel trailerViewModel = new ViewModelProvider(DetailMovieActivity.this, new ViewModelProvider.NewInstanceFactory()).get(TrailerViewModel.class);
        trailerViewModel.setTrailer(movie.getId());
        trailerViewModel.getTrailer().observe(DetailMovieActivity.this, new Observer<ArrayList<Trailer>>() {
            @Override
            public void onChanged(ArrayList<Trailer> trailers) {
                if (trailers != null) {
                    for (int i = trailers.size()-1; i > -1; i--){
                        if (trailers.get(i).getSite().equals("YouTube") && trailers.get(i).getType().equals("Trailer")){
                            movieTrailer = trailers.get(i).getId();
                            btnTrailer.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

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
            case R.id.fab_back:
                onBackPressed();
                break;

            case R.id.fab_favorite:
                if (isAdded){
                    removeFavorite();
                } else{
                    addFavorite();
                }

                updateWidgetItem(this);
                isAdded = !isAdded;
                setFavoriteIcon(btnFavorite, isAdded);
                break;

            case R.id.fab_trailer:
                if (movieTrailer != null){
                    Intent trailerIntent = new Intent(Intent.ACTION_VIEW);
                    trailerIntent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movieTrailer));
                    startActivity(trailerIntent);
                }
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

    public void addFavorite(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, mediaId);
        contentValues.put(MEDIA_TYPE, mediaType);
        contentValues.put(TITLE, mediaTitle);
        contentValues.put(POSTER, mediaPoster);
        contentValues.put(RATE, mediaRate);

        getContentResolver().insert(CONTENT_URI, contentValues);
        showSnackbar(findViewById(R.id.layout_detail_movie), getResources().getString(R.string.add_favorite_success));
    }

    public void removeFavorite(){
        getContentResolver().delete(uri, null, null);
        showSnackbar(findViewById(R.id.layout_detail_movie), getResources().getString(R.string.remove_favorite_success));
    }
}