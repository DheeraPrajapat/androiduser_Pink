package com.marius.valeyou.ui.fragment.more.profile.addidentity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityAddIdentityBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivityVM;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddIdentityActivity extends AppActivity<ActivityAddIdentityBinding,AddIdentityActivityVM> {

    int camera;
    File backfile;
    File frontfile;
    File selfiefile;
    String comeFrom;
    String selection;
    String id;
    int type = 0;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, AddIdentityActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<AddIdentityActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_add_identity, AddIdentityActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(AddIdentityActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.add_identity));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        AllowGalleryPermision();
        Intent intent = getIntent();
        if (intent != null) {
            comeFrom = intent.getStringExtra("comeFrom");
            if (comeFrom.equalsIgnoreCase("add")) {
                binding.header.tvTitle.setText(getResources().getString(R.string.add_identity));
            } else {
                binding.header.tvTitle.setText(getResources().getString(R.string.edit_identity));
                GetProfileBean.IdentityBean identityBean = intent.getParcelableExtra("identityBean");
                id = String.valueOf(identityBean.getId());

                type = identityBean.getType();

                ImageViewBindingUtils.setProviderImage(binding.imageFront,identityBean.getFrontImage());
                ImageViewBindingUtils.setProviderImage(binding.imageBack,identityBean.getBackImage());
                ImageViewBindingUtils.setProviderImage(binding.imageSelfie,identityBean.getSelfie());

                if (identityBean.getType()==0) {
                    binding.spinner.setSelection(0);
                }else{
                    binding.spinner.setSelection(1);
                }


            }
        }

        ArrayList<String> idType = new ArrayList<String>();
        idType.add("CPF");
        idType.add("Driving Lisence");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, idType);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setSelection(type);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selection = parent.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        vm.addIdentityBean.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        onBackPressed();
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")) {
                            Intent intent1 = LoginActivity.newIntent(AddIdentityActivity.this);
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



        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){

                    case R.id.cv_cancel:
                        finish(true);
                        break;

                    case R.id.tv_choose_front:
                        camera = 1;
                        AllowCameraPermision();

                        break;

                    case R.id.tv_choose_back:

                        camera = 2;

                        AllowCameraPermision();


                        break;

                    case R.id.tv_choose_selfie:

                        camera = 3;

                        AllowCameraPermision();


                        break;


                    case R.id.cv_save:


                        if (frontfile == null) {
                            vm.info.setValue(getResources().getString(R.string.please_choose_image));
                        } else if(backfile == null) {

                            vm.info.setValue(getResources().getString(R.string.please_choose_image));

                        } else if(selfiefile == null) {

                            vm.info.setValue(getResources().getString(R.string.please_choose_image));

                        }else {

                            RequestBody requestFrontFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), frontfile);

                            MultipartBody.Part frontbody =
                                    MultipartBody.Part.createFormData("frontImage", frontfile.getName(), requestFrontFile);


                            RequestBody requestBackFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), backfile);

                            MultipartBody.Part backbody =
                                    MultipartBody.Part.createFormData("backImage", backfile.getName(), requestBackFile);

                            RequestBody requestSelfieFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), selfiefile);

                            MultipartBody.Part selfiebody =
                                    MultipartBody.Part.createFormData("selfie", selfiefile.getName(), requestSelfieFile);

                            RequestBody type=null;

                            if (comeFrom.equalsIgnoreCase("add")) {
                                if (selection.equalsIgnoreCase("CPF")){
                                     type = RequestBody.create(MediaType.parse("text/plain"), "0");
                                }else if (selection.equalsIgnoreCase("Driving Lisence")){
                                     type = RequestBody.create(MediaType.parse("text/plain"), "1");
                                }
                                RequestBody   api_type = RequestBody.create(MediaType.parse("text/plain"), "0");

                                vm.addIdentity(vm.sharedPref.getUserData().getAuthKey(),type,api_type, frontbody, backbody, selfiebody);

                            } else if (comeFrom.equalsIgnoreCase("edit")) {
                                if (selection.equalsIgnoreCase("CPF")){
                                    type = RequestBody.create(MediaType.parse("text/plain"), "0");
                                }else if (selection.equalsIgnoreCase("Driving Lisence")){
                                    type = RequestBody.create(MediaType.parse("text/plain"), "1");
                                }
                                RequestBody   api_type = RequestBody.create(MediaType.parse("text/plain"), "1");
                                RequestBody   identity_id = RequestBody.create(MediaType.parse("text/plain"), id);

                                vm.editIdentity(vm.sharedPref.getUserData().getAuthKey(),identity_id,type,api_type,frontbody, backbody, selfiebody);

                            }
                        }

                        break;

                }
            }
        });

    }

    private void AllowGalleryPermision() {
        Permissions.check(this, Manifest.permission.READ_EXTERNAL_STORAGE, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
            }
        });
    }

    private void AllowCameraPermision() {
        Permissions.check(this, Manifest.permission.CAMERA, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                galaryCameraIntent();
            }
        });
    }

    private void galaryCameraIntent() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                if (camera == 1){
                    Uri frontUri = result.getUri();
                    binding.imageFront.setImageURI(frontUri);
                    String frontpathString = getRealPathFromURI(frontUri);
                    frontfile = new File(frontpathString);
                    binding.tvFrontfileName.setText("File Name : "+ frontfile.getName());
                }else if (camera == 2){
                    Uri backUri = result.getUri();
                    binding.imageBack.setImageURI(backUri);
                    String backpathstring = getRealPathFromURI(backUri);
                    backfile = new File(backpathstring);
                    binding.tvBackfileName.setText("File Name : "+backfile.getName());

                }
                else if (camera == 3){
                    Uri selfieUri = result.getUri();
                    binding.imageSelfie.setImageURI(selfieUri);
                    String Selfiepathstring = getRealPathFromURI(selfieUri);
                    selfiefile = new File(Selfiepathstring);
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
