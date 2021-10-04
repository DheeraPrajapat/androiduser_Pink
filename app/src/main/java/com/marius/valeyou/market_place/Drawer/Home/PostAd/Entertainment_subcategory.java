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

public class Entertainment_subcategory extends Fragment {

    View view;
    ListView lv;

    String[] list = {"Acting - Modeling Roles","Modeling Agencies","Musicians","Photographers - Cameraman",
                "Actor - Model Portfolios","Acting Schools","Make Up - Hair - Films & TV","Script Writers",
                "Fashion Designers - Stylists","Art Directors - Editors","Studios - Locations for hire",
                "Sound Engineers","Set Designers","Other Entertainment"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.entertainment_subcatg, container, false);

        PostFreeAd.tb_title.setText("Entertainment");
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