package com.marius.valeyou.market_place.Drawer.Msgs_Notifications;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Alert_Info extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    TextView tv;
    Toolbar header;

    public void Change_Color_Dynmic (){
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, Alert_Info.this);

            GradientDrawable gd = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            tv.setBackground(gd);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color


    } // End method to change Color Dynamically


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_info);

        iv = (ImageView) findViewById(R.id.back_id);
        tv = (TextView) findViewById(R.id.tv_id);


        iv.setOnClickListener(this);
        tv.setOnClickListener(this);
        Change_Color_Dynmic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;

            case R.id.tv_id:
                AlertDialog.Builder build = new AlertDialog.Builder(Alert_Info.this);
                build.setTitle("Confirm Unsubscribe");
                build.setMessage("Are you sure you want to unsubscribe the alert?");
                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                build.create();
                build.show();

                break;
        }
    }
}
