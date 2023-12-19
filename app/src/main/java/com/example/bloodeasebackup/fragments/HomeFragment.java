package com.example.bloodeasebackup.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodeasebackup.R;
import com.example.bloodeasebackup.adapters.DonationBenefitsAdapter;

public class HomeFragment extends Fragment {
    DonationBenefitsAdapter donationBenefitsAdapter;
    ViewPager viewPager;

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
        donationBenefitsAdapter = new DonationBenefitsAdapter(container.getContext()); // lỗi ở đây nè
        viewPager.setAdapter(donationBenefitsAdapter);

        return view;
    }
}