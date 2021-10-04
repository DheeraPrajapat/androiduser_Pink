package com.marius.valeyou.localMarketModel.ui.fragment.commonList;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentCommonListBinding;
import com.marius.valeyou.databinding.HolderAllCategoryBinding;
import com.marius.valeyou.databinding.HolderSelectGalleryImageBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommonListFragment extends AppFragment<FragmentCommonListBinding, CommonListFragmentVM> {
    public static final String TAG = "CommonListFragment";

    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    SimpleRecyclerViewAdapter recyclerViewAdapter;

    public static Fragment newInstance() {
        return new CommonListFragment();
    }

    public static Fragment newInstance(int header, boolean isRightText, int rightText, boolean isRightImage, int rightImage, int id, @LayoutRes int holderLayout) {
        CommonListFragment fragment = new CommonListFragment();
        Bundle args = new Bundle();
        args.putInt("header", header);
        args.putBoolean("isRightText", isRightText);
        args.putInt("rightText", rightText);
        args.putBoolean("isRightImage", isRightImage);
        args.putInt("rightImage", rightImage);
        args.putInt("id", id);
        args.putInt("holderLayout", holderLayout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketActivity.viewHeaderDefault();
    }

    @Override
    protected BindingFragment<CommonListFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_common_list, CommonListFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final CommonListFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        if (CommonListFragment.this.getArguments() != null) {
            marketActivity.viewHeaderItems(false, CommonListFragment.this.getArguments().getBoolean("isRightText", false), CommonListFragment.this.getArguments().getBoolean("isRightText", false));
            marketActivity.setHeader(CommonListFragment.this.getArguments().getInt("header", R.string.title));
            marketActivity.setRightText(CommonListFragment.this.getArguments().getInt("rightText", R.string.title));
        }

        initAdapter();

    }

    private void initAdapter() {
        if (CommonListFragment.this.getArguments().getInt("holderLayout", 0) == R.layout.holder_all_category) {
            recyclerViewAdapter = new SimpleRecyclerViewAdapter<MarketCategoryModel.Datum, HolderAllCategoryBinding>(R.layout.holder_all_category, BR.bean,
                    new SimpleRecyclerViewAdapter.SimpleCallback<MarketCategoryModel.Datum>() {
                        @Override
                        public void onItemClick(View v, MarketCategoryModel.Datum nearMe) {
                            switch (v != null ? v.getId() : 0) {
                                case R.id.holderItem:

                                    break;
                            }
                        }
                    });

            recyclerViewAdapter.setList(marketActivity.categoryListBeans.getData());


        } else if (CommonListFragment.this.getArguments().getInt("holderLayout", 0) == R.layout.holder_notification) {
            binding.txtHeading.setVisibility(View.GONE);
            recyclerViewAdapter = new SimpleRecyclerViewAdapter<MoreBean, HolderSelectGalleryImageBinding>(R.layout.holder_notification, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean nearMe) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.cv_item:

                            break;
                    }
                }
            });
            recyclerViewAdapter.setList(getList());

        }

        setRecyclerView();

    }

    private void setRecyclerView() {
        binding.rvCommonList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCommonList.setAdapter(recyclerViewAdapter);
    }


    private List<MoreBean> getList() {
        List<MoreBean> ll = new ArrayList<>();
        ll.add(new MoreBean(0, "Joseph", R.drawable.ic_mobiles));
        ll.add(new MoreBean(0, "Joseph", R.drawable.ic_home));
        ll.add(new MoreBean(0, "Joseph", R.drawable.ic_phone));
        ll.add(new MoreBean(0, "Joseph", R.drawable.ic_camera));
        return ll;
    }

}
