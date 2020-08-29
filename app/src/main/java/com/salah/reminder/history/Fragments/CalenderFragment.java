package com.salah.reminder.history.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.salah.reminder.history.Activities.ActivityViewPrayers;
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
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);

                View view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null);
                dialog.setContentView(view1);

                Button btnUpdatePrayer = view1.findViewById(R.id.btnUpdatePrayers);
                Button btnViewPrayer = view1.findViewById(R.id.btnViewPrayers);


                btnUpdatePrayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Do something
                        Intent intent = new Intent(getContext(), CalenderFurtherActivity.class);
                        intent.putExtra("date", dayOfMonth);
                        intent.putExtra("month", month);
                        intent.putExtra("year", year);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                btnViewPrayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Do something
                        Intent intent = new Intent(getContext(), ActivityViewPrayers.class);
                        intent.putExtra("date", dayOfMonth);
                        intent.putExtra("month", month);
                        intent.putExtra("year", year);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });


                dialog.show();
            }
        });

        return view;
    }
}