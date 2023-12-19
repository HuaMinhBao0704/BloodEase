package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    TextView directToSignUp;
    Button loginBtn;
    EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        directToSignUp = findViewById(R.id.directToSignUp);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        directToSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            if (validateLoginEmail() && validateLoginPassword()) {
                Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();

                // TODO: Sign In logic goes here
                Intent intent = new Intent(SignInActivity.this, BottomNavActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "NO OK!", Toast.LENGTH_SHORT).show();
            }
        });
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
}