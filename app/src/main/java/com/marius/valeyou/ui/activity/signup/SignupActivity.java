package com.marius.valeyou.ui.activity.signup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.reqbean.SocialDataBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySignupBinding;
import com.marius.valeyou.databinding.HolderNgoItemBinding;
import com.marius.valeyou.databinding.OtpBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Login_Register.Register;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.marius.valeyou.ui.PasswordStrength;
import com.marius.valeyou.ui.activity.LocationActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONObject;

public class SignupActivity extends AppActivity<ActivitySignupBinding, SignupActivityVM> {

    private static final String SOCIAL_BEAN = "bean";
    private SocialDataBean socialDataBean;
    private String social_id = "";
    private String social_type = "";
    private Location mCurrentlocation;
    Double latitude;
    Double longitude;

    @Override
    protected BindingActivity<SignupActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_signup, SignupActivityVM.class);
    }

    public static Intent newIntent(Activity activity, SocialDataBean dataBean) {
        Intent intent = new Intent(activity, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(SOCIAL_BEAN, dataBean);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCurrentLocation();
    }

    @Override
    protected void subscribeToEvents(final SignupActivityVM vm) {
        socialDataBean = getIntent().getParcelableExtra(SOCIAL_BEAN);
        binding.tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
        vm.base_back.observe(this, aVoid -> {
            onBackPressed(true);
        });

        if (socialDataBean != null) {
            social_id = socialDataBean.getSocial_id();
            social_type = socialDataBean.getSocial_type();
            setValueOnView();
        }

        vm.base_click.observe(this, view -> {
            if (view == null)
                return;
            switch (view != null ? view.getId() : 0) {
                case R.id.btnSignup:
                    signup();
                    break;
                case R.id.et_ngo:
                    binding.setType(true);
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
                            locCallback.getApiException().startResolutionForResult(SignupActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        break;
                    case FOUND:

                        mCurrentlocation = locCallback.getLocation();

                        latitude = mCurrentlocation.getLatitude();
                        longitude = mCurrentlocation.getLongitude();
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

                        Intent intent = LoginChooseActivity.newIntent(SignupActivity.this, "signup");
                        intent.putExtra("social", false);
                        startNewActivity(intent, true);
                        finishAffinity();

                        break;
                    case ERROR:
                        if (resource.message.equalsIgnoreCase("bad request")) {
                            vm.error.setValue("Invalid OTP");
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
                        Intent intent = LoginActivity.newIntent(SignupActivity.this);
                        startNewActivity(intent, true);
                        finishAffinity();

//                        String pass = binding.etPassword.getText().toString();
//                        if (pass.isEmpty()) {
//                            pass = "123456";
//                        }
//
//                        Sign_Up(resource.data.getEmail(), pass, resource.data.getFirstName() + " " + resource.data.getLastName(), resource.data.getPhone());

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
        setNgoRecycler();


        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() <= 0) {
                    binding.tvWarn.setVisibility(View.GONE);
                } else {
                    binding.tvWarn.setVisibility(View.VISIBLE);
                    calculatePasswordStrength(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setValueOnView() {

        binding.etFirstName.setText(socialDataBean.getFirst_name());
        binding.etLastName.setText(socialDataBean.getLast_name());
        binding.etEmail.setText(socialDataBean.getEmail());
        binding.passwordLayout.setVisibility(View.GONE);

    }

    private void setNgoRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvNgo.setLayoutManager(layoutManager);
        binding.rvNgo.setAdapter(adapter);
        adapter.setList(getData());
    }

    private List<MoreBean> getData() {
        List<MoreBean> moreBeans = new ArrayList<>();
        moreBeans.add(new MoreBean(1, getResources().getString(R.string.yes), 1));
        moreBeans.add(new MoreBean(1, getResources().getString(R.string.no), 0));
        return moreBeans;
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderNgoItemBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_ngo_item, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.cv_item:
                            ngo = bean.getImage();
                            binding.etNgo.setText(bean.getName());
                            binding.setType(false);
                            break;
                    }
                }
            });

    private int ngo = 1;

    private void signup() {
        if (isEmptyField()) {
            if (binding.cbTerms.isChecked()) {
                Map<String, String> map = new HashMap<>();
                map.put("first_name", binding.etFirstName.getText().toString());
                map.put("last_name", binding.etLastName.getText().toString());
                map.put("email", binding.etEmail.getText().toString());
                map.put("phone", binding.etPhone.getText().toString());

                String dummyPass = binding.etPassword.getText().toString();
                if (dummyPass.isEmpty()) {
                    dummyPass = "123456";
                }

                map.put("password", dummyPass);

                map.put("country_code", "+91");
                map.put("address", "");
                map.put("latitude", String.valueOf(latitude));
                map.put("longitude", String.valueOf(longitude));
                map.put("social_id", social_id);
                map.put("social_type", social_type);
                map.put("device_token ", sharedPref.get(Constants.FCM_TOKEN, "abc"));
                viewModel.signup(map, 1, ngo);
            } else {
                viewModel.error.setValue(getResources().getString(R.string.accept_privacy_policy));
            }
        }
    }

    private boolean isEmptyField() {
        if (TextUtils.isEmpty(binding.etFirstName.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_first_name));
            return false;
        }
        if (TextUtils.isEmpty(binding.etLastName.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_last_name));
            return false;
        }
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_email));
            return false;
        }

        if (!isValidEmail(binding.etEmail.getText().toString())) {
            viewModel.error.setValue(getResources().getString(R.string.valid_email));
            return false;
        }

        if (TextUtils.isEmpty(binding.etPhone.getText().toString().trim())) {
            viewModel.error.setValue(getResources().getString(R.string.enter_phone_number));
            return false;
        }
        if (social_id.equalsIgnoreCase("")) {
            if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
                viewModel.error.setValue(getResources().getString(R.string.enter_password));
                return false;
            }

            if (binding.etPassword.getText().toString().trim().length() < 6) {
                viewModel.error.setValue(getResources().getString(R.string.you_must_have_alteast_characters_pass));
                return false;
            }

            if (binding.tvWarn.getText().toString().equalsIgnoreCase("weak")) {
                viewModel.error.setValue(getResources().getString(R.string.your_password_is_weak));
                return false;
            }

            if (!binding.etCnfPassword.getText().toString().trim().matches(binding.etPassword.getText().toString().trim())) {
                viewModel.error.setValue(getResources().getString(R.string.both_password_must_be_matched));
                return false;
            }
        }

        if (!binding.cbTerms.isChecked()) {
            viewModel.error.setValue(getResources().getString(R.string.accept_privacy_policy));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private BaseCustomDialog<OtpBinding> verificationDialog;

    private void verifyDialog(String email) {
        verificationDialog = new BaseCustomDialog<>(SignupActivity.this, R.layout.otp, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {

                        case R.id.iv_cancel:
                            verificationDialog.dismiss();
                            Intent intent = LoginActivity.newIntent(SignupActivity.this);
                            startNewActivity(intent, true);
                            finishAffinity();
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

    private void getCurrentLocation() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
            }
        });
    }

    private void calculatePasswordStrength(String str) {
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        binding.tvWarn.setText(passwordStrength.msg);
        binding.tvWarn.setTextColor(passwordStrength.color);
    }

    @Override
    protected void onBackPressed(boolean animate) {
        sharedPref.deleteAll();
        super.onBackPressed(animate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void Sign_Up(String email, String password, String name, String mobile) {
        try {
            showProgressDialog(R.string.loading);
            JSONObject sendObj = new JSONObject();
            sendObj.put("full_name", "" + name);
            sendObj.put("email", "" + email);
            sendObj.put("password", "" + password);
            sendObj.put("phone", "" + mobile);

            Methods.toast_msg(SignupActivity.this, "" + sendObj.toString());

            Volley_Requests.New_Volley(
                    SignupActivity.this,
                    "" + API_LINKS.API_SIGN_UP,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response
                            try {
                                dismissProgressDialog();
                                if (response.getString("code").equals("200")) {
                                    // If code is 200 ==> OK

                                    JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                                    SharedPrefrence.save_info_share(
                                            SignupActivity.this,
                                            user_obj.toString(),
                                            SharedPrefrence.shared_user_login_detail_key
                                    );

                                    sharedPref.put("password", binding.etPassword.getText().toString());

                                    verifyDialog(email);

                                } else if (response.getString("code").equals("201")) {
                                    dismissProgressDialog();
                                    Methods.alert_dialogue(SignupActivity.this, "Info", "" + response.getString("msg"));

                                }

                            } catch (Exception b) {
                                dismissProgressDialog();
                            }


                        }
                    }


            );

        } catch (Exception b) {
            dismissProgressDialog();
        }


    } // End login Method


}
