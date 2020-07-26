package com.google.salahreminder.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.salahreminder.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager notificationManager;
    public String text;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @SuppressLint("ResourceAsColor")
    public NotificationCompat.Builder getChannelNotification() {

        /*PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);*/

        /*Intent contentIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);*/

        Intent activityIntent = new Intent(getApplicationContext(), SetCurrentData.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), ExecutableService.class);
        broadcastIntent.putExtra("Off_Alarm", "Off_Alarm");
        PendingIntent offAlarmPendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*Intent b_i = new Intent(getApplicationContext(), ExecutableService.class);
        b_i.putExtra("No", "No");
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(this, 1, b_i, PendingIntent.FLAG_UPDATE_CURRENT);*/

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Namaz Time Alert")
                .setSmallIcon(R.mipmap.official_logo)
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(R.color.colorPrimary)
                .setContentIntent(contentIntent)
                .setTicker("Namaz Reminder Notification")
                .addAction(R.mipmap.official_logo, "Off Alarm", offAlarmPendingIntent);
    }
}