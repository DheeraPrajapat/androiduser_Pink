package com.marius.valeyou.localMarketModel.ui.fragment.orders;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentMyOrdersBinding;
import com.marius.valeyou.databinding.HolderOrderListBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyOrdersFragment extends AppFragment<FragmentMyOrdersBinding, MyOrdersFragmentVM> {

    public static final String TAG = "MyOrdersFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    private MarketHomeModel marketHomeModel;
    List<MarketPostModel> privateList = new ArrayList();
    List<MarketPostModel> commercialList = new ArrayList();
    List<ShopProfile> marketShopProfiles = new ArrayList<>();
    MarketShopProfile marketShopProfile;
    PopupMenu shopsPopupMenu;
    String owner_type = "Private";


    public static Fragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    protected BindingFragment<MyOrdersFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_orders, MyOrdersFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(MyOrdersFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.my_products);
        marketActivity.setRightText(R.string.clear_history);

         marketActivity.imgToolRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marketShopProfiles.size() > 0) {
                    shopsPopupMenu.show();

                }
            }
        });

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.tvPrivate:
                   privateOwner();

                    break;

                case R.id.tvCommercial:
                    commercialOwner();
                    break;
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vm.getMyPost();

            }
        });
        vm.getMyPost();
        vm.getMarketShopProfile();

        vm.homeModelEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketHomeModel>() {

            @Override
            public void onRequestReceived(@NonNull Resource<MarketHomeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        privateList.clear();
                        commercialList.clear();
                        dismissProgressDialog();
                        marketHomeModel = resource.data;
                        binding.swipeRefreshLayout.setRefreshing(false);
                        if (marketHomeModel.getData().size() > 0) {

                            for (int i = 0; i < marketHomeModel.getData().size(); i++) {
                                if (marketHomeModel.getData().get(i).getOwnerType().equalsIgnoreCase("Private")) {
                                    privateList.add(marketHomeModel.getData().get(i));
                                } else {
                                    commercialList.add(marketHomeModel.getData().get(i));
                                }
                            }

                        }
                        if (owner_type == "Private") {
                            privateOwner();//api
                            adapter.setList(privateList);
                        } else {
                            commercialOwner();//api
                            adapter.setList(commercialList);
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });

        vm.productFilterEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketHomeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketHomeModel> resource) {

                if (owner_type == "Private") {
                    privateList.clear();
                } else {
                    commercialList.clear();
                }

                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        marketHomeModel = resource.data;
                        for (int i = 0; i < marketHomeModel.getData().size(); i++) {
                            if (marketHomeModel.getData().get(i).getOwnerType().equalsIgnoreCase("Private")) {
                                privateList.add(marketHomeModel.getData().get(i));
                                Log.d("AASDASD", marketHomeModel.getData().get(i).getOwnerType());
                            } else {
                                commercialList.add(marketHomeModel.getData().get(i));
                            }
                        }
                        if (owner_type == "Private") {
                            if (privateList.size() > 0) {
                                binding.noProduct.setVisibility(View.GONE);
                            } else {
                                binding.noProduct.setVisibility(View.VISIBLE);
                                binding.noProduct.setText(getActivity().getResources().getString(R.string.no_private_product_found));
                            }
                            adapter.setList(privateList);
                        } else {
                            if (commercialList.size() > 0) {
                                binding.noProduct.setVisibility(View.GONE);
                            } else {
                                binding.noProduct.setVisibility(View.VISIBLE);
                                binding.noProduct.setText(getActivity().getResources().getString(R.string.no_commercial_product_found));

                            }
                            adapter.setList(commercialList);

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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        vm.soldEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        viewModel.getMyPost();
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


        marketShopProfiles = new ArrayList<>();
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
                            shopsPopupMenu = new PopupMenu(getActivity(), marketActivity.imgToolRight);
                            for (int i = 0; i < marketShopProfiles.size(); i++) {
                                shopsPopupMenu.getMenu().add(0, Integer.parseInt(String.valueOf(marketShopProfiles.get(i).getId())), 0, marketShopProfiles.get(i).getCompanyName());

                            }

                            shopsPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    Log.d("ASDASDASA", String.valueOf(item.getTitle()) + " " + owner_type);
                                    vm.getFilterProducts(String.valueOf(item.getTitle()), owner_type);
                                    return true;
                                }
                            });

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


        setRecyclerView();
    }

    private void privateOwner() {
        binding.tvPrivate.setBackgroundResource(R.drawable.btn_selected_solid);
        binding.tvCommercial.setBackgroundResource(0);
        binding.tvPrivate.setTextColor(getActivity().getResources().getColor(R.color.white));
        binding.tvCommercial.setTextColor(getActivity().getResources().getColor(R.color.black));
        adapter.setList(privateList);
        adapter.notifyDataSetChanged();
        owner_type = "Private";
        marketActivity.imgToolRight.setVisibility(View.GONE);
        if (privateList.size() > 0) {
            binding.noProduct.setVisibility(View.GONE);
            binding.rvMyOrder.setVisibility(View.VISIBLE);
        } else {
            binding.noProduct.setVisibility(View.VISIBLE);
            binding.rvMyOrder.setVisibility(View.GONE);
            binding.noProduct.setText(getActivity().getResources().getString(R.string.no_private_product_found));
        }

    }

    private void commercialOwner() {
        binding.tvCommercial.setBackgroundResource(R.drawable.btn_selected_solid);
        binding.tvPrivate.setBackgroundResource(0);
        binding.tvCommercial.setTextColor(getActivity().getResources().getColor(R.color.white));
        binding.tvPrivate.setTextColor(getActivity().getResources().getColor(R.color.black));
        adapter.setList(commercialList);
        adapter.notifyDataSetChanged();
        marketActivity.imgToolRight.setVisibility(View.VISIBLE);
        owner_type = "Commercial";
        if (commercialList.size() > 0) {
            binding.noProduct.setVisibility(View.GONE);
            binding.rvMyOrder.setVisibility(View.VISIBLE);

        } else {
            binding.rvMyOrder.setVisibility(View.GONE);
            binding.noProduct.setVisibility(View.VISIBLE);
            binding.noProduct.setText(getActivity().getResources().getString(R.string.no_commercial_product_found));
        }
    }


    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvMyOrder.setLayoutManager(layoutManager);
        binding.rvMyOrder.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<MarketPostModel, HolderOrderListBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_order_list, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MarketPostModel>() {
                @Override
                public void onItemClick(View v, MarketPostModel model) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.txtDesc:
                        case R.id.relName:
                        case R.id.txtPrice:
                            marketActivity.imgToolRight.setVisibility(View.GONE);
                            marketActivity.marketPostModel = model;
                            marketActivity.marketPostModel.setUser(sharedPref.getUserData());
                            marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance(View.GONE));
                            break;

                        case R.id.txtSoled:
                            if (model.getSold().equalsIgnoreCase("No")) {
                                viewModel.markAsSold(String.valueOf(model.getId()), "Yes");
                            }

                            break;
                    }
                }
            });

}