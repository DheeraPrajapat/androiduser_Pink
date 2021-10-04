package com.marius.valeyou.di.base;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.facebook.ads.AudienceNetworkAds;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.FirebaseApp;
import com.marius.valeyou.R;
import com.marius.valeyou.di.component.DaggerAppComponent;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.util.misc.AppVisibilityDetector;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class MyApplication extends DaggerApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication application;

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MultiDex.install(this);
        attachErrorHandler();
        MobileAds.initialize(application, application.getResources().getString(R.string.admob_app_id));

        AppVisibilityDetector.init(this, new AppVisibilityDetector.AppVisibilityCallback() {
            @Override
            public void onAppGotoForeground() {

              /*  if (sharedPref.contains("userdata")) {
                    long ct = System.currentTimeMillis() / 1000;
                    if (lastonlinetime != -1 && ct - lastonlinetime < DEFAULT_ONLINE_TIME_THRESHOLD) {
                        lastonlinetime = ct;
                    } else {
                        startActivity(SecurityActivity.newIntent(application));
                    }
                }*/
            }

            @Override
            public void onAppGotoBackground() {
                // lastonlinetime = System.currentTimeMillis() / 1000;
            }
        });

        // for market place
        FirebaseApp.initializeApp(this);
        Fresco.initialize(this);
        AudienceNetworkAds.initialize(this);
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    public static MyApplication getInstance() {
        return application;
    }

    public void restartApp() {
       /* Intent intent = SplashActivity.newIntent(this);
        intent.putExtra("reset", true);
        startActivity(intent);*/

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }


    private void attachErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof OnErrorNotImplementedException || throwable instanceof ProtocolViolationException) {

                }
            }
        });
    }


    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(Variables.google_analytics_id);
        }

        return sTracker;
    }

}
