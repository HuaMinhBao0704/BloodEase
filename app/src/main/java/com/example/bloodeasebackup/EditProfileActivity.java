package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    ImageView backBtn;
    Spinner profileGenderSpinner;
    EditText profileFullName, profileDob, profileBloodGroup, profileWeight, profileEmail, profilePhone;
    Button submitProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.backBtn);
        profileGenderSpinner = findViewById(R.id.profileGenderSpinner);
        profileFullName = findViewById(R.id.profileFullName);
        profileDob = findViewById(R.id.profileDob);
        profileBloodGroup = findViewById(R.id.profileBloodGroup);
        profileWeight = findViewById(R.id.profileWeight);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
        submitProfile = findViewById(R.id.submitProfile);

        // get shared date from intent
        Intent intent = getIntent();
        String signInEmail = intent.getStringExtra("signInEmail");
        String signUpFullName = intent.getStringExtra("signUpFullName");

        // create gender option
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Gender");
        genders.add("Nam");
        genders.add("Nữ");

        /** Actions */
        profileEmail.setEnabled(false);
        profileEmail.setText(signInEmail);
        profileFullName.setText(signUpFullName);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        profileGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedGender = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileGenderSpinner.setAdapter(genderAdapter);
    }

    // Todo: validate Full name, birthday, blood group, phone, gender, weight
}