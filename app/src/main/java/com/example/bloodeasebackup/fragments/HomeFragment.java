package com.example.bloodeasebackup.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bloodeasebackup.LikedListActivity;
import com.example.bloodeasebackup.R;
import com.example.bloodeasebackup.adapters.DonationBenefitsAdapter;

public class HomeFragment extends Fragment {
    DonationBenefitsAdapter donationBenefitsAdapter;
    ViewPager viewPager;
    Button navigateToBooking;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.benefitSlider);
        navigateToBooking = view.findViewById(R.id.navigateToBooking);

        donationBenefitsAdapter = new DonationBenefitsAdapter(container.getContext());
        viewPager.setAdapter(donationBenefitsAdapter);

        navigateToBooking.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LikedListActivity.class);
            startActivity(intent);
        });

        return view;
    }
}