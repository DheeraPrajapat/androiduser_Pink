package com.marius.valeyou.market_place.Drawer.Msgs_Notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.PostFreeAd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Dialog_Fragment extends Fragment {

    View view;
    ListView lv;
    Dialog_Listview_Adp adp;
    String[] list = {"Honda","Toyota Corolla","Suzuki","United","BMW","Mercidies"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_f, container, false);

        PostFreeAd.tb.setVisibility(View.GONE);
        PostFreeAd.tv.setVisibility(View.GONE);

        lv = (ListView) view.findViewById(R.id.lv_id);
        adp = new Dialog_Listview_Adp(getContext(), list);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                PostFreeAd.tb.setVisibility(View.VISIBLE);
                PostFreeAd.tv.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}
