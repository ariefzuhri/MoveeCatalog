package com.ariefzuhri.moveecatalog.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.SplashActivity;
import com.ariefzuhri.moveecatalog.ViewAllActivity;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TITLE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.EXTRA_TYPE;
import static com.ariefzuhri.moveecatalog.ViewAllActivity.ID_RELEASE_TODAY;

public class ReminderHelper {
    static final String EXTRA_NOTIF_TITLE = "extra_title";
    static final String EXTRA_NOTIF_MESSAGE = "extra_message";

    public static final int REQUEST_DAILY_REMINDER = 100;
    public static final int REQUEST_RELEASE_REMINDER = 200;

    static void sendNotification(Context context, int requestCode, String title, String message){
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "movee channel";

        Intent intent = new Intent();
        if (requestCode == REQUEST_DAILY_REMINDER){
            intent = new Intent(context, SplashActivity.class);
        } else if(requestCode == REQUEST_RELEASE_REMINDER){
            intent = new Intent(context, ViewAllActivity.class);
            intent.putExtra(EXTRA_TYPE, ID_RELEASE_TODAY);
            intent.putExtra(EXTRA_TITLE, context.getResources().getString(R.string.release_today));
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo))
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        // Untuk Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = builder.build();

        if (notificationManager != null)
            notificationManager.notify(requestCode, notification);
    }

    public static void cancelReminder(Context context, int requestCode){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent();
        if (requestCode == REQUEST_DAILY_REMINDER){
            intent = new Intent(context, DailyReminder.class);
        } else if(requestCode == REQUEST_RELEASE_REMINDER){
            intent = new Intent(context, ReleaseReminder.class);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
