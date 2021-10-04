package com.marius.valeyou.localMarketModel.ui.activity.createShopProfile;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.ApiUtils;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityCreateShopProfileBinding;
import com.marius.valeyou.databinding.HolderBusinessTimeBinding;
import com.marius.valeyou.databinding.HolderImageCloseBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.responseModel.BusinessTimeModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.SelectGalleryImageAdapter;
import com.marius.valeyou.localMarketModel.ui.activity.allCategory.AllCategoryActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateShopProfileActivity extends AppActivity<ActivityCreateShopProfileBinding, CreateShopProfileActivityVM> {
    public static final String TAG = "CreateShopProfile";
    @Inject
    SharedPref sharedPref;
    private Uri resultUri = null;
    private List<MoreBean> imageList = new ArrayList<>();
    int CategoryRequestCode = 14;
    String lat = "";
    String lng = "";
    boolean isEdit = false;
    String shopId = "";
    String removeImgId = "";
    String business_hours = "";
    List<String> imgIdList = new ArrayList<>();
    List<PostImage> imgLinkList = new ArrayList<>();
    List<BusinessTimeModel> businessTimeList = new ArrayList<>();
    TimePickerDialog mTimePicker;
    BusinessTimeModel selectTimeBean;
    String timeType = "";
    public MarketCategoryModel categoryListBeans;
    MarketShopProfile marketShopProfile;

    int selected_position = 0;

    public static Intent newIntent(Activity activity, MarketCategoryModel categoryBeans, ShopProfile profile) {
        Intent intent = new Intent(activity, CreateShopProfileActivity.class);
        intent.putExtra("categoryListBeans", new Gson().toJson(categoryBeans));
        intent.putExtra("profile", new Gson().toJson(profile));
        return intent;
    }

    public static Intent newIntent(Activity activity, MarketCategoryModel categoryBeans, MarketShopProfile marketShopProfile) {
        Intent intent = new Intent(activity, CreateShopProfileActivity.class);
        intent.putExtra(Constants.IS_UPDATE, true);
        intent.putExtra("categoryListBeans", new Gson().toJson(categoryBeans));
        intent.putExtra("marketShopProfile", new Gson().toJson(marketShopProfile));
        intent.putExtra("profile", new Gson().toJson(new ShopProfile()));
        return intent;
    }

    public static Intent newIntent(FragmentActivity requireActivity, MarketCategoryModel categoryListBeans) {
        Intent intent = new Intent(requireActivity, CreateShopProfileActivity.class);
        intent.putExtra(Constants.IS_CREATE, true);
        intent.putExtra("categoryListBeans", new Gson().toJson(categoryListBeans));
        return intent;
    }


    @Override
    protected BindingActivity<CreateShopProfileActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_create_shop_profile, CreateShopProfileActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(final CreateShopProfileActivityVM vm) {
        categoryListBeans = new Gson().fromJson(this.getIntent().getStringExtra("categoryListBeans"), MarketCategoryModel.class);

        imgIdList.clear();
        setBusinessTimeList();

        // set location and phone from create ad page
        ShopProfile profile = new Gson().fromJson(this.getIntent().getStringExtra("profile"), ShopProfile.class);
        if (profile != null) {
            binding.setBean(profile);
            lat = profile.getLatitude();
            lng = profile.getLongitude();
        }

        ///////////////////////////////////////////

        if (this.getIntent().getBooleanExtra(Constants.IS_UPDATE, false)) {
            marketShopProfile = new Gson().fromJson(this.getIntent().getStringExtra("marketShopProfile"), MarketShopProfile.class);
            isEdit = true;
            binding.txtTitle.setText(this.getResources().getString(R.string.update));
            binding.btnCreate.setText(this.getResources().getString(R.string.update));
            for (int i = 0; i < marketShopProfile.getData().size(); i++) {

                if (sharedPref.get(Constants.SHOP_NAME_SELECTED, "").equalsIgnoreCase(marketShopProfile.getData().get(i).getCompanyName())) {
                    selected_position = i;
                }

            }

            binding.setBean(marketShopProfile.getData().get(selected_position));
            shopId = String.valueOf(marketShopProfile.getData().get(selected_position).getId());
            binding.rvImage.setVisibility(View.VISIBLE);

            lat = marketShopProfile.getData().get(selected_position).getLatitude();
            lng = marketShopProfile.getData().get(selected_position).getLongitude();

            imgLinkList.clear();

            imgLinkList.addAll(marketShopProfile.getData().get(selected_position).getMarketShopImages());
            businessTimeList.clear();
            businessTimeList.addAll(marketShopProfile.getData().get(selected_position).getBusinessHours());
            setLinkRecyclerView();
        }

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                onBackPressed();
            }
        });


        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.txtCategory:
                    Intent intent = AllCategoryActivity.newIntent(this, categoryListBeans);
                    startActivityForResult(intent, CategoryRequestCode);
                    break;

                case R.id.btnCreate:
                    if (checkValidation()) {
                        if (isEdit && imgIdList.size() > 0) {
                            viewModel.deleteShopImage(removeImgId);
                        } else {
                            PostJob();
                        }
                    } else {
                        vm.error.setValue("Please fill all fields.");
                    }
                    break;

            }
        });

        vm.shopProfileCreateEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        Intent intent = new Intent();
                        intent.putExtra(Constants.HIT_API, true);
                        setResult(RESULT_OK, intent);
                        finish();
                         break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(CreateShopProfileActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        vm.deletePostImageEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (imgIdList.size() > 1) {
                            imgIdList.remove(removeImgId);
                            removeImgId = imgIdList.get(0);
                            viewModel.deleteShopImage(removeImgId);
//                            imageLinkAdapter.setList(marketActivity.marketPostModel.getPostImages());
                        } else {
                            PostJob();
                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(CreateShopProfileActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        vm.marketShopProfileEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketShopProfile>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketShopProfile> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (resource.data.getData() != null) {
                            if (resource.data.getData().get(0).getCompanyName() != null) {
                                marketShopProfile = resource.data;
                                binding.txtTitle.setText(CreateShopProfileActivity.this.getResources().getString(R.string.update_ad));
                                isEdit = true;
                                shopId = String.valueOf(resource.data.getData().get(0).getId());

                                imgLinkList.clear();

                                imgLinkList.addAll(resource.data.getData().get(0).getMarketShopImages());

                                setLinkRecyclerView();
                            }
                        } else {
                            sharedPref.put(Constants.SHOP_NAME, "");
                            sharedPref.put(Constants.SHOP_ADDRESS, "");
                        }

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(CreateShopProfileActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        binding.etAddress.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                binding.etAddress.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails placeDetails) {

                        lat = String.valueOf(placeDetails.geometry.location.lat);
                        lng = String.valueOf(placeDetails.geometry.location.lng);

                        Log.d("CreateAdFragment", "Location: " + placeDetails.formatted_address
                                + ", lat: " + lat + ", lng: " + lng);

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        });

        setRecyclerView();
        setBusinessTimeRecyclerView();
        timePicker();
    }


    private void setLinkRecyclerView() {
        binding.rvImage.setVisibility(View.VISIBLE);
        binding.rvImage.setLayoutManager(new LinearLayoutManager(CreateShopProfileActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvImage.setAdapter(imageLinkAdapter);
        imageLinkAdapter.setList(imgLinkList);
    }


    SimpleRecyclerViewAdapter<PostImage, HolderImageCloseBinding> imageLinkAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_image_close, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<PostImage>() {
                @Override
                public void onItemClick(View v, PostImage bean) {
                    if (v.getId() == R.id.imgCancel) {
                        imgIdList.add(String.valueOf(bean.getId()));
                        imgLinkList.remove(bean);
                        imageLinkAdapter.setList(imgLinkList);
                        removeImgId = imgIdList.get(0);
                    }
                }
            });


    private void setBusinessTimeRecyclerView() {
        binding.rvBusinessTime.setLayoutManager(new LinearLayoutManager(CreateShopProfileActivity.this));
        binding.rvBusinessTime.setAdapter(adapterBusinessTime);
        adapterBusinessTime.setList(businessTimeList);
    }


    SimpleRecyclerViewAdapter<BusinessTimeModel, HolderBusinessTimeBinding> adapterBusinessTime = new SimpleRecyclerViewAdapter(R.layout.holder_business_time, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<BusinessTimeModel>() {
                @Override
                public void onItemClick(View v, BusinessTimeModel bean) {
                    if (v.getId() == R.id.txtStartTime) {
                        selectTimeBean = bean;
                        timeType = "start";
                        mTimePicker.show();
                    } else if (v.getId() == R.id.txtEndTime) {
                        selectTimeBean = bean;
                        timeType = "end";
                        mTimePicker.show();
                    }
                }
            });

    private void timePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        mTimePicker = new TimePickerDialog(CreateShopProfileActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String time = selectedHour + ":" + selectedMinute;

                SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");
                Date date = null;
                try {
                    date = fmt.parse(time);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");
                String formattedTime = fmtOut.format(date);


                Log.d(TAG, "onTimeSet: " + selectedHour + ":" + selectedMinute);
                Log.d(TAG, "onTimeSet: " + formattedTime);

                for (int i = 0; i < businessTimeList.size(); i++) {
                    if (businessTimeList.get(i) == selectTimeBean) {
                        if (timeType.equalsIgnoreCase("start")) {
                            long minuts = totalMinute(formattedTime, businessTimeList.get(i).getEnd_time());
                            if (minuts < 0) {
                                businessTimeList.get(i).setStart_time(formattedTime);
                            } else {
                                viewModel.error.setValue("You can't select after time.");
                            }

                        } else if (timeType.equalsIgnoreCase("end")) {
                            long minuts = totalMinute(businessTimeList.get(i).getStart_time(), formattedTime);
                            if (minuts < 0) {
                                businessTimeList.get(i).setEnd_time(formattedTime);
                            } else {
                                viewModel.error.setValue("You can't select before time.");
                            }

                        }
                        adapterBusinessTime.notifyItemChanged(i);
                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

    }

    private long totalMinute(String firstTime, String secondTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(firstTime);
            date2 = format.parse(secondTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = date1.getTime() - date2.getTime();
        long diffMinutes = diff / (60 * 1000);
        return diffMinutes;
    }


    private void setBusinessTimeList() {
        businessTimeList.clear();
        businessTimeList.add(new BusinessTimeModel("Mon", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Tue", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Wed", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Thu", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Fri", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Sat", "09:00", "18:00"));
        businessTimeList.add(new BusinessTimeModel("Sun", "09:00", "18:00"));

    }

    private void setRecyclerView() {
        binding.rvAddImage.setLayoutManager(new GridLayoutManager(CreateShopProfileActivity.this, 4));
        binding.rvAddImage.setAdapter(imageAdapter);
        imageList.clear();
        imageList.add(new MoreBean(0, "", 1));
        imageAdapter.setList(imageList);
    }

    SelectGalleryImageAdapter imageAdapter = new SelectGalleryImageAdapter(
            new SelectGalleryImageAdapter.SimpleCallback() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.imgCancel:
                            imageList.remove(bean);
                            imageAdapter.setList(imageList);
                            break;

                        case R.id.imgCamera:
                            if ((imgLinkList.size() + imageList.size()) < 4) {
                                CropImage.activity()
                                        .setGuidelines(CropImageView.Guidelines.ON)
                                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                                        .start(CreateShopProfileActivity.this);
                            } else {
                                viewModel.warn.setValue("maximum image are selected");
                            }
                            break;
                    }
                }
            });

    private boolean checkValidation() {

        if (imageList.size() <= 1 && !isEdit) {
            viewModel.error.setValue("Please add a image.");
            return false;
        }

        if (isEdit) {
            if ((imgLinkList.size() + imageList.size()) <= 1) {
                viewModel.error.setValue("Please add a image.");
                return false;
            }
        }

        if (binding.edtCompanyName.getText().toString().isEmpty()) {
//            viewModel.error.setValue(getResources().getString(R.string.please_add_job_title));
            binding.edtCompanyName.setError("");
            return false;
        }

        if (binding.edtRegistration.getText().toString().isEmpty()) {
            binding.edtRegistration.setError("");
            return false;
        }


        if (binding.edtShipping.getText().toString().isEmpty()) {
            binding.edtShipping.setError("");
            return false;
        }

        if (binding.etAddress.getText().toString().isEmpty() || lat.length() < 3) {
            binding.etAddress.setText("");
            binding.etAddress.setError("");
            return false;
        }

        if (binding.txtCategory.getText().toString().isEmpty()) {
            binding.txtCategory.setError("");
            return false;
        }

        if (binding.edtPrePhone.getText().toString().isEmpty()) {
             binding.edtPrePhone.setError("Please add Country code");
            return false;
        }
        if (binding.edtPhone.getText().toString().isEmpty()) {
            binding.edtPhone.setError("Please add  Phone number");
            return false;
        } if (binding.edtPhone.getText().toString().length()<9) {
            viewModel.error.setValue("Minimum 9 digits required");
            return false;
        }

        return true;

    }


    private void PostJob() {

        business_hours = new Gson().toJson(businessTimeList);

        Log.d(TAG, "PostJob_business_hours: " + business_hours);

        MultipartBody.Part[] imageBody = new MultipartBody.Part[imageList.size()];
        for (int i = 0; i < imageList.size() - 1; i++) {
            Uri imagePath = Uri.parse(imageList.get(i).getName());
            MultipartBody.Part image = ApiUtils.createMultipartBodySingle(new File(imagePath.getPath()), "image");
            imageBody[i] = image;
        }

        RequestBody shop_idBody = RequestBody.create(MediaType.parse("text/plain"), shopId);
        RequestBody company_nameBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtCompanyName.getText().toString());
        RequestBody register_numberBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtRegistration.getText().toString());
        RequestBody shippingBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtShipping.getText().toString());
        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), binding.etAddress.getText().toString());
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody longitudeBody = RequestBody.create(MediaType.parse("text/plain"), lng);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), binding.txtCategory.getText().toString());
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtPhone.getText().toString());
        RequestBody countryCodeBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtPrePhone.getText().toString());
        RequestBody business_hoursBody = RequestBody.create(MediaType.parse("text/plain"), business_hours);

        //company_name, register_number, category, shipping, business_hours, phone, address, latitude, longitude, image
        viewModel.shopCreateProfile(isEdit,
                shop_idBody, company_nameBody, register_numberBody, categoryBody, shippingBody, business_hoursBody, phoneBody, countryCodeBody, addressBody, latitudeBody, longitudeBody, imageBody);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CategoryRequestCode) {
                String categoryName = data.getStringExtra(Constants.CATEGORY_NAME);
                binding.txtCategory.setText(categoryName);

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                resultUri = result.getUri();
                imageList.remove(imageList.size() - 1);
                imageList.add(new MoreBean(1, String.valueOf(resultUri), 0));
                imageList.add(new MoreBean(0, "", 1));
                imageAdapter.setList(imageList);


            }
        }
    }
}
