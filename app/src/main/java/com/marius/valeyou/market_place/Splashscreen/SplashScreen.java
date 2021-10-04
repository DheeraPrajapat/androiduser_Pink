package com.marius.valeyou.market_place.Splashscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Login_Register.Login;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_Calling_methods;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    ImageView iv;
    String user_info;
    ProgressDialog pd;

    String email;
    String password;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        pd = new ProgressDialog(SplashScreen.this);
        pd.setMessage(getResources().getString(R.string.switching_to_app));
        pd.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Variables.height = getResources().getDisplayMetrics().heightPixels;
        Variables.width = getResources().getDisplayMetrics().widthPixels;


        user_info = SharedPrefrence.get_offline(SplashScreen.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );

        Date d = new Date();
        CharSequence s = DateFormat.format("d", d.getTime());
        API_Calling_methods.Get_App_Config(SplashScreen.this, SplashScreen.this, user_info);

        String date_local_saved = SharedPrefrence.get_offline(SplashScreen.this, "" + SharedPrefrence.share_today_date);

        if (date_local_saved == null) {
            // Save Date into Shared Pref
            // Current Save date into Local
            SharedPrefrence.save_info_share(SplashScreen.this, "" + s, "" + SharedPrefrence.share_today_date);
            // todo: Get Basic App Config
            API_Calling_methods.Get_App_Config(SplashScreen.this, SplashScreen.this, user_info);
            Methods.get_default_country_id(SplashScreen.this, SplashScreen.this, user_info);


        } else if (date_local_saved.equals("" + s.toString())) {
            // If local save date and Current date Equal
            // Manipulate App Setting Data

            Methods.Manipulate_app_Setting_data(SplashScreen.this);
            //Methods.get_default_country_id(SplashScreen.this, SplashScreen.this, user_info);

         /*   Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreen.this, Drawer.class));
                    finish();
                    // todo Old and orginal code SplashScreen.java


                }
            }, 3000);*/

            if (user_info != null) {
                // If user info is nt full , Go to Drawer Activity
                pd.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        startActivity(new Intent(SplashScreen.this, Drawer.class));
                        finish();
                    }
                }, 3000);


            } else {
                // Go to Login Activity
                // startActivity(new Intent(SplashScreen.this, Drawer.class));
                // finish();

                // Get Device Token
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("token", "getInstanceId failed", task.getException());
                                    return;
                                }

                                String token = task.getResult().getToken();
                                Login(email, password, token);

                            }
                        });


            }


        } else if (!date_local_saved.equals("" + s.toString())) {
            // If local save date and Current date are not Equal
            // todo: Get Basic App Config
            API_Calling_methods.Get_App_Config(SplashScreen.this, SplashScreen.this, user_info);
            Methods.get_default_country_id(SplashScreen.this, SplashScreen.this, user_info);
            // Current Save date into Local
            SharedPrefrence.save_info_share(SplashScreen.this, "" + s, "" + SharedPrefrence.share_today_date);

        }


        // if()


//        // todo: Get Basic App Config
//        API_Calling_methods.Get_App_Config(SplashScreen.this,SplashScreen.this, user_info);
//
//
//        Methods.get_default_country_id(SplashScreen.this, SplashScreen.this, user_info);

    }

    public void Login(String email, String password, String token) {
        pd.show();

        try {
            JSONObject sendObj = new JSONObject();
            sendObj.put("email", "" + email);
            sendObj.put("password", "" + password);
            sendObj.put("device_token", "" + token);

            Volley_Requests.New_Volley(
                    SplashScreen.this,
                    "" + API_LINKS.API_SIGN_IN,
                    sendObj,
                    "OK",
                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response
                            try {
                                pd.hide();
                                if (response.getString("code").equals("200")) {
                                    // If Login Successfull

                                    JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                                    SharedPrefrence.save_info_share(
                                            SplashScreen.this,
                                            user_obj.toString(),
                                            SharedPrefrence.shared_user_login_detail_key
                                    );

                                    Intent sendIntent = new Intent(SplashScreen.this, Drawer.class);
                                    startActivity(sendIntent);
                                    finish();
                                } else {
                                    Methods.alert_dialogue(SplashScreen.this, "Info", "" + response.getString("msg"));
                                    pd.hide();

                                }

                            } catch (Exception b) {
                                pd.hide();
                            }

                        }
                    }
            );

        } catch (Exception b) {
            pd.hide();
        }
    }

}
