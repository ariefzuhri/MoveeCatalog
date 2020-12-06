package com.ariefzuhri.moveecatalog.tvshow.adapter;

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
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity.EXTRA_TVSHOW;

public class OnAirTVShowAdapter extends RecyclerView.Adapter<OnAirTVShowAdapter.OnAirTVShowViewHolder> {

    private ArrayList<TVShow> listTVShows = new ArrayList<>();

    public void setData(ArrayList<TVShow> tvshows){
        listTVShows.clear();
        listTVShows.addAll(tvshows);
        notifyDataSetChanged();
    }

    public ArrayList<TVShow> getData(){
        return listTVShows;
    }

    @NonNull
    @Override
    public OnAirTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_on_air_tvshow, parent, false);
        return new OnAirTVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnAirTVShowViewHolder holder, final int i) {
        TVShow tvshow = listTVShows.get(i);
        holder.bind(tvshow);

        // Aksi ketika salah satu item diklik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailTVShowActivity.class);
                intent.putExtra(EXTRA_TVSHOW, listTVShows.get(i));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTVShows.size();
    }

    class OnAirTVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvRate;

        OnAirTVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_tvs_poster_on_air);
            tvRate = itemView.findViewById(R.id.tv_tvs_rate_on_air);
        }

        void bind(TVShow tvshow) {
            loadImage((Activity) itemView.getContext(), imgPoster, tvshow.getPoster(), "w154");
            tvRate.setText(tvshow.getRate());
        }
    }
}
