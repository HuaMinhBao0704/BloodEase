package com.example.bloodeasebackup;

import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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
public class SignInActivityTest {

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

    @Mock
    DocumentSnapshot mockDocumentSnapshot;

    private SignInActivity signInActivity;
    private EditText loginEmail;
    private EditText loginPassword;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signInActivity = Mockito.spy(new SignInActivity());
        signInActivity.mAuth = mockAuth;
        signInActivity.db = mockDb;

        // Initialize EditText fields
        loginEmail = new EditText(ApplicationProvider.getApplicationContext());
        loginPassword = new EditText(ApplicationProvider.getApplicationContext());
        signInActivity.loginEmail = loginEmail;
        signInActivity.loginPassword = loginPassword;
    }

    @Test
    public void validateLoginEmail_EmptyEmail_ReturnsFalse() {
        loginEmail.setText("");
        assertFalse(signInActivity.validateLoginEmail());
    }

    @Test
    public void validateLoginEmail_InvalidEmail_ReturnsFalse() {
        loginEmail.setText("invalidemail");
        assertFalse(signInActivity.validateLoginEmail());
    }

    @Test
    public void validateLoginEmail_ValidEmail_ReturnsTrue() {
        loginEmail.setText("validemail@example.com");
        assertTrue(signInActivity.validateLoginEmail());
    }

    @Test
    public void validateLoginPassword_EmptyPassword_ReturnsFalse() {
        loginPassword.setText("");
        assertFalse(signInActivity.validateLoginPassword());
    }

    @Test
    public void validateLoginPassword_NonEmptyPassword_ReturnsTrue() {
        loginPassword.setText("123456");
        assertTrue(signInActivity.validateLoginPassword());
    }

    @Test
    public void signInWithEmailAndPassword_SuccessfulLogin() {
        loginEmail.setText("vy@gmail.com");
        loginPassword.setText("123456");

        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(mockAuthResultTask);
        when(mockAuthResultTask.addOnCompleteListener(Mockito.any()))
                .then(invocation -> {
                    OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
                    when(mockAuthResultTask.isSuccessful()).thenReturn(true);
                    listener.onComplete(mockAuthResultTask);
                    return mockAuthResultTask;
                });

        signInActivity.signInWithEmailAndPassword("vy@gmail.com", "123456");

        verify(mockAuth).signInWithEmailAndPassword("vy@gmail.com", "123456");
    }

    @Test
    public void signInWithEmailAndPassword_FailedLogin() {
        loginEmail.setText("abc@gmail.com");
        loginPassword.setText("abcdeft");

        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(mockAuthResultTask);
        when(mockAuthResultTask.addOnCompleteListener(Mockito.any()))
                .then(invocation -> {
                    OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
                    when(mockAuthResultTask.isSuccessful()).thenReturn(false);
                    listener.onComplete(mockAuthResultTask);
                    return mockAuthResultTask;
                });

        signInActivity.signInWithEmailAndPassword("abc@gmail.com", "abcdeft");

        verify(mockAuth).signInWithEmailAndPassword("abc@gmail.com", "abcdeft");
    }
}
