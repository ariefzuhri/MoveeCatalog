package com.ariefzuhri.moveecatalog.favorite.adapter;

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
import com.ariefzuhri.moveecatalog.favorite.Favorite;
import com.ariefzuhri.moveecatalog.movie.DetailMovieActivity;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setTitle;
import static com.ariefzuhri.moveecatalog.movie.DetailMovieActivity.EXTRA_MOVIE;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    private ArrayList<Favorite> listFavorites = new ArrayList<>();

    public void setData(ArrayList<Favorite> favorites){
        listFavorites.clear();
        listFavorites.addAll(favorites);
        notifyDataSetChanged();
    }

    public ArrayList<Favorite> getData() {
        return listFavorites;
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_favorite_movie, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, final int i) {
        Favorite favorite = listFavorites.get(i);
        holder.bind(favorite);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = new Movie(
                        listFavorites.get(i).getId(),
                        listFavorites.get(i).getTitle(),
                        null,
                        listFavorites.get(i).getRate(),
                        listFavorites.get(i).getPoster()
                );

                Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
                intent.putExtra(EXTRA_MOVIE, movie);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorites.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvRate;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_favorite);
            tvRate = itemView.findViewById(R.id.tv_rate_favorite);
        }

        void bind(Favorite favorite) {
            loadImage((Activity) itemView.getContext(), imgPoster, favorite.getPoster(), "w154");
            tvTitle.setText(setTitle(favorite.getTitle()));
            tvRate.setText(favorite.getRate());
        }
    }
}
