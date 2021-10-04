package com.marius.valeyou.market_place.Drawer.Home.Electronics;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.marius.valeyou.R;

import androidx.appcompat.app.AppCompatActivity;

public class Elec_Search extends AppCompatActivity implements View.OnClickListener {

    EditText et;
    ImageView iv,back;
    ListView lv;
    Elec_Search_Adp adp;

    String[] list1 = {"Refrigerators", "Washing Machines", "Air Conditioners", "Air coolers", "Water Heater/Geysers",
            "Ceiling Fans", "Table Fans", "Vaccum Cleaners"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elec_search);

        lv = (ListView) findViewById(R.id.lv_id);
        et = (EditText) findViewById(R.id.et_id);
        iv = (ImageView) findViewById(R.id.cross_id);
        back = (ImageView)  findViewById(R.id.back_id);

        adp = new Elec_Search_Adp(getApplicationContext(),list1);
        lv.setAdapter(adp);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = et.length();
                if (len > 0){
                    iv.setVisibility(View.VISIBLE);
                    iv.setOnClickListener(Elec_Search.this);
                }else{
                    iv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;

            case R.id.cross_id:
                et.setText("");
                break;
        }
    }
}
