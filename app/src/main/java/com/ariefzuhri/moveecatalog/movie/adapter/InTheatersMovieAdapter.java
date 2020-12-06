package com.ariefzuhri.moveecatalog.movie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.movie.DetailMovieActivity;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.movie.DetailMovieActivity.EXTRA_MOVIE;

public class InTheatersMovieAdapter extends RecyclerView.Adapter<InTheatersMovieAdapter.InTheatersMovieViewHolder> {

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
    public InTheatersMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_in_theaters_movie, parent, false);
        return new InTheatersMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InTheatersMovieViewHolder holder, final int i) {
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

    class InTheatersMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvRate;

        InTheatersMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_in_theaters);
            tvRate = itemView.findViewById(R.id.tv_rate_in_theaters);
        }

        void bind(Movie movie) {
            loadImage((Activity) itemView.getContext(), imgPoster, movie.getPoster(), "w154");
            tvRate.setText(movie.getRate());
        }
    }
}
