package com.example.bloodeasebackup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bloodeasebackup.databinding.ActivityBottomNavBinding;
import com.example.bloodeasebackup.fragments.HomeFragment;
import com.example.bloodeasebackup.fragments.NotificationsTabFragment;
import com.example.bloodeasebackup.fragments.ProfileFragment;

public class BottomNavActivity extends AppCompatActivity {

    ActivityBottomNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bototmNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.notifications_nav) {
                replaceFragment(new NotificationsTabFragment());
            } else if (itemId == R.id.profile_nav) {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}