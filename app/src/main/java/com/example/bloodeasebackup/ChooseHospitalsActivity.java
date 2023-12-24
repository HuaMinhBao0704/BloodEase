package com.example.bloodeasebackup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import java.util.List;

import android.widget.ArrayAdapter;


public class ChooseHospitalsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment mapFragment;

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
                Intent certificatesIntent = new Intent(ChooseHospitalsActivity.this, CertificatesActivity.class);
                startActivity(certificatesIntent);
            }
        });

        // Thêm phần hiển thị bản đồ
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;

        LatLng bv175 = new com.google.android.gms.maps.model.LatLng(10.800053021374163, 106.66754334838929);
        googleMap.addMarker(new MarkerOptions().position(bv175).title("Bệnh viện phụ sản MêKông"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bv175));

    }
    private List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        // Add your available time slots here
        timeSlots.add("9:00 AM");
        timeSlots.add("11:00 AM");
        timeSlots.add("2:00 PM");
        // Add more time slots as needed
        return timeSlots;
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
}
