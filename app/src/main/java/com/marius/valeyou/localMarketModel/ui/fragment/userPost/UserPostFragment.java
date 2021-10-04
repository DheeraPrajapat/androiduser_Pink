package com.marius.valeyou.localMarketModel.ui.fragment.userPost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogBusinessHoursBinding;
import com.marius.valeyou.databinding.DialogBusinessHoursBindingImpl;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.databinding.FragmentUserPostBinding;
import com.marius.valeyou.databinding.HolderBusinessTimeBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.BusinessTimeModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.UserDetailsModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.home.AdapterMarketHome;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPostFragment extends AppFragment<FragmentUserPostBinding, UserPostFragmentVM> {
    public static final String TAG = "UserPostFragment";

    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    List<MarketPostModel> searchDataList = new ArrayList<>();
    MarketPostModel selectPostModel;
    ShopProfile shopProfile;
    String type = "0";//0=all,1=Commercial,2=private
    //    MarketShopProfile marketShopProfile;
    RecyclerView rvBusinessTime;

    public static Fragment newInstance(String userId, String userName) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putString(Constants.USER_ID, userId);
        args.putString(Constants.USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }


    public static Fragment newInstance(String userId, String userName, ShopProfile shopProfile) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putBoolean("isShop", true);
        args.putString(Constants.USER_ID, userId);
        args.putString(Constants.USER_NAME, userName);
        args.putString("shopProfile", new Gson().toJson(shopProfile));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BindingFragment<UserPostFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_user_post, UserPostFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final UserPostFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        dialogBusinessHour();
        if (this.getArguments() != null) {

            if (this.getArguments().getBoolean("isShop", false)) {
                binding.llSlide.setVisibility(View.VISIBLE);
                shopProfile = new Gson().fromJson(this.getArguments().getString("shopProfile"), ShopProfile.class);

                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(shopProfile.getMarketShopImages());
                binding.viewPager.setAdapter(viewPagerAdapter);
                binding.indicator.setViewPager(binding.viewPager);
                binding.txtCompanyName.setText(shopProfile.getCompanyName());
                marketActivity.setHeaderString(shopProfile.getCompanyName());
                binding.txtCompanyAddress.setText(shopProfile.getAddress());
                type = "1";
                binding.cvusinessHour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBusinessHour.show();
                        setBusinessTimeRecyclerView();

                    }
                });
            }
            Log.d("ADADADAADA", "Else");


            vm.getUserDetails(this.getArguments().getString(Constants.USER_ID), type);
        }

        binding.cvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ADADADAADAAA", "cvMessage");

                Intent intent1 = MessagingActivity.newIntent(requireActivity());
                intent1.putExtra("comeFrom", "profile");
                intent1.putExtra("id", marketActivity.marketPostModel.getUser().getId());
                intent1.putExtra("image", marketActivity.marketPostModel.getUser().getImage());
                intent1.putExtra("name", marketActivity.marketPostModel.getUser().getName());
                intent1.putExtra("view_type", "");
                startActivity(intent1);
            }
        });


        vm.userDetailsEvent.observe(this, new SingleRequestEvent.RequestObserver<UserDetailsModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<UserDetailsModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        searchDataList.clear();
                        searchDataList.addAll(resource.data.getData().getUserPost());
                        adapter.setList(searchDataList);
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });

        vm.addFavouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        for (int i = 0; i < searchDataList.size(); i++) {
                            if (searchDataList.get(i).getId().equals(selectPostModel.getId())) {
                                searchDataList.get(i).setFav(searchDataList.get(i).getFav() == 0 ? 1 : 0);
                            }
                        }
                        adapter.setList(searchDataList);
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        setRecyclerView();

    }

    private void setBusinessTimeRecyclerView() {
        rvBusinessTime.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBusinessTime.setAdapter(adapterBusinessTime);
        adapterBusinessTime.setList(shopProfile.getBusinessHours());
    }

    SimpleRecyclerViewAdapter<BusinessTimeModel, HolderBusinessTimeBinding> adapterBusinessTime = new SimpleRecyclerViewAdapter(R.layout.holder_business_time, BR.bean,
            (SimpleRecyclerViewAdapter.SimpleCallback<BusinessTimeModel>) (v, bean) -> {

            });

    private void setRecyclerView() {
        binding.rvUserPost.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvUserPost.setAdapter(adapter);

    }

    AdapterMarketHome adapter = new AdapterMarketHome(
            new AdapterMarketHome.ClickCallback() {
                @Override
                public void onItemClick(View v, MarketPostModel postModel) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.relName:
                        case R.id.txtDesc:
                        case R.id.txtPrice:
                            marketActivity.marketPostModel = postModel;
                            marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance(View.GONE));
                            break;
                        case R.id.imgFav:
                            selectPostModel = postModel;
                            viewModel.addToFavourite(String.valueOf(postModel.getId()), postModel.getFav() == 0 ? "1" : "0");
                            break;
                    }
                }
            });


    private BaseCustomDialog<DialogBusinessHoursBinding> dialogBusinessHour;

    private void dialogBusinessHour() {
        dialogBusinessHour = new BaseCustomDialog<>(getActivity(), R.layout.dialog_business_hours, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
//                        case R.id.btn_submit:
//                            dialogBusinessHour.dismiss();
////                            viewModel.deleteOrDeactivate(viewModel.sharedPref.getUserData().getAuthKey(), "0");
//                            break;
//                        case R.id.btn_cancel:
//                            dialogBusinessHour.dismiss();
//                            break;
//
                        case R.id.iv_cancel:
                            dialogBusinessHour.dismiss();
                            break;
                    }


                }
            }
        });
        rvBusinessTime = dialogBusinessHour.getBinding().rvBusinessTime;
        dialogBusinessHour.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogBusinessHour.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogBusinessHour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


}
