package com.marius.valeyou.ui.fragment.more.privacy_policy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityPrivacyPolicyBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.more.helpnsupport.faq.FAQActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class PrivacyPolicyFragment extends AppFragment<ActivityPrivacyPolicyBinding, PrivacyPolicyFragmentVM> {
    private MainActivity mainActivity;
    public static Fragment newInstance() {
        PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
        return privacyPolicyFragment;
    }
    @Override
    protected BindingFragment<PrivacyPolicyFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.activity_privacy_policy, PrivacyPolicyFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(PrivacyPolicyFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        vm.getDetails(2);
        vm.getDetailsTerms(1);
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                Intent intent ;
                switch (view.getId()){
                    case R.id.image_Back:
                        mainActivity.backStepFragment();
                }
            }
        });
        vm.requestEvent.observe(this, new SingleRequestEvent.RequestObserver<PrivacyResponseBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<PrivacyResponseBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        binding.setPtitle(getResources().getString(R.string.privacy_policy));
                        binding.setPtext(resource.data.getPrivacy());

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        vm.termsEvent.observe(this, new SingleRequestEvent.RequestObserver<PrivacyResponseBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<PrivacyResponseBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        binding.setTtitle(getResources().getString(R.string.terms_and_conditions));
                        binding.setTtext(resource.data.getTerm());
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
    }





}
