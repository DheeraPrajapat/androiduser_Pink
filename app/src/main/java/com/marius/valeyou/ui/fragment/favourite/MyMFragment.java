package com.marius.valeyou.ui.fragment.favourite;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.respbean.FavoriteListBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentMymBinding;
import com.marius.valeyou.databinding.HolderFavItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyMFragment extends AppFragment<FragmentMymBinding, MyMFragmentVM> {
    public static final String TAG = "MyMFragment";
    private MainActivity mainActivity;
    @Inject
    SharedPref sharedPref;
    private List<FavoriteListBean> listBeans;

    public static Fragment newInstance() {
        return new MyMFragment();
    }

    @Override
    protected BindingFragment<MyMFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_mym, MyMFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getFavouriteList(sharedPref.getUserData().getAuthKey());
    }

    @Override
    protected void subscribeToEvents(final MyMFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(getResources().getString(R.string.favourites));
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });
        vm.successData.observe(this, new SingleRequestEvent.RequestObserver<List<FavoriteListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<FavoriteListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        listBeans = resource.data;
                        if (listBeans.size()>0) {
                            binding.noData.setVisibility(View.GONE);
                            binding.rvFav.setVisibility(View.VISIBLE);
                            adapter.setList(listBeans);
                        }else{
                            binding.noData.setVisibility(View.VISIBLE);
                            binding.rvFav.setVisibility(View.GONE);
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))){
                           sharedPref.deleteAll();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvFav.setLayoutManager(layoutManager);
        binding.rvFav.setAdapter(adapter);
        if (listBeans != null)
            adapter.setList(listBeans);
    }

    SimpleRecyclerViewAdapter<FavoriteListBean, HolderFavItemsBinding> adapter =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_fav_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<FavoriteListBean>() {
        @Override
        public void onItemClick(View v, FavoriteListBean moreBean) {

            switch (v.getId()){
                case R.id.rl_fav:
                    Intent intent = new Intent(getContext(), ProviderProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", moreBean.getProviderId());
                    intent.putExtra("fav", moreBean.getStatus());
                    getContext().startActivity(intent);
                    break;
            }


        }
    });

    private List<MoreBean> getList() {
        List<MoreBean> moreBeans = new ArrayList<>();
        moreBeans.add(new MoreBean(1, "", 1));
        moreBeans.add(new MoreBean(1, "", 1));
        moreBeans.add(new MoreBean(1, "", 1));
        moreBeans.add(new MoreBean(1, "", 1));
        moreBeans.add(new MoreBean(1, "", 1));
        return moreBeans;
    }

}
