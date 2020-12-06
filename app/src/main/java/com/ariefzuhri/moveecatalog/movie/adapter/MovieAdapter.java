package com.ariefzuhri.moveecatalog.movie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.movie.DetailMovieActivity;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setTitle;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setYear;
import static com.ariefzuhri.moveecatalog.movie.DetailMovieActivity.EXTRA_MOVIE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> listMovies = new ArrayList<>();

    public void setData(ArrayList<Movie> movies){
        listMovies.clear();
        listMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getData(){
        return listMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int i) {
        Movie movie = listMovies.get(i);
        holder.bind(movie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
                intent.putExtra(EXTRA_MOVIE, listMovies.get(i));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvYear, tvRate;
        RatingBar ratingBar;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvRate = itemView.findViewById(R.id.tv_rate);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }

        void bind(Movie movie) {
            loadImage((Activity) itemView.getContext(), imgPoster, movie.getPoster(), "w154");
            tvTitle.setText(setTitle(movie.getTitle()));
            tvYear.setText(setYear(movie.getDate()));
            tvRate.setText(movie.getRate());

            float rating = Float.valueOf(movie.getRate());
            ratingBar.setRating(rating/2);
        }
    }
}
