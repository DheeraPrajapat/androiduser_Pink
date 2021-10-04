package com.marius.valeyou.ui.fragment.myjob.detail.attachments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityAttachmentsBinding;
import com.marius.valeyou.databinding.HolderAttachmentsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import java.io.File;
import java.util.List;

public class AttachmentsActivity extends AppActivity<ActivityAttachmentsBinding,AttachmentsActivityVM> {


    String order_id;
    int job_status;
    public static Intent newIntent(Activity activity, int id) {
        Intent intent = new Intent(activity, AttachmentsActivity.class);
        intent.putExtra("job_id",id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BaseActivity.BindingActivity<AttachmentsActivityVM> getBindingActivity() {
        return new BaseActivity.BindingActivity<>(R.layout.activity_attachments, AttachmentsActivityVM.class);
    }
    @Override
    protected void subscribeToEvents(AttachmentsActivityVM vm) {
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
                            adapterattachments.setList(imagesList);
                            binding.noData.setVisibility(View.GONE);

                        }else{
                            binding.noData.setVisibility(View.VISIBLE);
                        }

                        break;
                    case ERROR:
                        binding.setCheck(false);

                        vm.error.setValue(resource.message);

                        if (resource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(AttachmentsActivity.this);
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

    SimpleRecyclerViewAdapter<JobDetailsBean.ProviderWorkImagesBean, HolderAttachmentsBinding> adapterattachments =
            new SimpleRecyclerViewAdapter(R.layout.holder_attachments, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<JobDetailsBean.ProviderWorkImagesBean>() {
                @Override
                public void onItemClick(View v, JobDetailsBean.ProviderWorkImagesBean o) {

                    switch (v.getId()){
                        case R.id.dwonload_attachment:
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(AttachmentsActivity.this);
                            builder1.setMessage("Are you sure you want to download this file?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            downloadFile(o.getImage());
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();


                            break;
                    }

                }
            });

    private void setUpAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvImages.setLayoutManager(layoutManager);
        binding.rvImages.setAdapter(adapterattachments);

    }

    private void downloadFile(String file){
        Permissions.check(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(AttachmentsActivity.this, "downloading...", Toast.LENGTH_SHORT).show();
                File direct =
                        new File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                .getAbsolutePath() + "/" + "valeyou/attachemtns");


                if (!direct.exists()) {
                    direct.mkdir();
                }

                DownloadManager dm = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(Constants.IMAGE_BASE_URL+file);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("attachment-"+System.currentTimeMillis())
                        .setMimeType("application/pdf")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                File.separator + "attachment" + File.separator + System.currentTimeMillis());

                dm.enqueue(request);
            }
        });
    }
}