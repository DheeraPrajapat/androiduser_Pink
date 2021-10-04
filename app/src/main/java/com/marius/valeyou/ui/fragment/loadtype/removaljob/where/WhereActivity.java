package com.marius.valeyou.ui.fragment.loadtype.removaljob.where;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityWhereBinding;
import com.marius.valeyou.databinding.HolderCitiesBinding;
import com.marius.valeyou.databinding.HolderStatesBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.LocationActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class WhereActivity extends AppActivity<ActivityWhereBinding, WhereActivityVM> {
    double  lat,lng;
    List<StatesModel> statesModelsList;
    String state_id= "";
    String city_id = "";
    String cityName = "";
    List<CitiesModel> citiesModelList;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity,WhereActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<WhereActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_where, WhereActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhereActivityVM vm) {

        binding.header.tvTitle.setText(getResources().getString(R.string.where));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        binding.etAddress.setText(sharedPref.get("myLocation",""));
        binding.etCity.setText(sharedPref.get("myCity",""));
        binding.etState.setText(sharedPref.get("mystate",""));
        binding.etCountry.setText(sharedPref.get("country",""));
        binding.etZipCode.setText(sharedPref.get("myzipCode",""));
        binding.etApartmentNumber.setText(sharedPref.get("apartment",""));

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
                                        binding.etStreetNumber.setText(component.long_name);
                                        break;
                                    case ROUTE:
                                        break;
                                    case NEIGHBORHOOD:
                                        break;
                                    case SUBLOCALITY_LEVEL_1:
                                        break;
                                    case SUBLOCALITY:
                                        binding.etStreetName.setText(component.long_name);
                                        break;
                                    case LOCALITY:
                                        binding.etCity.setText(component.long_name);
                                        cityName = component.long_name;
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_1:
                                        binding.etState.setText(component.long_name);
                                       /* for (int i = 0;i<statesModelsList.size();i++){
                                        if (component.long_name.contains(statesModelsList.get(i).getName())){
                                            binding.etState.setText(statesModelsList.get(i).getName());
                                            state_id = String.valueOf(statesModelsList.get(i).getId());
                                            viewModel.getCities(statesModelsList.get(i).getId(),"");
                                        }
                                    }*/


                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_2:
                                        break;
                                    case COUNTRY:
                                        binding.etCountry.setText(component.long_name);
                                        break;
                                    case POSTAL_CODE:
                                        binding.etZipCode.setText(component.long_name);
                                        break;
                                    case POLITICAL:
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
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
                switch (view!=null?view.getId():0) {
                    case R.id.btn_save:
                        if (isEmpty()) {
                            Intent intent = new Intent();
                            intent.putExtra("location",binding.etAddress.getText().toString());
                            intent.putExtra("city",binding.etCity.getText().toString());
                            intent.putExtra("cityId",city_id);
                            intent.putExtra("stateId",state_id);
                            intent.putExtra("street",binding.etStreetName.getText().toString());
                            intent.putExtra("number",binding.etStreetNumber.getText().toString());
                            intent.putExtra("state",binding.etState.getText().toString());
                            intent.putExtra("country",binding.etCountry.getText().toString());
                            intent.putExtra("zip_code",binding.etZipCode.getText().toString());
                            intent.putExtra("apartment",binding.etApartmentNumber.getText().toString());

                            sharedPref.put("myLocation",binding.etAddress.getText().toString());
                            sharedPref.put("myCity",binding.etCity.getText().toString());
                            sharedPref.put("mystate",binding.etState.getText().toString());
                            sharedPref.put("country",binding.etCountry.getText().toString());
                            sharedPref.put("myzipCode",binding.etZipCode.getText().toString());
                            sharedPref.put("apartment",binding.etApartmentNumber.getText().toString());
                            sharedPref.put("lat",""+lat);
                            sharedPref.put("lng",""+lng);

                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                        break;

                    case R.id.iv_location:

                        AllowLocationPermision();

                        break;

                    case R.id.et_state:

                      /*  if(binding.rvStates.getVisibility() == View.VISIBLE){
                            binding.rvStates.setVisibility(View.GONE);
                        }else{
                            binding.rvStates.setVisibility(View.VISIBLE);
                        }*/

                        break;

                    case R.id.et_city:
                       /* if(binding.rvCity.getVisibility() == View.VISIBLE){
                            binding.rvCity.setVisibility(View.GONE);
                        }else{
                            binding.rvCity.setVisibility(View.VISIBLE);
                        }*/
                        break;
                }
            }
        });

       // vm.getStates("");




        vm.statesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<StatesModel>>() {
            /* binding.etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View view, boolean b) {
                   if (!b){
                       binding.etCity.addTextChangedListener(null);
                   }else{
                       binding.etCity.addTextChangedListener(cityWatcher);
                   }
               }
           });*/   @Override
            public void onRequestReceived(@NonNull Resource<List<StatesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:

                        statesModelsList = resource.data;
                        adapter.setList(statesModelsList);

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

        setStatesAdapter();
        setCitiesAdapter();


    }


    TextWatcher cityWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            if (input.length()>0) {
                binding.rvCity.setVisibility(View.VISIBLE);
                 viewModel.getCities(Integer.parseInt(state_id),input);
            }else{
                binding.rvCity.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

   TextWatcher stateWatcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            if (input.length()>0) {

                binding.rvStates.setVisibility(View.VISIBLE);
                viewModel.getStates(input);

            }else{
                binding.rvStates.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void AllowLocationPermision(){
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {

                Intent intent = new Intent(WhereActivity.this, LocationActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(binding.etAddress.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_address));
            return false;
        }
        if (TextUtils.isEmpty(binding.etState.getText().toString().trim())){
            viewModel.error.setValue(getResources().getString(R.string.select_state));
            return false;
        }


        if (binding.etState.getText().toString().equalsIgnoreCase(getResources().getString(R.string.select_state))) {
            viewModel.error.setValue(getResources().getString(R.string.select_state));
            return false;
        }

        if (TextUtils.isEmpty(binding.etCity.getText().toString().trim())){
            viewModel.error.setValue(getResources().getString(R.string.select_city));
            return false;
        }

        if (binding.etCity.getText().toString().equalsIgnoreCase(getResources().getString(R.string.select_city))) {
            viewModel.error.setValue(getString(R.string.select_city));
            return false;
        }
        if (TextUtils.isEmpty(binding.etZipCode.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_zip_code));
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            String result = data.getStringExtra("address");
            binding.etAddress.setText(result);
            String city = data.getStringExtra("city");
            String state = data.getStringExtra("state");
            String zipcode = data.getStringExtra("zipcode");
            String country = data.getStringExtra("country");
            lat = data.getDoubleExtra("latitude",0);
            lng = data.getDoubleExtra("longtitude",0);

            binding.etCity.setText(city);
            binding.etZipCode.setText(zipcode);
            binding.etCountry.setText(country);
/*
            for (int i = 0;i<statesModelsList.size();i++){
                if (state.contains(statesModelsList.get(i).getName())){
                    binding.etState.setText(statesModelsList.get(i).getName());
                    state_id = String.valueOf(statesModelsList.get(i).getId());
                    viewModel.getCities(statesModelsList.get(i).getId(),"");
                }
            }*/
        }

    }




    private void setStatesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvStates.setLayoutManager(layoutManager);
        binding.rvStates.setAdapter(adapter);
    }

    private void setCitiesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCity.setLayoutManager(layoutManager);
        binding.rvCity.setAdapter(cityadapter);
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
                            viewModel.getCities(bean.getId(),"");

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
                            binding.rvCity.setVisibility(View.GONE);
                            break;
                    }
                }
            });


}
