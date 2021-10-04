package com.marius.valeyou.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityRatingBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;


public class RatingActivity extends AppActivity<ActivityRatingBinding, RatingActivityVM> {

    String id;
    JobDetailsBean jobDetailModel;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, RatingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<RatingActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_rating, RatingActivityVM.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in  = getIntent();
        if (in!=null){

         id = in.getStringExtra("id");

         viewModel.getJobDetaial(sharedPref.getUserData().getAuthKey(),id);

        }

    }

    @Override
    protected void subscribeToEvents(RatingActivityVM vm) {

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.completeJobEventRequest.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        viewModel.getJobDetaial(sharedPref.getUserData().getAuthKey(),id);
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


        vm.changeStatusEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.rateProvider(sharedPref.getUserData().getAuthKey(), jobDetailModel.getProvider().getId(), jobDetailModel.getId(), binding.rbRating.getRating() + "", binding.etDescription.getText().toString());
                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (simpleApiResponseResource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(RatingActivity.this);
                            startNewActivity(intent1, true, true);

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(RatingActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage(simpleApiResponseResource.message);
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
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });




        vm.jobDetailBean.observe(this, new Observer<Resource<JobDetailsBean>>() {
            @Override
            public void onChanged(Resource<JobDetailsBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        binding.setVariable(BR.bean,resource.data);
                        jobDetailModel = resource.data;

                        String time = resource.data.getStartTime();
                        String convertedTime = AppUtils.getDateTime(Long.parseLong(time));
                        binding.startDate.setText(convertedTime);

                        if (resource.data.getOrderImages().size() > 0 ) {

                            String image = resource.data.getOrderImages().get(0).getImages();
                            ImageViewBindingUtils.setJobImage(binding.jobImage,image);

                        }

                        if (resource.data.getProvider().getImage()!=null){
                            ImageViewBindingUtils.setProfileUrl(binding.userImage,resource.data.getProvider().getImage());
                        }


                 if (jobDetailModel.getJobType()==0){
                     
                        if (jobDetailModel.getStatus()==9){
                            

                            if (jobDetailModel.getIsRate()==0){

                                binding.llRating.setVisibility(View.VISIBLE);

                            }else {

                                binding.llRating.setVisibility(View.GONE);

                            }

                        }
                    }else {

                     if (jobDetailModel.getStatus()==9){

                         if (jobDetailModel.getIsRate()==0){

                             binding.llRating.setVisibility(View.VISIBLE);

                         }else {

                             binding.llRating.setVisibility(View.GONE);

                         }

                     }

                        }



                        if (jobDetailModel.getProvider_rating().getId()!=null){
                            binding.cvUserRating.setVisibility(View.VISIBLE);
                            binding.providerName.setText(jobDetailModel.getProvider().getFirstName()+" "+jobDetailModel.getProvider().getLastName());
                            binding.providerRateDes.setText(jobDetailModel.getProvider_rating().getDescription());
                            binding.userRating.setRating(Float.parseFloat(jobDetailModel.getProvider_rating().getRatings()));
                            ImageViewBindingUtils.setProfileUrl(binding.uImage,jobDetailModel.getProvider().getImage());

                        }else{
                            binding.cvUserRating.setVisibility(View.GONE);
                        }

                        if (jobDetailModel.getUser_rating().getId()!=null){
                            binding.llRating.setVisibility(View.GONE);
                            binding.cvMyRating.setVisibility(View.VISIBLE);
                            binding.me.setText(sharedPref.getUserData().getFirstName()+" "+sharedPref.getUserData().getLastName()+"(Me)");
                            binding.myRateDes.setText(jobDetailModel.getUser_rating().getDescription());
                            binding.myRating.setRating(Float.parseFloat(jobDetailModel.getUser_rating().getRatings()));
                            ImageViewBindingUtils.setProfileUrl(binding.myImage,sharedPref.getUserData().getImage());

                        }else{
                            binding.llRating.setVisibility(View.VISIBLE);
                            binding.cvMyRating.setVisibility(View.GONE);
                        }

                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))){

                            Intent intent1 = LoginActivity.newIntent(RatingActivity.this);
                            startNewActivity(intent1, true, true);

                        }

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.ratingResponse.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(simpleApiResponseResource.message);
                        finish(true);

                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){
                    case R.id.btn_submit:

                        if (binding.rbRating.getRating()<=0){

                            vm.error.setValue(getResources().getString(R.string.please_add_your_rating));

                        }else if (binding.etDescription.getText().toString().isEmpty()){

                            vm.error.setValue(getResources().getString(R.string.please_write_review));

                        }else {

                        Log.d("Data : ", sharedPref.getUserData().getAuthKey() + "==" + jobDetailModel.getProvider().getId() + "=======" + jobDetailModel.getId() + "=====" + binding.rbRating.getRating() + "======" + binding.etDescription.getText().toString());

                        vm.changeJobStatus(vm.sharedPref.getUserData().getAuthKey(), String.valueOf(id), "0", "9","");

                    }

                        break;



                }
            }
        });
    }
}