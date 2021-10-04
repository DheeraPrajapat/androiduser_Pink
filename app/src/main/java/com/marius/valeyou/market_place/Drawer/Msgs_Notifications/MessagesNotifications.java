package com.marius.valeyou.market_place.Drawer.Msgs_Notifications;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox.Chat_Inbox;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MessagesNotifications extends AppCompatActivity implements View.OnClickListener {

    CollapsingToolbarLayout ctb;
    Toolbar tb;
    TextView tv2,tv3,tv4,tv5;
    ImageView back,iv;
    RelativeLayout rl,rl1;
    Boolean check = true;
    RelativeLayout open_inbox_RL;

    //CollapsingToolbarLayout cooll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgs_notifi);

        ctb = (CollapsingToolbarLayout) findViewById(R.id.ctb_id);
        tb = (Toolbar) findViewById(R.id.tb_id);
        open_inbox_RL = findViewById(R.id.open_inbox_RL);

        try{

            ctb.setContentScrimColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            // Gradient Color
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor(Variables.App_status_bar_color_code)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            RelativeLayout gradient_lay_RL = findViewById(R.id.gradient_lay);
            gradient_lay_RL.setBackground(Methods.getColorScala());
            // Change Status Bar Color (Its Dynamically)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }



        }catch (Exception v){

        }
        back = (ImageView) findViewById(R.id.close_id);

        tv2 = (TextView) findViewById(R.id.tv2_id);
        tv3 = (TextView) findViewById(R.id.tv3_id);
        tv4 = (TextView) findViewById(R.id.tv4_id);
        tv5 = (TextView) findViewById(R.id.tv5_id);
        iv = (ImageView) findViewById(R.id.iv_id);

        rl = (RelativeLayout) findViewById(R.id.rl_id);
        rl1 = (RelativeLayout) findViewById(R.id.rl1_id);


        back.setOnClickListener(this);
        rl.setOnClickListener(this);
        rl1.setOnClickListener(this);
        open_inbox_RL.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_id:
                finish();
                break;

            case R.id.rl_id:
                if (!check){
                    iv.setImageResource(R.drawable.ic_arrow_gray);
                    rl1.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    check = true;
                }else {
                    iv.setImageResource(R.drawable.ic_arrow_up);
                    rl1.setVisibility(View.VISIBLE);
                    tv5.setVisibility(View.VISIBLE);
                    check = false;
                }
                break;

            case R.id.rl1_id:
                startActivity(new Intent(MessagesNotifications.this, Alert_Info.class));
                break;
            case R.id.open_inbox_RL:
                // Open Chat Inbox
                startActivity(new Intent(MessagesNotifications.this, Chat_Inbox.class));

                break;


        }
    }
}
