package com.google.salahreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.salahreminder.R;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SetCurrentData extends AppCompatActivity {
    String year, month, day;
    TextView tvDay, tvMonth, tvYear, txt_View_Date;
    CheckBox fajarBtnYes, zuharBtnYes, asarBtnYes, maghribBtnYes, ishaBtnYes;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_current_data);

        initilize();

        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH));
        day = String.valueOf(calendar.get(Calendar.DATE));

        tvDay.setText(day);
        tvYear.setText(" - " + year);
        tvMonth.setText(" - " + month);
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        txt_View_Date.setText(weekday_name);

        mRef = FirebaseDatabase.getInstance().getReference("Offered_Prayer").child(getMacAddr()).child(String.valueOf(year))
                .child(String.valueOf(month)).child(String.valueOf(day));
        mRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("Fajar").exists()) {
                        String fajar = snapshot.child("Fajar").getValue().toString();
                        if (fajar.equals("Yes")) {
                            fajarBtnYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Zuhar").exists()) {
                        String zuhar = snapshot.child("Zuhar").getValue().toString();
                        if (zuhar.equals("Yes")) {
                            zuharBtnYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Asar").exists()) {
                        String asar = snapshot.child("Asar").getValue().toString();
                        if (asar.equals("Yes")) {
                            asarBtnYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Maghrib").exists()) {
                        String maghrib = snapshot.child("Maghrib").getValue().toString();
                        if (maghrib.equals("Yes")) {
                            maghribBtnYes.setChecked(true);
                        }
                    }
                    if (snapshot.child("Isha").exists()) {
                        String isha = snapshot.child("Isha").getValue().toString();
                        if (isha.equals("Yes")) {
                            ishaBtnYes.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fajarBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fajarBtnYes.isChecked()) {
                    mRef.child("Fajar").setValue("Yes");
                    fajarBtnYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    mRef.child("Fajar").setValue("No");
                    fajarBtnYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        zuharBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zuharBtnYes.isChecked()) {
                    mRef.child("Zuhar").setValue("Yes");
                    zuharBtnYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    mRef.child("Zuhar").setValue("No");
                    zuharBtnYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        asarBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (asarBtnYes.isChecked()) {
                    mRef.child("Asar").setValue("Yes");
                    asarBtnYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    mRef.child("Asar").setValue("No");
                    asarBtnYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        maghribBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (maghribBtnYes.isChecked()) {
                    mRef.child("Maghrib").setValue("Yes");
                    maghribBtnYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    mRef.child("Maghrib").setValue("No");
                    maghribBtnYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });

        ishaBtnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ishaBtnYes.isChecked()) {
                    mRef.child("Isha").setValue("Yes");
                    ishaBtnYes.setBackgroundResource(R.drawable.green_border);
                } else {
                    mRef.child("Isha").setValue("No");
                    ishaBtnYes.setBackgroundResource(R.drawable.red_border);
                }
            }
        });
    }

    private void initilize() {
        tvMonth = findViewById(R.id.tvMonth);
        tvDay = findViewById(R.id.tvDay);
        tvYear = findViewById(R.id.tvYear);
        fajarBtnYes = findViewById(R.id.fajar_btn_yes);
        zuharBtnYes = findViewById(R.id.zuhar_btn_yes);
        asarBtnYes = findViewById(R.id.asar_btn_yes);
        maghribBtnYes = findViewById(R.id.maghrib_btn_yes);
        ishaBtnYes = findViewById(R.id.isha_btn_yes);
        txt_View_Date = findViewById(R.id.current_day);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}