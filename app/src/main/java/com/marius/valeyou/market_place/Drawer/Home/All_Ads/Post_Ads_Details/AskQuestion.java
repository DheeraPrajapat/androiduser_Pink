package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class AskQuestion extends AppCompatActivity implements View.OnClickListener {

    ImageView cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_question);

        cross = (ImageView) findViewById(R.id.cross_id);

        cross.setOnClickListener(this);

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
