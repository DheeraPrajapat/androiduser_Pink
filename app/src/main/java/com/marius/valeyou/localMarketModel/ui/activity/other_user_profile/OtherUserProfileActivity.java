package com.marius.valeyou.localMarketModel.ui.activity.other_user_profile;

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
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityOtherUserProfileBinding;
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
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

public class OtherUserProfileActivity extends AppActivity<ActivityOtherUserProfileBinding, OtherUserProfileVM> {
   int user_id;

    public static Intent newIntent(Activity activity, int user_id) {
        Intent intent = new Intent(activity, OtherUserProfileActivity.class);
        intent.putExtra("user_id", user_id);
        return intent;
    }

    @Override
    protected BindingActivity<OtherUserProfileVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_other_user_profile, OtherUserProfileVM.class);
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
    protected void subscribeToEvents(OtherUserProfileVM vm) {
//        binding.setType("0");
        setToolbar();
        user_id = getIntent().getIntExtra("user_id",0);

        viewModel.getProfile(user_id);
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
//                    case R.id.tv_two:
//                        Intent intent = EditProfileActivity.newIntent(ProfileActivity.this);
//                        startNewActivity(intent);
//                        break;
//                    case R.id.cv_deactivate:
//                        dialogDeactivateAccount();
//                        break;
//                    case R.id.cv_delete:
//                        dialogDeleteAccount();
//                        break;
//                    case R.id.cv_change_password:
//                        intent = ChangePasswordFragment.newIntent(ProfileActivity.this);
//                        startNewActivity(intent);
//                        break;
//                    case R.id.cv_identity:
//
//                        if (binding.rvIdentity.getVisibility()!=View.VISIBLE){
//                            binding.rvIdentity.setVisibility(View.VISIBLE);
//                            binding.IMGAddIdentity.setVisibility(View.VISIBLE);
//                        }else{
//                            binding.rvIdentity.setVisibility(View.GONE);
//                            binding.IMGAddIdentity.setVisibility(View.GONE);
//                        }
//                        break;

//                    case R.id.IMG_add_identity:
//
//                        intent = AddIdentityActivity.newIntent(ProfileActivity.this);
//                        intent.putExtra("comeFrom","add");
//                        startNewActivity(intent);

//                        break;
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
                        binding.setBean(getProfileBean);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(OtherUserProfileActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                    case ERROR:
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
//        binding.header.tvTitle.setText(getResources().getString(R.string.profile));
//        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
//        binding.header.setCheck(true);
    }


}
