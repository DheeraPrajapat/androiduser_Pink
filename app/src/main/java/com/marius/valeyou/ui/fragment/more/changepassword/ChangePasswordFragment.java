package com.marius.valeyou.ui.fragment.more.changepassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentChangePasswordBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class ChangePasswordFragment extends AppActivity<FragmentChangePasswordBinding, ChangePasswordFragmentVM> {
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ChangePasswordFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }
    //Convert to activity

    @Override
    protected BindingActivity<ChangePasswordFragmentVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.fragment_change_password, ChangePasswordFragmentVM.class);
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
    protected void subscribeToEvents(ChangePasswordFragmentVM vm) {

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_done:
                        if (!TextUtils.isEmpty(binding.etOldPass.getText()) && !TextUtils.isEmpty(binding.etNewPass.getText())
                                && !TextUtils.isEmpty(binding.etConfirmPass.getText())) {
                            if (binding.etNewPass.getText().toString().trim().equalsIgnoreCase(binding.etConfirmPass.getText().toString().trim())) {
                                vm.changedPassword(binding.etOldPass.getText().toString(), binding.etNewPass.getText().toString());
                            } else {
                                vm.error.setValue(getResources().getString(R.string.password_doesnt_match));
                            }
                        }
                        break;
                }
            }
        });

        vm.changedPassword.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        finish();
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        if (resource.message.equalsIgnoreCase("bad request")){
                            vm.error.setValue(getResources().getString(R.string.old_password_dosent_match));
                        }else{
                            vm.error.setValue(resource.message);
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

    }
}
