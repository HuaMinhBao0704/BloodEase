package com.example.bloodeasebackup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CertificatesActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);

        // Ánh xạ các TextView từ layout
//        TextView tenBVGNTextView = findViewById(R.id.ten_bvgn);
//        TextView amountOfBloodTextView = findViewById(R.id.amount_of_blood);
//        TextView ngayDangKyTextView = findViewById(R.id.ngay_dang_ky);

        // Nhận dữ liệu từ Intent
//        Intent intentC = getIntent();
//        if (intentC != null) {
//            String tenBVGN = intentC.getStringExtra("bvgn");
//            String selectedBloodAmount =intentC.getStringExtra("selectedBloodAmount");
//            String ngayDangKy = intentC.getStringExtra("selectedDate");
//
//            // Hiển thị dữ liệu trong các TextView
//            tenBVGNTextView.setText(tenBVGN);
//            amountOfBloodTextView.setText(selectedBloodAmount);
//            ngayDangKyTextView.setText("Ngày hiến máu: "+ ngayDangKy);
//        }

        backBtn = findViewById(R.id.backBtn);
        testBtn = findViewById(R.id.testBtn);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userEmail = bundle.getString("signInEmail");
            getFirestoreUserData(userEmail);
            getFirestoreCertificatesData(userEmail);
        }

        testBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CertificatesActivity.this, CertificatesDetailActivity.class);
            //Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String userEmail = bundle.getString("signInEmail");
                intent.putExtra("signInEmail", userEmail);
                getFirestoreUserData(userEmail);
                getFirestoreCertificatesData(userEmail);
                }


            String selectedBloodAmount =getIntent().getStringExtra("selectedBloodAmount");
            //String userEmail = getIntent().getStringExtra("signInEmail");
            String tenBVGN=getIntent().getStringExtra("bvgn");
            String selectedDate = getIntent().getStringExtra("selectedDate");

            intent.putExtra("bvgn", tenBVGN);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedBloodAmount", selectedBloodAmount);

            startActivity(intent);
        });
    }

    public void getFirestoreUserData(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Thực hiện truy vấn để lấy dữ liệu người dùng với điều kiện email
        db.collection("Accounts")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy dữ liệu từ document và hiển thị hoặc xử lý theo ý muốn
                            String fullName = document.getString("fullName");
                            String phone = document.getString("phone");
                            Timestamp ngaysinh = document.getTimestamp("dob");
                            String ngayChonFirestore = ngaysinh != null ?
                                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngaysinh.toDate()) : "";
                            // Hiển thị dữ liệu trong TextView
                            TextView fullNameTextView = findViewById(R.id.ten);
                            fullNameTextView.setText(fullName);
                            TextView dobView = findViewById(R.id.ngaysinh);
                            dobView.setText(ngayChonFirestore);
                            TextView phoneView = findViewById(R.id.sdt);
                            phoneView.setText(phone);
                            TextView emailView = findViewById(R.id.gmail);
                            emailView.setText(userEmail);
                        }
                    } else {
                        // Xử lý khi truy vấn không thành công
                        // Ví dụ: Hiển thị thông báo lỗi
                        Toast.makeText(getApplicationContext(), "Lỗi khi lấy dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void getFirestoreCertificatesData(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Thực hiện truy vấn để lấy dữ liệu người dùng với điều kiện email
        db.collection("certificates")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy dữ liệu từ document và hiển thị hoặc xử lý theo ý muốn
                            String benhvien = document.getString("bvgn");
                            //String amount = document.getString("amount");
                            String eventId= document.getString("eventId");
                            boolean isVerified = document.getBoolean("isVerified");
                            //tring blood= document.getString("blood");
                            Timestamp ngaydangky = document.getTimestamp("date");
                            String ngayChonFirestore = ngaydangky != null ?
                                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngaydangky.toDate()) : "";
                            // Hiển thị dữ liệu trong TextView
                            TextView fullNameTextView = findViewById(R.id.ten_bvgn);
                            fullNameTextView.setText(benhvien);
                            TextView dobView = findViewById(R.id.ngay_dang_ky);
                            dobView.setText("Thời gian: "+ ngayChonFirestore);
                            TextView event = findViewById(R.id.soseri);
                            event.setText("Số seri: "+ eventId);
                            // Hiển thị trạng thái xác thực
                            TextView tinhtrangTextView = findViewById(R.id.tinhtrang);
                            if (isVerified) {
                                tinhtrangTextView.setText("Đã xác thực");
                                tinhtrangTextView.setTextColor(Color.parseColor("#126294"));  // Set text color to green
                            } else {
                                tinhtrangTextView.setText("Chờ xác thực");
                                tinhtrangTextView.setTextColor(Color.parseColor("#FFA927"));  // Set text color to yellow
                            }

                        }
                    } else {
                        // Xử lý khi truy vấn không thành công
                        // Ví dụ: Hiển thị thông báo lỗi
                        Toast.makeText(getApplicationContext(), "Lỗi khi lấy dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}