package com.example.bloodeasebackup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SelectBloodActivity extends AppCompatActivity {

    private String selectedBloodGroup = "";
    private Spinner bloodAmountSpinner;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood);

        bloodAmountSpinner = findViewById(R.id.bloodAmountSpinner);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();

        Intent chooseHospitalIntent = getIntent();
        String eventId = chooseHospitalIntent.getStringExtra("eventId");
        String tenBVGN = chooseHospitalIntent.getStringExtra("bvgn");

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
                    Intent likedListIntent = new Intent(SelectBloodActivity.this, ResultActivity.class);
                    String selectedDate = getIntent().getStringExtra("selectedDate");
                    String tenBVGN = getIntent().getStringExtra("bvgn");
                    //String userEmail = getIntent().getStringExtra("signInEmail");
                    String diaChiBVGN = getIntent().getStringExtra("diachi_bvgn");
                    //Spinner timeSlotSpinner = findViewById(R.id.timeSlotSpinner);
                    //String time = timeSlotSpinner.getSelectedItem().toString();
                    likedListIntent.putExtra("selectedDate", selectedDate);
                    likedListIntent.putExtra("diachi_bvgn", diaChiBVGN);
                    //likedListIntent.putExtra("signInEmail", userEmail);
                    likedListIntent.putExtra("bvgn", tenBVGN);
                   // likedListIntent.putExtra("timeSlotSpinner", time);
                    //likedListIntent.putExtra("SELECTED_BLOOD_GROUP", selectedBloodGroup);
                    likedListIntent.putExtra("selectedBloodAmount", selectedBloodAmount);


                    SimpleDateFormat dobFormat = new SimpleDateFormat("dd/MM/yyyy");

                    // Todo: create certificate in firestore
                    /*
                     * @param email
                     * @param eventId
                     * @param tenBVGN
                     * @param isVerified (default is false)
                     *  */
                    Map<String, Object> certificateInfo = new HashMap<>();

                    certificateInfo.put("email", email);
                    certificateInfo.put("eventId", eventId);
                    certificateInfo.put("bvgn", tenBVGN);
                    certificateInfo.put("isVerified", false);
                    certificateInfo.put("amount", selectedBloodAmount);
                    certificateInfo.put("address", diaChiBVGN);

                    try {
                        certificateInfo.put("date", dobFormat.parse(selectedDate));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    db.collection("certificates").add(certificateInfo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Certificate", "Account added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Certificate", "Error adding document", e);
                                }
                            });

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
