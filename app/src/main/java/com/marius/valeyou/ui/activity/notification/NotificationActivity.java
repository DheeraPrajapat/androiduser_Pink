package com.marius.valeyou.ui.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityNotificationBinding;
import com.marius.valeyou.databinding.HolderAcceptConfirmationBinding;
import com.marius.valeyou.databinding.HolderNoConfirmationDialogBinding;
import com.marius.valeyou.databinding.HolderNotificationItemBinding;
import com.marius.valeyou.databinding.HolderYesConfirmDialogBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.RatingActivity;
import com.marius.valeyou.ui.activity.PayPerHourActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.ui.fragment.myjob.reschedule.RescheduleActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Inject;

public class NotificationActivity extends AppActivity<ActivityNotificationBinding, NotificationActivityVM> {
    public static final String TAG = "MainActivity";
    private List<GetNotificationList> getNotificationLists;
    int pos;
    NotificationAdapter adapter;
    MainActivity mainActivity;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNotification();
    }

    @Override
    protected BindingActivity<NotificationActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_notification, NotificationActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(NotificationActivityVM vm) {

        mainActivity = new MainActivity();

        binding.header.tvTitle.setText(getResources().getString(R.string.notifications));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<List<GetNotificationList>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<GetNotificationList>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        getNotificationLists = resource.data;
                        if (getNotificationLists.size()>0) {
                            setRecyclerView(getNotificationLists);
                            binding.rvNotification.setVisibility(View.VISIBLE);
                            binding.noData.setVisibility(View.GONE);
                        }else{
                            binding.noData.setVisibility(View.VISIBLE);
                            binding.rvNotification.setVisibility(View.GONE);
                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))){

                            Intent intent1 = LoginActivity.newIntent(NotificationActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                }
            }
        });

        vm.readNotification.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        getNotificationLists.get(pos).setIsSeen(1);
                        adapter.notifyItemChanged(pos);
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


        vm.rescheduleconfirmationEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        adapter.notifyItemChanged(pos);
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

        vm.hireAnotherProviderEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
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

    private void setRecyclerView(List<GetNotificationList> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        binding.rvNotification.setLayoutManager(layoutManager);
         adapter = new NotificationAdapter(NotificationActivity.this, list, new NotificationAdapter.Listner() {
            @Override
            public void onItemClick(View v, int position, GetNotificationList moreBean) {

                switch (v != null ? v.getId() : 0) {

                    case R.id.yesBtn:
                        yesConfirmation(String.valueOf(moreBean.getOrder().getId()));
                        break;

                    case R.id.hireBtn:
                        hireAnotherDialog(String.valueOf(moreBean.getOrder().getId()));
                        break;

                    case R.id.suggestTime:

                        Intent reIntent = RescheduleActivity.newIntent(NotificationActivity.this,moreBean.getOrder().getId());
                        startNewActivity(reIntent);
                        break;


                    case R.id.tv_read:
                        pos=position;
                        viewModel.readNotification("1", moreBean.getId());
                        break;
                    case R.id.cv_items:
                        if (moreBean.getOrder().getJobType()==0){

                            if (moreBean.getOrder().getStatus()==1){

                                if (moreBean.getIsPayment()==0) {

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this, "jobs");
                                    startNewActivity(intent);
                                }else{
                                    Intent intent = JobDetailActivity.newIntent(NotificationActivity.this,moreBean.getOrder().getId());
                                    intent.putExtra("id",moreBean.getOrder().getId()+"");
                                    startNewActivity(intent);
                                }

                            }else if (moreBean.getOrder().getStatus()==9){
                                Intent intent = RatingActivity.newIntent(NotificationActivity.this);
                                intent.putExtra("id",moreBean.getOrder().getId()+"");
                                startNewActivity(intent);
                            }else{
                                Intent intent = JobDetailActivity.newIntent(NotificationActivity.this,moreBean.getOrder().getId());
                                intent.putExtra("id",moreBean.getOrder().getId()+"");
                                startNewActivity(intent);
                            }

                        }else if (moreBean.getOrder().getJobType()==1){
                            if (moreBean.getOrder().getStatus()==8){

                                if (moreBean.getIsPayment()==0) {

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this, "jobs");
                                    startNewActivity(intent);
                                }else{
                                    Intent intent = JobDetailActivity.newIntent(NotificationActivity.this,moreBean.getOrder().getId());
                                    intent.putExtra("id",moreBean.getOrder().getId()+"");
                                    startNewActivity(intent);
                                }

                            }else if (moreBean.getOrder().getStatus()==9){

                                Intent intent = RatingActivity.newIntent(NotificationActivity.this);
                                intent.putExtra("id",moreBean.getOrder().getId()+"");
                                startNewActivity(intent);

                            }else{

                                Intent intent = JobDetailActivity.newIntent(NotificationActivity.this,moreBean.getOrder().getId());
                                intent.putExtra("id",moreBean.getOrder().getId()+"");
                                startNewActivity(intent);
                            }


                        }

                     /*   if (moreBean.getOrder().getStatus()==8||moreBean.getOrder().getStatus()==9){

                            Intent intent = RatingActivity.newIntent(NotificationActivity.this);
                            intent.putExtra("id",moreBean.getOrder().getId()+"");
                            startNewActivity(intent);

                        } else if (moreBean.getOrder().getStatus() == 4){

                            if (moreBean.getOrder().getJobType()==1) {

                                if (moreBean.getIsPayment()==0) {

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                                    startNewActivity(intent);
                                }else{

                                    Intent intent = RatingActivity.newIntent(NotificationActivity.this);
                                    intent.putExtra("id",moreBean.getOrder().getId()+"");
                                    startNewActivity(intent);
                                }

                            }else{

                                Intent intent = RatingActivity.newIntent(NotificationActivity.this);
                                intent.putExtra("id",moreBean.getOrder().getId()+"");
                                startNewActivity(intent);

                            }

                        }else if (moreBean.getOrder().getStatus() == 3) {

                            Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                            startNewActivity(intent);

                        }else{

                            if (moreBean.getUserId()!=0) {

                                if (moreBean.getOrder().getJobType() == 0) {

                                    if (moreBean.getIsPayment() == 1) {

                                        Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                                        startNewActivity(intent);
                                    } else {

                                        Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                                        startNewActivity(intent);


                                    }

                                }else{

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs_detail",moreBean.getId());
                                    startNewActivity(intent);
                                }

                            }else {



                                if (moreBean.getOrder().getStatus() == 0) {

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                                    startNewActivity(intent);
                                } else {

                                    Intent intent = MainActivity.newIntent(NotificationActivity.this,"jobs");
                                    startNewActivity(intent);
                                }


                            }
                        }*/


                        break;
                }

            }
        });
        binding.rvNotification.setAdapter(adapter);
    }



    private BaseCustomDialog<HolderYesConfirmDialogBinding> YesConfirmDoalog;

    private void yesConfirmation(String id) {
        YesConfirmDoalog = new BaseCustomDialog<>(NotificationActivity.this, R.layout.holder_yes_confirm_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            YesConfirmDoalog.dismiss();
                            viewModel.rescheduleConfirmationApi(id);
                            break;
                        case R.id.btn_cancel:
                            YesConfirmDoalog.dismiss();
                            break;

                        case R.id.iv_cancel:
                            YesConfirmDoalog.dismiss();
                            break;
                    }
                }
            }
        });
        YesConfirmDoalog.getWindow().setBackgroundDrawableResource(R.color.white_trans);
        YesConfirmDoalog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        YesConfirmDoalog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        YesConfirmDoalog.show();
    }

    private BaseCustomDialog<HolderNoConfirmationDialogBinding> NoConfirmDoalog;

    private void hireAnotherDialog(String id) {
        NoConfirmDoalog = new BaseCustomDialog<>(NotificationActivity.this, R.layout.holder_no_confirmation_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            NoConfirmDoalog.dismiss();
                           viewModel.hireAnotherProvider(id);
                            break;
                        case R.id.btn_cancel:
                            NoConfirmDoalog.dismiss();
                            break;

                        case R.id.iv_cancel:
                            NoConfirmDoalog.dismiss();
                            break;
                    }
                }
            }
        });
        NoConfirmDoalog.getWindow().setBackgroundDrawableResource(R.color.white_trans);
        NoConfirmDoalog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        NoConfirmDoalog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        NoConfirmDoalog.show();
    }


}
