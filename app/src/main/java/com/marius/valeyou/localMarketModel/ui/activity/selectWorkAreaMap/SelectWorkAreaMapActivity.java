package com.marius.valeyou.localMarketModel.ui.activity.selectWorkAreaMap;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivitySelectWorkAreaMapBinding;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectWorkAreaMapActivity extends BaseActivity<ActivitySelectWorkAreaMapBinding, SelectWorkAreaMapVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, LocationListener {

    public static final String TAG = "SelectWorkAreaMap";
    private Location mCurrentlocation;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    String myLocation;
    String city;
    String state;
    String zipcode;
    String radius = "10";
    double latitude;
    double longitude;
    CountDownTimer countDownTimer;
    Circle circle;

    @Override
    protected BindingActivity<SelectWorkAreaMapVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_select_work_area_map, SelectWorkAreaMapVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SelectWorkAreaMapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void subscribeToEvents(SelectWorkAreaMapVM vm) {
        String lat = this.getIntent().getStringExtra(Constants.LATITUDE);
        String lng = this.getIntent().getStringExtra(Constants.LONGITUDE);

        if (lat.contains(".")) {
            myLocation = this.getIntent().getStringExtra(Constants.LOCATION);
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lng);
            radius = this.getIntent().getStringExtra(Constants.RADIUS);

            countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    if (googleMap == null) {
                        countDownTimer.start();
                    } else {
                        setMarkerOnMap(new LatLng(latitude, longitude));
                        getAddressFromLatLong(latitude, longitude);
                    }
                }
            };

            countDownTimer.start();

        } else {
            getCurrentLocation();
        }

        binding.tvTitle.setText("  Select Work Area  ");
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()) {

                    case R.id.saveBtn:

                        if (myLocation != null) {
                            Intent intent1 = new Intent();
                            intent1.putExtra(Constants.LOCATION, myLocation);
                            intent1.putExtra(Constants.LATITUDE, String.valueOf(latitude));
                            intent1.putExtra(Constants.LONGITUDE, String.valueOf(longitude));
                            intent1.putExtra(Constants.RADIUS, radius);
                            setResult(RESULT_OK, intent1);
                            finish();
                        }

                        break;
                }
            }
        });

        binding.seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius = String.valueOf(progress);
                binding.txtRadius.setText(radius + " KM");
                if (circle != null) {
                    circle.setRadius(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        if (mapFragment == null) {
            mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_view));

            if (mapFragment == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDV_Ik-g8KxKqALt9v0VmO3v8_NZkahW8A", Locale.US);
        }


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
                            locCallback.getApiException().startResolutionForResult(SelectWorkAreaMapActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        break;
                    case FOUND:

                        mCurrentlocation = locCallback.getLocation();
                        vm.liveLocationDetecter.removeLocationUpdates();
                        latitude = mCurrentlocation.getLatitude();
                        longitude = mCurrentlocation.getLongitude();
                        setMarkerOnMap(new LatLng(latitude, longitude));
                        getAddressFromLatLong(latitude, longitude);
                        break;
                }


            }
        });

        binding.etAddress.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull com.seatgeek.placesautocomplete.model.Place place) {
                binding.etAddress.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails placeDetails) {

                        latitude = placeDetails.geometry.location.lat;
                        longitude = placeDetails.geometry.location.lng;

                        Log.d("SelectWorkArea", "Location " + placeDetails);

                        onMapClick(new LatLng(latitude, longitude));

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        });


        binding.seekRadius.setProgress(Integer.parseInt(radius));
    }


    @Override
    public void onMapClick(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        setMarkerOnMap(latLng);
        getAddressFromLatLong(latitude, longitude);
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
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } catch (Exception e) {
            Log.d(TAG, "Can't find style. Error: ", e);
        }
        setMyLocation();
    }

    private void setMarkerOnMap(LatLng latLng) {
        if (googleMap == null)
            return;
        googleMap.clear();
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(googleMap.getCameraPosition().target)
                        .draggable(true)
                        .title(myLocation)
                );
            }
        });
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                latitude = googleMap.getCameraPosition().target.latitude;
                longitude = googleMap.getCameraPosition().target.longitude;
                getAddressFromLatLong(latitude, longitude);
            }
        });


        CameraPosition pos = new CameraPosition.Builder()
                .target(latLng)
                .zoom(13)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));


    }

    private void setMyLocation() {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(SelectWorkAreaMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectWorkAreaMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void getCurrentLocation() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
                setMyLocation();
            }
        });

    }

    private void getAddressFromLatLong(double lat, double lng) {
        if (googleMap != null) {
            circle = googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(Double.parseDouble(radius) * 1000)
                    .strokeColor(this.getResources().getColor(R.color.colorPrimary))
                    .fillColor(this.getResources().getColor(R.color.colorPrimaryTrans)));
        }
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                zipcode = addresses.get(0).getPostalCode();
                myLocation = addresses.get(0).getAddressLine(0);
                binding.txtaddress.setText(myLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//        getAddressFromlatLong(latitude, longitude);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}