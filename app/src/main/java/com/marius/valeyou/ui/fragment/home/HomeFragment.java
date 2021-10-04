package com.marius.valeyou.ui.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.LocationsBean;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou.databinding.DialogLogoutBinding;
import com.marius.valeyou.databinding.FragmentHomeBinding;
import com.marius.valeyou.databinding.HolderFilterCategoryBinding;
import com.marius.valeyou.databinding.HolderListItemsBinding;
import com.marius.valeyou.databinding.HolderMapItemsBinding;
import com.marius.valeyou.databinding.HolderMoreBinding;
import com.marius.valeyou.databinding.HolderSetCategoryBinding;
import com.marius.valeyou.databinding.HolderStatesBinding;
import com.marius.valeyou.databinding.HolderSuggestionsBinding;
import com.marius.valeyou.databinding.SearchProvidersBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Splashscreen.SplashScreen;
import com.marius.valeyou.ui.activity.block.BlockActivity;
import com.marius.valeyou.ui.activity.identityverfication.IdentityVerificationActivity;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragment;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.VerticalPagination;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.misc.RxBus;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.marius.valeyou.util.view.EndlessVerticalScroller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends AppFragment<FragmentHomeBinding, HomeFragmentVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{
    public static final String TAG = "HomeFragment";
    private MainActivity mainActivity;
    private Location mCurrentlocation;
    private BottomSheetBehavior bottomSheetBehavior;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private List<ProviderNearMe> nearMes;
    List<ProviderNearMe> providersList = new ArrayList<>();
    @Inject
    SharedPref sharedPref;
    private List<CategoryListBean> categoryListBeans;
    boolean flag = false;
    private String rate = "";
    private boolean isAccount = false;
    String state_id = "";
    int stateId;
    String city_id = "";
    List<StatesModel> statesModelList;
    List<CitiesModel> citiesModelList;
    List<String> citiesList;
    ArrayList<String> statesList;
    String catJson="";
    String catType="";

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    public static Fragment newInstance(String cat_id, String sub_cat_id,String id,String json,String catType) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("cat_id", cat_id);
        args.putString("sub_cat_id", sub_cat_id);
        args.putString("id", id);
        args.putString("json", json);
        args.putString("type", catType);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String cat_id, String sub_cat_id,String id,String distance,String noOfJobs,String state,String rate,String state_id, String city_id) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("cat_id", cat_id);
        args.putString("sub_cat_id", sub_cat_id);
        args.putString("id", id);
        args.putString("distance", distance);
        args.putString("jobs", noOfJobs);
        args.putString("state", state);
        args.putString("rate", rate);
        args.putString("state_id", state_id);
        args.putString("city_id", city_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected BindingFragment<HomeFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_home, HomeFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setBean(viewModel.sharedPref.getUserData());
        binding.nav.setBean(viewModel.sharedPref.getUserData());


    }


    private void getCurrentLocation() {
        Permissions.check(baseContext, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
                setMyLocation();
            }
        });
    }


    @Override
    protected void subscribeToEvents(final HomeFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            MapsInitializer.initialize(getActivity());
        }

   Bundle bundle = getArguments();
   if (bundle!=null){
       binding.setCheck(false);

       String cat_id = bundle.getString("cat_id");
       String sub_cat_id = bundle.getString("sub_cat_id");
       String id = bundle.getString("id");
       String noOfJobs = bundle.getString("jobs");
       String state = bundle.getString("state");
       String distance = bundle.getString("distance");
       String rate = bundle.getString("rate");
       String state_id = bundle.getString("state_id");
       String city_id = bundle.getString("city_id");
       catType = bundle.getString("type");
       catJson = bundle.getString("json");


       vm.addFilter(id,cat_id,rate,distance,"",noOfJobs,"",sub_cat_id,state_id,city_id);

   }else{

       vm.addFilter("","","","","","","","","","");
       binding.setCheck(true);

   }

        setActionOnSearch();
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.cv_list:
                    binding.setCheck(false);
                    break;
                case R.id.cv_map:
                    binding.setCheck(true);
                    break;

                case R.id.notificationIcon:
                    Intent in = NotificationActivity.newIntent(getActivity());
                    startNewActivity(in);
                    break;

                case R.id.cv_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.rl_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;
                case R.id.iv_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.cv_pic:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.iv_close:
                    binding.drawerView.closeDrawers();
                    break;

                case R.id.view_profile:
                    Intent intent1 = ProfileActivity.newIntent(getActivity());
                    startNewActivity(intent1);
                    break;

                case R.id.view_img:
                    Intent intent = ProfileActivity.newIntent(getActivity());
                    startNewActivity(intent);
                    break;

                case R.id.imageViewfilter:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    break;

                case R.id.iv_cancel:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;


                case R.id.view_five:
                    binding.bottomLayout.ftRating.setType(5);
                    rate = "";
                    break;
                case R.id.view_four:
                    binding.bottomLayout.ftRating.setType(4);
                    rate = "2";
                    break;
                case R.id.view_three:
                    binding.bottomLayout.ftRating.setType(3);
                    rate = "3";
                    break;
                case R.id.view_two:
                    binding.bottomLayout.ftRating.setType(2);
                    rate = "4";
                    break;
                case R.id.view_one:
                    binding.bottomLayout.ftRating.setType(1);
                    rate = "5";
                    break;
                case R.id.b_apply:
                    // Apply Filter
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    applyFilter();
                    break;

                case R.id.et_state:
                    flag = true;
                    binding.bottomLayout.ftState.spinner.performClick();
                    break;

                case R.id.et_city:
                    binding.bottomLayout.ftState.citySpinner.performClick();
                    break;

                case R.id.cv_my_account:
                    if (isAccount) {
                        isAccount = false;
                        binding.nav.llAccountsItems.setVisibility(View.GONE);

                    } else {
                        isAccount = true;
                        binding.nav.llAccountsItems.setVisibility(View.VISIBLE);

                    }

                    break;


                case R.id.txt_change_password:
                    Intent changePasswordIntent = ChangePasswordFragment.newIntent(getActivity());
                    startNewActivity(changePasswordIntent);
                    break;



                case R.id.txt_identity_verification:
                    binding.drawerView.closeDrawers();
                    Intent intent2 = IdentityVerificationActivity.newIntent(getActivity());
                    startNewActivity(intent2);
                    break;

                case R.id.txt_deactivate_account:
                    binding.drawerView.closeDrawers();
                    dialogDeactivateAccount();
                    break;

                case R.id.txt_delete_account:
                    binding.drawerView.closeDrawers();
                   dialogDeleteAccount();
                    break;



            }
        });

        vm.liveLocationDetecter.observe(this, new Observer<LocCallback>() {
            @Override
            public void onChanged(LocCallback locCallback) {
                switch (locCallback.getType()) {
                    case STARTED:
                        break;
                    case ERROR:
                        break;
                    case STOPPED:
                        break;
                    case OPEN_GPS:
                        try {
                            locCallback.getApiException().startResolutionForResult(getActivity(), LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:

                        break;
                    case FOUND:

                        mCurrentlocation = locCallback.getLocation();
                        sharedPref.put(Constants.LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        vm.liveLocationDetecter.removeLocationUpdates();
                        if (nearMes != null) {
                            setMarkerOnMap(nearMes);
                        }
                        break;
                }

            }
        });



        vm.addFilterEvent.observe(this, new SingleRequestEvent.RequestObserver<List<ProviderNearMe>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<ProviderNearMe>> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        getCurrentLocation();
                        nearMes = resource.data;
                        adapter.setList(resource.data);
                        setRecyclerViewList(resource.data);
                        setMarkerOnMap(resource.data);
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

        if (nearMes != null) {
            adapter.setList(nearMes);
            adapterList.setList(nearMes);
        }
        vm.getCount();
        vm.notificationbadgeEventRequest.observe(this, new SingleRequestEvent.RequestObserver<NotificationBadgeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<NotificationBadgeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        mainActivity.badgeCount(resource.data.getCount());
                        if (resource.data.getNotification()>0){

                            binding.notificationCount.setVisibility(View.VISIBLE);
                            if (resource.data.getNotification()>9){
                                binding.notificationCount.setText("9+");
                            }else{
                                binding.notificationCount.setText(resource.data.getNotification()+"");
                            }

                        }else{
                            binding.notificationCount.setVisibility(View.GONE);
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
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



        binding.bottomLayout.ftState.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag)        // Here

                    binding.bottomLayout.ftState.etState.setText(binding.bottomLayout.ftState.spinner.getSelectedItem().toString());

                for (i = 0; i < statesModelList.size(); i++) {
                    String name = statesModelList.get(i).getName();
                    if (name.equalsIgnoreCase(binding.bottomLayout.ftState.spinner.getSelectedItem().toString())) {
                        stateId = statesModelList.get(i).getId();
                        state_id = String.valueOf(stateId);
                    }
                }

                vm.getCities(stateId);
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.bottomLayout.ftState.citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                binding.bottomLayout.ftState.etCity.setText(binding.bottomLayout.ftState.citySpinner.getSelectedItem().toString());


                for (i = 0; i < citiesModelList.size(); i++) {

                    String name = citiesModelList.get(i).getName();

                    if (name.equalsIgnoreCase(binding.bottomLayout.ftState.citySpinner.getSelectedItem().toString())) {

                        city_id = String.valueOf(citiesModelList.get(i).getId());

                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                        citiesList = new ArrayList<>();
                        for (int i = 0;i<resource.data.size();i++){
                            citiesList.add(resource.data.get(i).getName());
                        }
                        citiesList.add(0,getResources().getString(R.string.select_city));
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, citiesList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.bottomLayout.ftState.citySpinner.setAdapter(adapter);

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



        vm.statesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<StatesModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<StatesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        statesModelList = resource.data;
                        statesList = new ArrayList<>();

                        for (int i = 0;i<resource.data.size();i++){
                            statesList.add(resource.data.get(i).getName());
                        }

                        statesList.add(0,getResources().getString(R.string.select_state));
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, statesList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.bottomLayout.ftState.spinner.setAdapter(arrayAdapter);

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


        vm.getStates();
        setBottomsheet();
        moveViewWithDrawer();
        setRecyclerViewMore();
        setRecyclerView();
        setRVSuggestions();
        setFilterRecycler();



        vm.suggestionsEvent.observe(this, new SingleRequestEvent.RequestObserver<List<ProviderNearMe>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<ProviderNearMe>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                     providersList = resource.data;

                     suggestionAdapter.setList(providersList);

                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
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



        binding.etSearch.addTextChangedListener(textWatcher);


    }



    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String str = charSequence.toString();
            Log.d("typing : ",str);
            if (str.length()>0){
                binding.llSuggestions.setVisibility(View.VISIBLE);
                viewModel.searchSuggestions("","","","","","",str,"","");
            }else{
                providersList.clear();
                binding.llSuggestions.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };




    private void callApi(String search) {
        viewModel.addFilter("","", "", "", "", "",search,"","","");
    }
    private void setMarkerOnMap(List<ProviderNearMe> driverBeans) {
        if (googleMap == null)
            return;
        googleMap.clear();
        if (driverBeans == null)
            driverBeans = new ArrayList<>();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < driverBeans.size(); i++) {

            Log.d("drivers","="+driverBeans.get(i).getFirstName());
            LatLng latLng = new LatLng(driverBeans.get(i).getLatitude(), driverBeans.get(i).getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(driverBeans.get(i).getFirstName())
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(driverBeans.get(i).getImage(), 1))));
           // googleMap.setInfoWindowAdapter(new CustomMarkerInfoWindowView(getActivity(),driverBeans.get(i).getFirstName()+" "+driverBeans.get(i).getLastName()));

            builder.include(latLng);

        }
        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

        CameraPosition pos = new CameraPosition.Builder()
                .target(new LatLng(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude()))
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));

    }

    private GoogleMap.OnCameraIdleListener onCameraIdleListener = () -> {

    };

    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener = i -> {

    };

    @Override
    public void onMapClick(LatLng latLng) {

        Log.i("HelloLATLON", "latidute" + latLng.latitude + ":::long" + latLng.longitude);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        try {

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        } catch (Exception e) {

            Log.d(TAG, "Can't find style. Error: ", e);

        }

        setMyLocation();
    }


    private void setMyLocation() {

        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private Bitmap getMarkerBitmapFromView(String image_url, int pos) {
        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        ImageView provider = customMarkerView.findViewById(R.id.iv_picture);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.override(50, 50);
        options.placeholder(R.drawable.app_logo);
       // GlideApp.with(getActivity()).load(Constants.IMAGE_BASE_URL + image_url).apply(options).into(provider);
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvList.setLayoutManager(layoutManager);
        binding.rvList.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<ProviderNearMe, HolderMapItemsBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_map_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderNearMe>() {
        @Override
        public void onItemClick(View v, ProviderNearMe nearMe) {
            switch (v != null ? v.getId() : 0) {
                case R.id.cv_items:
                    Intent intent = ProviderProfileActivity.newIntent(getActivity(), nearMe.getId(), nearMe.getFav(),catJson,catType);
                    startNewActivity(intent);
                    break;
            }
        }
    });

    private void setRecyclerViewList(List<ProviderNearMe> list) {
        if (list.size()>0) {

                binding.txtNoRecord.setVisibility(View.GONE);
                binding.rvListData.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                binding.rvListData.setLayoutManager(layoutManager);
                binding.rvListData.setAdapter(adapterList);
                adapterList.setList(list);


        }else{

            binding.txtNoRecord.setVisibility(View.VISIBLE);
            binding.rvListData.setVisibility(View.GONE);

        }
    }

    SimpleRecyclerViewAdapter<ProviderNearMe, HolderListItemsBinding> adapterList = new SimpleRecyclerViewAdapter(R.layout.holder_list_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderNearMe>() {
        @Override
        public void onItemClick(View v, ProviderNearMe nearMe) {
            switch (v != null ? v.getId() : 0) {
                case R.id.cv_items:
                    Intent intent = ProviderProfileActivity.newIntent(getActivity(), nearMe.getId(), nearMe.getFav(),catJson,catType);
                    startNewActivity(intent);
                    break;
            }
        }
    });


    private void setRVSuggestions() {
      LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
       binding.rvSuggestions.setLayoutManager(layoutManager);
        binding.rvSuggestions.setAdapter(suggestionAdapter);

    }

    SimpleRecyclerViewAdapter<ProviderNearMe, SearchProvidersBinding> suggestionAdapter =
            new SimpleRecyclerViewAdapter<>(R.layout.search_providers, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderNearMe>() {
                @Override
                public void onItemClick(View v, ProviderNearMe moreBean) {
                    viewModel.addFilter("","","","","","",moreBean.getFirstName(),"","","");
                    binding.llSuggestions.setVisibility(View.GONE);

                }
            });


    private void setActionOnSearch() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   binding.llSuggestions.setVisibility(View.GONE);
                    in.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);
                    callApi(binding.etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private void setBottomsheet() {
        binding.bottomLayout.ftRating.setType(5);
        bottomSheetBehavior = BottomSheetBehavior.from(binding.llOne);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float s = (1f - v / 2f);
            }
        });


    }

    private List<MoreBean> filterCat;
    private void setFilterRecycler() {
        binding.bottomLayout.setType(2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.rvFilterCat.setLayoutManager(layoutManager);
        binding.bottomLayout.rvFilterCat.setAdapter(adapterCat);
        filterCat = getListData();
        adapterCat.setList(filterCat);
    }
    private List<MoreBean> getListData() {
        List<MoreBean> beanList = new ArrayList<>();
        // beanList.add(new MoreBean(1, "Categories", 1));
        beanList.add(new MoreBean(2, getResources().getString(R.string.rating), 1));
        beanList.add(new MoreBean(3, getResources().getString(R.string.distance), 0));
        beanList.add(new MoreBean(4, getResources().getString(R.string.state), 0));
        beanList.add(new MoreBean(5, getResources().getString(R.string.no_of_jobs), 0));
        return beanList;
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderFilterCategoryBinding> adapterCat =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_filter_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean moreBean) {
                    for (int j = 0; j < filterCat.size(); j++) {
                        if (filterCat.get(j).getId() == moreBean.getId()) {
                            filterCat.get(j).setImage(1);
                        } else {
                            filterCat.get(j).setImage(0);
                        }
                    }
                    adapterCat.setList(filterCat);
                    binding.bottomLayout.setType(moreBean.getId());
                }
            });

    private void moveViewWithDrawer() {
        binding.nav.setName(viewModel.sharedPref.getUserData().getFirstName() + " " + viewModel.sharedPref.getUserData().getLastName());
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                binding.drawerView, binding.toolbar, R.string.acc_drawer_open, R.string.acc_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                binding.drawerView.setScrimColor(Color.TRANSPARENT);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawerView.addDrawerListener(mDrawerToggle);
    }


    private void setRecyclerViewMore() {
        binding.nav.rvMore.setLayoutManager(new LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter_more = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, com.marius.valeyou.BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean shopBean) {
                binding.drawerView.closeDrawers();
                Intent intent;
                switch (shopBean.getId()) {

                    case 1:
                        Intent notificationIntent = NotificationActivity.newIntent(getActivity());
                        startNewActivity(notificationIntent);
                        break;

                    case 2:
                        Intent intent1 = PaymentActivity.newIntnt(getActivity(),"home");
                        startNewActivity(intent1);
                        break;

//                    case 3:
//                        Intent langInt = AppLanguageActivity.newIntent(getActivity());
//                        startNewActivity(langInt);
//
//                        break;

                    case 4:

                        Intent blockIntent = BlockActivity.newIntent(getActivity());
                        startNewActivity(blockIntent);


                        break;

                    case 5:
                        mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance());
                        break;

                    case 6:
                        mainActivity.addSubFragment(TAG, HelpAndSupportFragment.newInstance());
                        break;
                    case 7:
                        mainActivity.addSubFragment(TAG, AboutUsFragment.newInstance());
                        break;
                    case 8:
                        dialogLogout();
                        break;


                }
            }
        });
        binding.nav.rvMore.setAdapter(adapter_more);
        adapter_more.setList(getMoreData());
    }

    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, getResources().getString(R.string.notifications), R.drawable.ic_notification_icon));
        //menuBeanList.add(new MoreBean(2, getResources().getString(R.string.payments), R.drawable.pay));
        //menuBeanList.add(new MoreBean(3, getResources().getString(R.string.select_language), R.drawable.ic_language));
        menuBeanList.add(new MoreBean(4, getResources().getString(R.string.blocked_contacts), R.drawable.ic_block));
        menuBeanList.add(new MoreBean(5, getResources().getString(R.string.terms), R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(6, getResources().getString(R.string.help_support), R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(7, getResources().getString(R.string.about_us), R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(8, getResources().getString(R.string.logout_account), R.drawable.ic_logout_icon));
        return menuBeanList;
    }


    private BaseCustomDialog<DialogLogoutBinding> dialogSuccess;

    private void dialogLogout() {
        dialogSuccess = new BaseCustomDialog<>(getActivity(), R.layout.dialog_logout, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            SharedPrefrence.logout_user(getActivity());
                            viewModel.logout();
                            break;
                        case R.id.b_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }

    private BaseCustomDialog<DialogDeactivateAccopuontBinding> dialogDeactiviateAccount;

    private void dialogDeactivateAccount() {
        dialogDeactiviateAccount = new BaseCustomDialog<>(getActivity(), R.layout.dialog_deactivate_accopuont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogDeactiviateAccount.dismiss();
                            viewModel.deleteOrDeactivate(viewModel.sharedPref.getUserData().getAuthKey(), "0");
                            break;
                        case R.id.btn_cancel:
                            dialogDeactiviateAccount.dismiss();
                            break;

                        case R.id.iv_cancel:
                            dialogDeactiviateAccount.dismiss();
                            break;
                    }


                }
            }
        });
        dialogDeactiviateAccount.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogDeactiviateAccount.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogDeactiviateAccount.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogDeactiviateAccount.show();
    }

    private BaseCustomDialog<DialogDeleteAccouontBinding> dialogDelete;

    private void dialogDeleteAccount() {
        dialogDelete = new BaseCustomDialog<>(getActivity(), R.layout.dialog_delete_accouont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:

                            dialogDelete.dismiss();
                            viewModel.deleteOrDeactivate(viewModel.sharedPref.getUserData().getAuthKey(), "1");
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


    String cat_ids = "";
    String state;

      private void applyFilter() {
        cat_ids = "";
        String distance = String.valueOf(binding.bottomLayout.ftDistance.distanceValue.getProgress() * 10);
        String no_of_job = String.valueOf(binding.bottomLayout.ftJob.vsJob.getProgress() * 10);

       /* for (int j = 0; j < categoryListBeans.size(); j++) {
            if (categoryListBeans.get(j).isCheck()) {
                cat_ids = cat_ids + categoryListBeans.get(j).getId() + ",";
            }
        }
        if (!cat_ids.equalsIgnoreCase("") && cat_ids.length() > 1) {
            cat_ids = cat_ids.substring(0, cat_ids.length() - 1);
        }*/
        if (no_of_job.equalsIgnoreCase("0")){
            no_of_job = "";
        }

        if (distance.equalsIgnoreCase("0")){
            distance = "";
        }

        state = binding.bottomLayout.ftState.etState.getText().toString();

        if (state.equalsIgnoreCase(getResources().getString(R.string.select_state))){
            state = "";
        }

        mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG,HomeFragment.newInstance("","","",distance,no_of_job,state,rate,state_id,city_id));

    }

}
