package com.salah.reminder.history.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.salah.reminder.history.R;

import static com.google.salahreminder.AdsManager.AdsKt.showBanner;
import static com.google.salahreminder.AdsManager.AdsKt.showInterstitial;

public class PDF_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_);
        showInterstitial(PDF_Activity.this);
        FrameLayout banner_container = findViewById(R.id.ad_view_container);
        showBanner(this,banner_container);


        PDFView pdfView;
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("salaatmethod.pdf")
                .enableSwipe(true)
                .swipeHorizontal(true)
                .defaultPage(7)
                .load();
    }
}