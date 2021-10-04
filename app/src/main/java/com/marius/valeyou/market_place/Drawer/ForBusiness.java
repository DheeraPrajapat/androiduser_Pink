package com.marius.valeyou.market_place.Drawer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class ForBusiness extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_business);

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
