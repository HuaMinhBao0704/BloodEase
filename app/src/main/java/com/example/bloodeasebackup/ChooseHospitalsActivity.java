package com.example.bloodeasebackup;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

public class ChooseHospitalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hospitals);

        // Nhận dữ liệu từ Intent (nếu có)
        Intent intent = getIntent();
        if (intent != null) {
            String tenBVGN = intent.getStringExtra("bvgn");
            int so_nguoi_da_dang_ky = intent.getIntExtra("so_nguoi_da_dang_ky", 0);

            // Ánh xạ các view từ layout
            TextView tenBVGNTextView = findViewById(R.id.bvgn);
            TextView soNguoiDangKyTextView = findViewById(R.id.so_nguoi_da_dang_ky);

            // Hiển thị dữ liệu trên giao diện
            tenBVGNTextView.setText(tenBVGN);
            soNguoiDangKyTextView.setText(so_nguoi_da_dang_ky + " người đăng ký");
        }

        // Find the confirmButton
        Button confirmButton = findViewById(R.id.confirmButton);

        // Set a click listener on the button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start CertificatesActivity
                Intent certificatesIntent = new Intent(ChooseHospitalsActivity.this, CertificatesActivity.class);
                startActivity(certificatesIntent);
            }
        });
    }
}
