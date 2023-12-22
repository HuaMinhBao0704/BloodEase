package com.example.bloodeasebackup;

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


public class LikedListActivity extends AppCompatActivity {

    private static final String TAG = "LikedListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_list);

        // Layout khung thông tin bệnh viện
        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        // Lấy id từ Firestore
        ArrayList<String> documentIds = new ArrayList<>();
        documentIds.add("2lWTtfc6YLRRGWv3dOLw");
        documentIds.add("92KszrRLz0yQN78ASGue");
        documentIds.add("5PAKsbyhJayJTp8UFHhw");
        documentIds.add("aLLmFopDSZo4jFBRGwse");
        documentIds.add("qx4rqk6cIQfGUxyWq0tA");
        documentIds.add("qwTsoVo01sL9oinJxiy8");
        documentIds.add("lYHuG6mNI9GDI30eBIjh");
        documentIds.add("hZ3xLuFpuGahW3Rc6FIU");
        documentIds.add("e028yMbXUxc25Ghrsu3w");

        // Access Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                    String ngayChon = ngayChonTimestamp != null ?
                                            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ngayChonTimestamp.toDate()) : "";
                                    Object so_nguoi_da_dang_ky = document.get("so_nguoi_da_dang_ky");
                                    if (so_nguoi_da_dang_ky instanceof Long) {
                                        Long value = (Long) so_nguoi_da_dang_ky;
                                    } else {

                                    }
                                    Object so_nguoi_dang_ky_toi_da = document.get("so_nguoi_dang_ky_toi_da");
                                    if (so_nguoi_dang_ky_toi_da instanceof Long) {

                                        Long value = (Long) so_nguoi_dang_ky_toi_da;
                                    } else {

                                    }
                                    String imgUrl = document.getString("img");
                                    Log.d(TAG, "Image URL: " + imgUrl);

                                    // Hiện thông tin
                                    tenBVGNTextView.setText(tenBVGN);
                                    diaChiBVGNTextView.setText(diaChiBVGN);
                                    ngayChonTextView.setText("Ngày đã chọn: " + ngayChon);
                                    soNguoiDangKyTextView.setText(so_nguoi_da_dang_ky + "/" + so_nguoi_dang_ky_toi_da+" người đăng ký");

                                    // Hiện hình ảnh (chưa hiện:<)
                                    loadImageFromUrl(imgUrl, imgBVGNImageView);

                                    // Nhấn nút đăng ký => xác nhận
                                    datLichButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Create an Intent to start ChooseHospitalsActivity
                                            Intent chooseHospitalIntent = new Intent(LikedListActivity.this, ChooseHospitalsActivity.class);

                                            // Gửi dữ liệu qua Intent
                                            chooseHospitalIntent.putExtra("bvgn", tenBVGN);

                                            //chưa lấy được số người đăng ký
                                            //chooseHospitalIntent.putExtra("so_nguoi_da_dang_ky", (Boolean) so_nguoi_da_dang_ky);

                                            // Chuyển sang ChooseHospitalsActivity
                                            startActivity(chooseHospitalIntent);
                                        }
                                    });

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

            // Add the inflated layout to the container
            containerLayout.addView(hospitalView);
        }
    }


    private void loadImageFromUrl(String imgUrl, ImageView imageView) {
        // Using Picasso to load image from URL
        Picasso.get().load(imgUrl).into(imageView);
    }

}
