package com.marius.valeyou.market_place.Drawer.App_Settigs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.ContactUs;
import com.marius.valeyou.market_place.Drawer.MyAccount.ChangePass;
import com.marius.valeyou.market_place.Drawer.MyAccount.EditProfile;
import com.marius.valeyou.market_place.Drawer.MyAccount.UserProfile;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AppSettings extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    TextView edit_prof_tv,chng_pass_tv,user_prof_tv,lan_tv;
    TextView app_version, privacy_policy, share_app, logout, terms_conditions;
    Toolbar header;

    // TODO: (MyAccount.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic () {
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, AppSettings.this);

            GradientDrawable gd = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            logout.setBackground(gd);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    } // End method to change Color Dynamically

    LinearLayout account_setting_LI;
    String  user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);


        app_version = findViewById(R.id.app_version);
        app_version.setText("" + BuildConfig.VERSION_NAME);
        privacy_policy = findViewById(R.id.privacy_policy);
        share_app = findViewById(R.id.share_app);
        logout = findViewById(R.id.logout);
        terms_conditions = findViewById(R.id.terms_conditions);
        account_setting_LI = findViewById(R.id.account_setting_LI);

        user_info = SharedPrefrence.get_offline(AppSettings.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );

        if(user_info != null){
            // If user Already Login

        }else{
            // If user is not Login
            account_setting_LI.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
        }

        iv = (ImageView) findViewById(R.id.back_id);
        edit_prof_tv = (TextView) findViewById(R.id.edit_prof_tv_id);
        chng_pass_tv = (TextView) findViewById(R.id.chng_pass_tv_id);
        user_prof_tv = (TextView) findViewById(R.id.user_prof_tv_id);
        lan_tv = (TextView) findViewById(R.id.lan_tv_id);


        iv.setOnClickListener(this);
        edit_prof_tv.setOnClickListener(this);
        chng_pass_tv.setOnClickListener(this);
        user_prof_tv.setOnClickListener(this);
        lan_tv.setOnClickListener(this);

        // New Click
        //app_version.setOnClickListener(this);
        privacy_policy.setOnClickListener(this);
        share_app.setOnClickListener(this);
        logout.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        Change_Color_Dynmic();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;

            case R.id.edit_prof_tv_id:
                startActivity(new Intent(AppSettings.this, EditProfile.class));
                break;

            case R.id.chng_pass_tv_id:
                startActivity(new Intent(AppSettings.this, ChangePass.class));
                break;

            case R.id.user_prof_tv_id:
                startActivity(new Intent(AppSettings.this, UserProfile.class));
                break;

            case R.id.lan_tv_id:
                startActivity(new Intent(AppSettings.this, Lang_Setting.class));
                break;
            case R.id.privacy_policy:
                // Open Privacy Policy
                //startActivity(new Intent(AppSettings.this, Lang_Setting.class));
                try{
                    ContactUs.openBrowser(AppSettings.this,"" + Variables.App_Privacy_Policy_new);
                }catch (Exception vq){
                    //Methods.toast_msg(ContactUs.this,"" + vq.toString());
                }
                break;
            case R.id.share_app:
                // Share App Code
                Methods.share_app(AppSettings.this,"");

                break;

            case R.id.logout:
                // Logout Code
                SharedPrefrence.logout_user(AppSettings.this);
                break;
            case R.id.terms_conditions:
                try{
                    ContactUs.openBrowser(AppSettings.this,"" + Variables.App_Terms_Condition);
                }catch (Exception vq){
                    //Methods.toast_msg(ContactUs.this,"" + vq.toString());
                }
        }
    }
}
