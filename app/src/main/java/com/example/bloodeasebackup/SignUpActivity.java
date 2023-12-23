package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    TextView directToSignIn;
    EditText registerFullName, registerEmail, registerPassword, registerConfirmPassword;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        directToSignIn = findViewById(R.id.directToSignIn);
        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);

        directToSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(view -> {
            if (validateRegisterFullName() && validateRegisterEmail() && validatePassword()) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // TODO: Sign Up logic goes here
            } else {
                Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: validate sign up form
    public boolean validateRegisterFullName() {
        String fullNameInput = registerFullName.getText().toString();

        if (fullNameInput.isEmpty()) {
            registerFullName.setError("Full name cannot be empty!");
            return false;
        } else {
            registerFullName.setError(null);
            return true;
        }
    }

    public boolean validateRegisterEmail() {
        String emailInput = registerEmail.getText().toString();

        if (emailInput.isEmpty()) {
            registerEmail.setError("Email cannot be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            registerEmail.setError("Invalid email");
            return false;
        } else {
            registerEmail.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String passwordInput = registerPassword.getText().toString();
        String confirmPasswordInput = registerConfirmPassword.getText().toString();

        if (passwordInput.isEmpty()) {
            registerPassword.setError("Password cannot be empty");
            return false;
        }
        if (confirmPasswordInput.isEmpty()) {
            registerConfirmPassword.setError("Confirm password cannot be empty");
            return false;
        }
        if (!passwordInput.equals(confirmPasswordInput)) {
            registerPassword.setError("Password have to be same with Confirm Password");
            registerConfirmPassword.setError("Password have to be same with Confirm Password");
            return false;
        } else {
            registerPassword.setError(null);
            registerConfirmPassword.setError(null);
            return true;
        }
    }
}