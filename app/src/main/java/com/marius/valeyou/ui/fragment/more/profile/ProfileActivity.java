package com.marius.valeyou.ui.fragment.more.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityProfileBinding;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou.databinding.HolderIdentityBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.profile.addidentity.AddIdentityActivity;
import com.marius.valeyou.ui.fragment.more.profile.editprofile.EditProfileActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ProfileActivity extends AppActivity<ActivityProfileBinding, ProfileActivityVM> {
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<ProfileActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_profile, ProfileActivityVM.class);
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
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void subscribeToEvents(ProfileActivityVM vm) {
        binding.setType("0");
        setToolbar();

        viewModel.getProfile();
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
                    case R.id.tv_two:
                        Intent intent = EditProfileActivity.newIntent(ProfileActivity.this);
                        startNewActivity(intent);
                        break;
                    case R.id.cv_deactivate:
                        dialogDeactivateAccount();
                        break;
                    case R.id.cv_delete:
                        dialogDeleteAccount();
                        break;
                    case R.id.cv_change_password:
                        intent = ChangePasswordFragment.newIntent(ProfileActivity.this);
                        startNewActivity(intent);
                        break;
                    case R.id.cv_identity:

                        if (binding.rvIdentity.getVisibility()!=View.VISIBLE){
                            binding.rvIdentity.setVisibility(View.VISIBLE);
                            binding.IMGAddIdentity.setVisibility(View.VISIBLE);
                        }else{
                            binding.rvIdentity.setVisibility(View.GONE);
                            binding.IMGAddIdentity.setVisibility(View.GONE);
                        }
                        break;

                    case R.id.IMG_add_identity:

                       intent = AddIdentityActivity.newIntent(ProfileActivity.this);
                       intent.putExtra("comeFrom","add");
                       startNewActivity(intent);

                        break;
                }
            }
        });

        vm.getProfile.observe(this, new SingleRequestEvent.RequestObserver<GetProfileBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<GetProfileBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        GetProfileBean getProfileBean = resource.data;
                        binding.setProfile(getProfileBean);

                        setRecycleIdentityCard(getProfileBean.getIdentity());

                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))){
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(ProfileActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                    case ERROR:
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

                        break;
                    case SUCCESS:

                        vm.success.setValue(resource.message);
                        vm.getProfile();

                        break;
                    case ERROR:

                        if (resource.message.equalsIgnoreCase("unauthorized")){

                            Intent intent1 = LoginActivity.newIntent(ProfileActivity.this);
                            startNewActivity(intent1, true, true);

                        }

                        vm.error.setValue(resource.message);

                        break;
                    case WARN:

                        vm.warn.setValue(resource.message);

                        break;
                }
            }
        });


        vm.successEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("account deactivate successfully")){
                        vm.success.setValue(getResources().getString(R.string.deactivate_success));
                    }
                        sharedPref.deleteAll();
                        Intent intent1 = LoginActivity.newIntent(ProfileActivity.this);
                        startNewActivity(intent1, true, true);
                        finishAffinity();
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

    private List<MoreBean> getData() {
        List<MoreBean> beanList = new ArrayList<>();
        beanList.add(new MoreBean(1, "Language", 1));
        beanList.add(new MoreBean(1, "Portfolio", 1));
        return beanList;
    }

    private void setToolbar() {
        binding.header.tvTitle.setText(getResources().getString(R.string.profile));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        binding.header.setCheck(true);
    }


    private BaseCustomDialog<DialogDeactivateAccopuontBinding> dialogSuccess;

    private void dialogDeactivateAccount() {
        dialogSuccess = new BaseCustomDialog<>(ProfileActivity.this, R.layout.dialog_deactivate_accopuont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            /*if (!TextUtils.isEmpty(dialogSuccess.getBinding().etDelete.getText().toString().trim())
                                    && dialogSuccess.getBinding().etDelete.getText().toString().trim().equalsIgnoreCase("Deactivate Account")) {
                            }*/
                            dialogSuccess.dismiss();
                            viewModel.deleteOrDeactivate(sharedPref.getUserData().getAuthKey(), "0");
                            break;
                        case R.id.btn_cancel:
                            dialogSuccess.dismiss();
                            break;

                        case R.id.iv_cancel:
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

    private BaseCustomDialog<DialogDeleteAccouontBinding> dialogDelete;

    private void dialogDeleteAccount() {
        dialogDelete = new BaseCustomDialog<>(ProfileActivity.this, R.layout.dialog_delete_accouont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            /*if (!TextUtils.isEmpty(dialogDelete.getBinding().etDelete.getText().toString().trim())
                                    && dialogDelete.getBinding().etDelete.getText().toString().trim().equalsIgnoreCase("DELETE")) {
                            }*/
                            dialogDelete.dismiss();
                            viewModel.deleteOrDeactivate(sharedPref.getUserData().getAuthKey(), "1");
                            break;
                        case R.id.btn_cancel:
                            dialogDelete.dismiss();
                            break;

                        case R.id.iv_cancel:
                            dialogDelete.dismiss();
                            break;
                    }
                }
            }
        });
        dialogDelete.getWindow().setBackgroundDrawableResource(R.color.white_trans);
        dialogDelete.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogDelete.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogDelete.show();
    }

    private void setRecycleIdentityCard(List<GetProfileBean.IdentityBean> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvIdentity.setLayoutManager(layoutManager);
        binding.rvIdentity.setAdapter(adapterIdentity);
        adapterIdentity.setList(list);
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
                            Intent intent = AddIdentityActivity.newIntent(ProfileActivity.this);
                            intent.putExtra("comeFrom","edit");
                            intent.putExtra("identityBean",moreBean);
                            startNewActivity(intent);
                            break;
                    }


                }
            });


    private BaseCustomDialog<DialogDeleteAccouontBinding> deleteItemDialog;
    private void dialogDeleteItem(String key,int id) {
        deleteItemDialog = new BaseCustomDialog<>(ProfileActivity.this, R.layout.dialog_delete_confirmation, new BaseCustomDialog.Listener() {
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
