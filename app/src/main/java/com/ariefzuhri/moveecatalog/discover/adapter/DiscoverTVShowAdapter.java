package com.ariefzuhri.moveecatalog.discover.adapter;

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
import com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setTitle;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.setYear;
import static com.ariefzuhri.moveecatalog.tvshow.DetailTVShowActivity.EXTRA_TVSHOW;

public class DiscoverTVShowAdapter extends RecyclerView.Adapter<DiscoverTVShowAdapter.TVShowViewHolder> {

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
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_tvshow, parent, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, final int i) {
        TVShow tvshow = listTVShows.get(i);
        holder.bind(tvshow);

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

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvYear, tvRate;
        RatingBar ratingBar;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_tvs_poster_result);
            tvTitle = itemView.findViewById(R.id.tv_tvs_title_result);
            tvYear = itemView.findViewById(R.id.tv_tvs_year_result);
            tvRate = itemView.findViewById(R.id.tv_tvs_rate_result);
            ratingBar = itemView.findViewById(R.id.tvs_ratingbar_result);
        }

        void bind(TVShow tvshow) {
            loadImage((Activity) itemView.getContext(), imgPoster, tvshow.getPoster(), "w154");
            tvTitle.setText(setTitle(tvshow.getTitle()));
            tvYear.setText(setYear(tvshow.getDate()));
            tvRate.setText(tvshow.getRate());

            float rating = Float.valueOf(tvshow.getRate());
            ratingBar.setRating(rating/2);
        }
    }
}
