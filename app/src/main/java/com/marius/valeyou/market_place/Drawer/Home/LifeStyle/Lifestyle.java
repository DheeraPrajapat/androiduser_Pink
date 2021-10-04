package com.marius.valeyou.market_place.Drawer.Home.LifeStyle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Lifestyle extends Fragment implements View.OnClickListener {

    View view;
    RelativeLayout rl1,rl2,rl3,rl4,rl5,sell_rl,quikr_rl,inter_rl;
    ImageView iv1,iv2,iv3,iv4,iv5;
    TextView tv2,tv3,tv5,tv6,tv7,tv8;
    LinearLayout kids_ll,mis_ll,comm_ll1,comm_ll2,comm_ll3;
    ListView lv1,lv2,lv3;

    Boolean check = true;

    String[] list1 = {"All lifestyle","Home Decor","Household Items","Kitchenware","Antiques & Handicrafts","Paintings"};

    String[] list2 = {"Fitness & Sports Equipments","Books & Hobbies","Music Instruments","Collectibles",
            "Coins & Stamps","Music & Movies","Bicycle"};

    String[] list3 = {"Clothes","Watches","Jewellery","Fashion Accessories","Health & Beauty","Footwear",
            "Bags & Luggage","Gifts & Stationery"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lifestyle,container,false);

        rl1 = (RelativeLayout) view.findViewById(R.id.rl1_id);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl2_id);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl3_id);
        rl4 = (RelativeLayout) view.findViewById(R.id.rl4_id);
        rl5 = (RelativeLayout) view.findViewById(R.id.rl5_id);

        iv1 = (ImageView) view.findViewById(R.id.iv2_id);
        iv2 = (ImageView) view.findViewById(R.id.iv4_id);
        iv3 = (ImageView) view.findViewById(R.id.iv6_id);
        iv4 = (ImageView) view.findViewById(R.id.iv8_id);
        iv5 = (ImageView) view.findViewById(R.id.iv10_id);

        tv2 = (TextView) view.findViewById(R.id.tv2_id);
        tv3 = (TextView) view.findViewById(R.id.tv3_id);
        tv5 = (TextView) view.findViewById(R.id.tv5_id);
        tv6 = (TextView) view.findViewById(R.id.tv6_id);
        tv7 = (TextView) view.findViewById(R.id.tv7_id);
        tv8 = (TextView) view.findViewById(R.id.tv8_id);

        kids_ll = (LinearLayout) view.findViewById(R.id.kids_ll_id);
        mis_ll = (LinearLayout) view.findViewById(R.id.mis_ll_id);

        lv1 = (ListView) view.findViewById(R.id.lv1_id);
        lv2 = (ListView) view.findViewById(R.id.lv2_id);
        lv3 = (ListView) view.findViewById(R.id.lv3_id);

        sell_rl = (RelativeLayout) view.findViewById(R.id.sell_rl_id);
        quikr_rl = (RelativeLayout) view.findViewById(R.id.quikrbazar_rl_id);
        inter_rl = (RelativeLayout) view.findViewById(R.id.intercity_rl_id);

        comm_ll1 = (LinearLayout) view.findViewById(R.id.comm_ll1_id);
        comm_ll2 = (LinearLayout) view.findViewById(R.id.comm_ll2_id);
        comm_ll3 = (LinearLayout) view.findViewById(R.id.comm_ll3_id);

        tv6.setText("Lifestyle & Decor");
        tv7.setText("Sports & Hobbies");
        tv8.setText("Kids & Toys");
        tv2.setText("Toys & Games");
        tv3.setText("Baby & Infant");
        tv5.setText("Wholesale & Bulk");


        double width = Variables.width/4;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sell_rl.getLayoutParams());
        lp.width = (int) (width*3.5);
        sell_rl.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(quikr_rl.getLayoutParams());
        lp1.width = (int) (width*3.5);
        quikr_rl.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(inter_rl.getLayoutParams());
        lp3.width = (int) (width*3.5);
        inter_rl.setLayoutParams(lp3);

        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(comm_ll1.getLayoutParams());
        lp4.width = (int) (width*3);
        comm_ll1.setLayoutParams(lp4);

        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(comm_ll2.getLayoutParams());
        lp5.width = (int) (width*3);
        comm_ll2.setLayoutParams(lp5);

        LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(comm_ll3.getLayoutParams());
        lp6.width = (int) (width*3);
        comm_ll3.setLayoutParams(lp6);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl1_id:
                METHOD_listview(list1,lv1);
                METHOD_lv1();
                METHOD_hidelistview(lv1,iv1);
                break;

            case R.id.rl2_id:
                METHOD_listview(list2,lv2);
                METHOD_lv2();
                METHOD_hidelistview(lv2,iv2);
                break;

            case R.id.rl3_id:
                METHOD_kids_ll();
                METHOD_hidelinearlayout(kids_ll,iv3);
                break;

            case R.id.rl4_id:
                METHOD_listview(list3,lv3);
                METHOD_lv4();
                METHOD_hidelistview(lv3,iv4);
                break;

            case R.id.rl5_id:
                METHOD_mis_ll();
                METHOD_hidelinearlayout(mis_ll,iv5);
                break;
        }
    }


    public void METHOD_listview(String[] list, ListView lv){
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), R.layout.elec_lv_item, R.id.tv_id, list);
        lv.setAdapter(adp);

        justifyListViewHeightBasedOnChildren(lv);
    }


    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    public void METHOD_lv1(){
        iv1.setImageResource(R.drawable.ic_arrow_up);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.VISIBLE);
        lv2.setVisibility(View.GONE);
        kids_ll.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        mis_ll.setVisibility(View.GONE);
    }


    public void METHOD_lv2(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_up);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.VISIBLE);
        kids_ll.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        mis_ll.setVisibility(View.GONE);
    }


    public void METHOD_kids_ll(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_up);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        kids_ll.setVisibility(View.VISIBLE);
        lv3.setVisibility(View.GONE);
        mis_ll.setVisibility(View.GONE);
    }


    public void METHOD_lv4(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_up);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        kids_ll.setVisibility(View.GONE);
        lv3.setVisibility(View.VISIBLE);
        mis_ll.setVisibility(View.GONE);
    }


    public void METHOD_mis_ll(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_up);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        kids_ll.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        mis_ll.setVisibility(View.VISIBLE);
    }


    public void METHOD_hidelistview(ListView lv,ImageView iv){
        if (check){
            lv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.ic_arrow_up);
            check = false;
        }else {
            lv.setVisibility(View.GONE);
            iv.setImageResource(R.drawable.ic_arrow_gray);
            check = true;
        }
    }


    public void METHOD_hidelinearlayout(LinearLayout ll,ImageView iv){
        if (check){
            ll.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.ic_arrow_up);
            check = false;
        }else {
            ll.setVisibility(View.GONE);
            iv.setImageResource(R.drawable.ic_arrow_gray);
            check = true;
        }
    }

}
