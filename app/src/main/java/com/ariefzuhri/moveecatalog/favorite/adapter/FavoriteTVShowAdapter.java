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
import com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setTitle;
import static com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity.EXTRA_TVSHOW;

public class FavoriteTVShowAdapter extends RecyclerView.Adapter<FavoriteTVShowAdapter.FavoriteTVShowViewHolder> {

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
    public FavoriteTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_favorite_tvshows, parent, false);
        return new FavoriteTVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTVShowViewHolder holder, final int i) {
        Favorite favorite = listFavorites.get(i);
        holder.bind(favorite);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TVShow tvshow = new TVShow(
                        listFavorites.get(i).getId(),
                        listFavorites.get(i).getTitle(),
                        null,
                        listFavorites.get(i).getRate(),
                        listFavorites.get(i).getPoster()
                );

                Intent intent = new Intent(view.getContext(), DetailTVShowActivity.class);
                intent.putExtra(EXTRA_TVSHOW, tvshow);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorites.size();
    }

    class FavoriteTVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvRate;

        FavoriteTVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_tvs_poster_favorite);
            tvTitle = itemView.findViewById(R.id.tv_tvs_title_favorite);
            tvRate = itemView.findViewById(R.id.tv_tvs_rate_favorite);
        }

        void bind(Favorite favorite) {
            loadImage((Activity) itemView.getContext(), imgPoster, favorite.getPoster(), "w154");
            tvTitle.setText(setTitle(favorite.getTitle()));
            tvRate.setText(favorite.getRate());
        }
    }
}
