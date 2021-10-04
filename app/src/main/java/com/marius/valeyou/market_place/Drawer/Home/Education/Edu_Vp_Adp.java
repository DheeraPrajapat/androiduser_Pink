package com.marius.valeyou.market_place.Drawer.Home.Education;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.marius.valeyou.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Edu_Vp_Adp extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] imges;

    public Edu_Vp_Adp(Context context, int[] imges) {
        this.context = context;
        this.imges = imges;
    }


    @Override
    public int getCount() {
        return imges.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.edu_vp_item,container,false);

        ImageView IV = (ImageView) view.findViewById(R.id.iv_id);
        IV.setImageResource(imges[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }

}
