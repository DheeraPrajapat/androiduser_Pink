package com.marius.valeyou.ui.activity.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.reqbean.SocialDataBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityLoginBinding;
import com.marius.valeyou.databinding.OtpBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.forgot.ForgotPasswordActivity;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.signup.SignupActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppActivity<ActivityLoginBinding, LoginActivityVM> {

    private static final int RC_SIGN_IN = 1;
    private Location mCurrentlocation;
    @Inject
    LiveLocationDetecter liveLocationDetecter;

    private GoogleSignInClient mGoogleSignInClient;
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private SocialDataBean socialDataBean;

    @Override
    protected BindingActivity<LoginActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_login, LoginActivityVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref.deleteAll();

        String lang = sharedPref.get("language", "en");

        if (lang.equalsIgnoreCase("en")) {

            binding.langName.setText("English");

        } else if (lang.equalsIgnoreCase("pt")) {

            binding.langName.setText("Português");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    protected void subscribeToEvents(final LoginActivityVM vm) {
        vm.base_back.observe(this, aVoid -> {
            finish(true);
        });

        printHashKey(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        sharedPref.put(Constants.FCM_TOKEN, task.getResult().getToken());
                    }
                });

        vm.base_click.observe(this, view -> {
            if (view == null)
                return;
            switch (view != null ? view.getId() : 0) {

//                case R.id.changeLanguage:
//                    Intent lngIntent = AppLanguageActivity.newIntent(LoginActivity.this);
//                    startNewActivity(lngIntent);
//                    break;

                case R.id.btn_login:
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }
                                    sharedPref.put(Constants.FCM_TOKEN, task.getResult().getToken());
                                }
                            });
                    if (isEmptyValue()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("email", binding.etEmail.getText().toString());
                        map.put("password", binding.etPassword.getText().toString());
                        map.put("device_type", "1");
                        map.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));

                        sharedPref.put("password", binding.etPassword.getText().toString());

                        if (mCurrentlocation != null) {

                            map.put("latitude", String.valueOf(mCurrentlocation.getLatitude()));
                            map.put("longitude", String.valueOf(mCurrentlocation.getLongitude()));

                        } else {

                            map.put("latitude", "30.7046");
                            map.put("longitude", "76.7179");

                        }

                        vm.signIn(map);
                    }
                    break;
                case R.id.tv_signup:
                    socialDataBean = null;
                    Intent intent = SignupActivity.newIntent(LoginActivity.this, socialDataBean);
                    startNewActivity(intent, false, true);
                    break;

                case R.id.tv_forgot:
                    Intent intent2 = ForgotPasswordActivity.newIntent(LoginActivity.this);
                    startNewActivity(intent2, false, true);
                    break;

                case R.id.cv_facebook:
                    facebooklogin();
                    break;

                case R.id.cv_google:
                    signInGoogle();
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
                            locCallback.getApiException().startResolutionForResult(LoginActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        break;
                    case FOUND:
                        mCurrentlocation = locCallback.getLocation();
                        sharedPref.put(Constants.LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        sharedPref.put(Constants.SELECT_LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.SELECT_LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        vm.liveLocationDetecter.removeLocationUpdates();
                        break;
                }

            }
        });

        vm.sendOTPEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        vm.success.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


        vm.verifyEmailEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        verificationDialog.dismiss();
                        vm.success.setValue(resource.message);

                        Intent intent = MainActivity.newIntent(LoginActivity.this, "login");
                        intent.putExtra("social", false);
                        startNewActivity(intent, true);
                        finishAffinity();

                        break;
                    case ERROR:
                        if (resource.message.equalsIgnoreCase("bad request")) {
                            vm.error.setValue(getResources().getString(R.string.invalid_otp));
                        } else {
                            vm.error.setValue(resource.message);
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


        vm.userBean.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
//                        vm.success.setValue(resource.message);
                        if (resource.data.getIsEmailVerify() == 0) {

                            verifyDialog(binding.etEmail.getText().toString());

                        } else {

                            Intent intent1 = MainActivity.newIntent(LoginActivity.this, "login");
                            startNewActivity(intent1, true, true);

                        }

                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.socialBean.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        Intent intent1;
                        if (resource.data != null && resource.data.getStatus() == 1) {
                            intent1 = MainActivity.newIntent(LoginActivity.this, "login");
                            startNewActivity(intent1, true);
                        } else {
                            intent1 = SignupActivity.newIntent(LoginActivity.this, socialDataBean);
                            startNewActivity(intent1);
                        }
                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("bad request")) {
                            vm.error.setValue(getResources().getString(R.string.incorrect_email_password));
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        googleSignIn();

    }

    private void facebooklogin() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile", "user_posts"));
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            /*viewModel.success.setValue("Success::" + id + "\n" + first_name + " " + last_name
                                    + "\n" + email + "\n" + image_url);*/
                            socialDataBean = new SocialDataBean(id, first_name, last_name, email, "1");
                            Map<String, String> data = new HashMap<>();
                            data.put("first_name", first_name);
                            data.put("last_name", last_name);
                            data.put("email", email);
                            data.put("phone", "");
                            data.put("country_code", "");
                            data.put("address", "");
                            data.put("latitude", String.valueOf(mCurrentlocation.getLatitude()));
                            data.put("longitude", String.valueOf(mCurrentlocation.getLongitude()));
                            data.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));
                            viewModel.socialLogin(id, "1", 1, data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private boolean isEmptyValue() {
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_email));
            return false;
        } else if (!isValidEmail(binding.etEmail.getText().toString())) {
            viewModel.error.setValue(getResources().getString(R.string.valid_email));
            return false;
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_password));
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        liveLocationDetecter.onActivityResult(requestCode, resultCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "onError : " + e.getMessage());
        }
    }

    private void updateUI(GoogleSignInAccount account) {
      /*  viewModel.success.setValue("Success::" + account.getId() + "\n" + account.getDisplayName()
                + "\n" + account.getEmail() + "\n" + account.getPhotoUrl());*/
        socialDataBean = new SocialDataBean(account.getId(), account.getGivenName(), account.getFamilyName(), account.getEmail(), "2");


        Map<String, String> data = new HashMap<>();
        data.put("first_name", account.getGivenName());
        data.put("last_name", account.getFamilyName());
        data.put("email", account.getEmail());
        data.put("phone", "");
        data.put("country_code", "");
        data.put("address", "");
        data.put("latitude", "");
        data.put("longitude", "");
        data.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));
        viewModel.socialLogin(account.getId(), "2", 1, data);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private BaseCustomDialog<OtpBinding> verificationDialog;

    private void verifyDialog(String email) {
        verificationDialog = new BaseCustomDialog<>(LoginActivity.this, R.layout.otp, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {

                        case R.id.iv_cancel:
                            verificationDialog.dismiss();
                            break;
                        case R.id.btn_submit:
                            if (!verificationDialog.getBinding().etOtp.getText().toString().isEmpty()) {

                                viewModel.verifyEmail(email, verificationDialog.getBinding().etOtp.getText().toString().trim());

                            } else {

                                viewModel.error.setValue(getResources().getString(R.string.enter_otp));

                            }
                            break;

                        case R.id.resend_otp:
                            if (!email.isEmpty()) {
                                viewModel.sendOTP(email);
                            } else {
                                viewModel.error.setValue("Something went wrong");
                            }
                            break;

                    }
                }
            }
        });
        verificationDialog.getBinding().tvTwo.setText(getResources().getString(R.string.enter_the_otp_you_recive_at) + "\n" + email);
        verificationDialog.setCancelable(true);
        verificationDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        verificationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        verificationDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        verificationDialog.show();

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("LoginActivity", "HashKey : " + hashKey);
            }
        } catch (Exception e) {
            Log.d("LoginActivity", "HashKeyException", e);
        }
    }

}
