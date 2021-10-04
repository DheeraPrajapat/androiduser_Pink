package com.marius.valeyou.localMarketModel.ui.fragment.profile;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentProfileBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.ui.activity.editProfile.EditProfile2Activity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;

import javax.inject.Inject;

public class ProfileFragment extends AppFragment<FragmentProfileBinding, ProfileFragmentVM> {
    public static final String TAG = "ProfileFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected BindingFragment<ProfileFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_profile, ProfileFragmentVM.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketActivity.viewHeaderDefault();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null)
            binding.setBean(viewModel.sharedPref.getUserData());
    }

    @Override
    protected void subscribeToEvents(final ProfileFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, true, false);
        marketActivity.setHeader(R.string.profile);
        marketActivity.setRightText(R.string.edit_profile);

        binding.setBean(vm.sharedPref.getUserData());

        marketActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {
                startNewActivity(new Intent(requireActivity(), EditProfile2Activity.class));
            }
        };

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });

    }

}
