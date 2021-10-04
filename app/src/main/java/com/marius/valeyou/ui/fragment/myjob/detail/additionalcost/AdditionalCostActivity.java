package com.marius.valeyou.ui.fragment.myjob.detail.additionalcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityAdditionalCostBinding;
import com.marius.valeyou.databinding.HolderCategoryItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.products.junkremove.JunkRemovalFragment;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

import java.util.ArrayList;
import java.util.List;

public class AdditionalCostActivity extends AppActivity<ActivityAdditionalCostBinding,AdditionalCostActivityVM> {


    int job_id;
    JobDetailsBean model;

    public static Intent newIntent(Activity activity, int job_id) {
        Intent intent = new Intent(activity, AdditionalCostActivity.class);
        intent.putExtra("id",job_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<AdditionalCostActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_additional_cost, AdditionalCostActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(AdditionalCostActivityVM vm) {
        job_id = getIntent().getIntExtra("id",0);
        binding.header.tvTitle.setText("Additional Cost");
        vm.getJobDetaial(sharedPref.getUserData().getAuthKey(),job_id);
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {

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

                        binding.setVariable(BR.bean, resource.data);

                        model = resource.data;
                        
                        if (model.getAdditional_works().size()>0) {

                            adapter.setList(model.getAdditional_works());

                        }

                        break;
                    case ERROR:
                      dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(AdditionalCostActivity.this);
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

        setRecyclerView();

    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdditionalCostActivity.this);
        binding.rvWork.setLayoutManager(layoutManager);
        binding.rvWork.setAdapter(adapter);
    }


    SimpleRecyclerViewAdapter<JobDetailsBean.AdditionalWorksBean, HolderCategoryItemsBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_additional_work_list, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<JobDetailsBean.AdditionalWorksBean>() {
                @Override
                public void onItemClick(View v, JobDetailsBean.AdditionalWorksBean moreBean) {


                }
            });

}