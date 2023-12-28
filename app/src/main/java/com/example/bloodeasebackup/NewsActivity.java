package com.example.bloodeasebackup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodeasebackup.fragments.NewsFragment;

public class NewsActivity  extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bao1);

        backBtn = findViewById(R.id.backBtn);

//        backBtn.setOnClickListener(view -> {
//            getOnBackPressedDispatcher().onBackPressed();
//        });

        backBtn.setOnClickListener(view -> {
            // Tạo Intent để chuyển hướng đến trang chủ (BottomNavActivity)
            Intent intent = new Intent(NewsActivity.this, BottomNavActivity.class);
            startActivity(intent);
            // Kết thúc CertificatesDetailActivity
            finish();
        });
    }

//    public void onBackPressed() {
//        // Lấy reference đến fragment bằng tag
//        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("NewsFragment");
//
//        if (newsFragment != null) {
//            // Thực hiện các thao tác cần thiết với fragment
//            // ...
//
//            // Đặc biệt hữu ích nếu bạn muốn popBackStack khi nhấn nút Back
//            getSupportFragmentManager().popBackStack();
//        } else {
//            // Gọi onBackPressed() mặc định nếu không có fragment
//            super.onBackPressed();
//        }
//    }

}
