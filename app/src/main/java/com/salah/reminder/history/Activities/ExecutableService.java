package com.salah.reminder.history.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.reminder.history.R;

import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExecutableService extends BroadcastReceiver {
    MediaPlayer mp = new MediaPlayer();

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificaitonHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificaitonHelper.getChannelNotification();
        notificaitonHelper.getManager().notify(1, nb.build());

        mp = MediaPlayer.create(context, R.raw.azan);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}