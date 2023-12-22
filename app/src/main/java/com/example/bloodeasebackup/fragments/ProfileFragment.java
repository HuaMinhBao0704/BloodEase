package com.example.bloodeasebackup.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bloodeasebackup.AboutUsActivity;
import com.example.bloodeasebackup.CertificatesActivity;
import com.example.bloodeasebackup.PersonalInfoActivity;
import com.example.bloodeasebackup.PrivacyActivity;
import com.example.bloodeasebackup.R;

public class ProfileFragment extends Fragment {

    ImageView basicInfoArrow, certificatesArrow, aboutUsArrow, privacyArrow, bookingArrow;

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

        /**
         * TODO: Navigate to booking screen (Vy's task)
        bookingArrow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BookingActivity.class);
            startActivity(intent)
        });
        */

        return view;
    }
}