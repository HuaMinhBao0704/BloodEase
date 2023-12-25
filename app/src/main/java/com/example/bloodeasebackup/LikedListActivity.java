package com.example.bloodeasebackup;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;
import com.example.bloodeasebackup.fragments.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class LikedListActivity extends AppCompatActivity {

    private static final String TAG = "LikedListActivity";
    private EditText inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_list);
        inputDate = findViewById(R.id.inputDate);
        // Lấy ngày đã chọn từ Intent
        String selectedDate = getIntent().getStringExtra("selectedDate");
        // Layout khung thông tin bệnh viện
        LinearLayout containerLayout = findViewById(R.id.containerLayout);
        // Access Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Danh sách ID từ Firestore
        ArrayList<String> documentIds = new ArrayList<>();
        documentIds.add("92KszrRLz0yQN78ASGue");
        documentIds.add("5PAKsbyhJayJTp8UFHhw");
        documentIds.add("aLLmFopDSZo4jFBRGwse");
        documentIds.add("qx4rqk6cIQfGUxyWq0tA");
        documentIds.add("qwTsoVo01sL9oinJxiy8");
        documentIds.add("lYHuG6mNI9GDI30eBIjh");
        documentIds.add("hZ3xLuFpuGahW3Rc6FIU");
        documentIds.add("e028yMbXUxc25Ghrsu3w");
        documentIds.add("2lWTtfc6YLRRGWv3dOLw");
        documentIds.add("hkWo8zE7b2YyxHY22b6h");

        // Danh sách hospitalView cần hiển thị
        ArrayList<View> hospitalsToDisplay = new ArrayList<>();
        // Đếm số lượng hospitalView đã được xử lý
        AtomicInteger processedCount = new AtomicInteger(0);

        for (String documentId : documentIds) {
            // Layout cho mỗi bệnh viện
            View hospitalView = getLayoutInflater().inflate(R.layout.hospital_item, containerLayout, false);
            // Lấy id các trường thông tin
            TextView tenBVGNTextView = hospitalView.findViewById(R.id.bvgn);
            TextView diaChiBVGNTextView = hospitalView.findViewById(R.id.diachi_bvgn);
            TextView ngayChonTextView = hospitalView.findViewById(R.id.ngay_dang_ky);
            TextView soNguoiDangKyTextView = hospitalView.findViewById(R.id.so_nguoi_da_dang_ky);
            Button datLichButton = hospitalView.findViewById(R.id.btn_datlich_bvgn);
            ImageView imgBVGNImageView = hospitalView.findViewById(R.id.img);

            // Access Firestore để lấy dữ liệu
            db.collection("Hospital").document(documentId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Access data from Firestore document
                                    String tenBVGN = document.getString("bvgn");
                                    String diaChiBVGN = document.getString("diachi_bvgn");
                                    Timestamp ngayChonTimestamp = document.getTimestamp("ngay_dang_ky");
                                    String ngayChonFirestore = ngayChonTimestamp != null ?
                                            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngayChonTimestamp.toDate()) : "";
                                    Object so_nguoi_da_dang_ky = document.get("so_nguoi_da_dang_ky");
                                    Object so_nguoi_dang_ky_toi_da = document.get("so_nguoi_dang_ky_toi_da");
                                    String imgUrl = document.getString("img");

                                    // Hiện thông tin
                                    tenBVGNTextView.setText(tenBVGN);
                                    diaChiBVGNTextView.setText(diaChiBVGN);
                                    ngayChonTextView.setText("Ngày đã chọn: " + ngayChonFirestore);
                                    soNguoiDangKyTextView.setText(so_nguoi_da_dang_ky + "/" + so_nguoi_dang_ky_toi_da + " người đăng ký");

                                    // Hiện hình ảnh
                                    loadImageFromUrl(imgUrl, imgBVGNImageView);

                                    // Nhấn nút đăng ký => xác nhận
                                    datLichButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String selectedBloodAmount =getIntent().getStringExtra("selectedBloodAmount");
                                            // Create an Intent to start ChooseHospitalsActivity
                                            Intent chooseHospitalIntent = new Intent(LikedListActivity.this, ChooseHospitalsActivity.class);
                                            String userEmail = getIntent().getStringExtra("user_email");
                                            chooseHospitalIntent.putExtra("user_email", userEmail);
                                            chooseHospitalIntent.putExtra("bvgn", tenBVGN);
                                            chooseHospitalIntent.putExtra("selectedDate", selectedDate);
                                            chooseHospitalIntent.putExtra("selectedBloodAmount", selectedBloodAmount);

                                            if (so_nguoi_da_dang_ky instanceof Number) {
                                                chooseHospitalIntent.putExtra("so_nguoi_da_dang_ky", ((Number) so_nguoi_da_dang_ky).intValue());}
                                            // Chuyển sang ChooseHospitalsActivity
                                            startActivity(chooseHospitalIntent);
                                        }
                                    });

                                    // Kiểm tra ngày đã chọn
                                    boolean isDateMatched = isDateMatched(selectedDate, ngayChonFirestore);

                                    if (isDateMatched) {
                                        // Thêm vào danh sách để hiển thị
                                        hospitalsToDisplay.add(hospitalView);
                                    }
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                            // Tăng số lượng đã xử lý
                            int processed = processedCount.incrementAndGet();
                            inputDate.setText(selectedDate);

                            // Gắn sự kiện click cho EditText để hiển thị DatePickerDialog
                            inputDate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDatePickerDialog();
                                }
                            });

                            // Kiểm tra xem đã xử lý tất cả hospitalView chưa
                            if (processed == documentIds.size()) {
                                // Thêm tất cả hospitalView thỏa mãn điều kiện vào containerLayout
                                for (View hospitalToDisplay : hospitalsToDisplay) {
                                    containerLayout.addView(hospitalToDisplay);
                                }
                            }
                        }
                    });
        }
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại nếu có, hoặc ngày mặc định nếu chưa có
        String currentDate = inputDate.getText().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(currentDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LikedListActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn ngày
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        inputDate.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }


    // kiểm tra ngày trùng khớp
    private boolean isDateMatched(String selectedDate, String ngayChonFirestore) {

        // Parse ngày đã chọn và ngày trong Firestore để so sánh
        if (selectedDate == null || ngayChonFirestore == null) {
            showNoHospitalsAvailableMessage();
            return false;
        }
        Log.d(TAG, "Selected date: " + selectedDate);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.setTime(sdf.parse(selectedDate));

            Calendar firestoreCalendar = Calendar.getInstance();
            firestoreCalendar.setTime(sdf.parse(ngayChonFirestore));

            // So sánh ngày, tháng, năm
            boolean isDateMatched = selectedCalendar.get(Calendar.DAY_OF_MONTH) == firestoreCalendar.get(Calendar.DAY_OF_MONTH) &&
                    selectedCalendar.get(Calendar.MONTH) == firestoreCalendar.get(Calendar.MONTH) &&
                    selectedCalendar.get(Calendar.YEAR) == firestoreCalendar.get(Calendar.YEAR);

            if (isDateMatched) {
                return true;
            } else {
                showNoHospitalsAvailableMessage();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            showNoHospitalsAvailableMessage();
            return false;
        }
    }

    // Hàm để hiển thị thông báo khi không có bệnh viện nào có lịch ngày hôm đó
    private void showNoHospitalsAvailableMessage() {
        // Thực hiện logic hiển thị thông báo cho người dùng
        //Toast.makeText(this, "Không có bệnh viện nào có lịch ngày hôm đó", Toast.LENGTH_SHORT).show();
    }

    private void loadImageFromUrl(String imgUrl, ImageView imageView) {
        // Using Picasso to load image from URL
        Picasso.get().load(imgUrl).into(imageView);
    }
}
