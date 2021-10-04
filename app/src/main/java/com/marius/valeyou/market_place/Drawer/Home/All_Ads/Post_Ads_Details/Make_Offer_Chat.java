package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class Make_Offer_Chat extends AppCompatActivity implements View.OnClickListener {

    TextView tv1,tv2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_offer_chat);

        tv1 = (TextView) findViewById(R.id.tv_id);
        tv2 = (TextView) findViewById(R.id.tv1_id);

        iv = (ImageView) findViewById(R.id.cross_id);

        tv1.setText("Make Offer & Chat");
        tv2.setText("By proceeding you agree to our Terms of Use & Privacy Policy");

        iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cross_id:
                finish();
                break;
        }
    }
}
