package com.marius.valeyou.localMarketModel.ui.fragment.myShop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentMyShopDetailBinding;
import com.marius.valeyou.databinding.HolderBusinessTimeBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.BusinessTimeModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;


public class MyShopDetailFragment extends AppFragment<FragmentMyShopDetailBinding, MyShopDetailFragmentVM> {
    public static final String TAG = "MyShopDetailFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    MarketShopProfile marketShopProfile;

    public static Fragment newInstance() {
        return new MyShopDetailFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.getMarketShopProfile();
    }


    @Override
    protected BindingFragment<MyShopDetailFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_shop_detail, MyShopDetailFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final MyShopDetailFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, true, false);
        marketActivity.setHeader(R.string.my_shop_detail);
        marketActivity.setRightText(R.string.edit);
        marketActivity.clickListener = type -> {
            if (sharedPref.get(Constants.SHOP_NAME_SELECTED, "").equalsIgnoreCase("")) {
                Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans, new ShopProfile());
                startActivity(intent);
            } else {
                Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans, marketShopProfile);
                startNewActivity(intent);
            }
        };

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });


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
                                if (sharedPref.get(Constants.SHOP_ID, "").equalsIgnoreCase(String.valueOf(marketShopProfile.getData().get(i).getId()))) {
                                    binding.setBean(marketShopProfile.getData().get(i));
                                }

                            }

                            setBusinessTimeRecyclerView();
                        }

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


    private void setBusinessTimeRecyclerView() {
        binding.rvBusinessTime.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBusinessTime.setAdapter(adapterBusinessTime);
        for (int i = 0; i < marketShopProfile.getData().size(); i++) {
            if (sharedPref.get(Constants.SHOP_ID, "").equalsIgnoreCase(String.valueOf(marketShopProfile.getData().get(i).getId()))) {
                adapterBusinessTime.setList(marketShopProfile.getData().get(i).getBusinessHours());
            }

        }
    }


    SimpleRecyclerViewAdapter<BusinessTimeModel, HolderBusinessTimeBinding> adapterBusinessTime = new SimpleRecyclerViewAdapter(R.layout.holder_business_time, BR.bean,
            (SimpleRecyclerViewAdapter.SimpleCallback<BusinessTimeModel>) (v, bean) -> {

            });

}
