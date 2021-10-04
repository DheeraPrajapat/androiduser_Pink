package com.marius.valeyou.localMarketModel.ui.fragment.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appyvet.materialrangebar.RangeBar;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogCreateAdBinding;
import com.marius.valeyou.databinding.DialogHomeFilterBinding;
import com.marius.valeyou.databinding.FragmentHomeMarketBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.activity.allCategory.AllCategoryActivity;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CompanyAdapter;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CompanyListModel;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.userPost.UserPostFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment.isNetworkAvailable;
import static com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment.noNetworkToast;

public class HomeFragmentMarket extends AppFragment<FragmentHomeMarketBinding, HomeFragmentMarketVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    public static final String TAG = "HomeFragmentMarket";
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    private MarketHomeModel marketHomeModel;
    List<MarketPostModel> searchDataList = new ArrayList<>();
    Bitmap bitmapMarker;
    Bitmap bitmapMarkerTrans;
    int CategoryRequestCode = 11;
    boolean privateSell = false;
    boolean commercial = false;
    boolean rental = false;
    boolean sell = false;
    boolean price = false;
    boolean distance = false;
    boolean date = false;
    boolean newProduct = false;
    boolean usedProdut = false;
    String private_saller = "";
    String commercial_seller = "";
    String only_sale = "";
    String only_rent = "";
    String productType = "";
    String price_min = "";
    String price_max = "";
    String priceFilter = "";
    String dateFilter = "";
    String searchItem = "";
    Circle circle;
    ImageView[] imgCategory = new ImageView[4];
    String categoryName = "";
    MarketPostModel selectPostModel;
    BottomSheetBehavior bottomSheetBehavior;


    int CreateShopRequestCode = 15;
    boolean showDialog = false;
    String ownerType = "";
    CompanyAdapter companyAdapter;
    List<CompanyListModel> companyNameList = new ArrayList<>();
    public String shop_id = "";
    public String address = "";
    public String phone = "";
    public String lati = "";
    public String longi = "";
    public String selected_category = "";
    public String country_code = "";
    //    public String country_code = "";
    CompanyListModel companyListModel;

    public static Fragment newInstance() {
        return new HomeFragmentMarket();
    }

    @Override
    public void onStart() {
        super.onStart();
        getCurrentLocation();
    }

    @Override
    protected BindingFragment<HomeFragmentMarketVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_home_market, HomeFragmentMarketVM.class);
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
    public void onResume() {
        super.onResume();
        viewModel.getMarketShopProfile(this);
       }



    private static String getScreenResolution(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }

    @Override
    protected void subscribeToEvents(final HomeFragmentMarketVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        loadBannerAds();
        searchHomeData();

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetBehaviorId.llBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                // do something when state changes
                // React to state change
                Log.d("onStateChanged", "onStateChanged:" + newState);
                if (marketActivity.isList) {
                    if (!(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN)) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                    return;
                }


                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (googleMap != null) {
                        googleMap.getUiSettings().setScrollGesturesEnabled(false);
                    }
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    //   bottomSheetBehavior.setPeekHeight(80);
                    if (googleMap != null) {
                        googleMap.getUiSettings().setScrollGesturesEnabled(true);
                    }
                }

            }

            @Override
            public void onSlide(View view, float v) {
                // do something when slide happens
//                binding.bottomSheetBehaviorId.llBottomSheet.animate().y(v <= 0 ?
//                        view.getY() + bottomSheetBehavior.getPeekHeight() - binding.bottomSheetBehaviorId.llBottomSheet.getHeight() :
//                        view.getHeight() - binding.bottomSheetBehaviorId.llBottomSheet.getHeight()).setDuration(1000).start();
            }
        });
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        int screenSize1 = getResources().getConfiguration().screenHeightDp;
        Log.d("HEIGHTTTT", String.valueOf(screenSize1));


//        32 dp	≤ 400 dp
//        50 dp	> 400 dp and ≤ 720 dp
//        90 dp	> 720 dp
        if (screenSize1 <= 400) {
            bottomSheetBehavior.setPeekHeight((int) (screenSize1 / 1.7));
            binding.bottomSheetBehaviorId.rvList.setPadding(0, 0, 0, 96);
        } else if (screenSize1 > 400 && screenSize1 <= 600) {
            bottomSheetBehavior.setPeekHeight((int) (screenSize1 / 1.1));
            binding.bottomSheetBehaviorId.rvList.setPadding(0, 0, 0, 150);
        } else if (screenSize1 > 600 && screenSize1 <= 720) {
            bottomSheetBehavior.setPeekHeight((int) (screenSize1 / 1.68));
            binding.bottomSheetBehaviorId.rvList.setPadding(0, 0, 0, 150);
        } else {
            bottomSheetBehavior.setPeekHeight((int) (screenSize1 / 1.4));
            binding.bottomSheetBehaviorId.rvList.setPadding(0, 0, 0, 270);
        }


        bitmapMarker = drawableToBitmap(requireContext().getResources().getDrawable(R.drawable.ic_map_pin));
        bitmapMarkerTrans = drawableToBitmap(requireContext().getResources().getDrawable(R.drawable.ic_map_pin_transparent));

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            MapsInitializer.initialize(requireContext());
        }

        vm.getCategoryList();
        sharedPref.put(Constants.RADIUS1, "0");


        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.txtRadius.setText("Radius: " + "" + (float) progress / 2 + " KM");

                if (circle != null) {
                    circle.setRadius((float) progress / 2 * 1000);

                    double latitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LATITUDE, "0.00"));
                    double longitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LONGITUDE, "0.00"));
                    LatLng latLng= new LatLng(latitude, longitude);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));
                }
                sharedPref.put(Constants.RADIUS2, String.valueOf("" + (int) binding.seekBar.getProgress()/2));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar.setProgress(Integer.parseInt(sharedPref.get(Constants.RADIUS2, "1")));


        marketActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {
                if (type.equalsIgnoreCase(requireContext().getResources().getString(R.string.buy_rent_product))) {
                    binding.txtList.performClick();
                } else if (type.equalsIgnoreCase(requireContext().getResources().getString(R.string.market_place))) {
                    binding.txtMap.performClick();
                } else if (type.equalsIgnoreCase(requireContext().getResources().getString(R.string.done))) {
                    binding.txtDone.performClick();
                } else {
                    searchItem = type;
                }
            }
        };
        imgCategory[0] = binding.img0;
        imgCategory[1] = binding.img1;
        imgCategory[2] = binding.img2;
        imgCategory[3] = binding.img3;
        for (int i = 0; i < imgCategory.length; i++) {
            int finalI = i;
            imgCategory[i].setOnClickListener(v -> {
                for (ImageView imgView : imgCategory) {
                    imgView.setBackground(requireContext().getResources().getDrawable(R.drawable.circle_blue_light));
                }
                imgCategory[finalI].setBackground(requireContext().getResources().getDrawable(R.drawable.circle_blue));
                categoryName = marketActivity.categoryListBeans.getData().get(finalI).getName();
                searchHomeData();
            });
        }


        vm.marketShopProfileEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketShopProfile>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketShopProfile> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        companyNameList.clear();
                        dismissProgressDialog();
                        if (resource.data.getData() != null) {
                            sharedPref.put(Constants.SHOP_NAME, resource.data.getData().get(0).getCompanyName() == null ? "" : resource.data.getData().get(0).getCompanyName());
                            sharedPref.put(Constants.SHOP_ADDRESS, resource.data.getData().get(0).getAddress() == null ? "" : resource.data.getData().get(0).getAddress());

                            for (int i = 0; i < resource.data.getData().size(); i++) {
                                companyListModel = new CompanyListModel();
                                companyListModel.setComapny_name(resource.data.getData().get(i).getCompanyName());
                                companyListModel.setShop_id(String.valueOf(resource.data.getData().get(i).getId()));
                                companyListModel.setPhone(String.valueOf(resource.data.getData().get(i).getPhone()));
                                companyListModel.setAddress(String.valueOf(resource.data.getData().get(i).getAddress()));
                                companyListModel.setLatitude(String.valueOf(resource.data.getData().get(i).getLatitude()));
                                companyListModel.setLongitude(String.valueOf(resource.data.getData().get(i).getLongitude()));
                                companyListModel.setCategory(String.valueOf(resource.data.getData().get(i).getCategory()));
                                companyListModel.setCountry_code(String.valueOf(resource.data.getData().get(i).getCountry_code()));
                                companyListModel.setCompany_selected(false);
                                companyNameList.add(companyListModel);

                            }


                        } else {
                            sharedPref.put(Constants.SHOP_NAME, "");
                            sharedPref.put(Constants.SHOP_ADDRESS, "");
                        }

                        if (showDialog) {
                            showDialog = false;
                            setCreateDialog();
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.txtDone:
                    searchHomeData();
                    break;
                case R.id.imgCurrentLocation:
                    sharedPref.put(Constants.SELECT_LATITUDE, sharedPref.get(Constants.LATITUDE, "0.00"));
                    sharedPref.put(Constants.SELECT_LONGITUDE, sharedPref.get(Constants.LONGITUDE, "0.00"));
                    searchHomeData();
                    break;

                case R.id.txtList:
                    marketActivity.isList = true;
                    binding.rvList.setVisibility(View.VISIBLE);
                    binding.txtRadius.setVisibility(View.GONE);
                    binding.relSeek.setVisibility(View.GONE);
                    binding.rvListBottom.setVisibility(View.GONE);
                    binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.GONE);
                    binding.txtNoRecord.setVisibility(View.GONE);
                    binding.adView.setVisibility(View.GONE);

                    binding.txtMap.setBackground(requireContext().getResources().getDrawable(R.drawable.rounded_corners_grey));
                    binding.txtMap.setTextColor(requireContext().getResources().getColor(R.color.colorPrimaryDark));
                    binding.txtList.setBackground(requireContext().getResources().getDrawable(R.drawable.rounded_corners_shape));
                    binding.txtList.setTextColor(requireContext().getResources().getColor(R.color.white));
                    if (!(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN)) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    if (searchDataList.size() == 0) {

                        binding.txtNoRecord.setVisibility(View.VISIBLE);
                    }

                    break;
                case R.id.txtMap:
                    marketActivity.isList = false;
                    binding.txtNoRecord.setVisibility(View.GONE);
                    binding.rvList.setVisibility(View.GONE);
                    binding.txtRadius.setVisibility(View.VISIBLE);
                    binding.relSeek.setVisibility(View.VISIBLE);
                    binding.rvListBottom.setVisibility(View.GONE);
                    binding.adView.setVisibility(View.VISIBLE);

                    binding.txtMap.setBackground(requireContext().getResources().getDrawable(R.drawable.rounded_corners_shape));
                    binding.txtMap.setTextColor(requireContext().getResources().getColor(R.color.white));
                    binding.txtList.setBackground(requireContext().getResources().getDrawable(R.drawable.rounded_corners_grey));
                    binding.txtList.setTextColor(requireContext().getResources().getColor(R.color.colorPrimaryDark));
                    if (searchDataList.size() > 0) {
                        binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.VISIBLE);

                    } else {
                        binding.txtNoRecord.setVisibility(View.VISIBLE);
                        binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.GONE);

                    }
                    break;

                case R.id.createAd:
                    showDialog = true;
                    if (showDialog) {
                        showDialog = false;
                        setCreateDialog();
                    }

                    //  marketActivity.addSubFragment(CreateAdFragment.TAG, CreateAdFragment.newInstance());
                    break;

                case R.id.llViewAll:
                    Intent intent = AllCategoryActivity.newIntent(requireActivity(), marketActivity.categoryListBeans);
                    startActivityForResult(intent, CategoryRequestCode);
                    break;

                case R.id.imgFilter:

                    filterOptionDialog();
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

                        Location mCurrentlocation = locCallback.getLocation();
                        sharedPref.put(Constants.LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        if (sharedPref.get(Constants.SELECT_LONGITUDE, "").equalsIgnoreCase("")) {
                            sharedPref.put(Constants.SELECT_LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                            sharedPref.put(Constants.SELECT_LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                            binding.txtDone.performLongClick();
                        }
                        vm.liveLocationDetecter.removeLocationUpdates();
                        getAddressFromLatLong(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude());
                        break;
                }

            }
        });

        vm.categoryModelEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketCategoryModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketCategoryModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        marketActivity.categoryListBeans = resource.data;
                        binding.setCategoryBean(marketActivity.categoryListBeans);
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        vm.homeModelEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketHomeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketHomeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        marketHomeModel = resource.data;
                        searchDataList.clear();
                        searchDataList.addAll(resource.data.getData());


                        if (marketActivity.latLngFilter.length() > 0) {
                            filterLocation();
                            Marker marker = null;
                            Marker marker1 = null;
                            for (int i = 0; i < searchDataList.size(); i++) {
                                if (searchDataList.get(i).getTagId().equalsIgnoreCase(marketActivity.click_tag_id)) {
                                    googleMap.clear();
                                    LatLng latLng = new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude()));
                                    Log.e("ADD_MARKERERE","Private");

                                    marker = googleMap.addMarker(new MarkerOptions()
                                            .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(createMarkerBitmap(marker, searchDataList.get(i).getTitle()))));
                                    marker.setTag(i);
                                     if (searchDataList.get(i).getOwnerType().equalsIgnoreCase(getActivity().getResources().getString(R.string.private_text))) {
                                        googleMap.addCircle(new CircleOptions()
                                                .center(latLng)
                                                .radius(Double.parseDouble("1") * 1000)
                                                .strokeColor(getResources().getColor(R.color.color_orange))
                                                .fillColor(getResources().getColor(R.color.yellow)));
                                        CameraPosition pos = new CameraPosition.Builder()
                                                .target(new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude())))
                                                .zoom(14)
                                                .build();
                                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));

                                    } else {

                                        googleMap.addCircle(new CircleOptions()
                                                .center(latLng)
                                                .radius(Double.parseDouble("1") * 1000)
                                                .strokeColor(getActivity().getResources().getColor(R.color.colorPrimary))
                                                .fillColor(getActivity().getResources().getColor(R.color.colorPrimaryTrans)));
Log.e("ADD_MARKERERE","COMMERCIAL");
                                        marker1 = googleMap.addMarker(new MarkerOptions()
                                                .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(createMarkerBitmap(marker1, searchDataList.get(i).getTitle()))));
                                        marker1.setTag(i);
                                        CameraPosition pos = new CameraPosition.Builder()
                                                .target(new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude())))
                                                .zoom(20)
                                                .build();
                                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));
                                    }


                                    //googleMap.setMaxZoomPreference(14f);

                                }

                            }

                        } else {
                            filter();
                        }
//                        adapter.setList(searchDataList);
//                        setMarkerOnMap(searchDataList);

                        if (searchDataList.size() > 0) {
                            binding.txtNoRecord.setVisibility(View.GONE);
                            binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.VISIBLE);

                        } else {
                            binding.txtNoRecord.setVisibility(View.VISIBLE);
                            binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.GONE);

                        }
                        if (marketActivity.isList) {
                            binding.txtList.performClick();
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });

        vm.addFavouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        for (int i = 0; i < searchDataList.size(); i++) {
                            if (searchDataList.get(i).getId().equals(selectPostModel.getId())) {
                                searchDataList.get(i).setFav(searchDataList.get(i).getFav() == 0 ? 1 : 0);
                                Log.e("PRICEEE", String.valueOf(searchDataList.get(i).getPrice()));
                            }
                        }
                        adapter.setList(searchDataList);
                        adapterNoAds.setList(searchDataList);
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
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        setRecyclerView();

    }

    private Bitmap createMarkerBitmap(Marker marker, String title) {

        View markerLayout = getLayoutInflater().inflate(R.layout.marker_dialog, null);

        ImageView markerImage = (ImageView) markerLayout.findViewById(R.id.marker_image);
        TextView markerText = (TextView) markerLayout.findViewById(R.id.marker_text);
        markerImage.setImageResource(R.drawable.ic_map_pin);
        markerText.setText(title);

        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());

        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerLayout.draw(canvas);


        return bitmap;
    }


    public void marketShopResponse(@NonNull Resource<MarketShopProfile> resource) {
         companyNameList.clear();
        dismissProgressDialog();
        if (resource.data.getData() != null) {
            sharedPref.put(Constants.SHOP_NAME, resource.data.getData().get(0).getCompanyName() == null ? "" : resource.data.getData().get(0).getCompanyName());
            sharedPref.put(Constants.SHOP_ADDRESS, resource.data.getData().get(0).getAddress() == null ? "" : resource.data.getData().get(0).getAddress());

            for (int i = 0; i < resource.data.getData().size(); i++) {
                companyListModel = new CompanyListModel();
                companyListModel.setComapny_name(resource.data.getData().get(i).getCompanyName());
                companyListModel.setShop_id(String.valueOf(resource.data.getData().get(i).getId()));
                companyListModel.setPhone(String.valueOf(resource.data.getData().get(i).getPhone()));
                companyListModel.setAddress(String.valueOf(resource.data.getData().get(i).getAddress()));
                companyListModel.setLatitude(String.valueOf(resource.data.getData().get(i).getLatitude()));
                companyListModel.setLongitude(String.valueOf(resource.data.getData().get(i).getLongitude()));
                companyListModel.setCategory(String.valueOf(resource.data.getData().get(i).getCategory()));
                companyListModel.setCountry_code(String.valueOf(resource.data.getData().get(i).getCountry_code()));
                companyListModel.setCompany_selected(false);
                companyNameList.add(companyListModel);

            }


        } else {
            sharedPref.put(Constants.SHOP_NAME, "");
            sharedPref.put(Constants.SHOP_ADDRESS, "");
        }

        if (showDialog) {
            showDialog = false;
            setCreateDialog();
        }

    }

    private BaseCustomDialog<DialogCreateAdBinding> dialogCreateAd;

    @SuppressLint("SetTextI18n")
    private void setCreateDialog() {
        ownerType = "";
        dialogCreateAd = new BaseCustomDialog<>(requireContext(), R.layout.dialog_create_ad, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            if (ownerType.isEmpty() || ownerType.equalsIgnoreCase("")) {
                                viewModel.error.setValue("Please select ownerType.");
                                // dialogCreateAd.dismiss();
                                return;
                            }
//                            if (isEdit && imgIdList.size() > 0) {
//                                viewModel.deletePostImage(removeImgId);
//                            } else {
                            if (isNetworkAvailable(getActivity())) {

                               if(!ownerType.equalsIgnoreCase(getActivity().getResources().getString(R.string.private_text))) {
                                   marketActivity.marketPostModel = new MarketPostModel();
                                   marketActivity.marketPostModel.setLocation(address);
                                   marketActivity.marketPostModel.setLatitude(lati);
                                   marketActivity.marketPostModel.setLongitude(longi);
                                   marketActivity.marketPostModel.setPhone(phone);
                                   marketActivity.marketPostModel.setCategory(selected_category);
                                   marketActivity.marketPostModel.setCountry_code(country_code);
                               }else {
                                   marketActivity.marketPostModel = new MarketPostModel();
                                   marketActivity.marketPostModel.setLocation("");
                                   marketActivity.marketPostModel.setLatitude("");
                                   marketActivity.marketPostModel.setLongitude("");
                                   marketActivity.marketPostModel.setPhone("");
                                   marketActivity.marketPostModel.setCategory("");
                                   marketActivity.marketPostModel.setCountry_code("");
                               }
                                Fragment fragment = CreateAdFragment.newInstance();
                                marketActivity.addSubFragment(CreateAdFragment.TAG, fragment);

                                sharedPref.put("shop_id", shop_id);
                                sharedPref.put("owner_type", ownerType);


                            } else {
                                noNetworkToast(getActivity());
                            }

//                            }
                            dialogCreateAd.dismiss();
                            break;
                        case R.id.iv_cancel:
                            dialogCreateAd.dismiss();
                            break;

                        case R.id.ivCreateCompany:
//                            ShopProfile profile = new ShopProfile();
//                            profile.setPhone(binding.edtPhone.getText().toString());
//                            profile.setAddress(binding.etAddress.getText().toString());
//                            profile.setLatitude(lat);
//                            profile.setLongitude(lng);
                            Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans);
                            startActivityForResult(intent, CreateShopRequestCode);
                            dialogCreateAd.dismiss();
                            break;
                    }
                }
            }
        });

        dialogCreateAd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogCreateAd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogCreateAd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogCreateAd.show();

//        dialogCreateAd.getBinding().txtShopName.setText(sharedPref.get(Constants.SHOP_NAME, "Shop name")
//                + "\n" + sharedPref.get(Constants.SHOP_ADDRESS, "Address"));

        companyAdapter = new CompanyAdapter(HomeFragmentMarket.this, companyNameList,
                new CompanyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int item) {
                    }


                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dialogCreateAd.getBinding().rvCompanies.setLayoutManager(layoutManager);
        dialogCreateAd.getBinding().rvCompanies.setAdapter(companyAdapter);


        dialogCreateAd.getBinding().rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPrivate) {
                    dialogCreateAd.getBinding().rbCommercial.setChecked(false);
                    dialogCreateAd.getBinding().rbCommercial.setTextColor(requireContext().getResources().getColor(R.color.black));
                    dialogCreateAd.getBinding().rbPrivate.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    ownerType = "Private";
                    dialogCreateAd.getBinding().rlCompanies.setVisibility(View.GONE);
                    shop_id = "";
                } else {
                    dialogCreateAd.getBinding().rbPrivate.setChecked(false);
                    dialogCreateAd.getBinding().rbPrivate.setTextColor(requireContext().getResources().getColor(R.color.black));
                    dialogCreateAd.getBinding().rbCommercial.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    ownerType = "Commercial";

                    if (companyNameList.size() > 0) {
                        shop_id = companyNameList.get(0).getShop_id();
                        dialogCreateAd.getBinding().rlCompanies.setVisibility(View.VISIBLE);
                    } else {
                        dialogCreateAd.getBinding().rlCompanies.setVisibility(View.GONE);

                    }
                    if (sharedPref.get(Constants.SHOP_NAME, "").equalsIgnoreCase("")) {
//                        ShopProfile profile = new ShopProfile();
//                        profile.setPhone(binding.edtPhone.getText().toString());
//                        profile.setAddress(binding.etAddress.getText().toString());
//                        profile.setLatitude(lat);
//                        profile.setLongitude(lng);
                        Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans);
                        startActivityForResult(intent, CreateShopRequestCode);
                        dialogCreateAd.dismiss();
                    }
                }
            }
        });

//        if (isEdit) {
//            if (marketActivity.marketPostModel.getOwnerType().equalsIgnoreCase("Private")) {
//                dialogCreateAd.getBinding().rbPrivate.setChecked(true);
//            } else {
//                dialogCreateAd.getBinding().rbCommercial.setChecked(true);
//            }
//        }

    }

    private void searchHomeData() {
        if (privateSell == true) {
            private_saller = "Private";
        } else {
            private_saller = "";

        }
        if (commercial == true) {
            commercial_seller = "Commercial";
        } else {
            commercial_seller = "";

        }
        if (sell == true) {
            only_sale = "Sale";
        } else {
            only_sale = "";

        }
        if (rental == true) {
            only_rent = "Rent";
        } else {
            only_rent = "";

        }

        if (newProduct == true) {
            productType = "New";
        }
        if (usedProdut == true) {
            productType = "Used";
        }
        if (usedProdut && newProduct) {
            productType = "";
        }

        if (marketActivity.fromMyProduct) {
            marketActivity.fromMyProduct = false;

            viewModel.getHomeList(marketActivity.lati, marketActivity.longi,

                    searchItem + "", categoryName + "", private_saller, commercial_seller, only_sale, only_rent,
                    sharedPref.get(Constants.RADIUS1, "0"), sharedPref.get(Constants.RADIUS2, "10"), price_min, price_max, productType);
        } else {
            viewModel.getHomeList(sharedPref.get(Constants.SELECT_LATITUDE, "0.00"), sharedPref.get(Constants.SELECT_LONGITUDE, "0.00"),
                    searchItem + "", categoryName + "", private_saller, commercial_seller, only_sale, only_rent,
                    sharedPref.get(Constants.RADIUS1, "0"), sharedPref.get(Constants.RADIUS2, "10"), price_min, price_max, productType);
        }

    }


    void filterLocation() {
        if (marketHomeModel.getData() == null) {
            return;
        }
        List<MarketPostModel> newDataList = new ArrayList<>();


        for (MarketPostModel d : marketHomeModel.getData()) {
            if ((d.getLatitude() + d.getLongitude() + d.getTitle()).equalsIgnoreCase(marketActivity.latLngFilter)) {
                newDataList.add(d);
            }
        }

        marketActivity.latLngFilter = "";

        searchDataList.clear();
        searchDataList.addAll(newDataList);
        adapter.setList(searchDataList);
        adapterNoAds.setList(searchDataList);
        setMarkerOnMap(searchDataList);

    }

    void filter() {
        if (marketHomeModel.getData() == null) {
            return;
        }

        if (priceFilter.equalsIgnoreCase("lowToHighPrice")) {
            Collections.sort(searchDataList, (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
        } else if (priceFilter.equalsIgnoreCase("highToLowPrice")) {
            Collections.sort(searchDataList, (p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
        }
        if (dateFilter.equalsIgnoreCase("lowToHighDate")) {
            Collections.sort(searchDataList, (p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()));
        } else if (dateFilter.equalsIgnoreCase("highToLowDate")) {
            Collections.sort(searchDataList, (p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
        }

        adapter.setList(searchDataList);
        adapterNoAds.setList(searchDataList);
        setMarkerOnMap(searchDataList);

        if (searchDataList.size() == 0 && marketActivity.isList) {
            binding.txtNoRecord.setVisibility(View.VISIBLE);
        }
    }


    private void setRecyclerView() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvList.setLayoutManager(layoutManager);
        binding.rvList.setAdapter(adapter);
        binding.rvListBottom.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rvListBottom.setAdapter(adapterNoAds);
        layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.bottomSheetBehaviorId.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.bottomSheetBehaviorId.rvList.setAdapter(adapter);

        if (layoutManager.findLastCompletelyVisibleItemPosition() == searchDataList.size() - 1) {
            //bottom of list!
            binding.txtDone.performLongClick();
//            viewModel.getHomeList(sharedPref.get(Constants.SELECT_LATITUDE, ""), sharedPref.get(Constants.SELECT_LONGITUDE, ""),
//                    searchItem+"", categoryName+"", sharedPref.get(Constants.RADIUS, "10"));

        }
    }

    AdapterMarketHome adapter = new AdapterMarketHome(new AdapterMarketHome.ClickCallback() {
        @Override
        public void onItemClick(View v, MarketPostModel postModel) {
            switch (v != null ? v.getId() : 0) {
                case R.id.relName:
                case R.id.txtDesc:
                case R.id.txtPrice:
                    marketActivity.marketPostModel = postModel;
                    marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance());
                    break;

                case R.id.imgFav:
                    selectPostModel = postModel;
                    viewModel.addToFavourite(String.valueOf(postModel.getId()), postModel.getFav() == 0 ? "1" : "0");
                    break;
            }
        }
    });
    AdapterMarketHome adapterNoAds = new AdapterMarketHome(false, new AdapterMarketHome.ClickCallback() {
        @Override
        public void onItemClick(View v, MarketPostModel postModel) {
            switch (v != null ? v.getId() : 0) {
                case R.id.ivImage:
                case R.id.relName:
                case R.id.txtDesc:
                case R.id.txtPrice:
                    marketActivity.marketPostModel = postModel;
                    marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance());
                    break;

                case R.id.imgFav:
                    selectPostModel = postModel;
                    viewModel.addToFavourite(String.valueOf(postModel.getId()), postModel.getFav() == 0 ? "1" : "0");
                    break;
            }
        }
    });


    private void setMarkerOnMap(List<MarketPostModel> postModelList) {

        if (googleMap == null)
            return;
        googleMap.clear();
        if (postModelList == null)
            postModelList = new ArrayList<>();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        Marker marker = null;
        for (int i = 0; i < postModelList.size(); i++) {

            Log.d(TAG, "=" + postModelList.get(i).getLocation());

            if (!postModelList.get(i).getLatitude().equalsIgnoreCase("")
                    && !postModelList.get(i).getLongitude().equalsIgnoreCase("")
                    && !postModelList.get(i).getLatitude().equalsIgnoreCase("null")
                    && !postModelList.get(i).getLongitude().equalsIgnoreCase("null")
            ) {
                Log.e("ADD_MARKERERE","setMarkerOnMap");
                LatLng latLng = new LatLng(Double.parseDouble(postModelList.get(i).getLatitude()), Double.parseDouble(postModelList.get(i).getLongitude()));


                //Private,Commercial
                if (postModelList.get(i).getOwnerType().equalsIgnoreCase("Commercial")) {
                    marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)

                            .icon(BitmapDescriptorFactory.fromBitmap(createMarkerBitmap(marker, postModelList.get(i).getShopProfile().getCompanyName()))));
                    marker.setTag(i);
                } else {

//                   Log.d(TAG,"PRIVATE_MEMBER"+postModelList.get(i).getTitle());
//                    marker.setTitle(postModelList.get(i).getTitle());
//                    googleMap.addCircle(new CircleOptions()
//                            .center(latLng)
//                            .radius(Double.parseDouble("0.5") * 1000)
//                            .strokeColor(this.getResources().getColor(R.color.color_orange))
//                            .fillColor(this.getResources().getColor(R.color.yellow)));
                }

                builder.include(latLng);
            }

        }

        setMarkerOnMap();

    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 14;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
  Log.e("SADASDASDASDAS", String.valueOf(zoomLevel));
        return zoomLevel;
    }
    private void setMarkerOnMap() {

        double latitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LATITUDE, "0.00"));
        double longitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LONGITUDE, "0.00"));

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(Double.parseDouble(sharedPref.get(Constants.RADIUS2, "10")) * 1000)
                .strokeColor(this.getResources().getColor(R.color.colorPrimary))
                .fillColor(this.getResources().getColor(R.color.colorPrimaryTrans)));


        CameraPosition pos = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(18)
                .build();

        LatLng latLng= new LatLng(latitude, longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));


        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                sharedPref.put(Constants.SELECT_LATITUDE, String.valueOf(latLng.latitude));
                sharedPref.put(Constants.SELECT_LONGITUDE, String.valueOf(latLng.longitude));
                searchHomeData();//googleMapClick
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CameraPosition pos = new CameraPosition.Builder()
                        .target(marker.getPosition())
                        .zoom(18)
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                marketActivity.marketPostModel = searchDataList.get((int) marker.getTag());

                if (marketActivity.marketPostModel.getOwnerType().equalsIgnoreCase("Commercial")) {
                    marketActivity.addSubFragment(UserPostFragment.TAG, UserPostFragment.newInstance(String.valueOf(marketActivity.marketPostModel.getUser().getId()),
                            marketActivity.marketPostModel.getUser().getName(), marketActivity.marketPostModel.getShopProfile()));
                } else {
                    marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance());
                }
                return false;
            }
        });


        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.d(TAG, "onInfoWindowClick: " + marker.getTitle());
                marketActivity.marketPostModel = searchDataList.get((int) marker.getTag());

                if (marketActivity.marketPostModel.getOwnerType().equalsIgnoreCase("Commercial")) {
                    marketActivity.addSubFragment(UserPostFragment.TAG, UserPostFragment.newInstance(String.valueOf(marketActivity.marketPostModel.getUser().getId()),
                            marketActivity.marketPostModel.getUser().getName(), marketActivity.marketPostModel.getShopProfile()));
                } else {
                    marketActivity.addSubFragment(HomeListDetailFragment.TAG, HomeListDetailFragment.newInstance());
                }


            }
        });


//        googleMap.setMaxZoomPreference(14f);


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

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success =
                    googleMap.setMapStyle(new MapStyleOptions
                            (getActivity().getResources()
                                    .getString(R.string.style_json)));

            if (!success) {
                Log.d("TAG", "Style parsing failed.");
            }
            if (!success) {
                Log.d("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.d("TAG", "Can't find style. Error: ", e);
        }


        setMyLocation();
        setMarkerOnMap(searchDataList);

    }


    private void setMyLocation() {

        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    private void getAddressFromLatLong(double lat, double lng) {

        Geocoder gcd = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                sharedPref.put(Constants.LOCATION, addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private BaseCustomDialog<DialogHomeFilterBinding> dialogHomeFilter;

    private void filterOptionDialog() {
        marketActivity.clearEdtSearch();
        dialogHomeFilter = new BaseCustomDialog<>(requireContext(), R.layout.dialog_home_filter, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            privateSell = dialogHomeFilter.getBinding().cbPrivate.isChecked();
                            commercial = dialogHomeFilter.getBinding().cbCommercial.isChecked();
                            rental = dialogHomeFilter.getBinding().cbRental.isChecked();
                            sell = dialogHomeFilter.getBinding().cbSell.isChecked();


                            price = dialogHomeFilter.getBinding().rbPrice.isChecked();
                            distance = dialogHomeFilter.getBinding().rbDistance.isChecked();
                            date = dialogHomeFilter.getBinding().rbDate.isChecked();
                            newProduct = dialogHomeFilter.getBinding().cbNew.isChecked();
                            usedProdut = dialogHomeFilter.getBinding().cbUsed.isChecked();
                            Log.d("ASDSA1", String.valueOf(newProduct));
                            Log.d("ASDSA12", String.valueOf(usedProdut));

                            //  sharedPref.put(Constants.RADIUS2, String.valueOf(dialogHomeFilter.getBinding().seekBar.getProgress()));
                            binding.seekBar.setProgress(Integer.parseInt(sharedPref.get(Constants.RADIUS2, "20")));


                            dialogHomeFilter.dismiss();

                            searchHomeData();//filter
                            break;
                        case R.id.iv_cancel:
                            dialogHomeFilter.dismiss();
                            break;

                        case R.id.btn_clear:
                            clearAllFilters();

                            dialogHomeFilter.dismiss();
                            break;
                    }
                }
            }
        });

        dialogHomeFilter.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogHomeFilter.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogHomeFilter.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogHomeFilter.show();

        dialogHomeFilter.getBinding().cbPrivate.setChecked(privateSell);
        dialogHomeFilter.getBinding().cbCommercial.setChecked(commercial);
        dialogHomeFilter.getBinding().cbRental.setChecked(rental);
        dialogHomeFilter.getBinding().cbSell.setChecked(sell);
        dialogHomeFilter.getBinding().cbNew.setChecked(newProduct);
        dialogHomeFilter.getBinding().cbUsed.setChecked(usedProdut);
        if (!price_min.equalsIgnoreCase("")) {
            dialogHomeFilter.getBinding().etPriceMin.setText(price_min);
        }
        if (!price_max.equalsIgnoreCase("")) {
            dialogHomeFilter.getBinding().etPriceMax.setText(price_max);
        }

        dialogHomeFilter.getBinding().rbPrice.setChecked(price);
        dialogHomeFilter.getBinding().rbDistance.setChecked(distance);
        dialogHomeFilter.getBinding().rbDate.setChecked(date);

        dialogHomeFilter.getBinding().seekBar.setProgress(Integer.parseInt(sharedPref.get(Constants.RADIUS2, "20")));
        dialogHomeFilter.getBinding().txtRadius.setText("Radius: " + "" + (float) dialogHomeFilter.getBinding().seekBar.getProgress() / 2 + " KM");

        if (priceFilter.equalsIgnoreCase("lowToHighPrice")) {
            dialogHomeFilter.getBinding().rbLowToHighPrice.setChecked(true);
        } else if (priceFilter.equalsIgnoreCase("highToLowPrice")) {
            dialogHomeFilter.getBinding().rbHighToLowPrice.setChecked(true);
        }
        if (dateFilter.equalsIgnoreCase("lowToHighDate")) {
            dialogHomeFilter.getBinding().rbLowToHighDate.setChecked(true);
        } else if (dateFilter.equalsIgnoreCase("highToLowDate")) {
            dialogHomeFilter.getBinding().rbHighToLowDate.setChecked(true);
        }


        dialogHomeFilter.getBinding().rgPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbHighToLowPrice) {
                    dialogHomeFilter.getBinding().rbLowToHighPrice.setChecked(false);
//                    dialogHomeFilter.getBinding().rbLowToHighPrice.setTextColor(requireContext().getResources().getColor(R.color.black));
//                    dialogHomeFilter.getBinding().rbHighToPrice.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    priceFilter = "highToLowPrice";
                } else {
                    dialogHomeFilter.getBinding().rbHighToLowPrice.setChecked(false);
//                    dialogHomeFilter.getBinding().rbHighToPrice.setTextColor(requireContext().getResources().getColor(R.color.black));
//                    dialogHomeFilter.getBinding().rbLowToHighPrice.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    priceFilter = "lowToHighPrice";
                }
            }
        });
        dialogHomeFilter.getBinding().rgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPrice) {
                    dialogHomeFilter.getBinding().rbLowToHighPrice.setChecked(false);
//                    dialogHomeFilter.getBinding().rbLowToHighPrice.setTextColor(requireContext().getResources().getColor(R.color.black));
//                    dialogHomeFilter.getBinding().rbHighToPrice.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    priceFilter = "lowToHighPrice";
                } else if (checkedId == R.id.rbDate) {
//                    dialogHomeFilter.getBinding().rbHighToLowPrice.setChecked(false);
////                    dialogHomeFilter.getBinding().rbHighToPrice.setTextColor(requireContext().getResources().getColor(R.color.black));
////                    dialogHomeFilter.getBinding().rbLowToHighPrice.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
//                    priceFilter = "lowToHighPrice";
                    dateFilter = "lowToHighDate";

                }
            }
        });
        dialogHomeFilter.getBinding().rgDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbHighToLowDate) {
                    dialogHomeFilter.getBinding().rbLowToHighDate.setChecked(false);
                    dateFilter = "highToLowDate";
                } else {
                    dialogHomeFilter.getBinding().rbHighToLowDate.setChecked(false);
                    dateFilter = "lowToHighDate";
                }
            }
        });


        dialogHomeFilter.getBinding().seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dialogHomeFilter.getBinding().txtRadius.setText("Radius: " + "" + (float) progress / 2 + " KM");
                if (circle != null) {
                    circle.setRadius((float) progress / 2 * 1000);
                    double latitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LATITUDE, "0.00"));
                    double longitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LONGITUDE, "0.00"));
                    LatLng latLng= new LatLng(latitude, longitude);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));
                }
                sharedPref.put(Constants.RADIUS2, String.valueOf("" + (int) progress/2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialogHomeFilter.getBinding().etPriceMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                price_min = String.valueOf(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialogHomeFilter.getBinding().etPriceMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                price_max = String.valueOf(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//        dialogHomeFilter.getBinding().seekBarRangePrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
//            @Override
//            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
//                price_min = leftPinValue;
//                price_max = rightPinValue;
//            }
//
//            @Override
//            public void onTouchEnded(RangeBar rangeBar) {
//
//            }
//
//            @Override
//            public void onTouchStarted(RangeBar rangeBar) {
//
//            }
//        });

//        if (!price_min.equalsIgnoreCase("0")) {
//            dialogHomeFilter.getBinding().seekBarRangePrice.setRangePinsByValue(Float.parseFloat(price_min), Float.parseFloat(price_max));
//        }
    }

    private void clearAllFilters() {
        dialogHomeFilter.getBinding().cbPrivate.setChecked(false);
        dialogHomeFilter.getBinding().cbCommercial.setChecked(false);
        dialogHomeFilter.getBinding().cbRental.setChecked(false);
        dialogHomeFilter.getBinding().cbSell.setChecked(false);
        dialogHomeFilter.getBinding().cbNew.setChecked(false);
        dialogHomeFilter.getBinding().cbUsed.setChecked(false);

        dialogHomeFilter.getBinding().rbPrice.setChecked(false);
        dialogHomeFilter.getBinding().rbDistance.setChecked(false);
        dialogHomeFilter.getBinding().rbDate.setChecked(false);


        privateSell = false;
        commercial = false;
        rental = false;
        sell = false;
        price = false;
        distance = false;
        date = false;
        newProduct = false;
        usedProdut = false;
        productType = "";
        private_saller = "";
        commercial_seller = "";
        only_sale = "";
        only_rent = "";

        priceFilter = "";
        dateFilter = "";
        searchItem = "";
        price_min = "";

//        dialogHomeFilter.getBinding().seekBarRangePrice.setRangePinsByValue(0, 5000);

        price_max = "";
        searchHomeData();//clearfilter

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CategoryRequestCode) {

                categoryName = data.getStringExtra(Constants.CATEGORY_NAME);

                for (int i = 0; i < marketActivity.categoryListBeans.getData().size(); i++) {
                    if (i < 4) {
                        imgCategory[i].setBackground(requireContext().getResources().getDrawable(R.drawable.circle_blue_light));
                        if (categoryName.equalsIgnoreCase(marketActivity.categoryListBeans.getData().get(i).getName())) {
                            imgCategory[i].performClick();
                        }
                    }
                }

                searchHomeData();//onActivityResult

            } else if (requestCode == CreateShopRequestCode) {
                showDialog = data.getBooleanExtra(Constants.HIT_API, false);
                Log.d("onActivityResult", String.valueOf(data.getBooleanExtra(Constants.HIT_API, false)));
                if (showDialog) {
                    showDialog = false;
                    viewModel.getMarketShopProfile(this);
                    setCreateDialog();
                }
            }
        }
    }

    public void loadBannerAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
        binding.adView.setAdListener(new

                                             AdListener() {
                                                 @Override
                                                 public void onAdLoaded() {
                                                     // Code to be executed when an ad finishes loading.

                                                     Log.i(TAG, "Ads_StatusMain : Finished");
                                                 }

                                                 @Override
                                                 public void onAdFailedToLoad(int errorCode) {
                                                     // Code to be executed when an ad request fails.
                                                     Log.i(TAG, "Ads_StatusMain : Fail : " + errorCode);
                                                 }

                                                 @Override
                                                 public void onAdOpened() {
                                                     // Code to be executed when an ad opens an overlay that
                                                     // covers the screen.
                                                     Log.i(TAG, "Ads_StatusMain : An ad opens an overlay");
                                                 }

                                                 @Override
                                                 public void onAdLeftApplication() {
                                                     // Code to be executed when the user has left the app.
                                                     Log.i(TAG, "Ads_StatusMain : Left the App");
                                                 }

                                                 @Override
                                                 public void onAdClosed() {
                                                     // Code to be executed when when the user is about to return
                                                     // to the app after tapping on an ad.
                                                     Log.i(TAG, "Ads_StatusMain : Close");
                                                 }
                                             });
    }


}
