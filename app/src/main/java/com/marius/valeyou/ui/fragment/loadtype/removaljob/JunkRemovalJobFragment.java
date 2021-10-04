package com.marius.valeyou.ui.fragment.loadtype.removaljob;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.google.gson.Gson;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.reqbean.RequestBean;
import com.marius.valeyou.data.beans.respbean.AddJobBean;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogCustomImageBinding;
import com.marius.valeyou.databinding.DialogSuccessJobSubmitBinding;
import com.marius.valeyou.databinding.FragmentJunkRemovalJobBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.categorytitle.CategoryTitleActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype.WhenTypeActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.where.WhereActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.misc.BackStackManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JunkRemovalJobFragment extends AppActivity<FragmentJunkRemovalJobBinding, JunkRemovalJobFragmentVM> {
    private static final int WHERE_INTENT = 1;
    private static final int WHEN_INTENT = 2;
    private static final String DATA_BEAN = "data_bean";
    private static final String CATEGORY_DATA = "category_data";
    private static final int TITLE_INTENT = 3;

    private boolean check_where =
            false;
    private boolean check_when = false;
    private boolean check_photo = false;
    private boolean check_description = false;

    private static final String TITLE = "title";
    private String category_gson = "";
    private String location = "";
    private String city = " ";
    private String state = " ";
    private String coountry = " ";
    private String zip_code = " ";
    private String apartment = " ";

     String stateId = "";
     String cityId = "";

    private long start_time_stamp;
    private long end_time_stamp;
    private long time_stamp;
    private String start_time;
    private String end_time;
    private String job_type = "";
    private String job_title = "";
    private String job_description = "";
    private String job_hour = "";
    private String price = "0 brl";
    private String category_type = "";
    private String sub_category = "";
    int provider_id;

    private Boolean negociable = false;
    private boolean teste = false;
     String street_name;
     String street_number;

    @Inject
    SharedPref sharedPref;

    private Uri resultUri = null;

    public static Intent newInstance(Activity activity, String json, String category_type,
                                     String sub_category, int provider_id,String jobType) {
        Intent intent = new Intent(activity, JunkRemovalJobFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(CATEGORY_DATA, json);
        intent.putExtra("category_type", category_type);
        intent.putExtra("sub_category", sub_category);
        intent.putExtra("provider_id", provider_id);
        intent.putExtra("jobType", jobType);
        return intent;
    }

    @Override
    protected BindingActivity<JunkRemovalJobFragmentVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.fragment_junk_removal_job, JunkRemovalJobFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(JunkRemovalJobFragmentVM vm) {
        configureSwitch();
        binding.header.tvTitle.setText(getResources().getString(R.string.add_job));
        category_gson = getIntent().getStringExtra(CATEGORY_DATA);
        category_type = getIntent().getStringExtra("category_type");
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        setSubcategory(getIntent().getStringExtra("sub_category"));
        job_type = getIntent().getStringExtra("jobType");
        provider_id = getIntent().getIntExtra("provider_id",0);

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });
        binding.priceSeekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                price = String.valueOf(minValue);
                if (!price.trim().equals(binding.etPrice.getText().toString().trim())){
                    if(!teste){
                        binding.etPrice.setText(String.valueOf(minValue));
                        price = String.valueOf(minValue);
                    }
                    teste = false;
                }

            }
        });
        binding.etPrice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    binding.priceSeekBar.setMinStartValue( Integer.parseInt( binding.etPrice.getText().toString())).apply();
                    binding.etPrice.setSelection(binding.etPrice.getText().length());
                    price = binding.etPrice.getText().toString();
                    teste = true;
                    binding.tvRange.setText("R$ " + binding.etPrice.getText().toString());
                } catch(NumberFormatException nfe) {

                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                Intent intent;
                switch (view != null ? view.getId() : 0) {
                    case R.id.rl_one:
                        intent = WhereActivity.newIntent(JunkRemovalJobFragment.this);
                        startActivityForResult(intent, WHERE_INTENT);
                        break;
                    case R.id.rl_two:
                        if (provider_id==0) {
                            intent = WhenTypeActivity.newIntent(JunkRemovalJobFragment.this);
                            intent.putExtra("comeFrom","normal");
                            startActivityForResult(intent, WHEN_INTENT);
                        }else{
                            intent = WhenTypeActivity.newIntent(JunkRemovalJobFragment.this);
                            intent.putExtra("comeFrom","direct");
                            intent.putExtra("provider_id",provider_id);
                            intent.putExtra("job_type",job_type);
                            startActivityForResult(intent, WHEN_INTENT);
                        }
                        break;
                    case R.id.rl_three:
                        showCustomDialog();
                        break;
                    case R.id.rl_five:
                        intent = CategoryTitleActivity.newIntent(JunkRemovalJobFragment.this);
                        startActivityForResult(intent, TITLE_INTENT);
                        break;
                    case R.id.btn_next:

                        showConfirmationDialog();


                        break;
                    case R.id.tv_change:
                        finish(true);
                        break;

                    case R.id.swt_negociation:

                        break;
                }
            }
        });

        vm.requestEvent.observe(this, new SingleRequestEvent.RequestObserver<AddJobBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<AddJobBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);

                        showDoneDialog();
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

    private void setSubcategory(String sub_cat) {
        /*String subcategory = "";
        Type listType = new TypeToken<List<RequestBean>>() {
        }.getType();
        List<RequestBean> beanList = new Gson().fromJson(category_gson, listType);
        for (int j = 0; j < beanList.size(); j++) {
            for (int k=0;k<beanList.get(j).getSubcategory().size();k++){
                subcategory = subcategory+","+beanList.get(j).getSubcategory().get(k).getId();
            }
        }*/
        binding.tvSubcategory.setText(sub_cat);
    }

    private void configureSwitch(){
        binding.swtNegociation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(binding.swtNegociation.isChecked()){
                    binding.viewSetPrice.setVisibility(View.GONE);
                    negociable = true;
                    price = "0";
                }
                else{
                    binding.viewSetPrice.setVisibility(View.VISIBLE);
                    negociable = false;
                    price = binding.tvRange.getText().toString();
                }
            }
        });
    }


    private void PostJob(){

        if (job_title.isEmpty()){
            viewModel.error.setValue(getResources().getString(R.string.please_add_job_title));
            return;
        }

        if (job_description.isEmpty()){
            viewModel.error.setValue(getResources().getString(R.string.please_add_job_description));
            return;
        }

        if (price.equalsIgnoreCase("0") && !negociable){
            viewModel.error.setValue(getResources().getString(R.string.please_set_a_valid_price));
            return;
        }

        //delete temprory data fields from sharepreferences
        sharedPref.delete("myLocation");
        sharedPref.delete("myCity");
        sharedPref.delete("mystate");
        sharedPref.delete("country");
        sharedPref.delete("myzipCode");
        sharedPref.delete("apartment");
        sharedPref.delete("title");
        sharedPref.delete("description");

        MultipartBody.Part body = null;
        if (resultUri!=null) {
            File file = new File(resultUri.getPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }

        RequestBody pro_id = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(provider_id));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),
                job_title);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),
                job_description);
        RequestBody estimationTime = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(start_time_stamp)); // start time
        RequestBody estimationPrice = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(end_time_stamp)); // end time
        RequestBody location1 = RequestBody.create(MediaType.parse("text/plain"),
                location);
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),
                sharedPref.get("lat", ""));
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),
                sharedPref.get("lng", ""));
        RequestBody state1 = RequestBody.create(MediaType.parse("text/plain"),
                state);
        RequestBody zipCode = RequestBody.create(MediaType.parse("text/plain"),
                zip_code);
        RequestBody city1 = RequestBody.create(MediaType.parse("text/plain"),
                city);
        RequestBody street = RequestBody.create(MediaType.parse("text/plain"),
                street_name);
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"),
                street_number);
        RequestBody appartment = RequestBody.create(MediaType.parse("text/plain"),
                apartment);
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(start_time_stamp));
        RequestBody time = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(time_stamp));
        RequestBody selected_data = RequestBody.create(MediaType.parse("text/plain"),
                category_gson);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"),
                job_type);
        RequestBody startPrice = RequestBody.create(MediaType.parse("text/plain"),
                "0");
        RequestBody endPrice = RequestBody.create(MediaType.parse("text/plain"),
                price);
        RequestBody cat_type = RequestBody.create(MediaType.parse("text/plain"),
                category_type);


        viewModel.createJob(sharedPref.getUserData().getAuthKey(), pro_id, title, description, estimationTime, estimationPrice,
                location1, latitude, longitude, state1, zipCode, city1, street, appartment, date, time, selected_data,
                cat_type, startPrice, endPrice, body, type,number);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WHERE_INTENT:
                if (resultCode == Activity.RESULT_OK) {
                    check_where = true;
                    checkAll();
                    binding.ivNextOne.setImageResource(R.drawable.ic_tick_icon);

                    location = data.getStringExtra("location");
                    city = data.getStringExtra("city");
                    state = data.getStringExtra("state");
                    stateId = data.getStringExtra("stateId");
                    cityId = data.getStringExtra("cityId");
                    coountry = data.getStringExtra("country");
                    zip_code = data.getStringExtra("zip_code");
                    apartment = data.getStringExtra("apartment");
                    street_name = data.getStringExtra("street");
                    street_number = data.getStringExtra("number");

                }
                break;
            case WHEN_INTENT:
                if (resultCode == Activity.RESULT_OK) {
                    check_when = true;
                    checkAll();
                    binding.ivNextTwo.setImageResource(R.drawable.ic_tick_icon);
                    category_type = data.getStringExtra("category_type");
                    start_time_stamp = data.getLongExtra("start", 0);
                    end_time_stamp = data.getLongExtra("end", 0);
                    time_stamp = data.getLongExtra("time", 0);
                    start_time = data.getStringExtra("start_time");
                    end_time = data.getStringExtra("end_time");


                }
                break;
            case TITLE_INTENT:
                if (resultCode == Activity.RESULT_OK) {

                    check_description = true;
                    checkAll();
                    binding.ivNextFive.setImageResource(R.drawable.ic_tick_icon);

                    job_title = data.getStringExtra("title");
                    job_description = data.getStringExtra("description");
                    //job_hour = data.getStringExtra("hour");
                }
                break;
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Glide.with(this).load(resultUri).into(dialogSenderInfo.getBinding().ivCapture);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private BaseCustomDialog<DialogCustomImageBinding> dialogSenderInfo;

    private void showCustomDialog() {
        dialogSenderInfo = new BaseCustomDialog<>(JunkRemovalJobFragment.this, R.layout.dialog_custom_image, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_next:
                            if (resultUri != null && !resultUri.toString().equalsIgnoreCase("")) {
                                check_photo = true;
                                checkAll();
                                binding.ivNextThree.setImageResource(R.drawable.ic_tick_icon);
                            }
                            dialogSenderInfo.dismiss();
                            break;
                        case R.id.iv_image:
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                                    .start(JunkRemovalJobFragment.this);
                            break;
                    }
                }
            }
        });
        dialogSenderInfo.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogSenderInfo.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSenderInfo.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSenderInfo.show();
    }

    private void checkAll() {
        if (check_where && check_when && check_description) {
            binding.btnNext.setEnabled(true);
            binding.btnNext.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private BaseCustomDialog<DialogSuccessJobSubmitBinding> dialogSuccess;

    private void showDoneDialog() {
        dialogSuccess = new BaseCustomDialog<>(JunkRemovalJobFragment.this, R.layout.dialog_success_job_submit, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            BackStackManager.getInstance(JunkRemovalJobFragment.this).clear();
                            Intent intent = MainActivity.newIntent(JunkRemovalJobFragment.this,"jobs");
                            startNewActivity(intent);
                            break;
                        case R.id.iv_cancel:
                            dialogSuccess.dismiss();
                            BackStackManager.getInstance(JunkRemovalJobFragment.this).clear();
                            Intent intent1 = MainActivity.newIntent(JunkRemovalJobFragment.this,"jobs");
                            startNewActivity(intent1);
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }
    private BaseCustomDialog<DialogSuccessJobSubmitBinding> dialogConfirmation;
    private void showConfirmationDialog() {
        dialogConfirmation = new BaseCustomDialog<>(JunkRemovalJobFragment.this, R.layout.holder_post_confirmation_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogConfirmation.dismiss();
                            PostJob();


                            break;
                        case R.id.iv_cancel:
                            dialogConfirmation.dismiss();
                            break;
                    }
                }
            }
        });
        dialogConfirmation.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogConfirmation.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogConfirmation.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogConfirmation.show();
    }

}
