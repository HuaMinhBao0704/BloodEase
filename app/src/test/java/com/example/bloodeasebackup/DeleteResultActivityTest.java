package com.example.bloodeasebackup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class DeleteResultActivityTest {

    private DeleteResultActivity deleteResultActivity;

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @Mock
    private FirebaseFirestore mockFirestore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getEmail()).thenReturn("test@example.com");

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), DeleteResultActivity.class);
        intent.putExtra("bvgn", "Test Hospital");
        intent.putExtra("selectedDate", "01/06/2024");
        intent.putExtra("diachi_bvgn", "123 Test Street");

        deleteResultActivity = Robolectric.buildActivity(DeleteResultActivity.class, intent)
                .create()
                .start()
                .resume()
                .get();

        deleteResultActivity.mAuth = mockAuth;
        deleteResultActivity.firestore = mockFirestore;
    }

    @Test
    public void testInitialUIState() {
        TextView tenBVGNTextView = deleteResultActivity.findViewById(R.id.tencs_cnhm);
        TextView ngayDangKyTextView = deleteResultActivity.findViewById(R.id.dmyhienmau_cnhm);
        TextView diachiTextView = deleteResultActivity.findViewById(R.id.diachi);

        assertEquals("Test Hospital", tenBVGNTextView.getText().toString());
        assertEquals("01/06/2024", ngayDangKyTextView.getText().toString());
        assertEquals("123 Test Street", diachiTextView.getText().toString());
    }

    @Test
    public void testBackButtonFunctionality() {
        ImageView backBtn = deleteResultActivity.findViewById(R.id.backBtn);
        backBtn.performClick();

        Intent expectedIntent = new Intent(deleteResultActivity, BottomNavActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(deleteResultActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertNotNull(actualIntent);
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void testConfirmButtonFunctionality() {
        Button confirmButton = deleteResultActivity.findViewById(R.id.confirmButton);
        confirmButton.performClick();

        verify(mockFirestore.collection("certificates").document("mockDocumentId")).delete();
    }

    @Test
    public void testDatlichButtonFunctionality() {
        Button datlichBTN = deleteResultActivity.findViewById(R.id.datlich);
        datlichBTN.performClick();

        Intent expectedIntent = new Intent(deleteResultActivity, LikedListActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(deleteResultActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertNotNull(actualIntent);
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void testNoAppointmentMessageVisibility() {
        Button confirmButton = deleteResultActivity.findViewById(R.id.confirmButton);
        confirmButton.performClick();

        TextView noAppointmentMessage = deleteResultActivity.findViewById(R.id.noAppointmentMessage);
        assertEquals(View.VISIBLE, noAppointmentMessage.getVisibility());
    }
}
