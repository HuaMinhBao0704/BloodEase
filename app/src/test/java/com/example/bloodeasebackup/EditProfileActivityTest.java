package com.example.bloodeasebackup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = TestApp.class)
public class EditProfileActivityTest {

    private EditProfileActivity editProfileActivity;

    @Mock
    private FirebaseFirestore mockDb;

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), EditProfileActivity.class);
        intent.putExtra("signInEmail", "test@example.com");
        editProfileActivity = Robolectric.buildActivity(EditProfileActivity.class, intent).create().get();
        editProfileActivity.db = mockDb;
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getEmail()).thenReturn("test@example.com");
    }

    @Test
    public void validateFullName_validName_returnsTrue() {
        EditText profileFullName = editProfileActivity.findViewById(R.id.profileFullName);
        profileFullName.setText("Valid Name");

        boolean result = editProfileActivity.validateFullName();

        assertTrue(result);
    }

    @Test
    public void validateFullName_emptyName_returnsFalse() {
        EditText profileFullName = editProfileActivity.findViewById(R.id.profileFullName);
        profileFullName.setText("");

        boolean result = editProfileActivity.validateFullName();

        assertFalse(result);
        assertNotNull(profileFullName.getError());
    }

    @Test
    public void validatePhone_validPhone_returnsTrue() {
        EditText profilePhone = editProfileActivity.findViewById(R.id.profilePhone);
        profilePhone.setText("1234567890");

        boolean result = editProfileActivity.validatePhone();

        assertTrue(result);
    }

    @Test
    public void validatePhone_invalidPhone_returnsFalse() {
        EditText profilePhone = editProfileActivity.findViewById(R.id.profilePhone);
        profilePhone.setText("invalid phone");

        boolean result = editProfileActivity.validatePhone();

        assertFalse(result);
        assertNotNull(profilePhone.getError());
    }

    @Test
    public void validateWeight_validWeight_returnsTrue() {
        EditText profileWeight = editProfileActivity.findViewById(R.id.profileWeight);
        profileWeight.setText("70");

        boolean result = editProfileActivity.validateWeight();

        assertTrue(result);
    }

    @Test
    public void validateWeight_invalidWeight_returnsFalse() {
        EditText profileWeight = editProfileActivity.findViewById(R.id.profileWeight);
        profileWeight.setText("150");

        boolean result = editProfileActivity.validateWeight();

        assertFalse(result);
        assertNotNull(profileWeight.getError());
    }

    @Test
    public void validateGender_validGender_returnsTrue() {
        Spinner profileGenderSpinner = editProfileActivity.findViewById(R.id.profileGenderSpinner);
        profileGenderSpinner.setSelection(1); // Assuming 1 is a valid selection

        boolean result = editProfileActivity.validateGender();

        assertTrue(result);
    }

    @Test
    public void validateGender_invalidGender_returnsFalse() {
        Spinner profileGenderSpinner = editProfileActivity.findViewById(R.id.profileGenderSpinner);
        profileGenderSpinner.setSelection(0); // Assuming 0 is "Gender"

        boolean result = editProfileActivity.validateGender();

        assertFalse(result);
    }
}
