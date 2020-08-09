package com.google.salahreminder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.salahreminder.AdsManager.SingletonAds;
import com.google.salahreminder.PrayTimeClasess.AppController;
import com.google.salahreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class SplashScreen extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        SingletonAds.Companion.init(getApplicationContext());
        //showInterstitial(SplashScreen.this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //showBanner(this,banner_container);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}