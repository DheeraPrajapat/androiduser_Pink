package com.marius.valeyou.ui.activity.post_detail_sub;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySubMapBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class SubMapActivity extends AppActivity<ActivitySubMapBinding, SubMapVM> implements OnMapReadyCallback {
    public static final String TAG = "DetailMapFragment";

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    Bitmap bitmapMarker;
    Bitmap bitmapMarkerTrans;
    String lati = "", longi = "", tag_id = "", lat_lng_filter = "";
    @Inject
    SharedPref sharedPref;
    private MarketHomeModel marketHomeModel;
    List<MarketPostModel> searchDataList = new ArrayList<>();
    List<MarketPostModel> searchDataListBean = new ArrayList<>();

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SubMapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<SubMapVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_sub_map, SubMapVM.class);
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
    protected void subscribeToEvents(final SubMapVM vm) {
        Intent intent = getIntent();
        if (intent != null) {
            lati = intent.getStringExtra("lati");
            longi = intent.getStringExtra("longi");
            tag_id = intent.getStringExtra("tag_id");
            lat_lng_filter = intent.getStringExtra("lat_lng_filter");
        }

        if (lati != null && longi != null) {
            searchHomeData();
            loadBannerAds();
        }

        bitmapMarker = drawableToBitmap(getResources().getDrawable(R.drawable.ic_map_pin));
        bitmapMarkerTrans = drawableToBitmap(getResources().getDrawable(R.drawable.ic_map_pin_transparent));

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            MapsInitializer.initialize(this);
        }

        //sharedPref.put(Constants.RADIUS1, "20");
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

//                case R.id.txtDone:
//                    searchHomeData();
//                    break;


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
                            locCallback.getApiException().startResolutionForResult(SubMapActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
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
                        }
                        vm.liveLocationDetecter.removeLocationUpdates();
                        getAddressFromLatLong(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude());
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


                        if (lat_lng_filter.length() > 0) {
                            filterLocation();
                            for (int i = 0; i < searchDataList.size(); i++) {
                                if (searchDataList.get(i).getTagId().equalsIgnoreCase(tag_id)) {
                                    googleMap.clear();
                                    LatLng latLng = new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude()));
                                    Marker marker = googleMap.addMarker(new MarkerOptions()
                                            .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmapMarkerTrans)));
                                    marker.setTag(i);
                                    marker.setTitle(searchDataList.get(i).getTitle());
                                    if (searchDataList.get(i).getOwnerType().equalsIgnoreCase(SubMapActivity.this.getResources().getString(R.string.private_text))) {
                                        googleMap.addCircle(new CircleOptions()
                                                .center(latLng)
                                                .radius(Double.parseDouble("1") * 1000)
                                                .strokeColor(getResources().getColor(R.color.color_orange))
                                                .fillColor(getResources().getColor(R.color.yellow)));
                                        CameraPosition pos = new CameraPosition.Builder()
                                                .target(new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude())))
                                                .zoom(14)
                                                .build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                                    } else {

                                        googleMap.addCircle(new CircleOptions()
                                                .center(latLng)
                                                .radius(Double.parseDouble("1") * 1000)
                                                .strokeColor(getResources().getColor(R.color.colorPrimary))
                                                .fillColor(getResources().getColor(R.color.colorPrimaryTrans)));

                                        Marker marker1 = googleMap.addMarker(new MarkerOptions()
                                                .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmapMarker)));
                                        marker1.setTag(i);
                                        marker1.setTitle(searchDataList.get(i).getTitle());

                                        CameraPosition pos = new CameraPosition.Builder()
                                                .target(new LatLng(Double.parseDouble(searchDataList.get(i).getLatitude()), Double.parseDouble(searchDataList.get(i).getLongitude())))
                                                .zoom(18)
                                                .build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                                    }


                                    //googleMap.setMaxZoomPreference(14f);

                                }

                            }

                        } else {
//                            filter();
                        }
//                        adapter.setList(searchDataList);
//                        setMarkerOnMap(searchDataList);

//                        if (searchDataList.size() > 0) {
//                            binding.txtNoRecord.setVisibility(View.GONE);
//                            binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.VISIBLE);
//
//                        } else {
//                            binding.txtNoRecord.setVisibility(View.VISIBLE);
//                            binding.bottomSheetBehaviorId.llBottomSheet.setVisibility(View.GONE);
//
//                        }
//                        if (marketActivity.isList) {
//                            binding.txtList.performClick();
//                        }
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
                            Intent in = LoginActivity.newIntent(SubMapActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


    }

    private void getAddressFromLatLong(double lat, double lng) {

        Geocoder gcd = new Geocoder(SubMapActivity.this, Locale.getDefault());
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

    private void searchHomeData() {
        viewModel.getHomeList(lati, longi,
                "", "", "", "", "", "",
                "0", "20", "", "", "");

    }

    void filterLocation() {
        if (marketHomeModel.getData() == null) {
            return;
        }
        List<MarketPostModel> newDataList = new ArrayList<>();


        for (MarketPostModel d : marketHomeModel.getData()) {
            if ((d.getLatitude() + d.getLongitude() + d.getTitle()).equalsIgnoreCase(lat_lng_filter)) {
                newDataList.add(d);
            }
        }

        lat_lng_filter = "";

        searchDataList.clear();
        searchDataList.addAll(newDataList);
//        adapter.setList(searchDataList);
//        adapterNoAds.setList(searchDataList);
//        setMarkerOnMap(searchDataList);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        try {

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        } catch (Exception e) {


        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success =
                    googleMap.setMapStyle(new MapStyleOptions
                            (getResources()
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
        //  setMarkerOnMap(searchDataList);

    }

    private void setMyLocation() {

        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(SubMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SubMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
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