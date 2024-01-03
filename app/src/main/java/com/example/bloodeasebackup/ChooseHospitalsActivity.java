package com.example.bloodeasebackup;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ChooseHospitalsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment mapFragment;
    private Button rectangle1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hospitals);

        // Nhận dữ liệu từ Intent (nếu có)
        Intent intent = getIntent();
        if (intent != null) {
            String tenBVGN = intent.getStringExtra("bvgn");
            int so_nguoi_da_dang_ky = intent.getIntExtra("so_nguoi_da_dang_ky", 0);
            so_nguoi_da_dang_ky++;// tăng số người đăng ký
            updateSoNguoiDangKyFirestore(tenBVGN, so_nguoi_da_dang_ky);

            rectangle1Button = findViewById(R.id.rectangle_1);
            String selectedDate = getIntent().getStringExtra("selectedDate");



            TextView tenBVGNTextView = findViewById(R.id.bvgn);
            TextView soNguoiDangKyTextView = findViewById(R.id.so_nguoi_da_dang_ky);

            // Hiển thị dữ liệu trên giao diện
            rectangle1Button.setText(selectedDate);
            tenBVGNTextView.setText(tenBVGN);
            soNguoiDangKyTextView.setText(so_nguoi_da_dang_ky + " người đăng ký");
        }

        Spinner timeSlotSpinner = findViewById(R.id.timeSlotSpinner);
        List<String> timeSlots = getTimeSlots(); // Replace this with your method to get available time slots

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlotSpinner.setAdapter(adapter);
        // Find the confirmButton
        Button confirmButton = findViewById(R.id.confirmButton);

        // Set a click listener on the button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start CertificatesActivity
                String selectedBloodAmount =getIntent().getStringExtra("selectedBloodAmount");
                String tenBVGN = intent.getStringExtra("bvgn");
                String eventId = intent.getStringExtra("eventId");
                Spinner timeSlotSpinner = findViewById(R.id.timeSlotSpinner);
                String time = timeSlotSpinner.getSelectedItem().toString();
                String selectedDate = getIntent().getStringExtra("selectedDate");
                Intent certificatesIntent = new Intent(ChooseHospitalsActivity.this, SelectBloodActivity.class);
                //String userEmail = getIntent().getStringExtra("signInEmail");
                String diaChiBVGN = getIntent().getStringExtra("diachi_bvgn");
                certificatesIntent.putExtra("diachi_bvgn", diaChiBVGN);
                certificatesIntent.putExtra("eventId", eventId);
                //certificatesIntent.putExtra("signInEmail", userEmail);
                certificatesIntent.putExtra("bvgn", tenBVGN);
                //certificatesIntent.putExtra("timeSlotSpinner", time);
                certificatesIntent.putExtra("selectedDate", selectedDate);
                certificatesIntent.putExtra("selectedBloodAmount", selectedBloodAmount);
                startActivity(certificatesIntent);
            }
        });

        // Thêm phần hiển thị bản đồ
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }

    // lưu ý địa điểm mặc định





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Nhận LatLng từ Intent
        LatLng viTriBv = getIntent().getParcelableExtra("vitri");
        String tenBVGN = getIntent().getStringExtra("bvgn");

        // Kiểm tra xem có LatLng không
        if (viTriBv != null) {
            // Thêm Marker vào bản đồ
            googleMap.addMarker(new MarkerOptions().position(viTriBv).title(tenBVGN));
            // Di chuyển Camera đến vị trí bệnh viện
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(viTriBv));
        }
    }



    private List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        timeSlots.add("8:00 AM");
        timeSlots.add("10:00 AM");
        timeSlots.add("2:00 PM");
        return timeSlots;
    }
    private void updateSoNguoiDangKyFirestore(String tenBVGN, int soNguoiDangKyMoi) {
        // Thực hiện cập nhật lên Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Tạo tham chiếu đến tài liệu của bệnh viện trong Firestore
        DocumentReference docRef = db.collection("Hospital").document(tenBVGN);

        // Cập nhật số người đăng ký mới
        docRef
                .update("so_nguoi_da_dang_ky", soNguoiDangKyMoi)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Cập nhật số người đăng ký thành công!");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Lỗi khi cập nhật số người đăng ký", e);
                });
    }

}
//    public void showTimePickerDialog(View view) {
//        final Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        // Update the chosen time
//                        updateTime(hourOfDay, minute);
//                    }
//                }, hour, minute, false);
//
//        timePickerDialog.show();
//    }
//
//    private void updateTime(int hour, int minute) {
//        Button chooseTimeButton = findViewById(R.id.chooseTimeButton);
//
//        // Format the chosen time and update the button text
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//
//        String chosenTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
//        chooseTimeButton.setText(chosenTime);
//    }

