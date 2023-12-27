package com.example.bloodeasebackup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
public class SelectBloodActivity extends AppCompatActivity {

    private String selectedBloodGroup = "";
    private Spinner bloodAmountSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood);

        bloodAmountSpinner = findViewById(R.id.bloodAmountSpinner);


        // Thiết lập sự kiện click cho mỗi nhóm máu
        setBloodGroupClickListener(R.id.rectangle_ABplus, "AB+");
        setBloodGroupClickListener(R.id.rectangle_Aplus, "A+");
        setBloodGroupClickListener(R.id.rectangle_Bplus, "B+");
        setBloodGroupClickListener(R.id.rectangle_Oplus, "O+");

        setBloodGroupClickListener(R.id.rectangle_AB, "AB-");
        setBloodGroupClickListener(R.id.rectangle_A, "A-");
        setBloodGroupClickListener(R.id.rectangle_B, "B-");
        setBloodGroupClickListener(R.id.rectangle_O, "O-");
        setBloodGroupClickListener(R.id.rectangle_unknown, "unknown");

        // Sự kiện click cho nút Tìm kiếm
        Button searchBloodButton = findViewById(R.id.Search_Blood_Button);
        searchBloodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedBloodAmount = bloodAmountSpinner.getSelectedItem().toString();
                if (!selectedBloodGroup.isEmpty()) {
                    Intent likedListIntent = new Intent(SelectBloodActivity.this, LikedListActivity.class);
                    String selectedDate = getIntent().getStringExtra("selectedDate");
                    likedListIntent.putExtra("selectedDate", selectedDate);
                    String userEmail = getIntent().getStringExtra("signInEmail");
                    likedListIntent.putExtra("signInEmail", userEmail);
                    likedListIntent.putExtra("SELECTED_BLOOD_GROUP", selectedBloodGroup);
                    likedListIntent.putExtra("selectedBloodAmount", selectedBloodAmount);
                    startActivity(likedListIntent);
                } else {
                    Toast.makeText(SelectBloodActivity.this, "Vui lòng chọn nhóm máu trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setBloodGroupClickListener(int bloodGroupId, final String bloodGroupName) {
        View bloodGroupView = findViewById(bloodGroupId);
        bloodGroupView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String selectedBloodAmount = bloodAmountSpinner.getSelectedItem().toString();

                // Kiểm tra xem đã chọn nhóm máu chưa
                if (!selectedBloodGroup.isEmpty()) {
                    // Nếu đã chọn, loại bỏ màu nền của nhóm máu trước đó
                    View previousBloodGroupView = findViewById(getBloodGroupIdByName(selectedBloodGroup));
                    if (previousBloodGroupView != null) {
                        previousBloodGroupView.setBackgroundResource(R.drawable.rectangle_blood); // Đặt lại hình nền về hình nền ban đầu
                    }
                }

                // Lưu lại nhóm máu được chọn
                selectedBloodGroup = bloodGroupName;

                // Thay đổi màu nền của khung khi được chọn
                bloodGroupView.setBackgroundColor(ContextCompat.getColor(SelectBloodActivity.this, R.color.selectedBloodGroup));
            }
        });
    }

    private int getBloodGroupIdByName(String bloodGroupName) {
        switch (bloodGroupName) {
            case "AB+":
                return R.id.rectangle_ABplus;
            case "A+":
                return R.id.rectangle_Aplus;
            case "B+":
                return R.id.rectangle_Bplus;
            case "O+":
                return R.id.rectangle_Oplus;
            case "AB-":
                return R.id.rectangle_AB;
            case "A-":
                return R.id.rectangle_A;
            case "B-":
                return R.id.rectangle_B;
            case "O-":
                return R.id.rectangle_O;
            case "unknown":
                return R.id.rectangle_unknown;
            default:
                return 0;
        }
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
