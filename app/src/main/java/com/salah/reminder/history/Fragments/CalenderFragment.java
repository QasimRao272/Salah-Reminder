package com.salah.reminder.history.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.salah.reminder.history.Activities.CalenderFurtherActivity;
import com.salah.reminder.history.R;

public class CalenderFragment extends Fragment {

    CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarView = view.findViewById(R.id.calender_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getContext(), CalenderFurtherActivity.class);
                intent.putExtra("date", dayOfMonth);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });

        return view;
    }
}