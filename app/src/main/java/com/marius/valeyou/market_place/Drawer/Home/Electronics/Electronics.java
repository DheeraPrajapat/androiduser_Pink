package com.marius.valeyou.market_place.Drawer.Home.Electronics;

import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Electronics extends Fragment implements View.OnClickListener {

    View view;

    RelativeLayout rl,rl1, rl2, rl3, rl4, rl5, rl6, sell_rl, quikr_rl, inter_rl;
    ListView lv1, lv2, lv3, lv4, lv5, lv6;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6;
    LinearLayout ll,comm_ll1,comm_ll2,comm_ll3;
    TextView com_tv,audio_tv;
    CardView rkl;
    Boolean check = true;

    String[] list1 = {"Refrigerators", "Washing Machines", "Air Conditioners", "Air coolers", "Water Heater/Geysers",
            "Ceiling Fans", "Table Fans", "Vaccum Cleaners", "More [+]"};

    String[] list2 = {"Water Purifiers", "Microwave Ovens", "Mixer/Grinder/Juicer", "Induction Cook Tops",
            "Dish Washers", "Oven Toaster Griller", "Food Processors", "Electric Cookers", "More [+]"};

    String[] list3 = {"Laptop", "Desktop", "Printers", "Routers", "Monitors","CPU", "External hard disks", "Data Cards", "More [+]"};

    String[] list4 = {"TV", "Music Systems - Home Theatre", "Video Games - Consoles", "iPods, MP3 Players",
            "DVD Players", "Set Top Box"};

    String[] list5 = {"Camera", "Camera Accessories"};

    String[] list6 = {"Inverters, UPS and Generators", "Tools - Machinery - Industrial","Everything Else","FAx,EPABX,Office Equipment",
            "Security Equipment - Products","Office Supplies"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.electronics, container, false);

        rl = (RelativeLayout) view.findViewById(R.id.rl_id);
        rl1 = (RelativeLayout) view.findViewById(R.id.rl1_id);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl2_id);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl3_id);
        rl4 = (RelativeLayout) view.findViewById(R.id.rl4_id);
        rl5 = (RelativeLayout) view.findViewById(R.id.rl5_id);
        rl6 = (RelativeLayout) view.findViewById(R.id.rl6_id);

        lv1 = (ListView) view.findViewById(R.id.lv1_id);
        lv2 = (ListView) view.findViewById(R.id.lv2_id);
        lv3 = (ListView) view.findViewById(R.id.lv3_id);
        lv4 = (ListView) view.findViewById(R.id.lv4_id);
        lv5 = (ListView) view.findViewById(R.id.lv5_id);
        lv6 = (ListView) view.findViewById(R.id.lv6_id);

        iv1 = (ImageView) view.findViewById(R.id.iv2_id);
        iv2 = (ImageView) view.findViewById(R.id.iv4_id);
        iv3 = (ImageView) view.findViewById(R.id.iv6_id);
        iv4 = (ImageView) view.findViewById(R.id.iv8_id);
        iv5 = (ImageView) view.findViewById(R.id.iv10_id);
        iv6 = (ImageView) view.findViewById(R.id.iv12_id);

        sell_rl = (RelativeLayout) view.findViewById(R.id.sell_rl_id);
        quikr_rl = (RelativeLayout) view.findViewById(R.id.quikrbazar_rl_id);
        inter_rl = (RelativeLayout) view.findViewById(R.id.intercity_rl_id);

        com_tv = (TextView) view.findViewById(R.id.com_tv_id);
        audio_tv = (TextView) view.findViewById(R.id.audio_tv_id);

        com_tv.setText("Computers & Accesories");
        audio_tv.setText("Audio,Video & Gaming");

        rkl = (CardView) view.findViewById(R.id.rkl_id);

        ll = (LinearLayout) view.findViewById(R.id.ll_id);
        comm_ll1 = (LinearLayout) view.findViewById(R.id.comm_ll1_id);
        comm_ll2 = (LinearLayout) view.findViewById(R.id.comm_ll2_id);
        comm_ll3 = (LinearLayout) view.findViewById(R.id.comm_ll3_id);

        double width = Variables.width/4;
        double height = Variables.height/4.5;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sell_rl.getLayoutParams());
        lp.width = (int) (width*3.5);
        sell_rl.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(quikr_rl.getLayoutParams());
        lp1.width = (int) (width*3.5);
        quikr_rl.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(inter_rl.getLayoutParams());
        lp3.width = (int) (width*3.5);
        inter_rl.setLayoutParams(lp3);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams (ll.getLayoutParams());
        lp2.height = (int) (width*2.0);
        ll.setLayoutParams(lp2);

        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(comm_ll1.getLayoutParams());
        lp4.width = (int) (width*3);
        comm_ll1.setLayoutParams(lp4);

        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(comm_ll2.getLayoutParams());
        lp5.width = (int) (width*3);
        comm_ll2.setLayoutParams(lp5);

        LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(comm_ll3.getLayoutParams());
        lp6.width = (int) (width*3);
        comm_ll3.setLayoutParams(lp6);

        LinearLayout.LayoutParams lp7 = new LinearLayout.LayoutParams(rl.getLayoutParams());
        lp7.height = (int) (height);
        rl.setLayoutParams(lp7);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rkl.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rkl_id:
                startActivity(new Intent(getActivity(), Elec_Search.class));
                break;

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
                METHOD_listview(list3,lv3);
                METHOD_lv3();
                METHOD_hidelistview(lv3,iv3);
                break;

            case R.id.rl4_id:
                METHOD_listview(list4,lv4);
                METHOD_lv4();
                METHOD_hidelistview(lv4,iv4);
                break;

            case R.id.rl5_id:
                METHOD_listview(list5,lv5);
                METHOD_lv5();
                METHOD_hidelistview(lv5,iv5);
                break;

            case R.id.rl6_id:
                METHOD_listview(list6,lv6);
                METHOD_lv6();
                METHOD_hidelistview(lv6,iv6);
                break;

        }
    }






    public void METHOD_listview(String[] list, ListView lv){
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), R.layout.elec_lv_item, R.id.tv_id, list);
        lv.setAdapter(adp);

        justifyListViewHeightBasedOnChildren(lv);
    }

    public void METHOD_lv1(){
        iv1.setImageResource(R.drawable.ic_arrow_up);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        iv6.setImageResource(R.drawable.ic_arrow_gray);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        lv4.setVisibility(View.GONE);
        lv5.setVisibility(View.GONE);
        lv6.setVisibility(View.GONE);
    }

    public void METHOD_lv2(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_up);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        iv6.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.VISIBLE);
        lv3.setVisibility(View.GONE);
        lv4.setVisibility(View.GONE);
        lv5.setVisibility(View.GONE);
        lv6.setVisibility(View.GONE);
    }

    public void METHOD_lv3(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_up);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        iv6.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.VISIBLE);
        lv4.setVisibility(View.GONE);
        lv5.setVisibility(View.GONE);
        lv6.setVisibility(View.GONE);
    }

    public void METHOD_lv4(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_up);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        iv6.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        lv4.setVisibility(View.VISIBLE);
        lv5.setVisibility(View.GONE);
        lv6.setVisibility(View.GONE);
    }

    public void METHOD_lv5(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_up);
        iv6.setImageResource(R.drawable.ic_arrow_gray);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        lv4.setVisibility(View.GONE);
        lv5.setVisibility(View.VISIBLE);
        lv6.setVisibility(View.GONE);
    }

    public void METHOD_lv6(){
        iv1.setImageResource(R.drawable.ic_arrow_gray);
        iv2.setImageResource(R.drawable.ic_arrow_gray);
        iv3.setImageResource(R.drawable.ic_arrow_gray);
        iv4.setImageResource(R.drawable.ic_arrow_gray);
        iv5.setImageResource(R.drawable.ic_arrow_gray);
        iv6.setImageResource(R.drawable.ic_arrow_up);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);
        lv4.setVisibility(View.GONE);
        lv5.setVisibility(View.GONE);
        lv6.setVisibility(View.VISIBLE);
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




}
