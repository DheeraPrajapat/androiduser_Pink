package com.marius.valeyou.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityLocationBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppActivity<ActivityLocationBinding,LocationActivityVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener  {


    public static final String TAG = "LocationActivity";
    private Location mCurrentlocation;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    String myLocation;
    String city;
    String state;
    String zipcode;
    String country;
    double lat,lng;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, LocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<LocationActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_location, LocationActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(LocationActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.location));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.place_key), Locale.US);
        }

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
                    case R.id.et_search:
                      /*  List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                                .build(LocationActivity.this);
                        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/
                        break;

                    case R.id.btn_save:
                        if (myLocation!=null) {
                            Intent intent1 = new Intent();
                            intent1.putExtra("address", myLocation);
                            intent1.putExtra("city", city);
                            intent1.putExtra("state", state);
                            intent1.putExtra("zipcode", zipcode);
                            intent1.putExtra("country",country);
                            intent1.putExtra("latitude",lat);
                            intent1.putExtra("longtitude",lng);
                            setResult(1, intent1);
                            finish();
                        }

                        break;
                }
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
                            locCallback.getApiException().startResolutionForResult(LocationActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        break;
                    case FOUND:

                        mCurrentlocation = locCallback.getLocation();
                        vm.liveLocationDetecter.removeLocationUpdates();
                        LatLng latLng = new LatLng(mCurrentlocation.getLatitude(),mCurrentlocation.getLongitude());
                        setMarkerOnMap(latLng);

                        List<Address> addresses  = getAddressFromlatLong(mCurrentlocation.getLatitude(),mCurrentlocation.getLongitude());
                        binding.txtFullAddress.setText(addresses.get(0).getAddressLine(0));

                        break;
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getCurrentLocation();

    }

    private void setMyLocation() {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void onMapClick(LatLng latLng) {
        setMarkerOnMap(latLng);
        List<Address> addresses  = getAddressFromlatLong(latLng.latitude,latLng.longitude);
        binding.txtFullAddress.setText(addresses.get(0).getAddressLine(0));

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

    private void setMarkerOnMap(LatLng latLng) {
        if (googleMap == null)
            return;
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("")
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("", 1))));


        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

        CameraPosition pos = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));


    }

    private GoogleMap.OnCameraIdleListener onCameraIdleListener = () -> {

    };

    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener = i -> {

    };



    private Bitmap getMarkerBitmapFromView(String distance, int pos) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
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

    private List<Address> getAddressFromlatLong(double latitude, double lngtude) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            lat = latitude;
            lng = lngtude;
            addresses = gcd.getFromLocation(latitude, lngtude, 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                zipcode = addresses.get(0).getPostalCode();
                country = addresses.get(0).getCountryName();
                myLocation = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addresses;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                Toast.makeText(this, "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                String address = place.getAddress();
                // do query with address

            }  else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}