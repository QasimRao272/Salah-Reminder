package com.google.salahreminder.Activities;

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
import com.google.salahreminder.R;

import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExecutableService extends BroadcastReceiver {
    MediaPlayer mp = new MediaPlayer();
    String off_Alarm;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificaitonHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificaitonHelper.getChannelNotification();
        notificaitonHelper.getManager().notify(1, nb.build());

         /* Notification notification = nb.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;*/

        mp = MediaPlayer.create(context, R.raw.azan);
        mp.start();

        off_Alarm = intent.getStringExtra("Off_Alarm");
        if (off_Alarm != null) {
            Toast.makeText(notificaitonHelper, "Off Alarm", Toast.LENGTH_SHORT).show();
            mp.stop();
            mp.release();
            setAppComponentEnabled(context, ExecutableService.class, false);
        }
    }

    public static void setAppComponentEnabled(@NonNull final Context context, @NonNull final Class<?> componentClass, final boolean enable) {
        final PackageManager pm = context.getPackageManager();
        final int enableFlag = enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        pm.setComponentEnabledSetting(new ComponentName(context, componentClass), enableFlag, PackageManager.DONT_KILL_APP);
    }
}