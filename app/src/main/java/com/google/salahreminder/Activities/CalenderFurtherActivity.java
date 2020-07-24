package com.google.salahreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.salahreminder.AdsManager.SingletonAds;
import com.google.salahreminder.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class CalenderFurtherActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_further);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        TextView textView = toolbar.findViewById(R.id.toolbar_text);
        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        textView.setText("Checking");
        showInterstitial(CalenderFurtherActivity.this);
        tv = findViewById(R.id.tv);
        int date = getIntent().getIntExtra("date", 0);
        tv.setText("Selected Date: " + date);

        /*//final TextView result = (TextView) findViewById(R.id.textview);
        String text = "I tend to shy away from restaurant chains, but wherever I go, PF Chang&apos;s has solidly good food and, like Starbucks, they&apos;re reliable. We were staying in Boston for a week and after a long day and blah blah blah blah...";

        if (text.length() > 20) {
            text = text.substring(0, 20) + "...";
            tv.setText(Html.fromHtml(text + "<font color='red'> <u>View More</u></font>"));
        }*/

        SingletonAds.Companion.init(getApplicationContext());
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
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
            ex.printStackTrace();
        }
        return "";
    }
}