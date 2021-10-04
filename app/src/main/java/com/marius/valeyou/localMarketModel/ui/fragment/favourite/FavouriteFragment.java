package com.marius.valeyou.localMarketModel.ui.fragment.favourite;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentFavouriteBinding;
import com.marius.valeyou.databinding.HolderFavouritesBinding;
import com.marius.valeyou.databinding.HolderSelectGalleryImageBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.FavouriteModel;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;

public class FavouriteFragment extends AppFragment<FragmentFavouriteBinding, FavouriteFragmentVM> {
    public static final String TAG = "FavouriteFragment";

    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;

    public static Fragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketActivity.viewHeaderDefault();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected BindingFragment<FavouriteFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_favourite, FavouriteFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final FavouriteFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.favourites);
        vm.getFavorite();

        vm.addFavouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        viewModel.getFavorite();
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

        vm.favouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<FavouriteModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<FavouriteModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        adapter.setList(resource.data.getData());
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

    private void setRecyclerView() {
        binding.rvFavourite.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavourite.setAdapter(adapter);

    }

    SimpleRecyclerViewAdapter<FavouriteModel.Datum, HolderFavouritesBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_favourites, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<FavouriteModel.Datum>() {
                @Override
                public void onItemClick(View v, FavouriteModel.Datum favData) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.txtTitle:
                        case R.id.txtDesc:
                        case R.id.relBottom:
                            marketActivity.marketPostModel = favData.getMarketPost();
                            marketActivity.marketPostModel.setFav(1);
                            marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance());
                            break;
                        case R.id.imgFav:
                            //type(1=fav,0=unfav)
                            viewModel.addToFavourite(String.valueOf(favData.getPostId()), "0");
                            break;
                    }
                }
            });

}
