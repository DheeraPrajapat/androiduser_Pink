package com.marius.valeyou.market_place.Login_Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.google.android.gms.common.Scopes.EMAIL;

public class Register extends AppCompatActivity implements View.OnClickListener {

    ImageView back,contry_flag;
    TextView country_coke;
    Button btn;
    ProgressDialog pd;
    Boolean check = true;
    Toolbar header;
    EditText email_tiet, password_tiet, name_tiet, conf_password_tiet, mobile_num_tiet;
    String email, name, passwors, con_password, mobile, user_id;

    LinearLayout country_ll;

    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    LoginButton loginButton;

    // Google Login

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    CollapsingToolbarLayout cooll;
    Context context;

    TextView email_til_id, name_til_id, mbl_til_id, pass_til_id, con_pass_til_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.register);

        context = Register.this;

        pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.loading));
        pd.setCancelable(false);



        email_tiet = findViewById(R.id.email_tie_id);
        password_tiet = findViewById(R.id.pass_tie_id);
        name_tiet = findViewById(R.id.name_tie_id);
        conf_password_tiet = findViewById(R.id.con_pass_tie_id);
        mobile_num_tiet = findViewById(R.id.mbl_tie_id);

        // TextInputLayout

        email_til_id = findViewById(R.id.email_til_id);
        name_til_id = findViewById(R.id.name_til_id);
        mbl_til_id = findViewById(R.id.mbl_til_id);
        pass_til_id = findViewById(R.id.pass_til_id);
        con_pass_til_id = findViewById(R.id.con_pass_til_id);

        country_ll = (LinearLayout) findViewById(R.id.country_ll_id);
        country_coke = (TextView) findViewById(R.id.country_code_id);
        contry_flag = (ImageView) findViewById(R.id.country_flag_id);

        country_ll.setOnClickListener(this);

        try{

            email_til_id.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            name_til_id.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            mbl_til_id.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            pass_til_id.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            con_pass_til_id.setTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));


        }catch (Exception b){

        }




        loginButton =  findViewById(R.id.login_details_fb_iV_id);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);

            }
            @Override
            public void onCancel() {
                Methods.toast_msg(context,"You cancel this.");
            }
            @Override
            public void onError(FacebookException error) {
                Methods.toast_msg(context,"" + error.toString());

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


        back = (ImageView) findViewById(R.id.back_id);
        btn = (Button) findViewById(R.id.btn_id);



        back.setOnClickListener(this);
        btn.setOnClickListener(this);

        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            btn.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            Methods.Change_header_color(header, Register.this);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color

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

                            //Methods.toast_msg(context,""+fb_name);

                            name_tiet.setText(fb_name);
                            email_tiet.setText(fb_email);

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
                                    context,
                                    Variables.flag_Sign_up
                            );

                            LoginManager.getInstance().logOut();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Methods.toast_msg(context,""+e.toString());
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

//        Gmail login
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
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(Register.this, new GoogleApiClient.OnConnectionFailedListener() {
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
//                Uri personPhotoUrl = acct.getPhotoUrl();
                email = acct.getEmail();
                API_Calling_methods.Sign_Up(
                        name,
                        email,
                        user_id ,
                        context,
                        Variables.flag_Sign_up
                );

            }catch (Exception v){
                Methods.toast_msg(context,""+v.toString());

            }
        } else {
            // Signed out, show unauthenticated UI.

        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;


            case R.id.country_ll_id:
                /*final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        // Implement your code here
                        contry_flag.setImageResource(flagDrawableResID);
                        country_coke.setText(dialCode);
                        picker.dismiss();
                    }
                });*/

                break;

            case R.id.btn_id:


                email = email_tiet.getText().toString();
                name = name_tiet.getText().toString();
                passwors = password_tiet.getText().toString();
                con_password = conf_password_tiet.getText().toString();
                mobile = mobile_num_tiet.getText().toString();
                if(email.length() == 0){
                    // If length is zeroo
                    email_tiet.setError("Please fill Email.");
                }else if(name.length() == 0){
                    name_tiet.setError("Please fill Name");
                }else if(passwors.length() == 0){
                    password_tiet.setError("Please fill Password.");
                }else if(con_password.length() == 0){
                    conf_password_tiet.setError("Please Fill");
                }else if(!passwors.equals("" + con_password)){
                    // If password and confirm Password is not same
                    conf_password_tiet.setError("Confirm Password does not Match");
                }else{
                    Sign_Up("" + email,"" + passwors,"" + name,"0" );
                }
                break;

        }
    }

    public void Sign_Up (String email, String password, String name, String mobile){
        try{
            pd.show();


            JSONObject sendObj = new JSONObject();
            sendObj.put("full_name" , "" + name);
            sendObj.put("email" , "" + email);
            sendObj.put("password" , "" + password);
            sendObj.put("phone" , "" + mobile);

            Methods.toast_msg(Register.this,"" + sendObj.toString());

            Volley_Requests.New_Volley(
                    Register.this,
                    "" + API_LINKS.API_SIGN_UP,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response
                            try{
                                pd.hide();
                                if(response.getString("code").equals("200")){
                                    // If code is 200 ==> OK

                                    JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                                    SharedPrefrence.save_info_share(
                                            Register.this,
                                            user_obj.toString(),
                                            SharedPrefrence.shared_user_login_detail_key
                                    );


                                    Intent sendIntent = new Intent(Register.this, Drawer.class);
                                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(sendIntent);
                                }else if(response.getString("code").equals("201")){
                                    pd.hide();
                                    Methods.alert_dialogue(Register.this,"Info","" + response.getString("msg") );

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



}
