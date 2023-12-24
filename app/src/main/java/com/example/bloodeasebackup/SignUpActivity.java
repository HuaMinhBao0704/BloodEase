package com.example.bloodeasebackup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "GoogleSignUp";
    private static final int RC_SIGN_IN = 9001;
    TextView directToSignIn;
    EditText registerFullName, registerEmail, registerPassword, registerConfirmPassword;
    Button registerBtn, googleRegisterBtn;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String ACCOUNT_DB_TAG = "AccountsDB";

    // private GoogleSignInClient mGoogleSignInClient;

    /**
     * Google Sign In ActivityLauncher
     * private final ActivityResultLauncher<Intent> googleSignInLauncher =
     * registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
     * if (result.getResultCode() == RESULT_OK) {
     * Intent data = result.getData();
     * Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
     * handleGoogleSignInResult(task);
     * }
     * });
     */

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        boolean isCreatedNewUser = false;
        directToSignIn = findViewById(R.id.directToSignIn);
        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        googleRegisterBtn = findViewById(R.id.googleRegisterBtn);
        db = FirebaseFirestore.getInstance();

        /** Google Sign In declarations
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         .requestIdToken(getString(R.string.default_web_client_id))
         .requestEmail()
         .build();
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
         */
        mAuth = FirebaseAuth.getInstance();

        directToSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(view -> {
            if (validateRegisterFullName() && validateRegisterEmail() && validatePassword()) {
                // create new account
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String fullName = registerFullName.getText().toString();
                createAccount(email, password);

                // then show success notification and navigate to Sign In activity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "NO OK!", Toast.LENGTH_SHORT).show();
            }
        });

        googleRegisterBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Not developing yet!", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Email & Password Register handlers
     */
    private void addUserToFirestore(String email, String fullName) {
        Map<String, Object> personalInfo = new HashMap<>();
        personalInfo.put("email", email);
        personalInfo.put("fullName", fullName);
        personalInfo.put("phone", "");
        db.collection("Accounts").add(personalInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(ACCOUNT_DB_TAG, "Account added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(ACCOUNT_DB_TAG, "Error adding document", e);
                    }
                });
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUserToFirestore(email, password);
                            Toast.makeText(SignUpActivity.this, "create new account OK.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "create new account failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    /** Google Sign In handlers
     private void signIn() {
     Intent signInIntent = mGoogleSignInClient.getSignInIntent();
     googleSignInLauncher.launch(signInIntent);
     }
     private void handleGoogleSignInResult(Task<GoogleSignInAccount> task) {
     try {
     GoogleSignInAccount account = task.getResult(ApiException.class);
     Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
     firebaseAuthWithGoogle(account.getIdToken());
     } catch (ApiException e) {
     Log.w(TAG, e);
     }
     }
     private void firebaseAuthWithGoogle(String idToken) {
     AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
     mAuth.signInWithCredential(credential)
     .addOnCompleteListener(this, task -> {
     if (task.isSuccessful()) {
     Log.d(TAG, "signInWithCredential:success");
     FirebaseUser user = mAuth.getCurrentUser();
     updateUI(user);
     } else {
     Log.w(TAG, "signInWithCredential:failure", task.getException());
     updateUI(null);
     }
     });
     }
     */

    /**
     * Validators
     */
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

    private void updateUI(FirebaseUser user) {
        // Your UI update logic here
    }

    private void reload() {
    }
}
