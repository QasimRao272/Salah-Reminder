package com.google.salahreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.google.salahreminder.AdsManager.AdsKt.showBanner;
import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class CalenderFurtherActivity extends AppCompatActivity {
    private CheckBox checkBoxFajarYes, checkBoxZuharYes, checkBoxAsarYes, checkBoxMaghribYes;
    private CheckBox checkBoxishaYes;
    private DatabaseReference databaseReference;
    private TextView tvDay, tvMonth, tvYear;
    String year, month;
    TextView txt_View_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_further);

        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        showInterstitial(CalenderFurtherActivity.this);
        showBanner(CalenderFurtherActivity.this, banner_container);
        SingletonAds.Companion.init(getApplicationContext());

        initilise();

        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH));

        String date = String.valueOf(getIntent().getIntExtra("date", 0));

        tvDay.setText(date);
        tvMonth.setText(" - " + month);
        tvYear.setText(" - " + year);

        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        txt_View_Date.setText(weekday_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Offered_Prayer")
                .child(getMacAddr()).child(year).child(month).child(date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("Fajar").exists()) {
                        String fajar = snapshot.child("Fajar").getValue().toString();
                        if (fajar.equals("Yes")) {
                            checkBoxFajarYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Zuhar").exists()) {
                        String zuhar = snapshot.child("Zuhar").getValue().toString();
                        if (zuhar.equals("Yes")) {
                            checkBoxZuharYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Asar").exists()) {
                        String asar = snapshot.child("Asar").getValue().toString();
                        if (asar.equals("Yes")) {
                            checkBoxAsarYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Maghrib").exists()) {
                        String maghrib = snapshot.child("Maghrib").getValue().toString();
                        if (maghrib.equals("Yes")) {
                            checkBoxMaghribYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Isha").exists()) {
                        String isha = snapshot.child("Isha").getValue().toString();
                        if (isha.equals("Yes")) {
                            checkBoxishaYes.setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(CalenderFurtherActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        checkBoxFajarYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxFajarYes.isChecked()) {
                    databaseReference.child("Fajar").setValue("Yes");
                    checkBoxFajarYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    databaseReference.child("Fajar").setValue("No");
                    checkBoxFajarYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        checkBoxZuharYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxZuharYes.isChecked()) {
                    databaseReference.child("Zuhar").setValue("Yes");
                    checkBoxZuharYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    databaseReference.child("Zuhar").setValue("No");
                    checkBoxZuharYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        checkBoxAsarYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxAsarYes.isChecked()) {
                    databaseReference.child("Asar").setValue("Yes");
                    checkBoxAsarYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    databaseReference.child("Asar").setValue("No");
                    checkBoxAsarYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        checkBoxMaghribYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxMaghribYes.isChecked()) {
                    databaseReference.child("Maghrib").setValue("Yes");
                    checkBoxMaghribYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    databaseReference.child("Maghrib").setValue("No");
                    checkBoxMaghribYes.setBackgroundResource(R.drawable.red_border);

                }
            }
        });

        checkBoxishaYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxishaYes.isChecked()) {
                    databaseReference.child("Isha").setValue("Yes");
                    checkBoxishaYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    databaseReference.child("Isha").setValue("No");
                    checkBoxishaYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });
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

    private void initilise() {
        checkBoxFajarYes = findViewById(R.id.fajar_btn_yes);
        checkBoxZuharYes = findViewById(R.id.zuhar_btn_yes);
        checkBoxAsarYes = findViewById(R.id.asar_btn_yes);
        checkBoxMaghribYes = findViewById(R.id.maghrib_btn_yes);
        checkBoxishaYes = findViewById(R.id.isha_btn_yes);
        tvDay = findViewById(R.id.tvDay);
        tvMonth = findViewById(R.id.tvMonth);
        tvYear = findViewById(R.id.tvYear);
        txt_View_Date = findViewById(R.id.current_day);
    }
}