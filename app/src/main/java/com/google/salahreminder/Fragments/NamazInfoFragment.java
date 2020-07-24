package com.google.salahreminder.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.salahreminder.Activities.ExecutableService;
import com.google.salahreminder.Activities.MainActivity;
import com.google.salahreminder.Activities.PDF_Activity;
import com.google.salahreminder.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class NamazInfoFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_namaz_info, container, false);

        TextView tvNewActivity = view.findViewById(R.id.tvNewActivity);
        tvNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PDF_Activity.class));
            }
        });

        TextView textView = view.findViewById(R.id.tvNewActivity);
        SpannableString content = new SpannableString("Method of Salaat");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        return view;
    }
}