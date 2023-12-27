package com.example.bloodeasebackup;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
        TextView emailView = findViewById(R.id.email);

        // Nhận dữ liệu từ Intent
        Intent intentC = getIntent();
        if (intentC != null) {
            String tenBVGN = intentC.getStringExtra("bvgn");
            String selectedBloodAmount = intentC.getStringExtra("selectedBloodAmount");
            String ngayDangKy = intentC.getStringExtra("selectedDate");
            String userEmail1 = getIntent().getStringExtra("signInEmail");

            // Hiển thị dữ liệu trong các TextView
            tenBVGNTextView.setText(tenBVGN);
            amountOfBloodTextView.setText(selectedBloodAmount);
            ngayDangKyTextView.setText(ngayDangKy);

            // Lấy dữ liệu người dùng từ Firestore
            String userEmail = userEmail1;
            Log.d(TAG, "testtt: " + userEmail1);// Thay đổi thành email đăng nhập
            getFirestoreUserData(userEmail);
        }

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
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
                            TextView fullNameTextView = findViewById(R.id.name_cnhm);
                            fullNameTextView.setText(fullName);
                            TextView dobView = findViewById(R.id.dmy_cnhm);
                            dobView.setText(ngayChonFirestore);
                            TextView phoneView = findViewById(R.id.phone);
                            phoneView.setText(phone);
                            TextView emailView = findViewById(R.id.email);
                            emailView.setText(userEmail);
                        }
                    } else {
                        // Xử lý khi truy vấn không thành công
                        // Ví dụ: Hiển thị thông báo lỗi
                        Toast.makeText(getApplicationContext(), "Lỗi khi lấy dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
