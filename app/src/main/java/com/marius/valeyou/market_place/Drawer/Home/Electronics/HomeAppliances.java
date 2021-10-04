package com.marius.valeyou.market_place.Drawer.Home.Electronics;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class HomeAppliances extends AppCompatActivity implements View.OnClickListener {

    ImageView back,cross,search;
    EditText et;
    TextView tv;
    ListView lv;

    String[] list = {"Appliances","Appliances","Appliances","Appliances","Appliances","Appliances"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_appliances);

        back = (ImageView) findViewById(R.id.back_id);
        cross = (ImageView) findViewById(R.id.cross_id);
        search = (ImageView) findViewById(R.id.search_id);
        et = (EditText) findViewById(R.id.et_id);
        tv = (TextView) findViewById(R.id.tv_id);
        lv = (ListView) findViewById(R.id.lv_id);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(HomeAppliances.this, R.layout.elec_lv_item, R.id.tv_id, list);
        lv.setAdapter(adp);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = et.length();
                if (len > 0){
                    cross.setVisibility(View.VISIBLE);
                    cross.setOnClickListener(HomeAppliances.this);
                }else {
                    cross.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(this);
        search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                cross.setVisibility(View.GONE);
                finish();
                break;

            case R.id.search_id:
                tv.setVisibility(View.GONE);
                et.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                break;

            case R.id.cross_id:
                et.setText("");
                break;

        }
    }
}
