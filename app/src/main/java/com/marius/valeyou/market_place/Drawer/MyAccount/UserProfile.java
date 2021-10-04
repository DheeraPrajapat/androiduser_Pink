package com.marius.valeyou.market_place.Drawer.MyAccount;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        iv = (ImageView) findViewById(R.id.cross_id);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
