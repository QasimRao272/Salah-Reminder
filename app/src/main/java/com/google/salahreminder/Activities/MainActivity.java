package com.google.salahreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.salahreminder.AdsManager.SingletonAds;
import com.google.salahreminder.Fragments.CalenderFragment;
import com.google.salahreminder.Fragments.HomeFragment;
import com.google.salahreminder.Fragments.NamazInfoFragment;
import com.google.salahreminder.R;

import static com.google.salahreminder.AdsManager.AdsKt.showBanner;
import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class MainActivity extends AppCompatActivity {

    TextView tvFajar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SingletonAds.Companion.init(getApplicationContext());
        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        showBanner(MainActivity.this, banner_container);
        showInterstitial(MainActivity.this);
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        HomeFragment frag_name = new HomeFragment();
        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentcontainer, frag_name, frag_name.getTag()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            //showInterstitial(MainActivity.this);
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.calender:
                            //showInterstitial(MainActivity.this);
                            selectedFragment = new CalenderFragment();
                            break;
                        case R.id.namaz_info:
                            //showInterstitial(MainActivity.this);
                            selectedFragment = new NamazInfoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    return true;
                }
            };
}