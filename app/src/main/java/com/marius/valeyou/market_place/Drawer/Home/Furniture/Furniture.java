package com.marius.valeyou.market_place.Drawer.Home.Furniture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Furniture extends Fragment {

    View view;
    TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    RelativeLayout rl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.furniture, container, false);

        tv = (TextView) view.findViewById(R.id.tv_id);
        tv1 = (TextView) view.findViewById(R.id.tv1_id);
        tv2 = (TextView) view.findViewById(R.id.tv2_id);
        tv3 = (TextView) view.findViewById(R.id.tv3_id);
        tv4 = (TextView) view.findViewById(R.id.tv4_id);
        tv5 = (TextView) view.findViewById(R.id.tv5_id);
        tv6 = (TextView) view.findViewById(R.id.tv6_id);
        tv7 = (TextView) view.findViewById(R.id.tv7_id);
        tv8 = (TextView) view.findViewById(R.id.tv8_id);

        rl = (RelativeLayout) view.findViewById(R.id.rl_id);

        tv.setText("Furniture & Decor");
        tv1.setText("All Furniture & Decor Items");
        tv2.setText("Swings & Jhulas");
        tv3.setText("Home & Office Tables");
        tv4.setText("Home & Office Chairs");
        tv5.setText("Wardrobes & Cabinets");
        tv6.setText("Cradles & Cots");
        tv7.setText("Baby Chair & High Chair");
        tv8.setText("Prams, Walkers & Strollers");

        double height = Variables.height/4.5;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rl.getLayoutParams());
        lp.height = (int) height;
        rl.setLayoutParams(lp);

        return view;
    }
}
