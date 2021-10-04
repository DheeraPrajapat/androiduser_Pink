package com.marius.valeyou.ui.fragment.loadtype;

import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.databinding.FragmentStandardPickupBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class StandardPickFragment extends AppFragment<FragmentStandardPickupBinding,StandardPickFragmentVM> {
    private static final String BEAN = "bean";
    private static final String TITLE = "title";
    private MainActivity mainActivity;

    private String SUB_TAG = MyJobFragment.TAG;
    private SubCategoryBean categoryBean;

    public static Fragment newInstance(SubCategoryBean subCategoryBean, String Title) {
        StandardPickFragment pickFragment = new StandardPickFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BEAN,subCategoryBean);
        bundle.putString(TITLE,Title);
        pickFragment.setArguments(bundle);
        return pickFragment;
    }

    @Override
    protected BindingFragment<StandardPickFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_standard_pickup,StandardPickFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(StandardPickFragmentVM vm) {
        categoryBean = getArguments().getParcelable(BEAN);
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(categoryBean.getName());
        binding.setBean(categoryBean);
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0) {
                    case R.id.btn_next:
                        //mainActivity.addSubFragment(SUB_TAG, JunkRemovalJobFragment.newInstance(getArguments().getString(TITLE)));
                        break;
                }
            }
        });

    }
}
