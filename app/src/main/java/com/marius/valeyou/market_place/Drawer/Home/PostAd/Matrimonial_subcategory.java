package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Matrimonial_subcategory extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.matri_subcatg, container, false);

        PostFreeAd.tb_title.setText("Matrimonial");
        PostFreeAd.tv.setText("Select Sub-Category");

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        PostFreeAd.tb_title.setText("Post Free Ad");
        PostFreeAd.tv.setText("Select Category");
    }
}