package com.marius.valeyou.market_place.Drawer.Home.City_Listt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Drawer.Home.Country_list.Country_list_to_select;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class City_loc extends AppCompatActivity implements View.OnClickListener {
    JSONObject def_country_info = new JSONObject();
    ImageView back, cross;
    EditText et;
    ListView popular_lv, other_lv;

    String[] popular_list = {"Lahore", "Islamabad", "Karachi", "Multan", "Faisalabad", "Quetta", "Peshawar"};

    String[] other_list = {"Attock", "Bahawalpur", "Chaman", "Dera ismael Khan", "Faisalabad", "Gujrat",
            "Hyderabad", "Jhang", "Karachi", "Lahore", "Multan", "Nawabshah", "Peshawar", "Quetta"};
    //    List<String> City_list = new ArrayList<>();
    List<City_Get_Set> City_list = new ArrayList<>();

    TextView country_name;
    String country_id;
    Toolbar header;
    String country_id_ok, country_name_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_loc);

        try {
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, City_loc.this);
        } catch (Exception v) {

        } // End Catch of changing Toolbar Color

        try {
            Intent intent = getIntent();
            country_id_ok = intent.getExtras().getString("country_id");
            country_name_ok = intent.getExtras().getString("country_name");

        } catch (Exception v) {

        }


        back = (ImageView) findViewById(R.id.back_id);
        cross = (ImageView) findViewById(R.id.cross_id);

        et = (EditText) findViewById(R.id.et_id);

        popular_lv = (ListView) findViewById(R.id.popular_lv_id);
        other_lv = (ListView) findViewById(R.id.other_lv_id);

        // Get Default Country ID and country name

        try {

            String default_country = SharedPrefrence.get_offline(City_loc.this,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
            country_id = de.getString("country_id");
            //  country_id_ok = country_id;


//            String default_country = SharedPrefrence.get_offline(City_loc.this,
//                    SharedPrefrence.share_default_country_info
//                    );
//            JSONObject de = new JSONObject(default_country);
            country_name = findViewById(R.id.country_name);

            if (country_name_ok != null) {
                country_name.setText("" + country_name_ok);
            } else {
                country_name.setText("" + de.getString("country_name"));
            }


//            de.getString("");
            // country_id = de.getString("country_id");

            Methods.Log_d_msg(City_loc.this, "" + de.getString("country_name"));
        } catch (Exception b) {
            Methods.Log_d_msg(City_loc.this, "" + b.toString());
        }


//        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.city_loc_item, R.id.tv_id, other_list);
//        other_lv.setAdapter(adp1);

        justifyListViewHeightBasedOnChildren(other_lv);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = et.length();
                if (len > 0) {
                    cross.setVisibility(View.VISIBLE);
                    cross.setOnClickListener(City_loc.this);
                } else {
                    cross.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(this);
        try {
            country_name.setOnClickListener(this);
        } catch (Exception b) {

        }


        Get_Country();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_id:
                startActivity(new Intent(City_loc.this, Drawer.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


                finish();
                break;

            case R.id.cross_id:
                et.setText("");
                break;

            case R.id.country_name:


                startActivity(new Intent(City_loc.this, Country_list_to_select.class));
                finish();
                break;


        }
    }


    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    // Get All Cities

    public void Get_Country() {
        try {
            //JSONObject sendObj = new JSONObject("{ '': '' }");


            JSONObject sendObj = new JSONObject();

            if (country_id_ok != null) {
                sendObj.put("country_id", "" + country_id_ok);
            } else {
                sendObj.put("country_id", "" + country_id);
            }


            Volley_Requests.New_Volley(
                    City_loc.this,
                    "" + API_LINKS.API_CITY_LIST,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            // Maipulate Response
                            Methods.toast_msg(City_loc.this, "City " + response.toString());
                            try {

//                                def_country_info.put("country_id" , "" + country_id_ok);
//                                def_country_info.put("country_name" , "" + country_name_ok);
////
////                                            ///  SharedPrefrence.remove_value(context,"" + SharedPrefrence.share_default_country_info);
////
////                                            Methods.toast_msg(City_loc.this,"Savdd " + def_country_info.toString());
////
//                                SharedPrefrence.save_info_share(
//                                        City_loc.this,
//                                        "" + def_country_info.toString(),
//                                        "" + SharedPrefrence.share_default_country_info
//                                );


                                JSONArray msg = response.getJSONArray("msg");
                                for (int i = 0; i < msg.length(); i++) {
                                    JSONObject json = msg.getJSONObject(i);
                                    JSONObject City_obj = json.getJSONObject("City");
                                    JSONObject country = json.getJSONObject("Country");

                                    City_obj.getString("name");
                                    City_obj.getString("id");
                                    City_obj.getString("country_id");
                                    country.getString("id");
                                    country.getString("name");

                                    City_Get_Set City = new City_Get_Set(
                                            "" + City_obj.getString("id"),
                                            "" + City_obj.getString("name"),
                                            "" + City_obj.getString("country_id"),
                                            "" + City_obj.getString("default"),
                                            "" + country.getString("name")

                                    );
                                    City_list.add(City);


//                                    String default_country = SharedPrefrence.get_offline(City_loc.this,
//                                            SharedPrefrence.share_default_country_info
//                                    );


                                } // End for Loop

                                //ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.city_loc_item, R.id.tv_id, City_list);

                                City_Adapter adp = new City_Adapter(getApplicationContext(), City_list);


                                popular_lv.setAdapter(adp);
                                popular_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                                            long id) {
                                        City_Get_Set city = City_list.get(position);

                                        Methods.Log_d_msg(City_loc.this, "" + city.getCity_name() + "" +
                                                " " + city.getDefault_val());

                                        try {

                                            final JSONObject def_city_info = new JSONObject();
                                            def_city_info.put("city_id", "" + city.getId());
                                            def_city_info.put("city_name", "" + city.getCity_name());

                                            // Save into Shared Pref...
                                            SharedPrefrence.save_info_share(
                                                    City_loc.this,
                                                    "" + def_city_info.toString(),
                                                    "" + SharedPrefrence.share_default_city_info
                                            );

//                                            Methods.toast_msg;


                                            if (country_name_ok != null) {

                                                def_country_info.put("country_id", "" + country_id_ok);
                                                def_country_info.put("country_name", "" + country_name_ok);
                                            } else {
                                                def_country_info.put("country_id", "" + country_id);
                                                def_country_info.put("country_name", "" + country_name);
                                            }


//
//                                            ///  SharedPrefrence.remove_value(context,"" + SharedPrefrence.share_default_country_info);
//
//                                            Methods.toast_msg(City_loc.this,"Savdd " + def_country_info.toString());
//
                                            SharedPrefrence.save_info_share(
                                                    City_loc.this,
                                                    "" + def_country_info.toString(),
                                                    "" + SharedPrefrence.share_default_country_info
                                            );


                                            startActivity(new Intent(City_loc.this, Drawer.class));
                                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                            finish();


                                        } catch (Exception b) {

                                        }


                                    }
                                });
                                justifyListViewHeightBasedOnChildren(popular_lv);

                            } catch (Exception b) {
                                Methods.Log_d_msg(City_loc.this, "" + b.toString());
                            }


                        }
                    }


            );


        } catch (Exception v) {

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }


}
