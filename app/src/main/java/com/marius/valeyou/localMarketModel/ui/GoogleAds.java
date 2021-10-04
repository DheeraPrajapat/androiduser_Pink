package com.marius.valeyou.localMarketModel.ui;

import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GoogleAds {

    String TAG = "GoogleAds";

    public void loadBannerAds(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new

                                             AdListener() {
                                                 @Override
                                                 public void onAdLoaded() {
                                                     // Code to be executed when an ad finishes loading.
                                                     Log.i(TAG, "Ads_StatusMain : Finished");
                                                     adView.setVisibility(View.VISIBLE);
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
