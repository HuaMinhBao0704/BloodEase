package com.example.bloodeasebackup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CertificatesActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);

        // Ánh xạ các TextView từ layout
        TextView tenBVGNTextView = findViewById(R.id.ten_bvgn);
        TextView amountOfBloodTextView = findViewById(R.id.amount_of_blood);
        TextView ngayDangKyTextView = findViewById(R.id.ngay_dang_ky);

        // Nhận dữ liệu từ Intent
        Intent intentC = getIntent();
        if (intentC != null) {
            String tenBVGN = intentC.getStringExtra("bvgn");
            String selectedBloodAmount = intentC.getStringExtra("selectedBloodAmount");
            String ngayDangKy = intentC.getStringExtra("selectedDate");

            // Hiển thị dữ liệu trong các TextView
            tenBVGNTextView.setText(tenBVGN);
            amountOfBloodTextView.setText(selectedBloodAmount);
            ngayDangKyTextView.setText("Ngày hiến máu: " + ngayDangKy);
        }

        backBtn = findViewById(R.id.backBtn);
        testBtn = findViewById(R.id.testBtn);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        testBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CertificatesActivity.this, CertificatesDetailActivity.class);

            String selectedBloodAmount = getIntent().getStringExtra("selectedBloodAmount");
            String userEmail = getIntent().getStringExtra("signInEmail");
            String tenBVGN = getIntent().getStringExtra("bvgn");
            String selectedDate = getIntent().getStringExtra("selectedDate");

            intent.putExtra("signInEmail", userEmail);
            intent.putExtra("bvgn", tenBVGN);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedBloodAmount", selectedBloodAmount);

            startActivity(intent);
        });
    }
}