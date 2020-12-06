package com.ariefzuhri.myfavoritemovies.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ariefzuhri.myfavoritemovies.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class AppHelper {
    public static void loadImage(Activity activity, ImageView imgView, String imgPath, String imgSize){
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/" + imgSize + imgPath)
                .apply(RequestOptions.placeholderOf(R.drawable.loading_icon)) // Taro gambar sebelum selesai di-load
                .error(R.drawable.no_pic) // Jika terjadi error
                .into(imgView);
    }

    public static void showLoading(ProgressBar progressBar, boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
