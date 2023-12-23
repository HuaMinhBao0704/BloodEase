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
import com.example.bloodeasebackup.R;


import java.util.Calendar;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme, // Để sử dụng theme tùy chỉnh
                (view, year, month, dayOfMonth) -> {
                    // Format ngày để hiển thị trong EditText
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // Hiển thị ngày trong EditText
                    inputDate.setText(formattedDate);

                    // Chuyển hướng sang LikedListActivity
                    navigateToSelectBloodActivity();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void navigateToSelectBloodActivity() {


        Intent intent = new Intent(requireContext(), SelectBloodActivity.class);

        // (Nếu có) Đưa dữ liệu cần thiết sang SelectBloodActivity
        // intent.putExtra("KEY", value);

        // Thực hiện chuyển hướng
        startActivity(intent);
    }
}
