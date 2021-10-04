package com.marius.valeyou.localMarketModel.ui.fragment.home;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.appyvet.materialrangebar.RangeBar;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogHomeFilterBinding;
import com.marius.valeyou.databinding.FragmentSearchMarkerBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.userPost.UserPostFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.marius.valeyou.util.AppUtils.hideKeyboard;

public class SearchMarkerFragment extends AppFragment<FragmentSearchMarkerBinding, HomeFragmentMarketVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    public static final String TAG = "SearchMarkerFragment";

    double latitude;
    double longitude;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    @Inject
    SharedPref sharedPref;

    MainLocalMarketActivity marketActivity;

    private MarketHomeModel marketHomeModel;
    List<MarketPostModel> searchDataList = new ArrayList<>();
    Bitmap bitmapMarker;

    Circle circle;
    String radius1 = "0";
    String radius2 = "5";


    public static Fragment newInstance() {
        return new SearchMarkerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getCurrentLocation();
    }

    @Override
    protected BindingFragment<HomeFragmentMarketVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_search_marker, HomeFragmentMarketVM.class);
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
    protected void subscribeToEvents(final HomeFragmentMarketVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.search_location);

        bitmapMarker = drawableToBitmap(requireContext().getResources().getDrawable(R.drawable.ic_map_pin));

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            MapsInitializer.initialize(requireContext());
        }

        latitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LATITUDE, "0.00"));
        longitude = Double.parseDouble(sharedPref.get(Constants.SELECT_LONGITUDE, "0.00"));

        radius1 = sharedPref.get(Constants.RADIUS1, "0");
        radius2 = sharedPref.get(Constants.RADIUS2, "10");

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.txtRadius.setText("Radius: " + progress + " KM");
                if (circle != null) {
                    circle.setRadius(progress * 1000);
                }

                sharedPref.put(Constants.RADIUS2, String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar.setProgress(Integer.parseInt(sharedPref.get(Constants.RADIUS2, "10")));

        binding.etAddress.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull com.seatgeek.placesautocomplete.model.Place place) {
                binding.etAddress.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails placeDetails) {

                        latitude = placeDetails.geometry.location.lat;
                        longitude = placeDetails.geometry.location.lng;
                        Log.d("SelectWorkArea", "Location " + placeDetails);
                        hideKeyboard(requireActivity());
                        searchHomeData();

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        });


        binding.etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i(TAG, "Enter pressed_actionId: " + actionId + " == " + EditorInfo.IME_ACTION_DONE);
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    searchHomeData();
                }
                return false;
            }
        });


        marketActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {

            }
        };


        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.txtDone:
                    searchHomeData();
                    break;

                case R.id.imgCurrentLocation:
                    latitude = Double.parseDouble(sharedPref.get(Constants.LATITUDE, "0.00"));
                    longitude = Double.parseDouble(sharedPref.get(Constants.LONGITUDE, "0.00"));
                    binding.etAddress.setText("");
                    searchHomeData();
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
                            latitude = mCurrentlocation.getLatitude();
                            longitude = mCurrentlocation.getLongitude();
                            binding.txtDone.performLongClick();
                        }
                        vm.liveLocationDetecter.removeLocationUpdates();
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
                        setMarkerOnMap(searchDataList);
                        Log.d(TAG, "searchDataListSize " + searchDataList.size());

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

        vm.getHomeList(sharedPref.get(Constants.SELECT_LATITUDE, ""), sharedPref.get(Constants.SELECT_LONGITUDE, ""),
                "", "","","","","",
                sharedPref.get(Constants.RADIUS1, "0"), sharedPref.get(Constants.RADIUS2, "10"),"","","");
    }

    private void searchHomeData() {
        viewModel.getHomeList(String.valueOf(latitude), String.valueOf(longitude), "", "","","","","", radius1, radius2,"","","");

        sharedPref.put(Constants.SELECT_LATITUDE, String.valueOf(latitude));
        sharedPref.put(Constants.SELECT_LONGITUDE, String.valueOf(longitude));
        sharedPref.put(Constants.RADIUS1, radius1);
        sharedPref.put(Constants.RADIUS2, radius2);

    }


    private void setMarkerOnMap(List<MarketPostModel> postModelList) {

        if (googleMap == null)
            return;
        googleMap.clear();
        if (postModelList == null)
            postModelList = new ArrayList<>();
        LatLngBounds.Builder builder = LatLngBounds.builder();

        for (int i = 0; i < postModelList.size(); i++) {

            Log.d(TAG, "=" + postModelList.get(i).getLocation());

            if (!postModelList.get(i).getLatitude().equalsIgnoreCase("")
                    && !postModelList.get(i).getLongitude().equalsIgnoreCase("")
                    && !postModelList.get(i).getLatitude().equalsIgnoreCase("null")
                    && !postModelList.get(i).getLongitude().equalsIgnoreCase("null")
            ) {

                if (postModelList.get(i).getOwnerType().equalsIgnoreCase("Commercial")) {
                    LatLng latLng = new LatLng(Double.parseDouble(postModelList.get(i).getLatitude()), Double.parseDouble(postModelList.get(i).getLongitude()));
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmapMarker)));
                    marker.setTag(i);
                    marker.setTitle(postModelList.get(i).getShopProfile().getCompanyName());
                    builder.include(latLng);
                }


//                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(driverBeans.get(i).getI(), 1))));
                // googleMap.setInfoWindowAdapter(new CustomMarkerInfoWindowView(getActivity(),driverBeans.get(i).getFirstName()+" "+driverBeans.get(i).getLastName()));

            }

        }
        setMarkerOnMap();

    }


    private void setMarkerOnMap() {

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(Double.parseDouble(radius2) * 1000)
                .strokeColor(this.getResources().getColor(R.color.colorPrimary))
                .fillColor(this.getResources().getColor(R.color.colorPrimaryTrans)));

//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .title("")
//                .icon(BitmapDescriptorFactory.fromBitmap(bitmapMarker)));


        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

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

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                binding.etAddress.setText("");
                searchHomeData();
            }
        });

        CameraPosition pos = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));

        googleMap.setMaxZoomPreference(13);

    }


    private GoogleMap.OnCameraIdleListener onCameraIdleListener = () -> {

    };

    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener = i -> {

    };


    @Override
    public void onMapClick(LatLng latLng) {
//        latitude = latLng.latitude;
//        longitude = latLng.longitude;
//        setMarkerOnMap();
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


    private BaseCustomDialog<DialogHomeFilterBinding> dialogHomeFilter;

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
}
