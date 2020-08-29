package com.salah.reminder.history.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

import com.salah.reminder.history.AzanControl;
import com.salah.reminder.history.R;

public class ExecutableService extends BroadcastReceiver {
    MediaPlayer mp = new MediaPlayer();

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificaitonHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificaitonHelper.getChannelNotification();
        notificaitonHelper.getManager().notify(1, nb.build());

       /* mp = MediaPlayer.create(context, R.raw.azan);
        mp.start();

        if (!intent.getStringExtra("val").isEmpty() && intent.getStringExtra("val").equals("stop_azan")) {
            mp.stop();
            mp.release();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });*/

        AzanControl.getInstance(context).playMusic();

    }
}