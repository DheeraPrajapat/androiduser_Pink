package com.marius.valeyou.ui.fragment.more;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.databinding.DialogLogoutBinding;
import com.marius.valeyou.databinding.FragmentMoreBinding;
import com.marius.valeyou.databinding.HolderMoreBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragment;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoreFragment extends AppFragment<FragmentMoreBinding, MoreFragmentVM> {
    public static final String TAG = "MoreFragment";
    private MainActivity mainActivity;
    @Inject
    SharedPref sharedPref;

    public static Fragment newInstance() {
        return new MoreFragment();
    }

    @Override
    protected BindingFragment<MoreFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_more, MoreFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void subscribeToEvents(final MoreFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("");
        mainActivity.hideBackButton();
        binding.setName(sharedPref.getUserData().getFirstName() + " " + sharedPref.getUserData().getLastName());
        setRecyclerViewMore();
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });

        vm.logout.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:

                        break;
                    case SUCCESS:
                        sharedPref.deleteAll();
                        Intent intent1 = LoginActivity.newIntent(getActivity());
                        startNewActivity(intent1, true, true);
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

    }

    private void setRecyclerViewMore() {
        binding.rvMore.setLayoutManager(new LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, com.marius.valeyou.BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean shopBean) {
                Intent intent;
                switch (shopBean.getId()) {
                    case 1:
                        intent = ProfileActivity.newIntent(getActivity());
                        startNewActivity(intent);
                        break;
                    case 2:
                        intent = ChangePasswordFragment.newIntent(getActivity());
                        startNewActivity(intent);
                        break;
                    case 3:
                       // mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance("Privacy Policy",1));
                        break;
                    case 4:
                      //  mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance("Terms & Conditions",2));
                        break;
                    case 5:
                        mainActivity.addSubFragment(TAG, AboutUsFragment.newInstance());
                        break;
                    case 6:
                        mainActivity.addSubFragment(TAG, HelpAndSupportFragment.newInstance());
                        break;
                    case 7:
                        dialogDeactivateAccount();
                        break;
                }
            }
        });
        binding.rvMore.setAdapter(adapter);
        adapter.setList(getMoreData());
    }

    /* load data in list */
    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, "Profile", R.drawable.ic_profile_sidemenu_icon));
        menuBeanList.add(new MoreBean(2, "Change Password", R.drawable.ic_change_password_icon));
        menuBeanList.add(new MoreBean(3, "Privacy Policy", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(4, "Terms & Conditions", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(5, "About US", R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(6, "Help And Support", R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(7, "Logout", R.drawable.ic_logout_icon));
        return menuBeanList;
    }

    private BaseCustomDialog<DialogLogoutBinding> dialogSuccess;

    private void dialogDeactivateAccount() {
        dialogSuccess = new BaseCustomDialog<>(getActivity(), R.layout.dialog_logout, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            viewModel.logout();
                            break;
                        case R.id.b_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }

}
