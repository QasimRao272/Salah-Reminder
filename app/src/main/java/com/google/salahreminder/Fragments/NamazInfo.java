package com.google.salahreminder.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.salahreminder.Activities.ExecutableService;
import com.google.salahreminder.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class NamazInfo extends Fragment implements View.OnClickListener {

    private TextView tvFajar, tvZuhar, tvAsar, tvMaghrib, tvIsha;
    private int mHour, mMin;
    private String month, year, date;
    private String MY_PREFS_NAME = "Namaz_Reminder";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_namaz_info, container, false);

        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMin = c.get(Calendar.MINUTE);

        initilise();

        tvFajar.setOnClickListener(this);
        tvZuhar.setOnClickListener(this);
        tvAsar.setOnClickListener(this);
        tvMaghrib.setOnClickListener(this);
        tvIsha.setOnClickListener(this);

        gettingData();

        OffsetDateTime offset = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            offset = OffsetDateTime.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            month = String.valueOf(offset.getMonth());
            year = String.valueOf(offset.getYear());
            date = String.valueOf(offset.getDayOfMonth());
        }

        return view;
    }

    private void gettingData() {
        String fajar = prefs.getString("Fajar", "Set Time");
        String zuhar = prefs.getString("Zuhar", "Set Time");
        String asar = prefs.getString("Asar", "Set Time");
        String maghrib = prefs.getString("Maghrib", "Set Time");
        String isha = prefs.getString("Isha", "Set Time");

        tvFajar.setText(fajar);
        tvZuhar.setText(zuhar);
        tvAsar.setText(asar);
        tvMaghrib.setText(maghrib);
        tvIsha.setText(isha);
    }

    private void initilise() {
        tvFajar = view.findViewById(R.id.tvFajar);
        tvZuhar = view.findViewById(R.id.tvZuhar);
        tvAsar = view.findViewById(R.id.tvAsar);
        tvMaghrib = view.findViewById(R.id.tvMaghrib);
        tvIsha = view.findViewById(R.id.tvIsha);
        editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFajar:
                TimePickerDialog t1 = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Time time = new Time(hourOfDay, minute, 0);
                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm a");
                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        tvFajar.setText(s);
                        editor.putString("Fajar", s);
                        editor.putLong("fajar_h", hourOfDay);
                        editor.putLong("fajar_m", minute);
                        editor.apply();

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), ExecutableService.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 109833, intent, 0);
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }

                        /*Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP,
                                c.getTimeInMillis(), pendingIntent);*/

                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                        }

                    }
                }, mHour, mMin, false);
                t1.show();
                break;
            case R.id.tvZuhar:
                TimePickerDialog t2 = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Time time = new Time(hourOfDay, minute, 0);
                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm a");
                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        tvZuhar.setText(s);
                        editor.putString("Zuhar", s);
                        editor.putLong("zuhar_h", hourOfDay);
                        editor.putLong("zuhar_m", minute);
                        editor.apply();

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), ExecutableService.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 07564, intent, 0);
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }

                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                        }

                    }
                }, mHour, mMin, false);
                t2.show();
                break;

            case R.id.tvAsar:
                TimePickerDialog t3 = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Time time = new Time(hourOfDay, minute, 0);
                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm a");
                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        tvAsar.setText(s);
                        editor.putString("Asar", s);
                        editor.putLong("asar_h", hourOfDay);
                        editor.putLong("asar_m", minute);
                        editor.apply();

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), ExecutableService.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 9876, intent, 0);
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }

                        /*Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP,
                                c.getTimeInMillis(), pendingIntent);*/
                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                        }

                    }
                }, mHour, mMin, false);
                t3.show();
                break;

            case R.id.tvMaghrib:
                TimePickerDialog t4 = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Time time = new Time(hourOfDay, minute, 0);
                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm a");
                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        tvMaghrib.setText(s);
                        editor.putString("Maghrib", s);
                        editor.putLong("maghrib_h", hourOfDay);
                        editor.putLong("maghrib_m", minute);

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), ExecutableService.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 6789, intent, 0);
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }
                        /*Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP,
                                c.getTimeInMillis(), pendingIntent);*/

                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                        }

                        editor.apply();

                    }
                }, mHour, mMin, false);
                t4.show();
                break;

            case R.id.tvIsha:
                TimePickerDialog t5 = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Time time = new Time(hourOfDay, minute, 0);
                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm a");
                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        tvIsha.setText(s);
                        editor.putString("Isha", s);
                        editor.putLong("isha_h", hourOfDay);
                        editor.putLong("isha_m", minute);
                        editor.apply();

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), ExecutableService.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 12345, intent, 0);
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }

                        /*Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP,
                                c.getTimeInMillis(), pendingIntent);*/
                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
                        }


                    }
                }, mHour, mMin, false);
                t5.show();
                break;
        }
    }
}