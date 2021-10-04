package com.marius.valeyou.ui.fragment.myjob.jobdetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.BidsBean;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou.databinding.DialogDeleteJobBinding;
import com.marius.valeyou.databinding.FragmentJobDetailsBinding;
import com.marius.valeyou.databinding.HolderAcceptConfirmationBinding;
import com.marius.valeyou.databinding.HolderBiddesItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class JobDetailsFragment extends AppFragment<FragmentJobDetailsBinding, JobDetailsFragmentVM> {

    public static final String BEAN = "bean";
    public static final String TAG = "JobDetailsFragment";
    private MainActivity mainActivity;
    private String SUB_TAG = MyJobFragment.TAG;
    JobDetailsBean jobDetailsBean;
    private List<JobDetailsBean.BidsBean> bidList;
    private int type = 1;
    int jobId;
    int provider_id;

    public static Fragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        JobDetailsFragment fragment = new JobDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BindingFragment<JobDetailsFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_job_details, JobDetailsFragmentVM.class);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void subscribeToEvents(JobDetailsFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(getResources().getString(R.string.job_Detail));

        jobId = getArguments().getInt("id",0);
        viewModel.getJobDetails(jobId);



        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){
                    case R.id.cancelJobBtn:

                        vm.cancelJob(String.valueOf(jobId),"2");

                        break;
                    case R.id.deleteJobBtn:
                        dialogDeleteJob();
                        break;

                    case R.id.ll_provider_profile:

                        if (provider_id!=0){
                            Intent intent = ProviderProfileActivity.newIntent(getActivity(),provider_id,0,"","");
                            startNewActivity(intent);
                        }

                        break;

                    case R.id.image_Back:
                        mainActivity.backStepFragment();
                }
            }
        });

        vm.deleteJobEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(simpleApiResponseResource.message);
                        mainActivity.onBackPressed();
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });

        vm.cancelJobEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(getResources().getString(R.string.job_Cancelled));
                        vm.getJobDetails(jobId);
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });

        vm.jobDetailsEvent.observe(this, new SingleRequestEvent.RequestObserver<JobDetailsBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<JobDetailsBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        bidList = resource.data.getBids();
                        binding.setBean(resource.data);
                        jobDetailsBean = resource.data;
                        provider_id = jobDetailsBean.getProviderId();

                        if (resource.data.getOrderCategories().get(0).getCategory()!=null){

                            binding.txtcategory.setText(resource.data.getOrderCategories().get(0).getCategory().getName());

                        }
                        String time = resource.data.getStartTime();
                     String convertedTime = AppUtils.getDateTime(Long.parseLong(time));
                     binding.txtTime.setText(convertedTime);

                        if (bidList != null && bidList.size() > 0) {
                            binding.tvBids.setVisibility(View.GONE);
                            adapter.setList(bidList);
                        }

                        if(resource.data.getJobType() ==0){

                           if (resource.data.getIsPayment()!=0){
                               binding.rvBids.setVisibility(View.GONE);
                               binding.tvBids.setVisibility(View.GONE);
                               binding.cancelJobBtn.setVisibility(View.VISIBLE);
                               binding.llProviderProfile.setVisibility(View.VISIBLE);
                               binding.proText.setVisibility(View.VISIBLE);
                           }else{
                               binding.rvBids.setVisibility(View.VISIBLE);
                               binding.tvBids.setVisibility(View.GONE);
                               binding.llProviderProfile.setVisibility(View.GONE);
                               binding.proText.setVisibility(View.GONE);
                           }

                           if (jobDetailsBean.getStatus()==0){
                               binding.deleteJobBtn.setVisibility(View.VISIBLE);
                           }else if(resource.data.getStatus() == 2){

                               binding.cancelJobBtn.setVisibility(View.GONE);
                               binding.llProviderProfile.setVisibility(View.GONE);
                               binding.deleteJobBtn.setVisibility(View.VISIBLE);
                               binding.proText.setVisibility(View.GONE);

                           }else if (jobDetailsBean.getStatus() == 3){
                               binding.cancelJobBtn.setVisibility(View.GONE);
                               binding.llProviderProfile.setVisibility(View.VISIBLE);
                               binding.deleteJobBtn.setVisibility(View.GONE);
                               binding.proText.setVisibility(View.VISIBLE);
                           }else{
                               binding.deleteJobBtn.setVisibility(View.GONE);
                           }

                        }else if (resource.data.getJobType() == 1){

                            if (resource.data.getStatus()==1){

                                    binding.cancelJobBtn.setVisibility(View.VISIBLE);
                                    binding.deleteJobBtn.setVisibility(View.GONE);
                                    binding.llProviderProfile.setVisibility(View.VISIBLE);
                                   binding.proText.setVisibility(View.VISIBLE);
                            }else if(resource.data.getStatus() == 2){

                                binding.cancelJobBtn.setVisibility(View.GONE);
                                binding.llProviderProfile.setVisibility(View.GONE);
                                binding.deleteJobBtn.setVisibility(View.VISIBLE);
                                binding.proText.setVisibility(View.GONE);

                            }else{

                                binding.cancelJobBtn.setVisibility(View.GONE);
                                binding.llProviderProfile.setVisibility(View.GONE);
                                binding.deleteJobBtn.setVisibility(View.VISIBLE);
                                binding.proText.setVisibility(View.GONE);

                            }

                            for (int i=0;i<bidList.size();i++) {
                                if (bidList.get(i).getStatus() == 1) {

                                    binding.rvBids.setVisibility(View.GONE);
                                    binding.tvBids.setVisibility(View.VISIBLE);

                                }
                            }
                        }

                       dismissProgressDialog();

                        break;
                    case WARN:
                       dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))){
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });
        vm.acceptRejectEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                       showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (type == 1) {
                            if (jobDetailsBean.getJobType() == 0) {

                                mainActivity.addSubFragment(SUB_TAG, InvoiceFragment.newInstance(jobDetailsBean.getId()));

                            } else {

                                vm.success.setValue(resource.message);
                                mainActivity.onBackPressed();

                            }
                        } else if (type == 2){
                            vm.success.setValue(resource.message);
                            mainActivity.onBackPressed();
                        }

                        break;
                    case WARN:
                     dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                       dismissProgressDialog();
                       if (type==1){
                           vm.error.setValue(getResources().getString(R.string.the_previous_provider_with_another_job));
                       }else {
                           vm.error.setValue(resource.message);
                       }
                        break;
                }
            }
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvBids.setLayoutManager(layoutManager);
        binding.rvBids.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<JobDetailsBean.BidsBean, HolderBiddesItemsBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_biddes_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<JobDetailsBean.BidsBean>() {
                @Override
                public void onItemClick(View v, JobDetailsBean.BidsBean bidsBean) {
                    Map<String, Integer> data = new HashMap<>();
                    data.put("post_id", jobId);
                    data.put("provider_id", bidsBean.getProviderId());
                    switch (v != null ? v.getId() : 0) {
                        case R.id.cv_message:
                            Intent intent1 = ChatActivity.newIntent(getActivity());
                            intent1.putExtra("comeFrom","bids");
                            intent1.putExtra("id",bidsBean.getProviderId());
                            intent1.putExtra("image",bidsBean.getProviderImage());
                            intent1.putExtra("name",bidsBean.getProviderFirstName()+" "+bidsBean.getProviderLastName());
                            startNewActivity(intent1);
                            break;
                        case R.id.cv_accept:
                            type = 1;
                            data.put("type", type);
                            acceptBidConfirmation(data);

                            break;
                        case R.id.cv_decline:
                            type = 2;
                            data.put("type", type);
                            viewModel.acceptRejectBid(data);
                            break;
                    }
                }
            });

    private BaseCustomDialog<DialogDeleteJobBinding> dialogDelete;

    private void dialogDeleteJob() {
        dialogDelete = new BaseCustomDialog<>(getActivity(), R.layout.dialog_delete_job, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:

                            dialogDelete.dismiss();

                            viewModel.deleteJob(String.valueOf(jobId));

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



    private BaseCustomDialog<HolderAcceptConfirmationBinding> acceptBidConfirmationDialog;

    private void acceptBidConfirmation(Map<String, Integer> data) {
        acceptBidConfirmationDialog = new BaseCustomDialog<>(getActivity(), R.layout.holder_accept_confirmation, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            acceptBidConfirmationDialog.dismiss();
                            viewModel.acceptRejectBid(data);
                            break;
                        case R.id.btn_cancel:
                            acceptBidConfirmationDialog.dismiss();
                            break;

                        case R.id.iv_cancel:
                            acceptBidConfirmationDialog.dismiss();
                            break;
                    }
                }
            }
        });
        acceptBidConfirmationDialog.getWindow().setBackgroundDrawableResource(R.color.white_trans);
        acceptBidConfirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        acceptBidConfirmationDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        acceptBidConfirmationDialog.show();
    }

}
