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

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "GoogleSignIn";
    private static final int RC_SIGN_IN = 9001;
    TextView directToSignUp;
    Button loginBtn;
    EditText loginEmail, loginPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        directToSignUp = findViewById(R.id.directToSignUp);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();

        directToSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            if (validateLoginEmail() && validateLoginPassword()) {
                // TODO: Sign In logic goes here
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                signInWithEmailAndPassword(email, password);
                Intent intent = new Intent(SignInActivity.this, BottomNavActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "NO OK!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Email & Password Sign In handlers */
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
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Login failed!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
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