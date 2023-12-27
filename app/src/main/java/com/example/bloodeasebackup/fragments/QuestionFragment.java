package com.example.bloodeasebackup.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bloodeasebackup.R;

public class QuestionFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        context = getActivity();

        // Tìm LinearLayout với id qs1
        LinearLayout qs1LinearLayout = rootView.findViewById(R.id.qs1);


        // Thiết lập sự kiện click cho LinearLayout
        qs1LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị popup khi LinearLayout được nhấn
                showPopup(v, "1. Ai có thể tham gia hiến máu?\n" +
                        "- Tất cả mọi người từ 18 - 60 tuổi, thực sự tình nguyện hiến máu của mình để cứu chữa người bệnh. \n" +
                        "- Cân nặng ít nhất là 45kg đối với phụ nữ, nam giới. Lượng máu hiến mỗi lần không quá 9ml/kg cân nặng và không quá 500ml mỗi lần. \n" +
                        "- Không bị nhiễm hoặc không có các hành vi lây nhiễm HIV và các bệnh lây nhiễm qua đường truyền máu khác. \n" +
                        "- Thời gian giữa 2 lần hiến máu là 12 tuần đối với cả Nam và Nữ. - Có giấy tờ tùy thân.");
            }
        });

        return rootView;
    }

    private void showPopup(View anchorView, String answerText) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
        TextView answerTextView = popupView.findViewById(R.id.answerTextView);
        answerTextView.setText(answerText);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Calculate the center of the screen
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int centerX = location[0] + anchorView.getWidth() / 2;
        int centerY = location[1] + anchorView.getHeight() / 2;

        // Show the popup at the center
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        //PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //popupWindow.showAsDropDown(anchorView);

        // Đặt sự kiện click để đóng popup
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}


//public class QuestionFragment extends Fragment {
//
//
//    public QuestionFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_question, container, false);
//    }
//}