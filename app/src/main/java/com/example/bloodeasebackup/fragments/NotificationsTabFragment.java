package com.example.bloodeasebackup.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodeasebackup.R;
import com.example.bloodeasebackup.adapters.NotificationsTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotificationsTabFragment extends Fragment {

    TabLayout notificationTabLayout;
    ViewPager2 viewPager2;
    NotificationsTabAdapter notificationsTabAdapter;
    public NotificationsTabFragment() {
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
        View view = inflater.inflate(R.layout.fragment_notifications_tab, container, false);

        notificationTabLayout = view.findViewById(R.id.notificationsTabLayout);
        viewPager2 = view.findViewById(R.id.notificationsPager);

        notificationsTabAdapter = new NotificationsTabAdapter(this);
        viewPager2.setAdapter(notificationsTabAdapter);

        notificationTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}