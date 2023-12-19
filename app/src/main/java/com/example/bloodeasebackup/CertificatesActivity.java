package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CertificatesActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);

        backBtn = findViewById(R.id.backBtn);
        testBtn = findViewById(R.id.testBtn);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        testBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CertificatesActivity.this, CertificatesDetailActivity.class);
            startActivity(intent);
        });
    }
}