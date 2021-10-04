package com.marius.valeyou.ui.activity.forgot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityForgotpasswordBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.forgot.recoverpass.RecoverPasswordActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public class ForgotPasswordActivity extends AppActivity<ActivityForgotpasswordBinding, ForgotPasswordActivityVM> {

    @Override
    protected BindingActivity<ForgotPasswordActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_forgotpassword, ForgotPasswordActivityVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    protected void subscribeToEvents(final ForgotPasswordActivityVM vm) {

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, view -> {
            if (view == null)
                return;
            switch (view != null ? view.getId() : 0) {
                case R.id.btn_done:
                    if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
                      viewModel.error.setValue(getResources().getString(R.string.enter_registere_email));
                    }else if (!binding.etEmail.getText().toString().trim().matches(Constants.emailPattern)){
                        viewModel.error.setValue(getResources().getString(R.string.valid_email));
                    } else{
                        vm.forgetPassword(binding.etEmail.getText().toString().trim());
                    }
                    break;
            }
        });
        vm.requestEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        finish(true);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
    }
}
