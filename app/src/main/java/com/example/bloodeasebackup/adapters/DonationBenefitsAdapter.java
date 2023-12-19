package com.example.bloodeasebackup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bloodeasebackup.R;

public class DonationBenefitsAdapter extends PagerAdapter {
    private final Context context;
    private final int[] headings = {
            R.string.benefit1,
            R.string.benefit2,
            R.string.benefit3,
            R.string.benefit4,
    };
    private final int[] contents = {
            R.string.benefit1_content,
            R.string.benefit2_content,
            R.string.benefit3_content,
            R.string.benefit4_content,
    };
    public DonationBenefitsAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return  headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.benefit_layout,container,false);

        TextView slideHeading = (TextView) view.findViewById(R.id.benefitHeading);
        TextView slideContent = (TextView) view.findViewById(R.id.benefitContent);

        slideHeading.setText(headings[position]);
        slideContent.setText(contents[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }
}