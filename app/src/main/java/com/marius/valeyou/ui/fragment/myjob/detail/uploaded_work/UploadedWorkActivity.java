package com.marius.valeyou.ui.fragment.myjob.detail.uploaded_work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityUploadedWorkBinding;
import com.marius.valeyou.databinding.HolderUploadedWorkBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.myjob.ViewImageActivity;
import com.marius.valeyou.util.Constants;

import java.util.List;

public class UploadedWorkActivity extends AppActivity<ActivityUploadedWorkBinding,UploadedWorkActivityVM> {


    String order_id;
    int job_status;
    public static Intent newIntent(Activity activity, int id) {
        Intent intent = new Intent(activity, UploadedWorkActivity.class);
        intent.putExtra("job_id",id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BaseActivity.BindingActivity<UploadedWorkActivityVM> getBindingActivity() {
        return new BaseActivity.BindingActivity<>(R.layout.activity_uploaded_work, UploadedWorkActivityVM.class);
    }


    @Override
    protected void subscribeToEvents(UploadedWorkActivityVM vm) {
        int job_id = getIntent().getIntExtra("job_id",0);
        vm.getJobDetaial(vm.sharedPref.getUserData().getAuthKey(), String.valueOf(job_id));

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
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
                        order_id = String.valueOf(resource.data.getId());

                        job_status = resource.data.getStatus();

                        List<JobDetailsBean.ProviderWorkImagesBean> imagesList = resource.data.getProvider_work_images();

                        if (imagesList.size()>0){
                            adapterWorkImages.setList(imagesList);
                            binding.noData.setVisibility(View.GONE);

                        }else{
                            binding.noData.setVisibility(View.VISIBLE);
                        }

                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(UploadedWorkActivity.this);
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


        setUpAdapter();
    }


    SimpleRecyclerViewAdapter<JobDetailsBean.ProviderWorkImagesBean, HolderUploadedWorkBinding> adapterWorkImages =
            new SimpleRecyclerViewAdapter(R.layout.holder_uploaded_work, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<JobDetailsBean.ProviderWorkImagesBean>() {
                @Override
                public void onItemClick(View v, JobDetailsBean.ProviderWorkImagesBean o) {

                    switch (v.getId()){
                        case R.id.images:
                            Intent intent = new Intent(UploadedWorkActivity.this, ViewImageActivity.class);
                            intent.putExtra("url", Constants.IMAGE_BASE_URL+o.getImage());
                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation(UploadedWorkActivity.this, v, "image");
                            startActivity(intent,options.toBundle());
                            break;
                    }

                }
            });

    private void setUpAdapter() {

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        binding.rvImages.setLayoutManager(layoutManager);
        binding.rvImages.setAdapter(adapterWorkImages);

    }

}