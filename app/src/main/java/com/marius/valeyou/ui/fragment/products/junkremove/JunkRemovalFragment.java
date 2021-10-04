package com.marius.valeyou.ui.fragment.products.junkremove;

import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentJunkRemovalBinding;
import com.marius.valeyou.databinding.HolderSubCategoryBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.loadtype.StandardPickFragment;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class JunkRemovalFragment extends AppFragment<FragmentJunkRemovalBinding, JunkRemovalFragmentVM> {

    private static final String CATEGORY_ID = "cat_id";
    private static final String CAT_TITLE = "cat_title";
    private MainActivity mainActivity;
    private String SUB_TAG = MyJobFragment.TAG;

    public static Fragment newInstance(int id, String title) {
        JunkRemovalFragment junkRemovalFragment = new JunkRemovalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, id);
        bundle.putString(CAT_TITLE,title);
        junkRemovalFragment.setArguments(bundle);
        return junkRemovalFragment;
    }

    @Override
    protected BindingFragment<JunkRemovalFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_junk_removal, JunkRemovalFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(JunkRemovalFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(getArguments().getString(CAT_TITLE));
        vm.getListOfCateegory(getArguments().getInt(CATEGORY_ID));
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    /*case R.id.rl_one:
                        break;
                    case R.id.rl_two:
                        mainActivity.addSubFragment(SUB_TAG, StandardPickFragment.newInstance());
                        break;
                    case R.id.rl_three:
                        break;
                    case R.id.rl_four:
                        break;*/
                }
            }
        });

        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<SubCategoryBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<SubCategoryBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        List<SubCategoryBean> data = resource.data;
                        adapter.setList(data);
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
        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvSubcategory.setLayoutManager(layoutManager);
        binding.rvSubcategory.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<SubCategoryBean, HolderSubCategoryBinding> adapter
            = new SimpleRecyclerViewAdapter<>(R.layout.holder_sub_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<SubCategoryBean>() {
        @Override
        public void onItemClick(View v, SubCategoryBean subCategoryBean) {
            mainActivity.addSubFragment(SUB_TAG, StandardPickFragment.newInstance(subCategoryBean,getArguments().getString(CAT_TITLE)));
        }
    });

}
