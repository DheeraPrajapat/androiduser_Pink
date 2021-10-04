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

public class Home_subcategory extends Fragment {

    View view;
    ListView lv;

    String[] list = {"Home - Office Lifestyle","Sport - Fitness Equipment","Home Decor - Furnishings","Household",
                "Baby - Infant Products","Toys - Games", "Clothing - Garments","Watches","Health - Beauty Products",
                "Coins - Stamps", "Jewellery","Books - Magazines","Antiques - Handcrafts","Bags - Luggage","Barter - Exchange",
                "Collectibles","Discounted - Sale Items","Fashion Accessories","Gift - Stationary","Music - Movies",
                "Musical Instruments","Paintings","Wholesale - Bulk","Everything Else","Kids Learning","Kitchenware",
                "Footwear","Bicycle"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_subcatg, container, false);

        PostFreeAd.tb_title.setText("Home & Lifestyle");
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