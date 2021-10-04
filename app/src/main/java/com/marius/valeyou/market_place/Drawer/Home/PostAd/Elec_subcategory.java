package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marius.valeyou.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Elec_subcategory extends Fragment {

    View view;
    ListView lv;

    String[] list = {"Washing Machines","Refrigerators","Laptop - Computers","Home - Kitchen Appliances",
                "TV - DVD - Multimedia","Computer Peripherals","Camera Accessories","Cameras - Digicams",
                "Fax, EPABX, Office Equipment","Inverters, UPS & Generators","iPods, MP3 Players",
                "Music Systems - Home Theatre","Office Supplies","Security Equipment - Products",
                "Tools - Machinery - Industrial","Video Games - Consoles","Everything Else","Laptops"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.elec_subcatg, container, false);

        PostFreeAd.tb_title.setText("Electronics & Appliances");
        PostFreeAd.tv.setText("Select Sub-Category");

        lv = (ListView) view.findViewById(R.id.lv_id);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), R.layout.home_subcatg_item, R.id.tv_id, list);
        lv.setAdapter(adp);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        PostFreeAd.tb_title.setText("Post Free Ad");
        PostFreeAd.tv.setText("Select Category");
    }

}