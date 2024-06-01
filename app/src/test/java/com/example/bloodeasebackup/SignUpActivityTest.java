package com.example.bloodeasebackup;

import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class SignUpActivityTest {

    @Mock
    FirebaseAuth mockAuth;

    @Mock
    FirebaseFirestore mockDb;

    @Mock
    Task<AuthResult> mockAuthResultTask;

    @Mock
    Task<QuerySnapshot> mockQuerySnapshotTask;

    @Mock
    QuerySnapshot mockQuerySnapshot;

    private SignUpActivity signUpActivity;
    private EditText registerFullName;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPassword;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signUpActivity = Mockito.spy(new SignUpActivity());
        signUpActivity.mAuth = mockAuth;
        signUpActivity.db = mockDb;

        // Initialize EditText fields
        registerFullName = new EditText(ApplicationProvider.getApplicationContext());
        registerEmail = new EditText(ApplicationProvider.getApplicationContext());
        registerPassword = new EditText(ApplicationProvider.getApplicationContext());
        registerConfirmPassword = new EditText(ApplicationProvider.getApplicationContext());
        signUpActivity.registerFullName = registerFullName;
        signUpActivity.registerEmail = registerEmail;
        signUpActivity.registerPassword = registerPassword;
        signUpActivity.registerConfirmPassword = registerConfirmPassword;
    }

    @Test
    public void validateRegisterFullName_EmptyFullName_ReturnsFalse() {
        registerFullName.setText("");
        assertFalse(signUpActivity.validateRegisterFullName());
    }

    @Test
    public void validateRegisterFullName_NonEmptyFullName_ReturnsTrue() {
        registerFullName.setText("Annie");
        assertTrue(signUpActivity.validateRegisterFullName());
    }

    @Test
    public void validateRegisterEmail_EmptyEmail_ReturnsFalse() {
        registerEmail.setText("");
        assertFalse(signUpActivity.validateRegisterEmail());
    }

    @Test
    public void validateRegisterEmail_InvalidEmail_ReturnsFalse() {
        registerEmail.setText("invalidemail");
        assertFalse(signUpActivity.validateRegisterEmail());
    }

    @Test
    public void validateRegisterEmail_ValidEmail_ReturnsTrue() {
        registerEmail.setText("annie@gmail.com");
        assertTrue(signUpActivity.validateRegisterEmail());
    }

    @Test
    public void validatePassword_EmptyPassword_ReturnsFalse() {
        registerPassword.setText("");
        registerConfirmPassword.setText("");
        assertFalse(signUpActivity.validatePassword());
    }

    @Test
    public void validatePassword_PasswordsDoNotMatch_ReturnsFalse() {
        registerPassword.setText("12345678");
        registerConfirmPassword.setText("4567890");
        assertFalse(signUpActivity.validatePassword());
    }

    @Test
    public void validatePassword_PasswordsMatch_ReturnsTrue() {
        registerPassword.setText("12345678");
        registerConfirmPassword.setText("12345678");
        assertTrue(signUpActivity.validatePassword());
    }

    @Test
    public void createAccount_SuccessfulRegistration() {
        registerEmail.setText("annie@gmail.com");
        registerPassword.setText("12345678");
        registerConfirmPassword.setText("12345678");

        when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(mockAuthResultTask);
        when(mockAuthResultTask.addOnCompleteListener(Mockito.any()))
                .then(invocation -> {
                    OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
                    when(mockAuthResultTask.isSuccessful()).thenReturn(true);
                    listener.onComplete(mockAuthResultTask);
                    return mockAuthResultTask;
                });

        signUpActivity.createAccount("annie@gmail.com", "12345678", "Annie");

        verify(mockAuth).createUserWithEmailAndPassword("annie@gmail.com", "12345678");
    }

    @Test
    public void createAccount_FailedRegistration() {
        registerEmail.setText("thvy@gmail.com");
        registerPassword.setText("password123");
        registerConfirmPassword.setText("password123");

        when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(mockAuthResultTask);
        when(mockAuthResultTask.addOnCompleteListener(Mockito.any()))
                .then(invocation -> {
                    OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
                    when(mockAuthResultTask.isSuccessful()).thenReturn(false);
                    listener.onComplete(mockAuthResultTask);
                    return mockAuthResultTask;
                });

        signUpActivity.createAccount("thvy@gmail.com", "password123", "Vy");

        verify(mockAuth).createUserWithEmailAndPassword("thvy@gmail.com", "password123");
    }
}
