package com.marius.valeyou.market_place.Drawer.Home.Country_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.City_Listt.City_loc;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Country_list_to_select extends AppCompatActivity implements View.OnClickListener {
    RecyclerView Country_RV;
    List<Country_get_Set> Country_list = new ArrayList<>();
    country_adp country_adp;
    public static Toolbar tb;
    public static TextView tv,tb_title;
    FrameLayout fl;
    String country_list_from_local;
    ImageView search, cross, back;
    String de;
    EditText et;
    JSONObject def_country_info = new JSONObject();
    //Toolbar header;

    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic () {
        try{
            tb = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(tb, Country_list_to_select.this);
//            GradientDrawable gd = new GradientDrawable();
//            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
//            gd.setCornerRadius(5);
//            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
//            logout.setBackground(gd);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    } // End method to change Color Dynamically



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list_to_select);

        tb = (Toolbar) findViewById(R.id.tb_id);
        tb_title = (TextView) findViewById(R.id.tb_title_id);
        tv = (TextView) findViewById(R.id.tv_id);
        fl = (FrameLayout) findViewById(R.id.fl_id);

        search = findViewById(R.id.search);
        cross = findViewById(R.id.cross_id);

        et = (EditText) findViewById(R.id.et_id);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = et.length();

                filter(s.toString());

//                if (len>0){
//                    cross.setVisibility(View.VISIBLE);
//                    cross.setOnClickListener(Country_list_to_select.this);
//                }else {
//                    cross.setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Country_RV = (RecyclerView) findViewById(R.id.country_adp);

        Country_RV.setLayoutManager(new LinearLayoutManager(Country_list_to_select.this, LinearLayoutManager.VERTICAL, false));
        Country_RV.setHasFixedSize(false);


        country_list_from_local = SharedPrefrence.get_offline(Country_list_to_select.this,
                "" + SharedPrefrence.share_all_country_list);

        Methods.toast_msg(Country_list_to_select.this,"C " + country_list_from_local);

        try{
            JSONObject country = new JSONObject(country_list_from_local);



                // Save Data Into Shared Pref;

                JSONArray msg = country.getJSONArray("msg");

                for(int op = 0; op< msg.length(); op++) {

                    JSONObject option_obj = msg.getJSONObject(op);
                    JSONObject option_val = option_obj.getJSONObject("Country");
                    option_val.getString("name");
                    option_val.getString("id");
                    option_val.getString("default");

                    Country_get_Set country_add = new Country_get_Set(
                            "" +  option_val.getString("id"),
                            "" + option_val.getString("name"),
                            "" +option_val.getString("code"),
                            "" + option_val.getString("active"),
                            "" + option_val.getString("default")
                    );
                    // Default country remain on Top
                    if(option_val.getString("default").equals("1")) {
//                        Country_list.add(0, country_add);
                        de = option_val.getString("default");
                        //tv.setText("" + option_val.getString("name"));
                    }
//                    }else{
                        Country_list.add(country_add);

                  //  }




                }  // End For Loop

            // Setting Up adapters

            country_adp = new country_adp(Country_list_to_select.this,
                    new country_adp.click() {
                        @Override
                        public void onclick(int pos) {

//                            Country_get_Set country_info = Country_list.get(pos);
//                            Methods.toast_msg(Country_list_to_select.this,"" + country_info.getName());
//
//                            try{
//                                def_country_info.put("country_id" , "" + country_info.getId());
//                                def_country_info.put("country_name" , "" + country_info.getName());
//                                SharedPrefrence.save_info_share(
//                                        Country_list_to_select.this,
//                                        "" + def_country_info.toString(),
//                                        "" + SharedPrefrence.share_default_country_info
//                                );
//                            }catch (Exception b){
//                                Methods.toast_msg(Country_list_to_select.this,"Err in Cpj " + b.toString());
//                            }

                          ///  Methods.toast_msg(Country_list_to_select.this,"Err in Cpj " + def_country_info);

//                            Intent myIntent = new Intent(Country_list_to_select.this, City_loc.class);
//                            myIntent.putExtra("country_id", country_info.getId()); //Optional parameters
//                            myIntent.putExtra("country_name", country_info.getName()); //Optional parameters
//                            startActivity(myIntent);


                          //  finish();

                        }
                    },Country_list
            );

            // TODO: Change Recycle View Edge Glow Color
            Country_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    try{
                        EdgeChanger.setEdgeGlowColor(Country_RV, Color.parseColor(Variables.Var_App_Config_header_bg_color));
                    }catch (Exception v){

                    } // End Catch Statement
                }
            });
            Country_RV.setAdapter(country_adp);
            Country_RV.getLayoutManager().scrollToPosition(Integer.parseInt(de));


        }catch (Exception b){
            Methods.toast_msg(Country_list_to_select.this,"Err " + b.toString());
        }



        back = findViewById(R.id.back_id);
        back.setOnClickListener(this);
        cross.setOnClickListener(this);
        search.setOnClickListener(this);

        Change_Color_Dynmic();

    }

    // Get Country List

    @Override
    public void onBackPressed() {

        try{
            String default_country = SharedPrefrence.get_offline(Country_list_to_select.this,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
//            country_name = findViewById(R.id.country_name);
//            country_name.setText("" + de.getString("country_name"));
////            de.getString("");
//            country_id = de.getString("country_id");

            Intent myIntent = new Intent(Country_list_to_select.this, City_loc.class);
            myIntent.putExtra("country_id", de.getString("country_id")); //Optional parameters
            myIntent.putExtra("country_name", de.getString("country_name")); //Optional parameters
            startActivity(myIntent);
            finish();


            Methods.toast_msg(Country_list_to_select.this,"" + de.getString("country_name") + " " + de.getString("country_id"));
        }catch (Exception b){
            Methods.toast_msg(Country_list_to_select.this,"" + b.toString());
        }




    }


    void filter(String text){

        List<Country_get_Set> temp = new ArrayList();
        for(Country_get_Set d: Country_list){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                // Methods.toast_msg(getContext(),"" + d.getName());
                temp.add(d);
            }
        }
        //update recyclerview
        country_adp.updateList((ArrayList<Country_get_Set>) temp);
    }



//    public void get_Country_list(){
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:

                try{
                    String default_country = SharedPrefrence.get_offline(Country_list_to_select.this,
                            SharedPrefrence.share_default_country_info
                    );
                    JSONObject de = new JSONObject(default_country);
//            country_name = findViewById(R.id.country_name);
//            country_name.setText("" + de.getString("country_name"));
////            de.getString("");
//            country_id = de.getString("country_id");

                    Intent myIntent = new Intent(Country_list_to_select.this, City_loc.class);
                    myIntent.putExtra("country_id", de.getString("country_id")); //Optional parameters
                    myIntent.putExtra("country_name", de.getString("country_name")); //Optional parameters
                    startActivity(myIntent);
                    finish();


                    Methods.toast_msg(Country_list_to_select.this,"" + de.getString("country_name") + " " + de.getString("country_id"));
                }catch (Exception b){
                    Methods.toast_msg(Country_list_to_select.this,"" + b.toString());
                }


//                Intent myIntent = new Intent(Country_list_to_select.this, City_loc.class);
//                myIntent.putExtra("country_id", ""); //Optional parameters
//                myIntent.putExtra("country_name",""); //Optional parameters
//                startActivity(myIntent);
//                finish();
                break;

            case R.id.cross_id:
                et.setText("");
                et.setVisibility(View.GONE);
                cross.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                tb_title.setVisibility(View.VISIBLE);
                Methods.hideKeyboardFrom(Country_list_to_select.this,et);
                break;
            case R.id.search:
//                et.setText("");
                et.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                cross.setVisibility(View.VISIBLE);
                tb_title.setVisibility(View.GONE);
                Methods.show_Keyboard(Country_list_to_select.this,et);

                break;


        }
    }





}
