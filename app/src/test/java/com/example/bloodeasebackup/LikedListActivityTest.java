package com.example.bloodeasebackup;

import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LikedListActivityTest {

    @Test
    public void testHospitalListDisplayedCorrectly() {
        // Chuẩn bị ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng trong Java bắt đầu từ 0
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = String.format("%02d/%02d/%04d", currentDay, currentMonth, currentYear);

        // Khởi tạo Intent với ngày hiện tại
        Intent intent = new Intent();
        intent.putExtra("selectedDate", currentDate);

        // Mô phỏng ActivityScenario cho LikedListActivity
        ActivityScenario<LikedListActivity> activityScenario = ActivityScenario.launch(LikedListActivity.class, intent.getExtras());

        // Tìm EditText cho ngày
        activityScenario.onActivity(activity -> {
            EditText inputDate = activity.findViewById(R.id.inputDate);
            assertEquals(currentDate, inputDate.getText().toString());
        });

        // Kiểm tra xem danh sách bệnh viện có hiển thị chính xác không
        activityScenario.onActivity(activity -> {
            LinearLayout containerLayout = activity.findViewById(R.id.containerLayout);
            int hospitalCount = containerLayout.getChildCount();

            // Kiểm tra xem có ít nhất một bệnh viện được hiển thị không
            assertTrue(hospitalCount > 0);

            // Kiểm tra các TextView trong mỗi hospital item
            for (int i = 0; i < hospitalCount; i++) {
                LinearLayout hospitalItemLayout = (LinearLayout) containerLayout.getChildAt(i);
                TextView tenBVGNTextView = hospitalItemLayout.findViewById(R.id.bvgn);
                TextView ngayChonTextView = hospitalItemLayout.findViewById(R.id.ngay_dang_ky);

                // Kiểm tra xem tên bệnh viện có được hiển thị không
                assertTrue(!tenBVGNTextView.getText().toString().isEmpty());

                // Kiểm tra xem ngày đăng ký có đúng không
                assertEquals(currentDate, ngayChonTextView.getText().toString().replace("Ngày đã chọn: ", ""));
            }
        });

        // Đóng ActivityScenario
        activityScenario.close();
    }
}
