package com.marius.valeyou.ui.fragment.more.aboutus;

import android.content.Intent;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityAboutUsBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class AboutUsFragment extends AppFragment<ActivityAboutUsBinding, AboutUsFragmentVM> {

    private MainActivity mainActivity;

    public static Fragment newInstance() {
        return new AboutUsFragment();
    }

    @Override
    protected BindingFragment<AboutUsFragmentVM> getBindingFragment() {
        return new BindingFragment<AboutUsFragmentVM>(R.layout.activity_about_us, AboutUsFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(AboutUsFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
      //  mainActivity.setHeader("About US");
        vm.getDetails(3);
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
                        break;
                    case SUCCESS:
                        binding.setDescription(resource.data.getAbout());
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
    }
}
