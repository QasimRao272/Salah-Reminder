package com.google.salahreminder.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.salahreminder.R;

import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ExecutableService extends BroadcastReceiver {
    MediaPlayer mp = new MediaPlayer();
    long date, month, year;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificaitonHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificaitonHelper.getChannelNotification();
        notificaitonHelper.getManager().notify(1, nb.build());

        //final MediaPlayer mp = MediaPlayer.create(context, R.raw.azan);

        if (mp.isPlaying()) {
            mp.release();
        } else {
            mp.start();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!intent.getStringExtra("Yes").isEmpty() && intent.getStringExtra("Yes").isEmpty()) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Offered_Prayers").child(getMacAddr());
                Toast.makeText(notificaitonHelper, "Yes Clicked", Toast.LENGTH_LONG).show();
                ref.child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date)).child("Fajar").setValue("Yes");
            } else if (!intent.getStringExtra("Yes").equals("") && intent.getStringExtra("Yes").equals("No")) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Offered_Prayers").child(getMacAddr());
                Toast.makeText(notificaitonHelper, "No Clicked", Toast.LENGTH_LONG).show();
                ref.child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date)).child("Fajar").setValue("No");
            } else if (!intent.getStringExtra("Yes").equals("") && intent.getStringExtra("Yes").equals("Alarm")) {
                mp.stop();
                mp.release();
            }
        }

        /* Notification notification = nb.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;*/

    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(":"));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "";
    }
}