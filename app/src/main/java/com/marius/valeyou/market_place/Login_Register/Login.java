package com.marius.valeyou.market_place.Login_Register;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_Calling_methods;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

import static com.google.android.gms.common.Scopes.EMAIL;

public class Login extends AppCompatActivity implements View.OnClickListener {

    AppBarLayout abl;
    ViewPager vp;
    Vp_Adp adp;
    CircleIndicator ci;

    TextView iv,login_register;
    TextView email_til,mbl_til;
    EditText email_tiet_1,mbl_tiet;
    Button btn;
    ProgressDialog pd;
    EditText email_tiet;
    String user_id, email, name, password, token;

    int[] imges = {
            R.drawable.ic_devices,
            R.drawable.ic_tracking,
            R.drawable.ic_fast
    };

    private String[] texts1 = {
            "Sync Across Devices",
            "Track Orders",
            "Doorstep Delivery"
    };

    private String[] texts2 = {
            "Access your account infromation on multiple devices",
            "Track the status for your orders",
            "Get cashback and free shipping (terms apply)"
    };

    Timer timer;

    Boolean check = true;
    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    LoginButton loginButton;

    // Google Login

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    CollapsingToolbarLayout cooll;
    RelativeLayout open_sign_up;



    //AppBarLayout app_bar;
    @SuppressLint({"RestrictedApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);

        pd = new ProgressDialog(Login.this);
        pd.setMessage(getResources().getString(R.string.loading));
        pd.setCancelable(false);

        open_sign_up = findViewById(R.id.rl1_id_1);
        try{
            cooll = findViewById(R.id.ctb_id);
            cooll.setContentScrimColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception v){

        }
        //todo: Print FB HashKey

        Methods.printHashKey(Login.this);

        // FB Login Button is here.
        loginButton =  findViewById(R.id.login_details_fb_iV_id);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        login_register = (TextView) findViewById(R.id.login_register_id);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);

            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {
                Methods.toast_msg(Login.this,"" + error.toString());

            }
        });

        RelativeLayout RL_fb = findViewById(R.id.fb);
        RL_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });


        /// Google Login

        try{
            google_sign_in();

        }catch (Exception v){

        }


        try{
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor(Variables.App_status_bar_color_code)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            RelativeLayout slider_RL = findViewById(R.id.slider_RL);
            slider_RL.setBackground(Methods.getColorScala());

        }catch (Exception v){

        }

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        RelativeLayout gmail_sign_in = findViewById(R.id.google_RL);

        gmail_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });


        email_til = (TextView) findViewById(R.id.email_til_id);
        mbl_til = (TextView) findViewById(R.id.mbl_til_id);
        email_til.setTextColor(R.color.white);

        email_tiet =  findViewById(R.id.email_tie_id);
        mbl_tiet = (EditText) findViewById(R.id.mbl_tie_id);
        try{
            email_til.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            mbl_til.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

        }catch (Exception b){

        }

        iv =  findViewById(R.id.close_id);

        btn = (Button) findViewById(R.id.btn_id);
        abl = (AppBarLayout) findViewById(R.id.abl_id);
        try{
            btn.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }


            abl.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){

        }

        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    login_register.setVisibility(View.VISIBLE);
                }
                else
                {
                    login_register.setVisibility(View.GONE);
                }
            }
        });

        double heightDp = getResources().getDisplayMetrics().heightPixels / 2.9;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)abl.getLayoutParams();
        lp.height = (int)heightDp;
        abl.setLayoutParams(lp);


        vp = (ViewPager) findViewById(R.id.vp_id);
        ci = (CircleIndicator) findViewById(R.id.ci_id);
        adp = new Vp_Adp(this,imges,texts1,texts2);

        vp.setAdapter(adp);
        ci.setViewPager(vp);



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                vp.post(new Runnable(){

                    @Override
                    public void run() {
                        vp.setCurrentItem((vp.getCurrentItem()+1)%imges.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);



        iv.setOnClickListener(this);
        email_tiet.setOnClickListener(this);
        mbl_tiet.setOnClickListener(this);
        btn.setOnClickListener(this);
        open_sign_up.setOnClickListener(this);

    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String fb_user_id = object.getString("id");
                            String imgurl = "https://graph.facebook.com/"+object.getString("id")+"/picture";
                            String fb_name = object.getString("name");
                            String fb_email = object.getString("email");

                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("fb_name", fb_name); // Set the first name/pair
                            jsonObj.put("fb_email", fb_email);
                            jsonObj.put("fb_user_id" , fb_user_id);
                            jsonObj.put("fb_user_img", imgurl);

                            //todo Calling an API

                            API_Calling_methods.Sign_Up(
                                    fb_name,
                                    fb_email,
                                    fb_user_id ,
                                    Login.this,
                                    Variables.flag_Sign_up
                            );

                            LoginManager.getInstance().logOut();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Methods.toast_msg(Login.this,"" + e.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode,  data);

//        / Gmail lognin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    public void google_sign_in()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(Login.this)
                .enableAutoManage(Login.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            try{
                name = acct.getDisplayName();
                user_id = acct.getId();
                String img_url = "https://plus.google.com/s2/photos/profile/" +user_id + "?sz=100";
                    email = acct.getEmail();

                API_Calling_methods.Sign_Up(
                        name,
                        email,
                        user_id ,
                        Login.this,
                        Variables.flag_Sign_up
                );



            }catch (Exception v){
                Methods.toast_msg(Login.this,""+v.toString());

            }
        } else {
            // Signed out, show unauthenticated UI.

        }
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_id:
                finish();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );


                break;

            case R.id.email_tie_id:
                abl.setExpanded(false);
                check = false;
                break;

            case R.id.mbl_tie_id:
                abl.setExpanded(false);
                check = false;
                break;

            case R.id.rl1_id_1:
                startActivity(new Intent(Login.this, Register.class));
                break;
            case R.id.btn_id:
                email  = email_tiet.getText().toString();
                password =  mbl_tiet.getText().toString();

                if(email.length() == 0){
                    email_tiet.setError("Please Enter Email");
                }else if(password.length() == 0){
                    mbl_tiet.setError("Please Enter Email");
                }else{

                    // Get Device Token
//                    FirebaseInstanceId.getInstance().getInstanceId()
//                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                    if (!task.isSuccessful()) {
//                                        Log.w("token", "getInstanceId failed", task.getException());
//                                        return;
//                                    }
//
//                                    token = task.getResult().getToken();
//                                    Login(email, password, token);
//
//
//
//
//
//                                }
//                            });



                }



                break;
        }
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (!check){
            abl.setExpanded(true);
            check = true;
        }else {
            finish();
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            super.onBackPressed();
        }

    }

    public void Login (String email, String password, String token){
        pd.show();

        try{

        JSONObject sendObj = new JSONObject();
        sendObj.put("email" , "" + email);
        sendObj.put("password" , "" + password);
        sendObj.put("device_token" , "" + token);

        Volley_Requests.New_Volley(
                Login.this,
                "" + API_LINKS.API_SIGN_IN,
                sendObj,
                "OK",

                new CallBack() {
                    @Override
                    public void Get_Response(String requestType, JSONObject response) {
                        // Maipulate Response

                        try{
                            pd.hide();
                            if(response.getString("code").equals("200")){
                                // If Login Successfull
                                JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                                SharedPrefrence.save_info_share(
                                        Login.this,
                                        user_obj.toString(),
                                        SharedPrefrence.shared_user_login_detail_key
                                );

                                Intent sendIntent = new Intent(Login.this, Drawer.class);
                                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sendIntent);
                            }else{
                                Methods.alert_dialogue(Login.this,"Info","" + response.getString("msg") );
                                pd.hide();

                            }

                        }catch (Exception b){
                            pd.hide();
                        }

                    }
                }


        );

        }catch (Exception b){
            pd.hide();
        }




    } // End login Method

    private void setInputTextLayoutColor(int color, TextInputLayout textInputLayout) {
        try {
            Field field = textInputLayout.getClass().getDeclaredField("mFocusedTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(textInputLayout, myList);

            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(textInputLayout, myList);

            Method method = textInputLayout.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(textInputLayout, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
