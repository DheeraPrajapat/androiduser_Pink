package com.marius.valeyou.ui.activity.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.ActivityWelcomeBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.tourpage.TourActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

public class WelcomeActivity extends AppActivity<ActivityWelcomeBinding, WelcomeActivityVM> {

    @Inject
    SharedPref sharedPref;

    @Override
    protected BindingActivity<WelcomeActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_welcome, WelcomeActivityVM.class);
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
    public void onConnectionRefresh(boolean connected) {
        if (connected) {
            if (sharedPref.getUserData() != null) {
                if (sharedPref.get("loginType", "").equalsIgnoreCase("service")) {
                    Intent intent1 = MainActivity.newIntent(WelcomeActivity.this, "welcome");
                    viewModel.nextUi(intent1, 1);
                } else if (sharedPref.get("loginType", "").equalsIgnoreCase("market")) {
                    Intent intent = MainLocalMarketActivity.newIntent(WelcomeActivity.this, "welcome");
                    viewModel.nextUi(intent, 1);
                } else {
                    Intent intent = LoginChooseActivity.newIntent(WelcomeActivity.this, "login");
                    intent.putExtra("social", false);
                    startNewActivity(intent, true);
                    finishAffinity();
                }

            } else {
                Intent intent = TourActivity.newInstance(WelcomeActivity.this);
                viewModel.nextUi(intent, 1);
            }
        }
    }

    @Override
    protected void subscribeToEvents(final WelcomeActivityVM vm) {
        // printHashKey(WelcomeActivity.this);
        passIntent();
        vm.clk_login.observe(this, aVoid -> {

        });

        vm.obr_nextui.observe(this, intent -> {
            startNewActivity(intent, true);
        });
    }

    private void passIntent() {
        //Intent intent = LoginActivity.newIntent(WelcomeActivity.this);
        if (sharedPref.getUserData() != null) {
            if (sharedPref.get("loginType", "").equalsIgnoreCase("service")) {
                Intent intent1 = MainActivity.newIntent(WelcomeActivity.this, "welcome");
                viewModel.nextUi(intent1, 1);
            } else if (sharedPref.get("loginType", "").equalsIgnoreCase("market")) {
                Intent intent = MainLocalMarketActivity.newIntent(WelcomeActivity.this, "welcome");
                viewModel.nextUi(intent, 1);
            } else {
                Intent intent = LoginChooseActivity.newIntent(WelcomeActivity.this, "login");
                intent.putExtra("social", false);
                startNewActivity(intent, true);
                finishAffinity();
            }

        } else {
            Intent intent = TourActivity.newInstance(WelcomeActivity.this);
            viewModel.nextUi(intent, 1);
        }
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG_RAHUL", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.d("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.d("TAG", "printHashKey()", e);
        }
    }


}

