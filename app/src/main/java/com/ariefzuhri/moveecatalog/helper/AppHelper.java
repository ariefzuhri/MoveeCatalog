package com.ariefzuhri.moveecatalog.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.widget.MyFavoriteMoviesWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.ariefzuhri.moveecatalog.widget.MyFavoriteMoviesWidget.UPDATE_WIDGET;

public class AppHelper {
    
    public static void loadImage(Activity activity, ImageView imgView, String imgPath, String imgSize){
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/" + imgSize + imgPath)
                .apply(RequestOptions.placeholderOf(R.drawable.loading_icon)) // Taro gambar sebelum selesai di-load
                .error(R.drawable.no_pic) // Jika terjadi error
                .into(imgView);
    }

    public static void showLoading(ProgressBar progressBar, boolean state){
        if (state) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    public static void showErrorDialog(Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle(R.string.error)
                .setMessage(R.string.notconnected)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public static String setDate(String date){
        String releaseDate = "-";

        if (date != null){
            String[] dateArray = date.split("-");

            if (dateArray.length == 3){
                int mm = Integer.parseInt(dateArray[1]);
                String month = new DateFormatSymbols().getMonths()[mm-1];
                releaseDate = dateArray[2] + " " + month + " " + dateArray[0];
            } else{
                releaseDate = date;
            }
        }

        return releaseDate;
    }

    public static String setRuntime(Context context, String duration){
        int time = 0, MM, HH;
        String HHMM = "-";

        if (duration != null)
            time = Integer.parseInt(duration);
            HH = time/60;
            MM = time%60;

            String m = context.getResources().getString(R.string.minute);
            String h = context.getResources().getString(R.string.hour);

            if (HH == 0){
                HHMM = MM + m;
            } else if (HH > 0){
                if (MM == 0){
                    HHMM = HH + h;
                } else if (MM > 0){
                    HHMM = HH + h + " " + MM + m;
                }
        }

        return HHMM;
    }

    public static String setYear(String date){
        String year = "-";

        if (date != null){
            String[] dateArray = date.split("-");

            if (dateArray.length == 3){
                year = dateArray[0];
            } else {
                year = date;
            }
        }

        return year;
    }

    public static String setTitle(String title){
        String fixTitle = title;

        if (title.length() > 45){
            fixTitle = title.substring(0, 45);
            fixTitle += "...";
        }

        return fixTitle;
    }

    public static void updateWidgetItem(Context context) {
        Intent updateWidgetIntent = new Intent(context, MyFavoriteMoviesWidget.class);
        updateWidgetIntent.setAction(UPDATE_WIDGET);
        context.sendBroadcast(updateWidgetIntent);
    }

    public static void showSnackbar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getTodayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
