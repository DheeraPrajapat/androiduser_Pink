package com.marius.valeyou.localMarketModel.ui.fragment.myShop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentMyShopBinding;
import com.marius.valeyou.databinding.HolderBusinessTimeBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.BusinessTimeModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CompanyAdapter;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyShopFragment extends AppFragment<FragmentMyShopBinding, MyShopFragmentVM> {
    public static final String TAG = "MyShopFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    MarketShopProfile marketShopProfile;
    List<ShopProfile> marketShopProfiles = new ArrayList<>();
    MyShopAdapter myShopAdapter;

    public static Fragment newInstance() {
        return new MyShopFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.getMarketShopProfile();
    }


    @Override
    protected BindingFragment<MyShopFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_shop, MyShopFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final MyShopFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();

         marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.my_shop);
//        marketActivity.setRightText(R.string.edit);

        marketShopProfiles=new ArrayList<>();
        vm.marketShopProfileEvent.observe(this, (SingleRequestEvent.RequestObserver<MarketShopProfile>) resource -> {
            switch (resource.status) {
                case LOADING:
                    showProgressDialog(R.string.plz_wait);
                    break;
                case SUCCESS:
                    dismissProgressDialog();
                    if (resource.data.getData() != null) {
                        marketShopProfile = resource.data;
                        if (marketShopProfile.getData().size() > 0) {
                            for (int i = 0; i < marketShopProfile.getData().size(); i++) {
                                marketShopProfiles.add(marketShopProfile.getData().get(i));

                            }

                            setAdapter();
                        }

                    } else {
                        sharedPref.put(Constants.SHOP_NAME, "");
                        sharedPref.put(Constants.SHOP_ADDRESS, "");
                    }

                    break;
                case WARN:
                    dismissProgressDialog();
                    vm.warn.setValue(resource.message);
                    break;
                case ERROR:
                    dismissProgressDialog();
                    vm.error.setValue(resource.message);
                    if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                        vm.sharedPref.deleteAll();
                        Intent in = LoginActivity.newIntent(requireActivity());
                        startNewActivity(in, true, true);

                    }
                    break;
            }
        });

    }

    private void setAdapter() {
         LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvMyShop.setLayoutManager(layoutManager);


        myShopAdapter = new MyShopAdapter(MyShopFragment.this, marketShopProfiles, new MyShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i, String type) {
                 marketActivity.addSubFragment(MyShopDetailFragment.TAG, MyShopDetailFragment.newInstance());
                sharedPref.put(Constants.SHOP_NAME_SELECTED, marketShopProfiles.get(i).getCompanyName() == null ? "" : marketShopProfiles.get(i).getCompanyName());
                sharedPref.put(Constants.SHOP_ID, String.valueOf(marketShopProfiles.get(i).getId() == null ? "" : marketShopProfiles.get(i).getId()));
             }
        });
        binding.rvMyShop.setAdapter(myShopAdapter);

    }

}
