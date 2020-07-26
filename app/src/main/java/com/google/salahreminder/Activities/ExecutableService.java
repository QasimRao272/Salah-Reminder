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
    //String MY_PREFS_NAME = "Namaz_Reminder";
    String off_Alarm;

     /*long date, month, year;
     public static String yes, no;
     String namaz_time;
     long c_h, c_m;
     long f_h, z_h, a_h, m_h, i_h;
     DatabaseReference ref;*/

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificaitonHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificaitonHelper.getChannelNotification();
        notificaitonHelper.getManager().notify(1, nb.build());

        mp = MediaPlayer.create(context, R.raw.azan);
        mp.start();

        off_Alarm = intent.getStringExtra("Off_Alarm");
        if (off_Alarm != null) {
            Toast.makeText(notificaitonHelper, "Off Alarm", Toast.LENGTH_SHORT).show();
            mp.stop();
            mp.release();
            setAppComponentEnabled(context, ExecutableService.class, false);
        }

        /*Calendar calendar = Calendar.getInstance();
          year = calendar.get(Calendar.YEAR);
          month = calendar.get(Calendar.MONTH);
          date = calendar.get(Calendar.DAY_OF_MONTH);
          c_h = calendar.get(Calendar.HOUR);
          c_m = calendar.get(Calendar.MINUTE);*/

        /* ref = FirebaseDatabase.getInstance().getReference("Offered_Prayers").child(getMacAddr())
                .child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date));*/

        /*SharedPreferences prefssss = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        f_h = prefssss.getLong("f_h", 0);
        long f_m = prefssss.getLong("f_m", 0);

        z_h = prefssss.getLong("z_h", 0);
        long z_m = prefssss.getLong("z_m", 0);

        a_h = prefssss.getLong("a_h", 0);
        long a_m = prefssss.getLong("z_m", 0);

        m_h = prefssss.getLong("m_h", 0);
        long m_m = prefssss.getLong("m_m", 0);

        i_h = prefssss.getLong("i_h", 0);
        long i_m = prefssss.getLong("i_m", 0);

        yes = intent.getStringExtra("Yes");
        no = intent.getStringExtra("No");

        if (yes != null) {
            Toast.makeText(notificaitonHelper, "Yes Clicked", Toast.LENGTH_LONG).show();
            if (f_h == c_h) {
                ref.child("Fajar").setValue("Yes");
            } else if (z_h == c_h) {
                ref.child("Zuhar").setValue("Yes");
            } else if (a_h == c_h) {
                ref.child("Asar").setValue("Yes");
            } else if (m_h == c_h) {
                ref.child("Maghri").setValue("Yes");
            } else if (i_h == c_h) {
                ref.child("Isha").setValue("Yes");
            }
            if (mp.isPlaying()) {
                mp.release();
            } else {
                mp.start();
            }
        } else if (no != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Offered_Prayers").child(getMacAddr());
            Toast.makeText(notificaitonHelper, "No Clicked", Toast.LENGTH_LONG).show();
            ref.child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date)).child(namaz_time).setValue("No");
            if (mp.isPlaying()) {
                mp.release();
            } else {
                mp.start();
            }
        }*/

        /* if (!intent.getStringExtra("Yes").isEmpty() && intent.getStringExtra("Yes").isEmpty()) {
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
        }*/

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {}
         */

        /* Notification notification = nb.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;*/

    }

    public static void setAppComponentEnabled(@NonNull final Context context, @NonNull final Class<?> componentClass, final boolean enable) {
        final PackageManager pm = context.getPackageManager();
        final int enableFlag = enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        pm.setComponentEnabledSetting(new ComponentName(context, componentClass), enableFlag, PackageManager.DONT_KILL_APP);
    }

    /*public static String getMacAddr() {
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
    }*/
}