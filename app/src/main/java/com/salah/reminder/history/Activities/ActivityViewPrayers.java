package com.salah.reminder.history.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.reminder.history.R;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityViewPrayers extends AppCompatActivity {
    String year, month, date;
    TextView tvDay, tvMonth, tvYear, current_day;
    CircleImageView ivFajar, ivZuhar, ivAsar, ivMaghrib, ivIsha;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prayers);

        init();

        date = String.valueOf(getIntent().getIntExtra("date", 0));
        month = String.valueOf(getIntent().getIntExtra("month", 0));
        year = String.valueOf(getIntent().getIntExtra("year", 0));

        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        current_day.setText(weekday_name);

        tvDay.setText(date);
        tvMonth.setText(" - " + month);
        tvYear.setText(" - " + year);

        databaseReference = FirebaseDatabase.getInstance().getReference("Offered_Prayer")
                .child(getMacAddr()).child(year).child(month).child(date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Fajar").exists()) {
                    if (snapshot.child("Fajar").getValue().toString().equals("Yes")) {
                        ivFajar.setImageResource(R.mipmap.green_circle);
                    }
                }

                if (snapshot.child("Zuhar").exists()) {
                    if (snapshot.child("Zuhar").getValue().toString().equals("Yes")) {
                        ivZuhar.setImageResource(R.mipmap.green_circle);
                    }
                }

                if (snapshot.child("Asar").exists()) {
                    if (snapshot.child("Asar").getValue().toString().equals("Yes")) {
                        ivAsar.setImageResource(R.mipmap.green_circle);
                    }
                }

                if (snapshot.child("Maghrib").exists()) {
                    if (snapshot.child("Maghrib").getValue().toString().equals("Yes")) {
                        ivMaghrib.setImageResource(R.mipmap.green_circle);
                    }
                }

                if (snapshot.child("Isha").exists()) {
                    if (snapshot.child("Isha").getValue().toString().equals("Yes")) {
                        ivIsha.setImageResource(R.mipmap.green_circle);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        tvDay = findViewById(R.id.tvDay);
        tvMonth = findViewById(R.id.tvMonth);
        tvYear = findViewById(R.id.tvYear);

        ivFajar = findViewById(R.id.iv_fajar);
        ivZuhar = findViewById(R.id.iv_zuhar);
        ivAsar = findViewById(R.id.iv_asar);
        ivMaghrib = findViewById(R.id.iv_maghrib);
        ivIsha = findViewById(R.id.iv_isha);

        current_day = findViewById(R.id.current_day);
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
