package com.marius.valeyou.localMarketModel.ui.fragment.copyHere.homeListDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentHomeMarketBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;

import javax.inject.Inject;

public class CopyFragment extends AppFragment<FragmentHomeMarketBinding, CopyFragmentVM> {
    public static final String TAG = "XYZ";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;


    public static Fragment newInstance() {
        return new CopyFragment();
    }

    public static Fragment newInstance(String cat_id, String sub_cat_id, String id, String json, String catType) {
        CopyFragment fragment = new CopyFragment();
        Bundle args = new Bundle();
        args.putString("cat_id", cat_id);
        args.putString("sub_cat_id", sub_cat_id);
        args.putString("id", id);
        args.putString("json", json);
        args.putString("type", catType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketActivity.viewHeaderDefault();
    }

    @Override
    protected BindingFragment<CopyFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_home_market, CopyFragmentVM.class);
    }


    @Override
    protected void subscribeToEvents(final CopyFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, true, false);
        marketActivity.setHeader(R.string.create_ad);
        marketActivity.setRightText(R.string.preview);

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.createAd:
                    Intent in = NotificationActivity.newIntent(getActivity());
                    startNewActivity(in);
                    break;


            }
        });

    }

}
