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

        /*Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);*/

        Intent broadcastIntent = new Intent(getApplicationContext(), ExecutableService.class);
        broadcastIntent.putExtra("Yes", "Yes");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent b_i = new Intent(getApplicationContext(), ExecutableService.class);
        b_i.putExtra("Yes", "No");
        PendingIntent a_i = PendingIntent.getBroadcast(this, 1, b_i, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent iiiii = new Intent(getApplicationContext(), ExecutableService.class);
        iiiii.putExtra("Yes", "Alarm");
        PendingIntent p_iiiii = PendingIntent.getBroadcast(this, 1, iiiii, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                //.setContentTitle("Namaz Alert")
                .setContentText("Are You Offering Namaz ?")
                .setSmallIcon(R.mipmap.official_logo)
                /*.setAutoCancel(true)*/
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(R.color.colorPrimary)
                .setOnlyAlertOnce(true)
                .setTicker("Namaz Reminder Notification")
                .setSound(Uri.parse("android.resource://"
                        + getPackageName() + "/" + R.raw.azan))
                /*.setContentIntent(contentIntent)*/
                .addAction(R.mipmap.official_logo, getString(R.string.yes), actionIntent)
                .addAction(R.mipmap.ic_kaba, getString(R.string.no), a_i)
                .addAction(R.mipmap.official_logo, "Off Alarm", p_iiiii);
    }
}