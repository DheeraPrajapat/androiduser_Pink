package com.marius.valeyou.ui.activity.identityverfication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityIdentityVerificationBinding;
import com.marius.valeyou.databinding.DialogDeleteConfirmationBinding;
import com.marius.valeyou.databinding.HolderIdentityBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.addidentity.AddIdentityActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.List;

public class IdentityVerificationActivity extends AppActivity<ActivityIdentityVerificationBinding,IdentityVerficationActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, IdentityVerificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<IdentityVerficationActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_identity_verification, IdentityVerficationActivityVM.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getProfile();
    }

    @Override
    protected void subscribeToEvents(IdentityVerficationActivityVM vm) {

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){
                    case R.id.imgAdd:
                        Intent intent = AddIdentityActivity.newIntent(IdentityVerificationActivity.this);
                        intent.putExtra("comeFrom","add");
                        startNewActivity(intent);
                        break;
                }
            }
        });


        vm.profileBeanEvent.observe(this, new SingleRequestEvent.RequestObserver<GetProfileBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<GetProfileBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        GetProfileBean getProfileBean = resource.data;
                        setRecycleIdentityCard(getProfileBean.getIdentity());
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){

                            Intent intent1 = LoginActivity.newIntent(IdentityVerificationActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        vm.deleteApiBean.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {

                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        vm.getProfile();

                        break;
                    case ERROR:
                        binding.setCheck(false);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(IdentityVerificationActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        vm.error.setValue(resource.message);

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


    }


    private void setRecycleIdentityCard(List<GetProfileBean.IdentityBean> list) {
        if (list.size()>0) {
            binding.noIdentity.setVisibility(View.GONE);
            binding.rvIdentity.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.rvIdentity.setLayoutManager(layoutManager);
            binding.rvIdentity.setAdapter(adapterIdentity);
            adapterIdentity.setList(list);
        }else{
            binding.noIdentity.setVisibility(View.VISIBLE);
            binding.rvIdentity.setVisibility(View.GONE);
        }
    }

    SimpleRecyclerViewAdapter<GetProfileBean.IdentityBean, HolderIdentityBinding> adapterIdentity =
            new SimpleRecyclerViewAdapter(R.layout.holder_identity, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<GetProfileBean.IdentityBean>() {
                @Override
                public void onItemClick(View v, GetProfileBean.IdentityBean moreBean) {

                    switch (v.getId()){
                        case R.id.iv_delete:

                            String key = viewModel.sharedPref.getUserData().getAuthKey();
                            dialogDeleteItem(key,moreBean.getId());

                            break;

                        case R.id.iv_edit:
                            Intent intent = AddIdentityActivity.newIntent(IdentityVerificationActivity.this);
                            intent.putExtra("comeFrom","edit");
                            intent.putExtra("identityBean",moreBean);
                            startNewActivity(intent);
                            break;
                    }


                }
            });

    private BaseCustomDialog<DialogDeleteConfirmationBinding> deleteItemDialog;
    private void dialogDeleteItem(String key,int id) {
        deleteItemDialog = new BaseCustomDialog<>(IdentityVerificationActivity.this, R.layout.dialog_delete_confirmation, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            viewModel.deleteIdentity(key,id);
                            deleteItemDialog.dismiss();
                            break;
                        case R.id.btn_cancel:
                            deleteItemDialog.dismiss();
                            break;
                    }
                }
            }
        });

        deleteItemDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        deleteItemDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        deleteItemDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        deleteItemDialog.show();

    }
}