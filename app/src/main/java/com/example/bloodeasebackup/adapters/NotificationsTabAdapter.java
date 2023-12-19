package com.example.bloodeasebackup.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bloodeasebackup.fragments.NewsFragment;
import com.example.bloodeasebackup.fragments.NotificationsFragment;
import com.example.bloodeasebackup.fragments.QuestionFragment;

public class NotificationsTabAdapter extends FragmentStateAdapter {

    final int NUM_OF_FRAGMENTS = 3;
    public NotificationsTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NotificationsFragment();
            case 1:
                return new QuestionFragment();
            case 2:
                return new NewsFragment();
            default:
                return new NotificationsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_OF_FRAGMENTS;
    }
}
