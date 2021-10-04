package com.marius.valeyou.di.module;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;

import com.marius.valeyou.util.location.LiveLocationDetecter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;

@Module
public class SystemModule {
    @Singleton
    @Provides
    static ConnectivityManager connectivityManager(DaggerApplication daggerApplication) {
        return (ConnectivityManager) daggerApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Singleton
    @Provides
    static LocationManager locationManager(DaggerApplication daggerApplication) {
        return (LocationManager) daggerApplication.getSystemService(Context.LOCATION_SERVICE);
    }

    @Singleton
    @Provides
    static NotificationManager notificationManager(DaggerApplication daggerApplication) {
        return (NotificationManager) daggerApplication.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Singleton
    @Provides
    static PackageManager packageManager(DaggerApplication daggerApplication) {
        return daggerApplication.getPackageManager();
    }

    @Singleton
    @Provides
    static AssetManager assetManager(DaggerApplication daggerApplication) {
        return daggerApplication.getAssets();
    }




    @Singleton
    @Provides
    static LiveLocationDetecter getlocation(DaggerApplication daggerApplication) {
        return new LiveLocationDetecter(daggerApplication);
    }


}
