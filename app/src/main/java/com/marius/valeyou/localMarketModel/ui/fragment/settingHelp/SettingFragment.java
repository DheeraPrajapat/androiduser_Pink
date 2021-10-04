package com.marius.valeyou.localMarketModel.ui.fragment.settingHelp;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentSettingBinding;
import com.marius.valeyou.databinding.HolderDrawerItemBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ui.activity.changePassword.ChangePasswordActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.privacyTermsAbout.PrivacyTermsAboutFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SettingFragment extends AppFragment<FragmentSettingBinding, SettingFragmentVM> {
    public static final String TAG = "SettingFragment";

    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;

    public static Fragment newInstance() {
        return new SettingFragment();
    }


    @Override
    protected BindingFragment<SettingFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_setting, SettingFragmentVM.class);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        marketActivity.viewHeaderDefault();
    }


    @Override
    protected void subscribeToEvents(final SettingFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.settings_help);

        binding.setBean(viewModel.sharedPref.getUserData());


        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {


            }
        });

        setRecyclerView();

    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvSetting.setLayoutManager(layoutManager);
        binding.rvSetting.setAdapter(adapter);
        adapter.setList(getList());
    }

    //    send type(1=term,2=privacy,3=aboutus)
    SimpleRecyclerViewAdapter<MoreBean, HolderDrawerItemBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_drawer_item, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    switch (bean != null ? bean.getId() : 0) {
                        case 1:
                            startNewActivity(new Intent(requireActivity(), ChangePasswordActivity.class));
                            break;
                        case 2:
                            marketActivity.addSubFragment(PrivacyTermsAboutFragment.TAG, PrivacyTermsAboutFragment.newInstance(R.string.privacy_policy, 2));
                            break;

                        case 3:
                            marketActivity.addSubFragment(PrivacyTermsAboutFragment.TAG, PrivacyTermsAboutFragment.newInstance(R.string.terms_and_conditions, 1));
                            break;

                        case 4:
                            marketActivity.addSubFragment(PrivacyTermsAboutFragment.TAG, PrivacyTermsAboutFragment.newInstance(R.string.about_us, 3));
                            break;

                    }
                }
            });


    private List<MoreBean> getList() {
        List<MoreBean> data = new ArrayList<>();
        data.add(new MoreBean(1, getStr(R.string.change_password), R.drawable.ic_change_password_icon));
        data.add(new MoreBean(2, getStr(R.string.privacy_policy), R.drawable.ic_privacy_policy_icon));
        data.add(new MoreBean(3, getStr(R.string.terms_and_conditions), R.drawable.ic_privacy_policy_icon));
        data.add(new MoreBean(4, getStr(R.string.about_us), R.drawable.ic_about_us_icon));
        return data;

    }

    private String getStr(int strName) {
        return requireActivity().getResources().getString(strName);
    }

}
