package com.marius.valeyou.market_place.Drawer;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;

public class OrdersPayments extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    Toolbar header;
    TextView tv1_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_payments);
        tv1_id = findViewById(R.id.tv1_id);
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, OrdersPayments.this);
            GradientDrawable gd = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            tv1_id.setBackground(gd);


        }catch (Exception v){

        } // End Catch of changing Toolbar


        iv = (ImageView) findViewById(R.id.back_id);

        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;
        }
    }
}
