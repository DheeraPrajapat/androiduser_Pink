package com.marius.valeyou.market_place.Drawer.Home.Mobiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.All_Ads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Mobiles extends Fragment implements View.OnClickListener {

    View view;
    RelativeLayout quikrbzr_rl,intercity_rl,mbl_rl,tab_rl,acces_rl,wear_rl;
    LinearLayout ll,ll5,ll6;
    TextView tv,tv1,vm_tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mobiles, container, false);

        mbl_rl = (RelativeLayout) view.findViewById(R.id.mbl_rl_id);
        tab_rl = (RelativeLayout) view.findViewById(R.id.tablets_rl_id);
        acces_rl = (RelativeLayout) view.findViewById(R.id.acces_rl_id);
        wear_rl = (RelativeLayout) view.findViewById(R.id.wear_rl_id);

        quikrbzr_rl = (RelativeLayout) view.findViewById(R.id.quikrbazar_rl_id);
        intercity_rl = (RelativeLayout) view.findViewById(R.id.intercity_rl_id);

        ll = (LinearLayout) view.findViewById(R.id.ll_id);
        ll5 = (LinearLayout) view.findViewById(R.id.ll5_id);
        ll6 = (LinearLayout) view.findViewById(R.id.ll6_id);

        tv = (TextView) view.findViewById(R.id.tv13_id);
        tv1 = (TextView) view.findViewById(R.id.tv14_id);
        vm_tv = (TextView) view.findViewById(R.id.vm_tv_id);

        vm_tv.setText("View More");

        double width = Variables.width / 4;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(quikrbzr_rl.getLayoutParams());
        lp.width = (int) (width*3.5);
        quikrbzr_rl.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(intercity_rl.getLayoutParams());
        lp1.width = (int) (width*3.5);
        intercity_rl.setLayoutParams(lp1);

        CardView.LayoutParams lp2 = (CardView.LayoutParams) ll.getLayoutParams();
        lp2.height = (int) (width*2.4);

        ll.setLayoutParams(lp2);


        tv.setText("2,000 & below");
        tv1.setText("20,000 & above");

        mbl_rl.setOnClickListener(this);
        tab_rl.setOnClickListener(this);
        acces_rl.setOnClickListener(this);
        wear_rl.setOnClickListener(this);
        vm_tv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mbl_rl_id:
                METHOD_openfragment();
                break;

            case R.id.tablets_rl_id:
                METHOD_openfragment();
                break;

            case R.id.acces_rl_id:
                METHOD_openfragment();
                break;

            case R.id.wear_rl_id:
                METHOD_openfragment();
                break;

            case R.id.vm_tv_id:
                if (vm_tv.getText().equals("View More")){
                    vm_tv.setText("View Less");
                    ll5.setVisibility(View.VISIBLE);
                    ll6.setVisibility(View.VISIBLE);
                }else {
                    vm_tv.setText("View More");
                    ll5.setVisibility(View.GONE);
                    ll6.setVisibility(View.GONE);
                }
                break;
        }
    }


    public void METHOD_openfragment(){
        All_Ads f = new All_Ads();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fl_id, f).commit();
    }

}
