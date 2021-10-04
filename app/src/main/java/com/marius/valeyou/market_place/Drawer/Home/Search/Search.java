package com.marius.valeyou.market_place.Drawer.Home.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marius.valeyou.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Search extends AppCompatActivity implements View.OnClickListener {

    ImageView back,cross,mike;
    EditText et;
    TextView tv;
    ListView lv;
    ArrayList<DataModels> list;
    List_Adp adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        back = (ImageView) findViewById(R.id.back_id);
        cross = (ImageView) findViewById(R.id.cross_id);
        mike = (ImageView) findViewById(R.id.mike_id);

        et = (EditText) findViewById(R.id.et_id);

        tv = (TextView) findViewById(R.id.clear_id);

        lv = (ListView) findViewById(R.id.lv_id);

        list = new ArrayList<>();
        list.add(new DataModels("Title One",R.drawable.ic_meter));
        list.add(new DataModels("Title Two",R.drawable.ic_meter));

        adp = new List_Adp(list, getApplicationContext());
        lv.setAdapter(adp);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = et.length();
                if (len>0){
                    mike.setVisibility(View.INVISIBLE);
                    cross.setVisibility(View.VISIBLE);
                    cross.setOnClickListener(Search.this);
                }else {
                    cross.setVisibility(View.INVISIBLE);
                    mike.setVisibility(View.VISIBLE);
                    mike.setOnClickListener(Search.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(this);
        tv.setOnClickListener(this);

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

            case R.id.mike_id:
                break;

            case R.id.clear_id:
                list.clear();
                break;
        }
    }
}
