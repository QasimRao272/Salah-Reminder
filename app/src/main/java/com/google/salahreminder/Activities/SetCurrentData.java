package com.google.salahreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.salahreminder.R;

import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class SetCurrentData extends AppCompatActivity implements View.OnClickListener {
    String year, month, day;
    TextView tvDay, tvMonth, tvYear;
    Button fajarBtnYes, fajarBtnNo, zuharBtnYes, zuharBtnNo, asarBtnYes, asarBtnNo;
    Button maghribBtnYes, maghribBtnNo, ishaBtnYes, ishaBtnNo;
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

        mRef = FirebaseDatabase.getInstance().getReference("Offered_Prayer").child(getMacAddr()).child(String.valueOf(year))
                .child(String.valueOf(month)).child(String.valueOf(day));

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("Fajar").exists()) {
                        if (snapshot.child("Fajar").getValue().toString().equals("Yes")) {
                            fajarBtnYes.setTextColor(Color.GREEN);
                            fajarBtnYes.setBackgroundResource(R.drawable.border);
                        }
                    }
                    if (snapshot.child("Zuhar").exists()) {
                        if (snapshot.child("Zuhar").getValue().toString().equals("Yes")) {
                            zuharBtnYes.setTextColor(Color.GREEN);
                            zuharBtnYes.setBackgroundResource(R.drawable.border);
                        }
                    }
                    if (snapshot.child("Asar").exists()) {
                        if (snapshot.child("Asar").getValue().toString().equals("Yes")) {
                            asarBtnYes.setTextColor(Color.GREEN);
                            asarBtnYes.setBackgroundResource(R.drawable.border);
                        }
                    }
                    if (snapshot.child("Maghrib").exists()) {
                        if (snapshot.child("MAghrib").getValue().toString().equals("Yes")) {
                            maghribBtnYes.setTextColor(Color.GREEN);
                            maghribBtnNo.setBackgroundResource(R.drawable.border);
                        }
                    }
                    if (snapshot.child("Isha").exists()) {
                        if (snapshot.child("Isha").getValue().toString().equals("Yes")) {
                            ishaBtnYes.setTextColor(Color.GREEN);
                            ishaBtnYes.setBackgroundResource(R.drawable.green_border);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fajarBtnYes.setOnClickListener(this);
        fajarBtnNo.setOnClickListener(this);
        zuharBtnYes.setOnClickListener(this);
        zuharBtnNo.setOnClickListener(this);
        asarBtnYes.setOnClickListener(this);
        asarBtnNo.setOnClickListener(this);
        maghribBtnYes.setOnClickListener(this);
        maghribBtnNo.setOnClickListener(this);
        ishaBtnYes.setOnClickListener(this);
        ishaBtnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fajar_btn_yes:
                mRef.child("Fajar").setValue("Yes");
                break;
            case R.id.fajar_btn_no:
                mRef.child("Fajar").setValue("No");
                break;
            case R.id.zuhar_btn_yes:
                mRef.child("Zuhar").setValue("Yes");
                break;
            case R.id.zuhar_btn_no:
                mRef.child("Zuhar").setValue("No");
                break;
            case R.id.asar_btn_yes:
                mRef.child("Asar").setValue("Yes");
                break;
            case R.id.asar_btn_no:
                mRef.child("Asar").setValue("No");
                break;
            case R.id.maghrib_btn_yes:
                mRef.child("Maghrib").setValue("Yes");
                break;
            case R.id.maghrib_btn_no:
                mRef.child("Maghrib").setValue("No");
                break;
            case R.id.isha_btn_yes:
                mRef.child("Isha").setValue("Yes");
                break;
            case R.id.isha_btn_no:
                mRef.child("Isha").setValue("No");
                break;
        }
    }

    private void initilize() {
        tvMonth = findViewById(R.id.tvMonth);
        tvDay = findViewById(R.id.tvDay);
        tvYear = findViewById(R.id.tvYear);
        fajarBtnYes = findViewById(R.id.fajar_btn_yes);
        fajarBtnNo = findViewById(R.id.fajar_btn_no);
        zuharBtnYes = findViewById(R.id.zuhar_btn_yes);
        zuharBtnNo = findViewById(R.id.zuhar_btn_no);
        asarBtnYes = findViewById(R.id.asar_btn_yes);
        asarBtnNo = findViewById(R.id.asar_btn_no);
        maghribBtnYes = findViewById(R.id.maghrib_btn_yes);
        maghribBtnNo = findViewById(R.id.maghrib_btn_no);
        ishaBtnYes = findViewById(R.id.isha_btn_yes);
        ishaBtnNo = findViewById(R.id.isha_btn_no);
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