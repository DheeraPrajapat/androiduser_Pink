package com.marius.valeyou.ui.fragment.myjob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentMyJobBinding;
import com.marius.valeyou.databinding.HolderUpcomingJobBinding;
import com.marius.valeyou.di.base.adapter.PagerAdapter;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.RatingActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.ui.fragment.myjob.upcoming.UpComingJobFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;

public class MyJobFragment extends AppFragment<FragmentMyJobBinding, MyJobFragmentVM> {

    public static final String TAG = "MyJobFragment";
    private MainActivity mainActivity;

    @Inject
    SharedPref sharedPref;
    private int page = 1;
    private int type = 0;
    private List<GetAllJobBean> getAllJobBeans;

    public static Fragment newInstance() {
        return new MyJobFragment();
    }

    @Override
    protected BindingFragment<MyJobFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_job, MyJobFragmentVM.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllJobList("", "", "0");
    }

    @Override
    protected void subscribeToEvents(MyJobFragmentVM vm) {

        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.hideBackButton();
        mainActivity.setHeader(getResources().getString(R.string.my_jobs));

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {

                    case R.id.ll_one:
                        binding.vpJob.setCurrentItem(0);
                        break;

                    case R.id.ll_two:
                        binding.vpJob.setCurrentItem(1);
                        break;

                    case R.id.fab_add_jobb:
                        //mainActivity.addSubFragment(TAG, ProductFragment.newInstance());
                        Intent intent = SelectCategoryActivity.newIntent(getActivity(), 0, "addjob");
                        startNewActivity(intent);

                        break;

                    case R.id.iv_filter:
                        binding.spinner.performClick();
                        break;

                    case R.id.notificationIcon:
                        Intent notificationIntent = NotificationActivity.newIntent(getActivity());
                        startNewActivity(notificationIntent);
                        break;
                }
            }
        });

        vm.getCount();
        vm.notificationbadgeEventRequest.observe(this, new SingleRequestEvent.RequestObserver<NotificationBadgeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<NotificationBadgeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        mainActivity.badgeCount(resource.data.getCount());
                        if (resource.data.getNotification() > 0) {
                            binding.notificationCount.setVisibility(View.VISIBLE);

                            if (resource.data.getNotification() > 9) {

                                binding.notificationCount.setText("9+");

                            } else {

                                binding.notificationCount.setText(resource.data.getNotification() + "");

                            }

                        } else {
                            binding.notificationCount.setVisibility(View.GONE);
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:

                        vm.error.setValue(resource.message);

                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {

                            sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });

        vm.text.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                if (value.equalsIgnoreCase(getResources().getString(R.string.all_jobs))) {
                    type = 0;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.upcomming))) {
                    type = 1;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.cancelled))) {
                    type = 2;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.ongoing))) {
                    type = 7;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.completed))) {
                    type = 8;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.closed))) {
                    type = 9;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.cheepest))) {
                    type = 19;
                }
                if (value.equalsIgnoreCase(getResources().getString(R.string.expensive))) {
                    type = 20;
                }
                viewModel.getAllJobList("", "", String.valueOf(type));
            }
        });

        vm.jobListEvent.observe(this, new SingleRequestEvent.RequestObserver<List<GetAllJobBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<GetAllJobBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.noData.setVisibility(View.GONE);
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        binding.setLoading(false);
                        getAllJobBeans = resource.data;
                        adapter.setList(getAllJobBeans);


                        if (getAllJobBeans.size() <= 0) {

                            binding.noData.setVisibility(View.VISIBLE);

                        } else {

                            binding.noData.setVisibility(View.GONE);

                        }

                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);

                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {

                            sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }

                        break;
                }
            }
        });
        setViewPager();
        setRecyclerView();

    }

    private List<Fragment> fragmentList;

    private void setViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(UpComingJobFragment.newInstance());
        fragmentList.add(UpComingJobFragment.newInstance());
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragmentList);
        binding.vpJob.setAdapter(pagerAdapter);
        binding.vpJob.setCurrentItem(0);
        binding.vpJob.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.setCheck(false);
                        break;
                    case 1:
                        binding.setCheck(true);
                        break;
                }
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvUpcoming.setLayoutManager(layoutManager);
        binding.rvUpcoming.setAdapter(adapter);
        if (getAllJobBeans != null)
            adapter.setList(getAllJobBeans);

    }

    SimpleRecyclerViewAdapter<GetAllJobBean, HolderUpcomingJobBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_upcoming_job, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<GetAllJobBean>() {
                @Override
                public void onItemClick(View v, GetAllJobBean bean) {

                    switch (v.getId()){

                        case R.id.cv_items:

                            if (bean.getJobType()==0){

                                if (bean.getStatus() == 0){

                                    mainActivity.addSubFragment(TAG, JobDetailsFragment.newInstance(bean.getId()));

                                }else if (bean.getStatus()==1){
                                    if (bean.getPaymentStatus()==0){

                                        mainActivity.addSubFragment(TAG, InvoiceFragment.newInstance(bean.getId()));

                                    }else if (bean.getPaymentStatus()==1){

                                        Intent intent = JobDetailActivity.newIntent(getActivity(),bean.getId());
                                        startNewActivity(intent);

                                    }
                                } else if (bean.getStatus()==9){

                                    Intent intent = RatingActivity.newIntent(getActivity());
                                    intent.putExtra("id", bean.getId() + "");
                                    startNewActivity(intent);

                                }else{

                                    Intent intent = JobDetailActivity.newIntent(getActivity(),bean.getId());
                                    startNewActivity(intent);

                                }



                            }else if (bean.getJobType()==1){

                                if (bean.getStatus()==0){

                                    mainActivity.addSubFragment(TAG, JobDetailsFragment.newInstance(bean.getId()));

                                }else  if (bean.getStatus()==8){

                                    if(bean.getIsPay()==0) {

                                        mainActivity.addSubFragment(TAG, InvoiceFragment.newInstance(bean.getId()));

                                    }else {

                                        Intent intent = JobDetailActivity.newIntent(getActivity(), bean.getId());
                                        startNewActivity(intent);

                                    }

                                }else if (bean.getStatus()==10){
                                    if(bean.getIsPay()==0) {

                                        mainActivity.addSubFragment(TAG, InvoiceFragment.newInstance(bean.getId()));

                                    }else {

                                        Intent intent = JobDetailActivity.newIntent(getActivity(), bean.getId());
                                        startNewActivity(intent);

                                    }


                                }else {

                                    Intent intent = JobDetailActivity.newIntent(getActivity(),bean.getId());
                                    startNewActivity(intent);

                                }

                            }


                            break;

                        case R.id.viewReason:
                            if (bean.getReason()!=null){
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("*** Reason ***");
                                alertDialog.setMessage(bean.getReason().getUser_data().getFirstName()+" "+bean.getReason().getUser_data().getLastName()+"\n"+bean.getReason().getReason());
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }else{

                                viewModel.error.setValue("No Reason Available");

                            }

                            break;

                    }



                }
            });

}
