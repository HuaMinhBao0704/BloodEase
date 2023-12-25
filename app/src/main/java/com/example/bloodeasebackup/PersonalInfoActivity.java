package com.example.bloodeasebackup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInfoActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView fullName, dob, sex, groupOfBlood, weight, mail, phone;
    CircleImageView avatar;
    Button directToEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        backBtn = findViewById(R.id.backBtn);
        fullName = findViewById(R.id.fullName);
        dob = findViewById(R.id.dob);
        sex = findViewById(R.id.sex);
        groupOfBlood = findViewById(R.id.groupOfBlood);
        weight = findViewById(R.id.weight);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        avatar = findViewById(R.id.avatar);
        directToEditProfile = findViewById(R.id.directToEditProfile);

        getUserProfile();

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        directToEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalInfoActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }

    private void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            fullName = findViewById(R.id.fullName);
            mail = findViewById(R.id.mail);
            avatar = findViewById(R.id.avatar);

            dob = findViewById(R.id.dob);
            sex = findViewById(R.id.sex);
            groupOfBlood = findViewById(R.id.groupOfBlood);
            weight = findViewById(R.id.weight);
            phone = findViewById(R.id.phone);

            // assign, email and avatar
            mail.setText(user.getEmail());
            avatar.setImageURI(user.getPhotoUrl());

            // get other info based on email on firestore
            db.collection("Accounts")
                    .whereEqualTo("email", user.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            DocumentSnapshot accountDoc = task.getResult().getDocuments().get(0);
                            if (accountDoc.exists()) {
                                fullName.setText(accountDoc.getString("fullName"));
                                sex.setText(accountDoc.getString("gender"));
                                groupOfBlood.setText(accountDoc.getString("bloodGroup"));
                                weight.setText(accountDoc.getDouble("weight").toString());
                                phone.setText(accountDoc.getString("phone"));

                                Timestamp timestamp = accountDoc.getTimestamp("dob");
                                Date date = timestamp.toDate();

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);

                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
                                int year = calendar.get(Calendar.YEAR);

                                String dobTxt = day + "/" + month + "/" + year;
                                dob.setText(dobTxt);
                            }
                        }
                    });
        }
    }
}