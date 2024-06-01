package com.example.bloodeasebackup;

import static android.content.ContentValues.TAG;

import android.accessibilityservice.TouchInteractionController;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DeleteResultActivity extends AppCompatActivity {

    ImageView backBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_result_infor);

        TextView tenBVGNTextView = findViewById(R.id.tencs_cnhm);
        TextView ngayDangKyTextView = findViewById(R.id.dmyhienmau_cnhm);
        //TextView emailView = findViewById(R.id.email);
        TextView diachiTextView = findViewById(R.id.diachi);
        TextView noAppointmentMessage = findViewById(R.id.noAppointmentMessage);
        ImageView imglogo = findViewById(R.id.imglogo);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userEmail = currentUser.getEmail();
        Button datlichBTN=findViewById(R.id.datlich);




        Intent intentC = getIntent();
        if (intentC != null) {
            String tenBVGN = intentC.getStringExtra("bvgn");
            String ngayDangKy = intentC.getStringExtra("selectedDate");
            //String userEmail1 = getIntent().getStringExtra("signInEmail");
            String diachiBVGN = getIntent().getStringExtra("diachi_bvgn");

            tenBVGNTextView.setText(tenBVGN);
            diachiTextView.setText(diachiBVGN);
            ngayDangKyTextView.setText(ngayDangKy);

            //String userEmail = userEmail1;
            //Log.d(TAG, "testtt: " + userEmail1);
            getFirestoreUserData(userEmail);
            getFirestoreCertificatesData(userEmail);
        }

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(DeleteResultActivity.this, BottomNavActivity.class);
            startActivity(intent);
            finish();
        });
        datlichBTN = findViewById(R.id.datlich);

        datlichBTN.setOnClickListener(view -> {
            // Tạo Intent để chuyển hướng đến trang liked list
            Intent intent = new Intent(DeleteResultActivity.this, LikedListActivity.class);
            startActivity(intent);
            // Kết thúc DeleteResultActivity
            finish();
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmButtonClick(v);
            }
        });

        Button finalDatlichBTN = datlichBTN;
        confirmButton.setOnClickListener(view -> {
            // Ẩn tất cả nội dung có sẵn
            hideAllContent();

            // Hiển thị thông báo thay thế
            noAppointmentMessage.setVisibility(View.VISIBLE);
            imglogo.setVisibility(View.VISIBLE);
            finalDatlichBTN.setVisibility(View.VISIBLE);
        });
    }


    private void hideAllContent() {
        // Ẩn tất cả nội dung có sẵn
        findViewById(R.id.thongbao).setVisibility(View.GONE);
        findViewById(R.id.confirmButton).setVisibility(View.GONE);
        findViewById(R.id.rectangle_cnhmct).setVisibility(View.GONE);
        findViewById(R.id.rectangle_tthm).setVisibility(View.GONE);
    }

    public void getFirestoreUserData(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Accounts")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String fullName = document.getString("fullName");
                            String phone = document.getString("phone");
                            Timestamp ngaysinh = document.getTimestamp("dob");
                            String ngayChonFirestore = ngaysinh != null ?
                                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngaysinh.toDate()) : "";

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
                            String diachi= document.getString("address");
                            String eventId= document.getString("eventId");
                            Timestamp ngaydangky = document.getTimestamp("date");
                            String ngayChonFirestore = ngaydangky != null ?
                                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngaydangky.toDate()) : "";
                            // Hiển thị dữ liệu trong TextView
                            TextView fullNameTextView = findViewById(R.id.tencs_cnhm);
                            fullNameTextView.setText(benhvien);
                            TextView dobView = findViewById(R.id.dmyhienmau_cnhm);
                            dobView.setText(ngayChonFirestore);
                            TextView addressTextView = findViewById(R.id.diachi);
                            addressTextView.setText(diachi);




                        }
                    } else {
                        // Xử lý khi truy vấn không thành công
                        // Ví dụ: Hiển thị thông báo lỗi
                        Toast.makeText(getApplicationContext(), "Lỗi khi lấy dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onConfirmButtonClick(View v) {
        String documentId = "mockDocumentId"; // Replace with actual logic to get the document ID
        firestore.collection("certificates").document(documentId).delete();
    }
}
