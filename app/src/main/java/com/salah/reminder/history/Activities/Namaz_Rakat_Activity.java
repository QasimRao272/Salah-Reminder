package com.salah.reminder.history.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.salah.reminder.history.R;

import static com.google.salahreminder.AdsManager.AdsKt.showBanner;
import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class Namaz_Rakat_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaz__rakat_);

        showInterstitial(Namaz_Rakat_Activity.this);
        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        showBanner(this,banner_container);

    }
}
