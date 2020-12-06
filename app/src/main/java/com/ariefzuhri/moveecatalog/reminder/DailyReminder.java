package com.ariefzuhri.moveecatalog.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.EXTRA_NOTIF_MESSAGE;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.EXTRA_NOTIF_TITLE;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.REQUEST_DAILY_REMINDER;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.sendNotification;

public class DailyReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(EXTRA_NOTIF_TITLE);
        String message = intent.getStringExtra(EXTRA_NOTIF_MESSAGE);

        sendNotification(context, REQUEST_DAILY_REMINDER, title, message);
    }

    public void setDailyReminder(Context context, String title, String message){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_NOTIF_TITLE, title);
        intent.putExtra(EXTRA_NOTIF_MESSAGE, message);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 7);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_DAILY_REMINDER, intent, 0);
        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
