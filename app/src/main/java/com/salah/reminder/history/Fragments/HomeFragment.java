package com.salah.reminder.history.Fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.reminder.history.Activities.ExecutableService;
import com.salah.reminder.history.PrayTimeClasess.AppController;
import com.salah.reminder.history.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Looper.getMainLooper;

public class HomeFragment extends Fragment {
    private TextView tvFajar, tvZuhar, tvAsar, tvMaghrib, tvIsha, txt_View_Day,
            txt_View_Date, tvSunrise, tvSunset, tvLocation1, tvLocation2;
    private View view;
    private String MY_PREFS_NAME = "Namaz_Reminder";
    private SharedPreferences prefs;
    private TextView fajar_fajar, zuhur_zuhar, asar_asar, maghrib_maghrib, isha_isha;
    long current_h, current_m;
    String tag_json_obj = "json_obj_req";
    String address;
    String month, year, date;
    String ff, zz, mm, ii, aa, sun_rise_sun, sunset_sun_set;
    TextView c_time;
    TextView tvQazaNamaz;
    int counter;
    String y, m, dd;
    List<Integer> values = new ArrayList<>();
    int last_counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initialization();

        // Qaza Namaz Functionality

        Calendar calendar = Calendar.getInstance();
        y = String.valueOf(calendar.get(Calendar.YEAR));
        m = String.valueOf(calendar.get(Calendar.MONTH));
        dd = String.valueOf(calendar.get(Calendar.DATE));

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("Offered_Prayer").child(getMacAddr());
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ///////////////////////////////////
                for (DataSnapshot ds1 : snapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        for (DataSnapshot ds3 : ds2.getChildren()) {
                            for (DataSnapshot ds4 : ds3.getChildren()) {
                                Log.d("Value", "onDataChange: " + ds4.getValue());
                                if (ds4.getValue().equals("No")) {
                                    values.clear();
                                    values.add(++counter);
                                    Log.d("Values", "onCreateView: " + values);
                                    tvQazaNamaz.setText("Qaza Namaz: " + values);
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ///////////////////////////////////////////////////////////////

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new

                                        Runnable() {
                                            @Override
                                            public void run() {
                                                c_time.setText(new SimpleDateFormat("hh:mm ss a", Locale.US).format(new Date()));
                                                someHandler.postDelayed(this, 1000);
                                            }
                                        }, 10);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String fajar = prefs.getString("f", "-- : --");
        String zuhar = prefs.getString("z", "-- : --");
        String asar = prefs.getString("a", "-- : --");
        String maghrib = prefs.getString("m", "-- : --");
        String isha = prefs.getString("i", "-- : --");
        String sun_rise = prefs.getString("s_r", "-- : --");
        String sun_set = prefs.getString("s_s", "-- : --");
        String loc = prefs.getString("location", "-- : --");

        tvFajar.setText(fajar);
        tvZuhar.setText(zuhar);
        tvAsar.setText(asar);
        tvMaghrib.setText(maghrib);
        tvIsha.setText(isha);
        tvSunrise.setText(sun_rise);
        tvSunset.setText(sun_set);

        String text1 = loc;
        String text2 = loc;

        if (text1.length() > 20) {
            text1 = text1.substring(0, 20);
            tvLocation1.setText(Html.fromHtml(text1));
        }
        if (text2.length() >= 40) {
            text2 = text2.substring(20, 40) + "...";
            tvLocation2.setText(Html.fromHtml(text2));
        }

        // locationEnabled();

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(

                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        }

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mmmm-yyyy");
        String todayString = formatter.format(todayDate);
        txt_View_Date.setText(todayString);

        OffsetDateTime offset = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            offset = OffsetDateTime.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            month = String.valueOf(offset.getMonth());
            year = String.valueOf(offset.getYear());
            date = String.valueOf(offset.getDayOfMonth());
        }

        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        txt_View_Day.setText("Today / " + weekday_name);

        Date d = new Date();
        CharSequence s = DateFormat.format("d MMMM yyyy ", d.getTime());
        txt_View_Date.setText(s);

        tvSunrise.setText(prefs.getString("s_r", "Set Time"));
        tvSunset.setText(prefs.getString("s_s", "Set Time"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            current_h = LocalDateTime.now().getHour();
            current_m = LocalDateTime.now().getMinute();
        }

        // Get LocationManager object
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = null;
        provider = locationManager != null ? locationManager.getBestProvider(criteria, true) : null;

        // Get Current Location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                //return;
            }
        }

        Location myLocation = null;
        if (provider != null) {
            myLocation = locationManager.getLastKnownLocation(provider);
        }

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            if (myLocation != null) {
                addresses = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                address = addresses.get(0).getAddressLine(0);
                Log.d("Location", "onLocationChanged: " + address);
                Toast.makeText(getContext(), "Location Updated!", Toast.LENGTH_SHORT).show();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        if (

                getContext() != null) {
            String url = "http://api.aladhan.com/v1/timingsByAddress?address=" + address;
            ////
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ff = response.getJSONObject("data").getJSONObject("timings").get("Fajr").toString();
                                sun_rise_sun = response.getJSONObject("data").getJSONObject("timings").get("Sunrise").toString();
                                zz = response.getJSONObject("data").getJSONObject("timings").get("Dhuhr").toString();
                                aa = response.getJSONObject("data").getJSONObject("timings").get("Asr").toString();
                                sunset_sun_set = response.getJSONObject("data").getJSONObject("timings").get("Sunset").toString();
                                mm = response.getJSONObject("data").getJSONObject("timings").get("Maghrib").toString();
                                ii = response.getJSONObject("data").getJSONObject("timings").get("Isha").toString();

                                /////////////////////////////////////////////
                                String alaramtime_fajar = ff;
                                String alaramtimesplit_fajar[] = alaramtime_fajar.split(":");
                                int firstval_fajar = Integer.parseInt(alaramtimesplit_fajar[0]);    //4
                                int secondval_fajar = Integer.parseInt(alaramtimesplit_fajar[1]);   //05

                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, firstval_fajar);
                                c.set(Calendar.MINUTE, secondval_fajar);
                                c.set(Calendar.SECOND, 0);

                                if (getContext() != null) {
                                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(getContext(), ExecutableService.class);

                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 109833, intent, 0);
                                    if (c.before(Calendar.getInstance())) {
                                        c.add(Calendar.DATE, 1);
                                    }

                                    if (alarmManager != null) {
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                                c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                                        Log.d("Fajar", "onResponse: Alarm Set Fajar");
                                    }
                                }
                                ///////////////////////////////////////////////

                                /////////////////////////////////////////////
                                String alaramtime_zuhar = zz;
                                String alaramtimesplit_zuhar[] = alaramtime_zuhar.split(":");
                                int firstval_zuhar = Integer.parseInt(alaramtimesplit_zuhar[0]);    //4
                                int secondval_zuhar = Integer.parseInt(alaramtimesplit_zuhar[1]);   //05

                                Calendar c2 = Calendar.getInstance();
                                c2.set(Calendar.HOUR_OF_DAY, firstval_zuhar);
                                c2.set(Calendar.MINUTE, secondval_zuhar);
                                c2.set(Calendar.SECOND, 0);

                                if (getContext() != null) {
                                    AlarmManager alarmManager_zuhar = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent_zuhar = new Intent(getContext(), ExecutableService.class);

                                    PendingIntent pendingIntent_zuhar = PendingIntent.getBroadcast(getContext(), 675483, intent_zuhar, 0);
                                    if (c2.before(Calendar.getInstance())) {
                                        c2.add(Calendar.DATE, 1);
                                    }

                                    if (alaramtime_zuhar != null) {
                                        alarmManager_zuhar.setRepeating(AlarmManager.RTC_WAKEUP,
                                                c2.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent_zuhar); //Repeat every 24 hours
                                        //Toast.makeText(getContext(), "Alarm Set Zuhar!", Toast.LENGTH_SHORT).show();
                                        Log.d("Zuhar", "onResponse: Alarm Set Zuhar");
                                    }
                                }
                                ///////////////////////////////////////////////
                                /////////////////////////////////////////////
                                String alaramtime_asar = aa;
                                String alaramtimesplit_asar[] = alaramtime_asar.split(":");
                                int firstval_asar = Integer.parseInt(alaramtimesplit_asar[0]);//4
                                int secondval_asar = Integer.parseInt(alaramtimesplit_asar[1]);   //05

                                Calendar c3 = Calendar.getInstance();
                                c3.set(Calendar.HOUR_OF_DAY, firstval_asar);
                                c3.set(Calendar.MINUTE, secondval_asar);
                                c3.set(Calendar.SECOND, 0);

                                if (getContext() != null) {
                                    AlarmManager alarmManager_asar = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent_asar = new Intent(getContext(), ExecutableService.class);

                                    PendingIntent pendingIntent_asar = PendingIntent.getBroadcast(getContext(), 768564, intent_asar, 0);
                                    if (c3.before(Calendar.getInstance())) {
                                        c3.add(Calendar.DATE, 1);
                                    }

                                    if (alarmManager_asar != null) {
                                        alarmManager_asar.setRepeating(AlarmManager.RTC_WAKEUP,
                                                c3.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent_asar); //Repeat every 24 hours
                                        //Toast.makeText(getContext(), "Alarm Set Asar!", Toast.LENGTH_SHORT).show();
                                        Log.d("Asar", "onResponse: Alarm Set Asar");
                                    }
                                }
                                ///////////////////////////////////////////////
                                /////////////////////////////////////////////
                                String alaramtime_maghrib = mm;
                                String alaramtimesplit_maghrib[] = alaramtime_maghrib.split(":");
                                int firstval_maghrib = Integer.parseInt(alaramtimesplit_maghrib[0]);//4
                                int secondval_maghrib = Integer.parseInt(alaramtimesplit_maghrib[1]);   //05

                                Calendar c4 = Calendar.getInstance();
                                c4.set(Calendar.HOUR_OF_DAY, firstval_maghrib);
                                c4.set(Calendar.MINUTE, secondval_maghrib);
                                c4.set(Calendar.SECOND, 0);

                                if (getContext() != null) {
                                    AlarmManager alarmManager_maghrib = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent_maghrib = new Intent(getContext(), ExecutableService.class);

                                    PendingIntent pendingIntent_zuhar = PendingIntent.getBroadcast(getContext(), 980324, intent_maghrib, 0);
                                    if (c4.before(Calendar.getInstance())) {
                                        c4.add(Calendar.DATE, 1);
                                    }

                                    if (alaramtime_maghrib != null) {
                                        alarmManager_maghrib.setRepeating(AlarmManager.RTC_WAKEUP,
                                                c4.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent_zuhar); //Repeat every 24 hours
                                        Log.d("Maghrib", "onResponse: Alarm Set Maghrib");
                                    }
                                }
                                ///////////////////////////////////////////////
                                /////////////////////////////////////////////
                                String alaramtime_isha = ii;
                                String alaramtimesplit_isha[] = alaramtime_isha.split(":");
                                int firstval_isha = Integer.parseInt(alaramtimesplit_isha[0]);//4
                                int secondval_isha = Integer.parseInt(alaramtimesplit_isha[1]);   //05

                                Calendar c5 = Calendar.getInstance();
                                c5.set(Calendar.HOUR_OF_DAY, firstval_isha);
                                c5.set(Calendar.MINUTE, secondval_isha);
                                c5.set(Calendar.SECOND, 0);

                                if (getContext() != null) {
                                    AlarmManager alarmManager_isha = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent_isha = new Intent(getContext(), ExecutableService.class);

                                    PendingIntent pendingIntent_isha = PendingIntent.getBroadcast(getContext(), 764687, intent_isha, 0);
                                    if (c5.before(Calendar.getInstance())) {
                                        c5.add(Calendar.DATE, 1);
                                    }

                                    if (alaramtime_isha != null) {
                                        alarmManager_isha.setRepeating(AlarmManager.RTC_WAKEUP,
                                                c5.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent_isha); //Repeat every 24 hours
                                        Log.d("Isha", "onResponse: Alarm Set Isha");
                                    }
                                }
                                ///////////////////////////////////////////////

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                    String f = "" + ff;
                                    String z = "" + zz;
                                    String a = "" + aa;
                                    String m = "" + mm;
                                    String i = "" + ii;
                                    String s_r = "" + sun_rise_sun;
                                    String s_s = "" + sunset_sun_set;

                                    f = LocalTime.parse(ff).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    z = LocalTime.parse(zz).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    a = LocalTime.parse(aa).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    m = LocalTime.parse(mm).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    i = LocalTime.parse(ii).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    s_r = LocalTime.parse(sun_rise_sun).format(DateTimeFormatter.ofPattern("hh : mm a"));
                                    s_s = LocalTime.parse(sunset_sun_set).format(DateTimeFormatter.ofPattern("hh : mm a"));

                                    tvFajar.setText("" + f);
                                    tvZuhar.setText("" + z);
                                    tvAsar.setText("" + a);
                                    tvMaghrib.setText("" + m);
                                    tvIsha.setText("" + i);
                                    tvSunrise.setText("" + s_r);
                                    tvSunset.setText("" + s_s);

                                    if (getContext() != null) {
                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("f", f);
                                        editor.putString("z", z);
                                        editor.putString("a", a);
                                        editor.putString("m", m);
                                        editor.putString("i", i);
                                        editor.putString("s_r", s_r);
                                        editor.putString("s_s", s_s);
                                        editor.putString("location", address);
                                        editor.apply();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("TAG2", "Error: " + error.getMessage());
                    Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        return view;
    }

    private void locationEnabled() {
        LocationManager lm = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(getContext())
                    .setMessage("GPS Enable")
                    .setPositiveButton("Go To Settings", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void initialization() {
        tvFajar = view.findViewById(R.id.tvFajar);
        tvZuhar = view.findViewById(R.id.tvZuhar);
        tvAsar = view.findViewById(R.id.tvAsar);
        tvMaghrib = view.findViewById(R.id.tvMaghrib);
        tvIsha = view.findViewById(R.id.tvIsha);

        txt_View_Date = view.findViewById(R.id.txt_View_Date);
        txt_View_Day = view.findViewById(R.id.txt_View_Day);

        tvSunrise = view.findViewById(R.id.tv_sunrise);
        tvSunset = view.findViewById(R.id.tv_sunset);

        fajar_fajar = view.findViewById(R.id.fajar);
        zuhur_zuhar = view.findViewById(R.id.Zuhar);
        asar_asar = view.findViewById(R.id.Asar);
        maghrib_maghrib = view.findViewById(R.id.Maghrib);
        isha_isha = view.findViewById(R.id.Isha);

        tvLocation1 = view.findViewById(R.id.tvLocation1);
        tvLocation2 = view.findViewById(R.id.tvLocation2);
        c_time = view.findViewById(R.id.c_time);

        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        tvQazaNamaz = view.findViewById(R.id.tvQazaNamaz);
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

    public void function(int i) {
        DatabaseReference refRefrence = FirebaseDatabase.getInstance().getReference("Offered_Prayer")
                .child(getMacAddr());
        refRefrence.child("Counter").setValue(i);
    }
}