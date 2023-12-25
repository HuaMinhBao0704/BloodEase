package com.example.bloodeasebackup.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bloodeasebackup.SelectBloodActivity;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bloodeasebackup.R;
import com.example.bloodeasebackup.adapters.DonationBenefitsAdapter;


import java.util.Calendar;


public class HomeFragment extends Fragment {
    DonationBenefitsAdapter donationBenefitsAdapter;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.benefitSlider);
        donationBenefitsAdapter = new DonationBenefitsAdapter(container.getContext()); // lỗi ở đây nè
        viewPager.setAdapter(donationBenefitsAdapter);

        // Lấy reference của EditText trong fragment_home.xml
        EditText inputDate = view.findViewById(R.id.inputDate);

        // Thiết lập sự kiện click cho EditText
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(inputDate);
            }
        });

        return view;
    }

    private void showDatePickerDialog(final EditText inputDate) {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();

        // Khởi tạo DatePickerDialog
        new DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                (view, year, month, dayOfMonth) -> {
                    // Format ngày để hiển thị trong EditText
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // Hiển thị ngày trong EditText
                    inputDate.setText(formattedDate);

                    // Chuyển hướng sang LikedListActivity
                    navigateToLikedListActivity(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void navigateToLikedListActivity(String selectedDate) {
        // Tạo Intent để chuyển hướng từ HomeFragment sang LikedListActivity
        Intent intent = new Intent(requireContext(), SelectBloodActivity.class);

        // Đưa ngày đã chọn vào Intent
        intent.putExtra("selectedDate", selectedDate);

        // Thực hiện chuyển hướng
        startActivity(intent);
    }

}
