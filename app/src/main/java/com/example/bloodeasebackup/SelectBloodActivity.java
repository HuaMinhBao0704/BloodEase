package com.example.bloodeasebackup;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.bloodeasebackup.fragments.HomeFragment;

import android.widget.ImageView;
import android.widget.Toast;
public class SelectBloodActivity extends AppCompatActivity {
        // Biến để theo dõi nhóm máu được chọn
        private String selectedBloodGroup = "";
        ImageView arrow_left;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_blood);

            arrow_left = findViewById(R.id.arrow_left);
            arrow_left.setOnClickListener(v -> {
                getOnBackPressedDispatcher().onBackPressed();
            });

            // Thiết lập sự kiện click cho mỗi nhóm máu
            setBloodGroupClickListener(R.id.rectangle_ABplus, "AB+");
            setBloodGroupClickListener(R.id.rectangle_Aplus, "A+");
            setBloodGroupClickListener(R.id.rectangle_Bplus, "B+");
            setBloodGroupClickListener(R.id.rectangle_Oplus, "O+");

            setBloodGroupClickListener(R.id.rectangle_AB, "AB-");
            setBloodGroupClickListener(R.id.rectangle_A, "A-");
            setBloodGroupClickListener(R.id.rectangle_B, "B-");
            setBloodGroupClickListener(R.id.rectangle_O, "O-");

            // Sự kiện click cho nút Tìm kiếm
            Button searchBloodButton = findViewById(R.id.Search_Blood_Button);
            searchBloodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Kiểm tra xem đã chọn nhóm máu chưa
                    if (!selectedBloodGroup.isEmpty()) {
                        // Nếu đã chọn, thực hiện chuyển hướng sang LikedListActivity
                        Intent likedListIntent = new Intent(SelectBloodActivity.this, LikedListActivity.class);

                        // Lấy ngày đã chọn từ HomeFragment (hoặc từ nơi bạn lưu giữ selectedDate)
                        String selectedDate = getIntent().getStringExtra("selectedDate");
                        Log.d(TAG, "testt: " + selectedDate);
                        likedListIntent.putExtra("selectedDate", selectedDate);
                        // Truyền nhóm máu đã chọn sang LikedListActivity
                        likedListIntent.putExtra("SELECTED_BLOOD_GROUP", selectedBloodGroup);
                        startActivity(likedListIntent);
                    } else {
                        // Nếu chưa chọn, có thể hiển thị một thông báo hoặc thực hiện hành động khác
                        Toast.makeText(SelectBloodActivity.this, "Vui lòng chọn nhóm máu trước", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        // Hàm chung để thiết lập sự kiện click cho từng nhóm máu
        private void setBloodGroupClickListener(int bloodGroupId, final String bloodGroupName) {
            View bloodGroupView = findViewById(bloodGroupId);
            bloodGroupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Lưu lại nhóm máu được chọn
                    selectedBloodGroup = bloodGroupName;

                    // Thay đổi màu nền của khung khi được chọn
                    bloodGroupView.setBackgroundColor(ContextCompat.getColor(SelectBloodActivity.this, R.color.selectedBloodGroup));
                }
            });
        }
    }


    //@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_blood);
//
//        // Find the Search Blood Button
//        Button searchBloodButton = findViewById(R.id.Search_Blood_Button);
//
//        // Set a click listener on the button
//        searchBloodButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create an Intent to start LikedListActivity
//                Intent likedListIntent = new Intent(SelectBloodActivity.this, LikedListActivity.class);
//                startActivity(likedListIntent);
//            }
//        });
//    }
//}
