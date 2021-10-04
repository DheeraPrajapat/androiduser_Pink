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

public class Realestate_subcategory extends Fragment {

    View view;
    ListView lv;

    String[] list = {"Houses - Apartments for Rent","Houses - Apartments for Sale","Land - Plot for Sale",
                "Flatmates","Commercial Property for Rent","Commercial Property for Sale","Paying Guest - Hostel",
                "Service - Apartments","Vacation Rentals - Timeshare","Villas/Bungalows for Rent",
                "Villas/Bungalows for Sale","Residential - Builder floors for Sale",
                "Residential - Builder floors for Rent"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.real_estate_subcatg, container, false);

        PostFreeAd.tb_title.setText("Real Estate");
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
