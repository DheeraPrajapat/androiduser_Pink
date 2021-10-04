package com.marius.valeyou.market_place.Drawer.MyAccount;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OffersMade extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    LinearLayout ll;
    TextView selling,buying,buy_tv1,buy_tv2,buy_tv3;
    RelativeLayout sell_rl,buy_rl;
    Toolbar header;
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic () {
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, OffersMade.this);
            post_ad.setBackgroundColor( Color.parseColor(Variables.Var_App_Config_header_bg_color));

            GradientDrawable gd = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            buy_tv1.setBackground(gd);
            buy_tv2.setBackground(gd);
            buy_tv3.setBackground(gd);
            buy_tv1.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            buy_tv2.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            buy_tv3.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            selling.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    } // End method to change Color Dynamically

    TextView post_ad;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_made);

        iv = (ImageView) findViewById(R.id.back_id);
        ll = (LinearLayout)  findViewById(R.id.ll_id);
        ll.setClipToOutline(true);
        header = findViewById(R.id.tb_id);
        post_ad = findViewById(R.id.post_ad);
        sell_rl = (RelativeLayout) findViewById(R.id.selling_rl_id);
        buy_rl = (RelativeLayout) findViewById(R.id.buying_rl_id);

        selling = (TextView) findViewById(R.id.selling_tv_id);
        buying = (TextView) findViewById(R.id.buying_tv_id);

        buy_tv1 = (TextView) findViewById(R.id.mbls_tablets_id);
        buy_tv2 = (TextView) findViewById(R.id.elec_appliances_id);
        buy_tv3 = (TextView) findViewById(R.id.home_lifestyle_id);

        buy_tv1.setText("Mobiles &  Tablets");
        buy_tv2.setText("Electronics & Appliances");
        buy_tv3.setText("Home & Lifestyle");

        iv.setOnClickListener(this);
        selling.setOnClickListener(this);
        buying.setOnClickListener(this);
        Change_Color_Dynmic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;

            case R.id.selling_tv_id:
                sell_rl.setVisibility(View.VISIBLE);
                buy_rl.setVisibility(View.GONE);
                selling.setBackgroundColor( Color.parseColor(Variables.Var_App_Config_header_bg_color));
                selling.setTextColor(getResources().getColor(R.color.white));
                buying.setBackgroundColor(getResources().getColor(R.color.white));
                buying.setTextColor(getResources().getColor(R.color.black));
                break;

            case R.id.buying_tv_id:
                sell_rl.setVisibility(View.GONE);
                buy_rl.setVisibility(View.VISIBLE);
                buying.setBackgroundColor( Color.parseColor(Variables.Var_App_Config_header_bg_color));
                buying.setTextColor(getResources().getColor(R.color.white));
                selling.setBackgroundColor(getResources().getColor(R.color.white));
                selling.setTextColor(getResources().getColor(R.color.black));
                break;

        }
    }
}
