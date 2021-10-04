package com.marius.valeyou.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.ActivityLoginChooseBinding;
import com.marius.valeyou.databinding.HolderExitDialogBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivityVM;

import javax.inject.Inject;

public class LoginChooseActivity extends AppActivity<ActivityLoginChooseBinding, WelcomeActivityVM> {

    String TAG = "LoginChooseActivity";
    AdView adView;

    public static Intent newIntent(Activity activity, String comeFrom) {
        Intent intent = new Intent(activity, LoginChooseActivity.class);
        intent.putExtra("comeFrom", comeFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


    @Inject
    SharedPref sharedPref;

    @Override
    protected BindingActivity<WelcomeActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_login_choose, WelcomeActivityVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeToEvents(WelcomeActivityVM vm) {
        adView = findViewById(R.id.adView);
        loadBannerAds();
        findViewById(R.id.cardService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newIntent(LoginChooseActivity.this, LoginChooseActivity.this.getIntent().getStringExtra("comeFrom"));
                if (LoginChooseActivity.this.getIntent().getStringExtra("comeFrom") != null) {
                    intent.putExtra("social", false);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                finishAffinity();

            }
        });


        findViewById(R.id.cardLocalMarket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainLocalMarketActivity.newIntent(LoginChooseActivity.this, LoginChooseActivity.this.getIntent().getStringExtra("comeFrom"));
                if (LoginChooseActivity.this.getIntent().getStringExtra("comeFrom") != null) {
                    intent.putExtra("social", false);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                finishAffinity();

            }
        });

    }

    public void loadBannerAds() {
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

    @Override
    public void onBackPressed() {
        setExitDialog();
    }

    private BaseCustomDialog<HolderExitDialogBinding> exitDialog;

    private void setExitDialog() {
        exitDialog = new BaseCustomDialog<>(LoginChooseActivity.this, R.layout.holder_exit_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            exitDialog.dismiss();
                            finish(true);
                            break;
                        case R.id.btn_cancel:
                            exitDialog.dismiss();
                            break;
                    }
                }
            }
        });

        exitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        exitDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        exitDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        exitDialog.show();
    }

    //Direct login activity to Main activity if user is registered
    @Override
    protected void onStart() {
        super.onStart();
        Handler handler;
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        },100);
    }
}