package com.marius.valeyou.market_place.Drawer.Home.Education;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class Education extends Fragment implements View.OnClickListener {

    View view;
    RelativeLayout rl;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;
    TextView tv;
    ViewPager vp;
    CircleIndicator ci;
    Edu_Vp_Adp adp;
    Timer timer;

    Boolean check = true;

    int[] imges = {R.drawable.image_placeholder, R.drawable.image_placeholder,R.drawable.image_placeholder,
                R.drawable.image_placeholder};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.education, container, false);

        rl = (RelativeLayout) view.findViewById(R.id.rl_id);

        ll1 = (LinearLayout) view.findViewById(R.id.ll1_id);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2_id);
        ll3 = (LinearLayout) view.findViewById(R.id.ll3_id);
        ll4 = (LinearLayout) view.findViewById(R.id.ll4_id);
        ll5 = (LinearLayout) view.findViewById(R.id.ll5_id);
        ll6 = (LinearLayout) view.findViewById(R.id.ll6_id);

        tv = (TextView) view.findViewById(R.id.tv13_id);

        double height = Variables.height/4.5;

        LinearLayout.LayoutParams lp7 = new LinearLayout.LayoutParams(rl.getLayoutParams());
        lp7.height = (int) (height);
        rl.setLayoutParams(lp7);

        vp = (ViewPager) view.findViewById(R.id.vp_id);
        ci = (CircleIndicator) view.findViewById(R.id.ci_id);
        adp = new Edu_Vp_Adp(getContext(),imges);

        vp.setAdapter(adp);
        ci.setViewPager(vp);


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                vp.post(new Runnable(){

                    @Override
                    public void run() {
                        vp.setCurrentItem((vp.getCurrentItem()+1)%imges.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);


        tv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv13_id:
                if (check){
                    ll6.setVisibility(View.VISIBLE);
                    tv.setText("Show Less");
                    check = false;
                }else {
                    ll6.setVisibility(View.GONE);
                    tv.setText("Show More");
                    check = true;
                }
                break;
        }
    }
}
