package com.ariefzuhri.moveecatalog.movie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.movie.DetailMovieActivity;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.movie.DetailMovieActivity.EXTRA_MOVIE;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieViewHolder> {
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
    public UpcomingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_upcoming_movie, parent, false);
        return new UpcomingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMovieViewHolder holder, final int i) {
        final Movie movie = listMovies.get(i);
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

    class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;

        UpcomingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_upcoming);
        }

        void bind(Movie movie){
            loadImage((Activity) itemView.getContext(), imgPoster, movie.getPoster(), "w154");
        }
    }
}
