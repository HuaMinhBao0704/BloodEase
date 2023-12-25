package com.example.bloodeasebackup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "GoogleSignIn";
    private static final String ACCOUNT_DB_TAG = "AccountsDB";
    private static final int RC_SIGN_IN = 9001;
    TextView directToSignUp;
    Button loginBtn;
    EditText loginEmail, loginPassword;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        directToSignUp = findViewById(R.id.directToSignUp);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        directToSignUp.setOnClickListener(view -> {
            Intent intentToSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intentToSignUp);
        });

        loginBtn.setOnClickListener(view -> {
            if (validateLoginEmail() && validateLoginPassword()) {
                // TODO: Sign In logic goes here
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                signInWithEmailAndPassword(email, password);
                // Todo: check if user have enough info in profile (yes => navigate to BottomNav, no => navigate to EditProfile)
                db.collection("Accounts")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot accountDoc = task.getResult().getDocuments().get(0);
                                    if (accountDoc.exists()) {
                                        String accountPhone = accountDoc.getString("phone");
                                        if (accountPhone.isEmpty()) {
                                            // user do not have enough information => direct to EditProfileActivity
                                            Intent intent2 = new Intent(SignInActivity.this, EditProfileActivity.class);
                                            intent2.putExtra("signInEmail", email);
                                            startActivity(intent2);
                                        } else {
                                            // else => navigate to BottomNavActivity (Home page)
                                            Intent intent2 = new Intent(SignInActivity.this, BottomNavActivity.class);
                                            intent2.putExtra("signInEmail", email);
                                            startActivity(intent2);
                                        }
                                    }
                                } else {
                                    Log.d(ACCOUNT_DB_TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "NO OK!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Email & Password Sign In handlers
     */
    private void signInWithEmailAndPassword(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "Login successfully!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Login failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    // TODO: Validate login form
    public boolean validateLoginEmail() {
        String emailInput = loginEmail.getText().toString();

        if (emailInput.isEmpty()) {
            loginEmail.setError("Email cannot be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            loginEmail.setError("Invalid email!");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public boolean validateLoginPassword() {
        String passwordInput = loginPassword.getText().toString();

        if (passwordInput.isEmpty()) {
            loginEmail.setError("Password cannot be empty!");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    // Actions after sign in
    private void updateUI(FirebaseUser user) {
        // Your UI update logic here
    }

    private void reload() {
    }
}