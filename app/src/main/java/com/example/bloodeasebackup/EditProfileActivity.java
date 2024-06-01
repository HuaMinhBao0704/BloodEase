package com.example.bloodeasebackup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    ImageView backBtn;
    Spinner profileGenderSpinner;
    EditText profileFullName, profileDob, profileBloodGroup, profileWeight, profileEmail, profilePhone;
    Button submitProfile;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.backBtn);
        profileGenderSpinner = findViewById(R.id.profileGenderSpinner);
        profileFullName = findViewById(R.id.profileFullName);
        profileDob = findViewById(R.id.profileDob);
        profileBloodGroup = findViewById(R.id.groupOfBlood);
        profileWeight = findViewById(R.id.profileWeight);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
        submitProfile = findViewById(R.id.submitProfile);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // get shared date from intent
        Intent intent = getIntent();
        String signInEmail = intent.getStringExtra("signInEmail");

        // create gender option
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Gender");
        genders.add("Nam");
        genders.add("Nữ");

        /** Actions */
        profileEmail.setText(signInEmail);

        backBtn.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        profileDob.setOnClickListener(v -> {
            showDatePickerDialog(profileDob);
        });

        profileGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileGenderSpinner.setAdapter(genderAdapter);

        getUserProfile();

        // todo: submit the form
        submitProfile.setOnClickListener(v -> {
            if (validateFullName() && validateDob() && validateBloodGroup() && validatePhone() && validateGender() && validateWeight()) {
                // ! submit data to firestore
                String profileEmailVal = profileEmail.getText().toString();
                String profileFullNameVal = profileFullName.getText().toString();
                String profileDobVal = profileDob.getText().toString();
                String profileBloodGroupVal = profileBloodGroup.getText().toString();
                String profilePhoneVal = profilePhone.getText().toString();
                String profileGenderVal = profileGenderSpinner.getSelectedItem().toString();
                String profileWeightVal = profileWeight.getText().toString();
                try {
                    editProfileOnFirestore(profileEmailVal, profilePhoneVal, profileFullNameVal, profileDobVal, profileBloodGroupVal, profileGenderVal, profileWeightVal);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(this, "Wait for submitting profile", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // todo: editProfileOnFirestore (if profile exists => change info, if not => create it)
    void editProfileOnFirestore(String email, String phone, String fullName, String dob, String bloodGroup, String gender, String weight) throws ParseException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference accountCollectionRef = db.collection("Accounts");
        Query accountQuery = accountCollectionRef.whereEqualTo("email", email);
        SimpleDateFormat dobFormat = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, Object> updatedAccount = new HashMap<>();
        updatedAccount.put("email", email);
        updatedAccount.put("fullName", fullName);
        updatedAccount.put("dob", dobFormat.parse(dob));
        updatedAccount.put("bloodGroup", bloodGroup);
        updatedAccount.put("gender", gender);
        updatedAccount.put("weight", Float.parseFloat(weight));
        updatedAccount.put("phone", phone);

        accountQuery
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot accountDoc = task.getResult().getDocuments().get(0);
                        String accountId = accountDoc.getId();
                        DocumentReference accountDocRef = accountCollectionRef.document(accountId);
                        accountDocRef.update(updatedAccount)
                                .addOnSuccessListener(aVoid -> {
                                    Intent intent = new Intent(EditProfileActivity.this, BottomNavActivity.class);
                                    intent.putExtra("profileEmail", email);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(EditProfileActivity.this, "Update account failed", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }

    // todo: showDatePickerDialog
    private void showDatePickerDialog(final EditText inputDate) {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this, R.style.DatePickerTheme, (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            String formattedDate = sdf.format(selectedDate.getTime());

            inputDate.setText(formattedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // todo: validate Full name, birthday, blood group, phone, gender, weight
    boolean validateFullName() {
        String profileFullNameVal = profileFullName.getText().toString();

        if (TextUtils.isEmpty(profileFullNameVal)) {
            profileFullName.setError("Full name cannot be empty");
            return false;
        } else {
            String regex = "^[a-zA-Z\\s]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(profileFullNameVal);

            if (!matcher.matches()) {
                profileFullName.setError("Full name just includes text and spaces");
                return false;
            }
        }
        return true;
    }

    boolean validateDob() {
        String profileDobVal = profileDob.getText().toString();

        if (TextUtils.isEmpty(profileDobVal)) {
            profileDob.setError("Date of birth cannot be empty");
            return false;
        }

        return true;
    }

    boolean validateBloodGroup() {
        String profileBloodGroupVal = profileBloodGroup.getText().toString();
        String[] validBloodGroups = {"","A", "B", "O", "AB+", "A+", "B+", "O+", "AB-", "A-", "B-", "O-"};

        for (String validBloodGroup : validBloodGroups) {
            if (validBloodGroup.equalsIgnoreCase(profileBloodGroupVal)) {
                return true;
            }
        }
        profileBloodGroup.setError("Invalid blood group");
        return false;
    }

    boolean validatePhone() {
        String profilePhoneVal = profilePhone.getText().toString();

        if (TextUtils.isEmpty(profilePhoneVal)) {
            profilePhone.setError("Phone cannot be empty");
        } else {
            String regex = "^[0-9]{10,12}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(profilePhoneVal);

            if (!matcher.matches()) {
                profilePhone.setError("Phone just includes digits and without text and space");
                return false;
            }
        }
        return true;
    }

    boolean validateGender() {
        String profileGenderVal = profileGenderSpinner.getSelectedItem().toString();
        View genderSpinnerView = profileGenderSpinner.getSelectedView();

        if (profileGenderVal.equalsIgnoreCase("Gender")) {
            Toast.makeText(this, "You have to choose your gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    boolean validateWeight() {
        String profileWeightVal = profileWeight.getText().toString();
        if (TextUtils.isEmpty(profileWeightVal)) {
            profileWeight.setError("Weight cannot be empty");
            return false;
        }
        try {
            double weight = Double.parseDouble(profileWeightVal);
            if (!(weight >= 0 && weight <= 100)) {
                profileWeight.setError("Weight just between 0 and 100 (kg)");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            profileEmail.setText(user.getEmail());
        }
    }
}