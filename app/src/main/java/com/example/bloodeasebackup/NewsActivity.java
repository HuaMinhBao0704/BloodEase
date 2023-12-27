package com.example.bloodeasebackup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodeasebackup.fragments.NewsFragment;

public class NewsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bao1);
    }

    public void onBackPressed() {
        // Lấy reference đến fragment bằng tag
        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("NewsFragment");

        if (newsFragment != null) {
            // Thực hiện các thao tác cần thiết với fragment
            // ...

            // Đặc biệt hữu ích nếu bạn muốn popBackStack khi nhấn nút Back
            getSupportFragmentManager().popBackStack();
        } else {
            // Gọi onBackPressed() mặc định nếu không có fragment
            super.onBackPressed();
        }
    }

}
