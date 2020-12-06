package com.ariefzuhri.myfavoritemovies.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ariefzuhri.myfavoritemovies.Favorite;
import com.ariefzuhri.myfavoritemovies.R;

import java.util.ArrayList;

import static com.ariefzuhri.myfavoritemovies.helper.AppHelper.loadImage;

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
            tvTitle.setText(favorite.getTitle());
            tvRate.setText(favorite.getRate());
        }
    }
}
