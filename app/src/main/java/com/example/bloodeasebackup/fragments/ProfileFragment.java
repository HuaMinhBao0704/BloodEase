package com.example.bloodeasebackup.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bloodeasebackup.AboutUsActivity;
import com.example.bloodeasebackup.BottomNavActivity;
import com.example.bloodeasebackup.CertificatesActivity;
import com.example.bloodeasebackup.DeleteResultActivity;
import com.example.bloodeasebackup.LikedListActivity;
import com.example.bloodeasebackup.PersonalInfoActivity;
import com.example.bloodeasebackup.PrivacyActivity;
import com.example.bloodeasebackup.R;
import com.example.bloodeasebackup.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    ImageView basicInfoArrow, certificatesArrow, aboutUsArrow, privacyArrow, bookingArrow,lichhenArrow;
    Button logoutBtn;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        basicInfoArrow = view.findViewById(R.id.basicInfoArrow);
        certificatesArrow = view.findViewById(R.id.certificatesArrow);
        aboutUsArrow = view.findViewById(R.id.aboutUsArrow);
        privacyArrow = view.findViewById(R.id.privacyArrow);
        bookingArrow = view.findViewById(R.id.bookingArrow);
        lichhenArrow =view.findViewById(R.id.lichhenArrow);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        getUserProfile();
        lichhenArrow.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), DeleteResultActivity.class);
            startActivity(intent);
        });

        basicInfoArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
            startActivity(intent);
        });

        certificatesArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CertificatesActivity.class);
            startActivity(intent);
        });

        aboutUsArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        });

        privacyArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PrivacyActivity.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });


         // TODO: Navigate to booking screen (Vy's task)
        bookingArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LikedListActivity.class);
            startActivity(intent);
        });


        return view;
    }

    private void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
    }
}