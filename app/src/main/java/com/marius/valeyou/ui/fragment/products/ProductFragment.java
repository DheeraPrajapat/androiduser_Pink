package com.marius.valeyou.ui.fragment.products;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentProductBinding;
import com.marius.valeyou.databinding.HolderCategoryItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.products.junkremove.JunkRemovalFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductFragment extends AppFragment<FragmentProductBinding, ProductFragmentVM> {
    public static final String TAG = "ProductFragment";
    private MainActivity mainActivity;
    public String TAG_SUB = MyJobFragment.TAG;

    @Inject
    SharedPref sharedPref;

    public static Fragment newInstance() {
        return new ProductFragment();
    }

    @Override
    protected BindingFragment<ProductFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_product, ProductFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void subscribeToEvents(final ProductFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("");
        mainActivity.hideBackButton();
        binding.setName("Hey " + sharedPref.getUserData().getFirstName() + " Welcome");
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });
        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        adapter.setList(resource.data);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });
        vm.getListOfCateegory();
        setRecyclerView();

    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        binding.rvCategory.setLayoutManager(layoutManager);
        binding.rvCategory.setAdapter(adapter);
    }

    private List<MoreBean> getListData() {
        List<MoreBean> beanList = new ArrayList<>();
        beanList.add(new MoreBean(1, "AUTO/BOAT", R.drawable.ic_auto_boat_icon));
        beanList.add(new MoreBean(2, "HANDYMAN", R.drawable.ic_handyman_icon));
        beanList.add(new MoreBean(3, "JUNK REMOVAL", R.drawable.ic_junk_removal_icon));
        beanList.add(new MoreBean(4, "PET CARE", R.drawable.ic_per_care_icon));
        beanList.add(new MoreBean(5, "PAINTING", R.drawable.ic_painting_icon));
        beanList.add(new MoreBean(6, "TV MOUNT &\nELECTRONICS", R.drawable.ic_electronics_icon));
        return beanList;
    }

    SimpleRecyclerViewAdapter<CategoryListBean, HolderCategoryItemsBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_category_items, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<CategoryListBean>() {
                @Override
                public void onItemClick(View v, CategoryListBean moreBean) {
                    mainActivity.addSubFragment(TAG_SUB, JunkRemovalFragment.newInstance(moreBean.getId(),moreBean.getName()));
                }
            });

}
