package com.marius.valeyou.ui.fragment.more.profile.editprofile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityEditProfileBinding;
import com.marius.valeyou.databinding.HolderCitiesBinding;
import com.marius.valeyou.databinding.HolderStatesBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.LocationActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.util.DateInputMask;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppActivity<ActivityEditProfileBinding, EditProfileActivityVM> {

    private static final String USER_BEAN = "user_bean";
    private SignupData signupData;
    public static final int RESULT_GALLERY = 0;
    public static final int RESULT_ADDRESS = 1;
    Double lat;
    Double lng;

    List<StatesModel> statesModelsList;
    String state_id= "";
    String city_id = "";
    String cityName="";
    List<CitiesModel> citiesModelList;

    Calendar today = Calendar.getInstance();
    boolean isFirstTimeDateSelection = true;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, EditProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<EditProfileActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_edit_profile, EditProfileActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(EditProfileActivityVM vm) {
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        binding.header.tvTitle.setText(getResources().getString(R.string.edit_profile));
        binding.header.setCheck(true);
        binding.header.tvTwo.setText(getResources().getString(R.string.save));
        signupData = vm.sharedPref.getUserData();
        binding.setBean(signupData);


        String dateOfBirth = vm.sharedPref.getUserData().getDob();


        if (dateOfBirth!=null) {

            if (!dateOfBirth.equalsIgnoreCase("")) {
                String[] separated = dateOfBirth.split("/");
                String day = separated[0];
                String month = separated[1];
                String year = separated[2];

                binding.etday.setText(day);
                binding.etMonth.setText(month);
                binding.etYear.setText(year);

            }

        }

        List<String> daysList = new ArrayList<>();
        daysList.add(0,"Day");
        for (int i = 1;i<=31;i++){
            daysList.add(""+i);
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, daysList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.etDate.setAdapter(adp1);

        binding.etDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long id) {
                String day = adapterView.getAdapter().getItem(position).toString();
                if (isFirstTimeDateSelection) {
                    isFirstTimeDateSelection = true;
                }else{
                    binding.etday.setText(day);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        if (!signupData.getImage().isEmpty()){
            ImageViewBindingUtils.setProviderImage(binding.ivProfile,signupData.getImage());
        }
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {

                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.loading);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(resource.message);
                        Intent intent = ProfileActivity.newIntent(EditProfileActivity.this);
                        startNewActivity(intent,true);
                        finish(true);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {


                    case R.id.et_state:

                      /*  if(binding.rvStates.getVisibility() == View.VISIBLE){
                            binding.rvStates.setVisibility(View.GONE);
                        }else{
                            binding.rvStates.setVisibility(View.VISIBLE);
                        }*/

                        break;

                    case R.id.et_city:
                       /* if(binding.rvCities.getVisibility() == View.VISIBLE){
                            binding.rvCities.setVisibility(View.GONE);
                        }else{
                            binding.rvCities.setVisibility(View.VISIBLE);
                        }*/
                        break;

                    case R.id.iv_location:
                        AllowLocationPermision();
                        break;

                    case R.id.etday:
                        isFirstTimeDateSelection = false;
                        binding.etDate.performClick();
                        break;

                    case R.id.etMonth :
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(EditProfileActivity.this,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        binding.etMonth.setText((selectedMonth + 1) + "");
                                    }
                                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
                        );
                        builder.showMonthOnly().build().show();
                        break;

                    case R.id.etYear :
                        MonthPickerDialog.Builder builder1 = new MonthPickerDialog.Builder(EditProfileActivity.this,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        binding.etYear.setText(selectedYear + "");
                                    }
                                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
                        );
                        builder1.setMaxYear(2021).showYearOnly().build().show();
                        break;



                    case R.id.cv_image:
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .setCropShape(CropImageView.CropShape.RECTANGLE)
                                .start(EditProfileActivity.this);
                        break;

                    case R.id.tv_two:

                        MultipartBody.Part body = null;

                        if (resultUri != null) {
                            File file = new File(resultUri.getPath());
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                        }

                        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etFirstName.getText().toString());
                        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etLastName.getText().toString());
                        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etEmail.getText().toString());
                        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etPhone.getText().toString());
                        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"),
                                "91");
                        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etAbout.getText().toString());
                        RequestBody city = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etCity.getText().toString());
                        RequestBody state = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etState.getText().toString());
                        RequestBody location = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etAddress.getText().toString());
                        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),
                                String.valueOf(lat));
                        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),
                                String.valueOf(lng));

                        String dob = binding.etday.getText().toString()+"/"+binding.etMonth.getText().toString()+"/"+binding.etYear.getText().toString();


                        RequestBody age = RequestBody.create(MediaType.parse("text/plain"),
                            dob);
                        Map<String, RequestBody> bodyMap = new HashMap<>();
                        bodyMap.put("first_name", first_name);
                        bodyMap.put("last_name", last_name);
                        bodyMap.put("email", email);
                        bodyMap.put("phone", phone);
                        bodyMap.put("country_code", country_code);
                        bodyMap.put("description", description);
                        bodyMap.put("city", city);
                        bodyMap.put("state", state);
                        bodyMap.put("location", location);
                        bodyMap.put("latitude", latitude);
                        bodyMap.put("longitude", longitude);
                        bodyMap.put("dob", age);
                        vm.editProfile(bodyMap, body);
                        // done
                        break;
                }
            }
        });





        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
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

       // vm.getStates();
        vm.statesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<StatesModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<StatesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        statesModelsList = resource.data;
                        if (statesModelsList.size()>0){
                            adapter.setList(statesModelsList);
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        vm.citiesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<CitiesModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CitiesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:

                        citiesModelList = resource.data;
                        if (citiesModelList.size()>0) {
                            cityadapter.setList(citiesModelList);
                        }

                       /* for (int i = 0;i<citiesModelList.size();i++) {
                            if (citiesModelList.get(i).getName().contains(cityName)) {
                                binding.etCity.setText(citiesModelList.get(i).getName());
                                city_id = String.valueOf(citiesModelList.get(i).getId());
                            }
                        }*/

                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
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

                        lat = placeDetails.geometry.location.lat;
                        lng = placeDetails.geometry.location.lng;

                        Log.d("test", "details " + placeDetails);
                        for (AddressComponent component : placeDetails.address_components) {
                            for (AddressComponentType type : component.types) {
                                switch (type) {
                                    case STREET_NUMBER:
                                        break;
                                    case ROUTE:
                                        break;
                                    case NEIGHBORHOOD:
                                        break;
                                    case SUBLOCALITY_LEVEL_1:
                                        break;
                                    case SUBLOCALITY:
                                        break;
                                    case LOCALITY:
                                        binding.etCity.setText(component.long_name);
                                        cityName = component.long_name;
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_1:
                                        binding.etState.setText(component.long_name);
                                       /* for (int i = 0;i<statesModelsList.size();i++){
                                            if (statesModelsList.get(i).getName().contains(component.long_name)){
                                                binding.etState.setText(statesModelsList.get(i).getName());
                                                state_id = String.valueOf(statesModelsList.get(i).getId());
                                                viewModel.getCities(statesModelsList.get(i).getId());
                                            }
                                        }*/


                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_2:
                                        break;
                                    case COUNTRY:
                                        break;
                                    case POSTAL_CODE:
                                        break;
                                    case POLITICAL:
                                        break;
                                }
                            }
                        }

                        if (cityName.isEmpty()){
                            binding.etCity.setText("");
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        });



       // setStatesAdapter();
        //setCitiesAdapter();


    }

    private void AllowLocationPermision(){
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {

                Intent intent = new Intent(EditProfileActivity.this, LocationActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }

    private Uri resultUri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == 1) {

                if (data!=null) {
                    String result = data.getStringExtra("address");
                    binding.etAddress.setText(result);
                    String city = data.getStringExtra("city");
                    String state = data.getStringExtra("state");
                    String zipcode = data.getStringExtra("zipcode");
                    lat = data.getDoubleExtra("latitude",0);
                    lng = data.getDoubleExtra("longtitude",0);
                    binding.etCity.setText(city);
                   /* for (int i = 0;i<statesModelsList.size();i++){
                        if (state.contains(statesModelsList.get(i).getName())){
                            binding.etState.setText(statesModelsList.get(i).getName());
                            state_id = String.valueOf(statesModelsList.get(i).getId());
                            viewModel.getCities(statesModelsList.get(i).getId());
                        }
                    }*/
                }

            }else {

                if (null != data) {
                    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if (resultCode == RESULT_OK) {
                            resultUri = result.getUri();
                            binding.ivProfile.setImageURI(resultUri);
                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            Exception error = result.getError();
                        }
                    }

                }

            }

        }




    private void setStatesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvStates.setLayoutManager(layoutManager);
        binding.rvStates.setAdapter(adapter);
    }

    private void setCitiesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCities.setLayoutManager(layoutManager);
        binding.rvCities.setAdapter(cityadapter);
    }


    SimpleRecyclerViewAdapter<StatesModel, HolderStatesBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_states, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<StatesModel>() {
                @Override
                public void onItemClick(View v, StatesModel bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.ll_states:
                            binding.etState.setText(bean.getName());
                            binding.rvStates.setVisibility(View.GONE);
                            state_id = String.valueOf(bean.getId());
                            viewModel.getCities(bean.getId());

                            break;
                    }
                }
            });

    SimpleRecyclerViewAdapter<CitiesModel, HolderCitiesBinding> cityadapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_cities, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<CitiesModel>() {
                @Override
                public void onItemClick(View v, CitiesModel bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.ll_states:
                            city_id = String.valueOf(bean.getId());
                            binding.etCity.setText(bean.getName());
                            binding.rvCities.setVisibility(View.GONE);
                            break;
                    }
                }
            });


}
