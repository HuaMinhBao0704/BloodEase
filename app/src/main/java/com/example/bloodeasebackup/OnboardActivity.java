package com.example.bloodeasebackup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bloodeasebackup.adapters.OnboardAdapter;

public class OnboardActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private OnboardAdapter onboardingAdapter;
    private Button nextBtn;
    private TextView skipStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        nextBtn = findViewById(R.id.nextBtn);
        skipStep = findViewById(R.id.skipStep);

        nextBtn.setOnClickListener(view -> nextPage(view));
        skipStep.setOnClickListener(v3 -> {
            Intent intent=new Intent(OnboardActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        makeStatusbarTransparent();

        viewPager = findViewById(R.id.onboarding_view_pager);

        onboardingAdapter = new OnboardAdapter(this);
        viewPager.setAdapter(onboardingAdapter);
    }

    public void nextPage(View view) {
        if (view.getId() == R.id.nextBtn) {
            nextBtn = findViewById(R.id.nextBtn);
            skipStep = findViewById(R.id.skipStep);
            if (viewPager.getCurrentItem() < onboardingAdapter.getCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                nextBtn.setText("Hãy bắt đầu");
                skipStep.setText("Bỏ qua");
            }
            if (viewPager.getCurrentItem() + 1 == onboardingAdapter.getCount()) {
                nextBtn.setText("Tôi lần đầu tham gia");
                skipStep.setText("Đăng nhập");

                nextBtn.setOnClickListener(v2 -> {
                    Intent intent=new Intent(OnboardActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    finish();
                });
                skipStep.setOnClickListener(v3 -> {
                    Intent intent=new Intent(OnboardActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }
    }

    private void makeStatusbarTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}