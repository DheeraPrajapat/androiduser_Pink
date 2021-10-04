package com.marius.valeyou.market_place.Login_Register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Vp_Adp extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] imges;

    private String[] texts1;
    private String[] texts2;

    public Vp_Adp(Context context, int[] imges, String[] texts1, String[] texts2) {
        this.context = context;
        this.imges = imges;
        this.texts1 = texts1;
        this.texts2 = texts2;
    }


    @Override
    public int getCount() {
        return imges.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(RelativeLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.login_vp_item,container,false);

        TextView TV1 = (TextView) view.findViewById(R.id.tv1_id);
        TextView TV2 = (TextView) view.findViewById(R.id.tv2_id);
        ImageView IV = (ImageView) view.findViewById(R.id.iv_id);
        IV.setImageResource(imges[position]);
        TV1.setText(texts1[position]);
        TV2.setText(texts2[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }

}
