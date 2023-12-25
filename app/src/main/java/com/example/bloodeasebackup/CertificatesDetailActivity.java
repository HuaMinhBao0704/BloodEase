package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CertificatesDetailActivity extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates_detail);
        // Ánh xạ các TextView từ layout
        TextView tenBVGNTextView = findViewById(R.id.tencs_cnhm);
        TextView amountOfBloodTextView = findViewById(R.id.luongmauhien_cnhm);
        TextView ngayDangKyTextView = findViewById(R.id.dmyhienmau_cnhm);

        // Nhận dữ liệu từ Intent
        Intent intentC = getIntent();
        if (intentC != null) {
            String tenBVGN = intentC.getStringExtra("bvgn");
            String selectedBloodAmount =intentC.getStringExtra("selectedBloodAmount");
            String ngayDangKy = intentC.getStringExtra("selectedDate");

            // Hiển thị dữ liệu trong các TextView
            tenBVGNTextView.setText(tenBVGN);
            amountOfBloodTextView.setText(selectedBloodAmount);
            ngayDangKyTextView.setText(ngayDangKy);
        }


        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
}