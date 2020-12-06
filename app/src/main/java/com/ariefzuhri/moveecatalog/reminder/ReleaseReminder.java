package com.ariefzuhri.moveecatalog.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.movie.MovieResponse;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.getTodayDate;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.EXTRA_NOTIF_TITLE;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.REQUEST_RELEASE_REMINDER;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.sendNotification;

public class ReleaseReminder extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ArrayList<Movie> listFound = new ArrayList<>();
        final ArrayList<String> movieTitles = new ArrayList<>();
        RetrofitClient client = new RetrofitClient();
        client.getService().getReleaseTodayMovies(API_KEY, getTodayDate(), getTodayDate(), 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();

                        if (movieResponse.getMovies().size() != 0){
                            listFound.addAll(movieResponse.getMovies());

                            for (Movie movie : listFound){
                                movieTitles.add(movie.getTitle());
                            }

                            sendNotification(
                                    context,
                                    REQUEST_RELEASE_REMINDER,
                                    intent.getStringExtra(EXTRA_NOTIF_TITLE),
                                    TextUtils.join(", ", movieTitles)
                            );
                        }
                    }
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setReleaseReminder(Context context, String title){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        intent.putExtra(EXTRA_NOTIF_TITLE, title);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_RELEASE_REMINDER, intent, 0);
        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
