package com.marius.valeyou.util.location;

import android.Manifest;
import android.app.Activity;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


/**
 * Created by Arvind on 13-09-2017.
 */

public class LiveLocationDetecter extends LiveData<LocCallback> {
    private static final String TAG = LiveLocationDetecter.class.getSimpleName();
    public static final int REQUEST_CHECK_SETTINGS = 1908;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 0;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Context activity;
    private Object tag;

    private boolean active = false;


    public LiveLocationDetecter(Context activity) {
        this.activity = activity;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mSettingsClient = LocationServices.getSettingsClient(activity);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                if (mCurrentLocation != null) {
                    LocCallback locCallback = new LocCallback(LocCallback.Type.FOUND, mCurrentLocation);
                    if (tag != null)
                        locCallback.setTag(tag);
                    postValue(locCallback);
                }
            }
        };
    }

    private void init() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(0);
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener(successListener);
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnFailureListener(onFailureListener);
    }


    public void onActivityResult(int requestCode, int resultCode) {
        if (this.active) {
            if (requestCode == REQUEST_CHECK_SETTINGS) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    postValue(new LocCallback(LocCallback.Type.PROMPT_CANCEL));
                }
                    init();
                }

        }
    }


    @Override
    protected void onActive() {
        super.onActive();
        active = true;
    }


    @Override
    protected void onInactive() {
        removeLocationUpdates();
        super.onInactive();
        active = false;
    }

    public void startLocationUpdates(LocationRequest locationRequest) {
        this.mLocationRequest = locationRequest;
        this.tag = null;
        init();
    }


    public void startLocationUpdates() {
        startLocationUpdates(null);
    }

    public void startLocationUpdateswithTag(LocationRequest locationRequest, Object tag) {
        this.tag = tag;
        this.mLocationRequest = locationRequest;
        init();
    }

    public void startLocationUpdateswithTag(Object tag) {
        this.tag = tag;
        startLocationUpdateswithTag(null, tag);
    }

    public void removeLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        postValue(new LocCallback(LocCallback.Type.STOPPED));
                    }
                });
    }


    private OnSuccessListener<LocationSettingsResponse> successListener = new OnSuccessListener<LocationSettingsResponse>() {
        @Override
        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            postValue(new LocCallback(LocCallback.Type.STARTED));
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());


        }
    };
    private OnFailureListener onFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    ResolvableApiException rae = (ResolvableApiException) e;
                    postValue(new LocCallback(LocCallback.Type.OPEN_GPS, rae));
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    String errorMessage = "Location settings are inadequate, and cannot be " +
                            "fixed here. Fix in Settings.";
                    Log.d(TAG, errorMessage);
                    postValue(new LocCallback(LocCallback.Type.ERROR, errorMessage));

            }


        }
    };

}