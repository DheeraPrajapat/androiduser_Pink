package com.marius.valeyou.localMarketModel.ui.fragment.privacyTermsAbout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentPrivacyTermsAboutBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;

public class PrivacyTermsAboutFragment extends AppFragment<FragmentPrivacyTermsAboutBinding, PrivacyTermsAboutFragmentVM> {

    public static final String TAG = "PrivacyTermsAboutFragment";

    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    int type = 2;

    public static Fragment newInstance(int header, int type) {
        PrivacyTermsAboutFragment fragment = new PrivacyTermsAboutFragment();
        Bundle args = new Bundle();
        args.putInt("header", header);
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BindingFragment<PrivacyTermsAboutFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_privacy_terms_about, PrivacyTermsAboutFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final PrivacyTermsAboutFragmentVM vm) {

        marketActivity = (MainLocalMarketActivity) requireActivity();
        if (this.getArguments() != null) {
            marketActivity.viewHeaderItems(false, false, false);
            marketActivity.setHeader(this.getArguments().getInt("header", R.string.privacy_policy));
            type = this.getArguments().getInt("type", 2);
            vm.getAllContent(type);
        }

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {


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
                        //send type(1=term,2=privacy,3=aboutus)
                        if (type == 1) {
                            binding.setPtext(resource.data.getTerm());
                        } else if (type == 2) {
                            binding.setPtext(resource.data.getPrivacy());
                        } else if (type == 3) {
                            binding.setPtext(resource.data.getAbout());
                        }
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