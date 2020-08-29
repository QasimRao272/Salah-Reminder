package com.salah.reminder.history;

import android.content.Context;
import android.media.MediaPlayer;

public class AzanControl {
    private static AzanControl sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public AzanControl(Context context) {
        mContext = context;
    }

    public static AzanControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AzanControl(context);
        }
        return sInstance;
    }

    public void playMusic() {
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.azan);
        mMediaPlayer.start();
    }

    public void stopMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }
}
