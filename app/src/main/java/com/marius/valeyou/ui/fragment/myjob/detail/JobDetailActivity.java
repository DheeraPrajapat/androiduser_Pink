package com.marius.valeyou.ui.fragment.myjob.detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityJobDetailBinding;
import com.marius.valeyou.databinding.HolderCancelJobDialogBinding;
import com.marius.valeyou.databinding.HolderJobSchedulerBinding;
import com.marius.valeyou.databinding.HolderUpdateJobPriceBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.ui.RatingActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.additionalcost.AdditionalCostActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.attachments.AttachmentsActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.uploaded_work.UploadedWorkActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.ui.fragment.myjob.reschedule.RescheduleActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

public class JobDetailActivity extends BaseActivity<ActivityJobDetailBinding, JobDetailActivityVM> {
    private MainActivity mainActivity;
    JobDetailsBean model;
    int job_status;
    int job_type;
    int ispayment;
    int id;
    String price;

    public static Intent newIntent(Activity activity, int id) {
        Intent intent = new Intent(activity, JobDetailActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<JobDetailActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_job_detail, JobDetailActivityVM.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        id = getIntent().getIntExtra("id", 0);
        viewModel.getJobDetaial(viewModel.sharedPref.getUserData().getAuthKey(), id);

    }

    @Override
    protected void subscribeToEvents(JobDetailActivityVM vm) {

        binding.header.tvTitle.setText(getResources().getString(R.string.job_Detail));

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()) {

                    case R.id.cancelJobBtn:
                        cancel_job_dialog();
                        break;

                    case R.id.reschedule_job:

                        if (model.getIsSchedule()==0) {
                            Intent intent = RescheduleActivity.newIntent(JobDetailActivity.this, id);
                            startNewActivity(intent);
                        }else if (model.getIsSchedule()==1){

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Reschedule Request Pending");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }else if (model.getIsSchedule()==2){

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Provider has sent you reschedule request. Please check your notifications");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();


                        }

                        break;

                    case R.id.updatePrice:
                        updateEndPriceDialog(price);
                        break;

                    case R.id.additionalCost:

                        Intent costIntnt = AdditionalCostActivity.newIntent(JobDetailActivity.this, id);
                        startNewActivity(costIntnt);

                        break;

                    case R.id.completeButton:

                        if (job_status == 1) {

                            cancel_job_dialog();

                        }
                        if (job_status == 2) {

                            vm.deleteJob(String.valueOf(id));

                        } else if (job_status == 8) {

                            Intent intentRat = RatingActivity.newIntent(JobDetailActivity.this);
                            intentRat.putExtra("id", id + "");
                            startNewActivity(intentRat);

                        } else if (job_status == 9) {
                            Intent intent2 = RatingActivity.newIntent(JobDetailActivity.this);
                            intent2.putExtra("id", id + "");
                            startNewActivity(intent2);
                        }

                        break;

                    case R.id.iv_chat:

                        Intent intentchat = ChatActivity.newIntent(JobDetailActivity.this);
                        intentchat.putExtra("comeFrom", "job_detail");
                        intentchat.putExtra("id", model.getProvider().getId());
                        intentchat.putExtra("name", model.getProvider().getFirstName() + " " + model.getProvider().getLastName());
                        intentchat.putExtra("image", model.getProvider().getImage());
                        startNewActivity(intentchat);

                        break;

                    case R.id.uploaded_work:

                        Intent intent = UploadedWorkActivity.newIntent(JobDetailActivity.this,id);
                        startActivity(intent);
                        break;

                    case R.id.uploadedAttachments:
                        Intent intentAttach = AttachmentsActivity.newIntent(JobDetailActivity.this,id);
                        startActivity(intentAttach);
                        break;
                }
            }
        });

           vm.updateEndPriceEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        viewModel.getJobDetaial(viewModel.sharedPref.getUserData().getAuthKey(), id);

                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(JobDetailActivity.this);
                            startNewActivity(intent1, true, true);

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage(resource.message);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });



        vm.deleteJobEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        finish(true);

                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(JobDetailActivity.this);
                            startNewActivity(intent1, true, true);

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage(resource.message);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });



        vm.cancelJobEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);

                        viewModel.getJobDetaial(viewModel.sharedPref.getUserData().getAuthKey(), id);

                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(JobDetailActivity.this);
                            startNewActivity(intent1, true, true);

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage(resource.message);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });



        vm.changeStatusEvent.observe(this, new Observer<Resource<ApiResponse<SimpleApiResponse>>>() {
            @Override
            public void onChanged(Resource<ApiResponse<SimpleApiResponse>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);

                        vm.success.setValue(resource.message);
                        viewModel.getJobDetaial(viewModel.sharedPref.getUserData().getAuthKey(),id);
                        /*Intent intent = RatingActivity.newIntent(JobDetailActivity.this);
                        intent.putExtra("id", id + "");
                        startNewActivity(intent);*/


                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(JobDetailActivity.this);
                            startNewActivity(intent1, true, true);

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage(resource.message);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


        vm.jobDetailBean.observe(this, new Observer<Resource<JobDetailsBean>>() {
            @Override
            public void onChanged(Resource<JobDetailsBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        binding.setVariable(BR.bean, resource.data);
                        model = resource.data;
                        price = model.getEndPrice();
                        job_status = resource.data.getStatus();

                        binding.additionalCost.setText(getResources().getString(R.string.additional_cost) + " : " + model.getTotal_additional_amount() + " Brl");

                        job_type = resource.data.getJobType();
                        ispayment = resource.data.getIsPayment();
                        adapterJobScheduler.setList(resource.data.getOrder_activities());

                        if (resource.data.getDate() != null) {
                            String date = AppUtils.getDateTime(Long.parseLong(resource.data.getDate()));
                            binding.txtDate.setText(date);
                        }

                        if (resource.data.getProvider_work_images().size()>0){
                            if (job_type==0){
                                binding.uploadedAttachments.setVisibility(View.VISIBLE);
                            }else if (job_type == 1){
                                binding.uploadedWork.setVisibility(View.VISIBLE);
                            }
                        }

                        if (resource.data.getOrderImages().size() > 0) {
                            String image = resource.data.getOrderImages().get(0).getImages();
                            ImageViewBindingUtils.setJobImage(binding.jobImage, image);

                        } else {

                            binding.jobImage.setImageResource(R.drawable.new_placeholder);

                        }


                        if (job_status == 1) {

                            binding.completeButton.setVisibility(View.VISIBLE);
                            binding.completeButton.setText(getResources().getString(R.string.cancel_job));
                            binding.completeButton.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.options.setVisibility(View.GONE);

                            if (resource.data.getBidPrice() == 0) {
                                binding.updatePrice.setVisibility(View.VISIBLE);
                            } else {
                                binding.updatePrice.setVisibility(View.GONE);
                            }

                        } else if (job_status == 2) {

                            binding.completeButton.setVisibility(View.VISIBLE);
                            binding.completeButton.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.completeButton.setText(getResources().getString(R.string.delete_job));

                        }else if (job_status == 8) {

                            binding.updatePrice.setVisibility(View.GONE);
                            binding.completeButton.setVisibility(View.VISIBLE);
                            binding.completeButton.setText(getResources().getString(R.string.click_here_to_complete_this_job));

                        } else if (job_status == 9) {
                            binding.updatePrice.setVisibility(View.GONE);
                            binding.completeButton.setVisibility(View.VISIBLE);
                            binding.completeButton.setText(getResources().getString(R.string.add_and_view_ratings));
                        } else if (job_status == 10){

                            binding.updatePrice.setVisibility(View.GONE);
                            binding.completeButton.setVisibility(View.GONE);
                            binding.options.setVisibility(View.VISIBLE);

                        }else if (job_status == 11){

                            binding.updatePrice.setVisibility(View.GONE);
                            binding.completeButton.setVisibility(View.GONE);
                            binding.options.setVisibility(View.VISIBLE);

                        }else{
                            binding.completeButton.setVisibility(View.GONE);

                            if (resource.data.getBidPrice()==0){

                                binding.updatePrice.setVisibility(View.VISIBLE);

                            }else{

                                binding.updatePrice.setVisibility(View.GONE);

                            }
                        }




                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(JobDetailActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;

                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });
        setJobShedulerStatus();
    }

    private void setJobShedulerStatus() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvJobDcheduler.setLayoutManager(layoutManager);
        binding.rvJobDcheduler.setAdapter(adapterJobScheduler);
    }


    SimpleRecyclerViewAdapter<JobDetailsBean.OrderActivitiesBean, HolderJobSchedulerBinding> adapterJobScheduler =
            new SimpleRecyclerViewAdapter(R.layout.holder_job_scheduler, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<JobDetailsBean.OrderActivitiesBean>() {
                @Override
                public void onItemClick(View v, JobDetailsBean.OrderActivitiesBean moreBean) {


                }
            });


    private BaseCustomDialog<HolderUpdateJobPriceBinding> dilaogUpdate;

    private void updateEndPriceDialog(String price) {
        dilaogUpdate = new BaseCustomDialog<>(this, R.layout.holder_update_job_price, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_update:

                            int price = Integer.parseInt(dilaogUpdate.getBinding().bidPrice.getText().toString().trim());

                            if (dilaogUpdate.getBinding().bidPrice.getText().toString().isEmpty()) {
                                viewModel.error.setValue(getResources().getString(R.string.please_enter_price));
                            } else {
                                dilaogUpdate.dismiss();

                                viewModel.updateEndPRice(String.valueOf(id), String.valueOf(price));

                            }

                            break;

                        case R.id.btn_cancel:
                            dilaogUpdate.dismiss();
                            break;
                    }
                }
            }
        });

        dilaogUpdate.getBinding().bidPrice.setText(price);
        dilaogUpdate.setCancelable(false);
        dilaogUpdate.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dilaogUpdate.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dilaogUpdate.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dilaogUpdate.show();

    }

    private BaseCustomDialog<HolderCancelJobDialogBinding> cancelJobDialog;

    private void cancel_job_dialog() {
        cancelJobDialog = new BaseCustomDialog<>(this, R.layout.holder_cancel_job_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            if (cancelJobDialog.getBinding().etReason.getText().toString().isEmpty()){
                                viewModel.error.setValue("Please enter reason of cancelling job");

                            }else{

                                cancelJobDialog.dismiss();
                                viewModel.changeJobStatus(viewModel.sharedPref.getUserData().getAuthKey(),
                                        String.valueOf(id),"0","2",cancelJobDialog.getBinding().etReason.getText().toString());

                            }

                            break;

                        case R.id.btn_cancel:
                            cancelJobDialog.dismiss();
                            break;

                    }
                }
            }
        });

        cancelJobDialog.setCancelable(true);
        cancelJobDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        cancelJobDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        cancelJobDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        cancelJobDialog.show();

    }

}