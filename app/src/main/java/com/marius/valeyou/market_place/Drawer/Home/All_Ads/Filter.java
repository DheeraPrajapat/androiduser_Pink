package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Filter extends AppCompatActivity implements View.OnClickListener {

    TextView save_tv,local_tv,quikr_tv,online_tv,brand_tv,model_tv,price_tv,sim_tv;
    ListView lv;
    ImageView iv;
//    ListView myList;

    JSONObject mainObject = new JSONObject();
    JSONArray recipients = new JSONArray();
    String option_selected = "option_";
    String value_selected = "value_";
    String index_selected = "index_";

    Lv_Adp adp;
    TextView rowTextView_1;
    ArrayList<String> selectedItems = new ArrayList<String>();
    ArrayList<String> selected = new ArrayList<String>();
    RelativeLayout rl1,rl2,rl3,rl4,rl5,rl6,rl7,quikr_rl,price_rl;
    LinearLayout local_ll,online_ll,brand_ll,model_ll,sim_ll;
    LinearLayout ll, other_layout_linear ;
    String sub_cate_id, main_cate_id;
    Context context;
    View previousView;
    Toolbar header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        try{
            header = findViewById(R.id.header);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, Filter.this);
        }catch (Exception b){

        }

        context = Filter.this;
        Intent intent = getIntent();
        sub_cate_id = intent.getStringExtra("sub_cate_id"); //if it's a string you stored.
        main_cate_id = intent.getStringExtra("main_cate_id");
        //Methods.toast_msg(context,"sub_cate_id " + sub_cate_id);

        save_tv = (TextView) findViewById(R.id.save_id);
        save_tv.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        save_tv.setText("Save & Apply");
        iv = (ImageView) findViewById(R.id.cross_id);

        local_tv = (TextView) findViewById(R.id.local_tv_id);
        quikr_tv = (TextView) findViewById(R.id.quikr_tv_id);
        online_tv = (TextView) findViewById(R.id.online_tv_id);
        brand_tv = (TextView) findViewById(R.id.brand_tv_id);
        model_tv = (TextView) findViewById(R.id.model_tv_id);
        price_tv = (TextView) findViewById(R.id.price_tv_id);
        sim_tv = (TextView) findViewById(R.id.sim_tv_id);

        rl1 = (RelativeLayout) findViewById(R.id.rl1_id);
        rl2 = (RelativeLayout) findViewById(R.id.rl2_id);
        rl3 = (RelativeLayout) findViewById(R.id.rl3_id);
        rl4 = (RelativeLayout) findViewById(R.id.rl4_id);
        rl5 = (RelativeLayout) findViewById(R.id.rl5_id);
        rl6 = (RelativeLayout) findViewById(R.id.rl6_id);
        rl7 = (RelativeLayout) findViewById(R.id.rl7_id);

        quikr_rl = (RelativeLayout) findViewById(R.id.quikr_rl_id);
        price_rl = (RelativeLayout) findViewById(R.id.price_rl_id);
        sim_ll = (LinearLayout) findViewById(R.id.sim_ll_id);

        local_ll = (LinearLayout) findViewById(R.id.local_ll_id);
        online_ll = (LinearLayout) findViewById(R.id.online_ll_id);
        brand_ll = (LinearLayout) findViewById(R.id.brand_ll_id);
        model_ll = (LinearLayout) findViewById(R.id.model_ll_id);

        lv = (ListView) findViewById(R.id.lv_id);
        adp = new Lv_Adp(getApplicationContext());

        lv.setAdapter(adp);

        iv.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        save_tv.setOnClickListener(this);
        get_form_for_filter(sub_cate_id);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cross_id:
                finish();
                break;

            case R.id.rl1_id:
                METHOD_rl1();
                break;

            case R.id.rl2_id:
                METHOD_rl2();
                break;

            case R.id.rl3_id:
                METHOD_rl3();
                break;

            case R.id.rl4_id:
                METHOD_rl4();
                break;

            case R.id.rl5_id:
                METHOD_rl5();
                break;

            case R.id.rl6_id:
                METHOD_rl6();
                break;

            case R.id.rl7_id:
                METHOD_rl7();
                break;
            case R.id.save_id:
                Methods.toast_msg(context,"" + selectedItems.toString());
                ad_filter();
        }
    }


    public void METHOD_rl1(){
        rl1.setBackgroundResource(R.color.white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.BOLD);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.VISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl2(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.BOLD);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.VISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl3(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.BOLD);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.VISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl4(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.BOLD);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.VISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl5(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.BOLD);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.VISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl6(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.white);
        rl7.setBackgroundResource(R.color.off_white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.BOLD);
        sim_tv.setTypeface(null, Typeface.NORMAL);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.VISIBLE);
        sim_ll.setVisibility(View.INVISIBLE);
    }


    public void METHOD_rl7(){
        rl1.setBackgroundResource(R.color.off_white);
        rl2.setBackgroundResource(R.color.off_white);
        rl3.setBackgroundResource(R.color.off_white);
        rl4.setBackgroundResource(R.color.off_white);
        rl5.setBackgroundResource(R.color.off_white);
        rl6.setBackgroundResource(R.color.off_white);
        rl7.setBackgroundResource(R.color.white);
        local_tv.setTypeface(null, Typeface.NORMAL);
        quikr_tv.setTypeface(null, Typeface.NORMAL);
        online_tv.setTypeface(null, Typeface.NORMAL);
        brand_tv.setTypeface(null, Typeface.NORMAL);
        model_tv.setTypeface(null, Typeface.NORMAL);
        price_tv.setTypeface(null, Typeface.NORMAL);
        sim_tv.setTypeface(null, Typeface.BOLD);
        local_ll.setVisibility(View.INVISIBLE);
        quikr_rl.setVisibility(View.INVISIBLE);
        online_ll.setVisibility(View.INVISIBLE);
        brand_ll.setVisibility(View.INVISIBLE);
        model_ll.setVisibility(View.INVISIBLE);
        price_rl.setVisibility(View.INVISIBLE);
        sim_ll.setVisibility(View.VISIBLE);
    }


    public void get_form_for_filter (String sub_cate_id){
        try {
            ll = (LinearLayout) findViewById(R.id.main_filter);
            other_layout_linear = (LinearLayout) findViewById(R.id.local_ll_id_n);
//            myList = (ListView) findViewById(R.id.list);
            rowTextView_1 = new TextView(context);
            JSONObject sendObj = new JSONObject();
            sendObj.put("sub_category_id", "" + sub_cate_id);
            Methods.toast_msg(context,"" + sendObj.toString());


            Volley_Requests.New_Volley(
                    Filter.this,
                    "" + API_LINKS.API_DYNAMIC_FORM,
                    sendObj,
                    "Dynamic",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                             ///Methods.toast_msg(Filter.this,"Section " + requestType+  " " + response.toString());
                            // Maipulate Response

//                            final TextView rowTextView_1 = new TextView(context);
//                            rowTextView_1.setText("Price");
//                            rowTextView_1.setPadding(25,25,25,25);
//                            rowTextView_1.setBackgroundColor(getResources().getColor(R.color.white));
//                            ll.addView(rowTextView_1);

                            try {
                                JSONArray Msg_arr = response.getJSONArray("msg");
                                //Msg_arr.put(0,"Price");
                                for (int i = 0; i < Msg_arr.length(); i++) {

                                    JSONObject obj = Msg_arr.getJSONObject(i);
                                    final JSONObject Form_obj = obj.getJSONObject("Form");
                                    //   int order = i+1;

                                    Form_obj.getString("id");
                                    Form_obj.getString("type");

                                    if (Form_obj.getString("type").equals("select")) {
                                        // If Type is Select ie Multiple
                                        final TextView rowTextView = new TextView(context);
                                        // set some properties of rowTextView or something
                                        //  rowTextView.setText("Price");
                                        rowTextView.setText("" + Form_obj.getString("name"));
                                        rowTextView.setPadding(25,25,25,25);
//                                    if(i==0){
                                        rowTextView.setBackgroundColor(getResources().getColor(R.color.white));
//                                    }else{
//                                        rowTextView.setBackgroundColor(getResources().getColor(R.color.gray));
//                                    }
//                                        rowTextView.setSelected(true);
                                        final int finalI = i;
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                if(finalI == 0){
                                                    rowTextView.performClick();
                                                }


                                            }
                                        }, 1000);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //rowTextView.isSelected()
//                                            if (rowTextView.isSelected()) {
//                                                rowTextView.setTextColor(Color.RED);
//                                                rowTextView.setSelected(false);
//                                            } else {
//                                                rowTextView.setTextColor(Color.BLUE);
//                                                rowTextView.setSelected(true);
//                                            }
                                                other_layout_linear.removeAllViews();
                                                final ListView myList = new ListView(context);


                                                TextView previousText = (TextView) previousView;
                                                TextView curText = (TextView) v;
//                                            curText.setBackgroundColor(getResources().getColor(R.color.gray));
//                                            previousText.setBackgroundColor(getResources().getColor(R.color.white));
                                                //If the clicked view is selected, deselect it
                                                if (curText.isSelected()) {
                                                    curText.setSelected(false);




                                                } else { // If this isn't selected, deselect  the previous one (if any)
                                                    if (previousText != null && previousText.isSelected()) {
                                                        previousText.setSelected(false);
                                                        //previousText.setTextColor(Color.RED);
                                                        previousText.setBackgroundColor(getResources().getColor(R.color.white));

                                                        // Methods.toast_msg(context,"okkk");



                                                    }
                                                    curText.setSelected(true);
                                                    //curText.setTextColor(Color.BLUE);
                                                    curText.setBackgroundColor(getResources().getColor(R.color.gray));
                                                    try{
                                                        JSONArray option_Arr = Form_obj.getJSONArray("select");
                                                        ArrayList<String> spinnerArray = new ArrayList<String>();
                                                        final  ArrayList<String> spinnerArray_options = new ArrayList<String>();
                                                        final ArrayList<String> spinnerArray_form_ids = new ArrayList<String>();
                                                        String[] types = {"By Zip", "By Category"};
                                                        for(int op = 0; op< option_Arr.length(); op++) {
                                                            // Add selection options Dynamicallu
                                                            JSONObject option_obj =  option_Arr.getJSONObject(op);
                                                            JSONObject option_val =  option_obj.getJSONObject("Option");
                                                            option_val.getString("name");

                                                            spinnerArray.add("" + option_val.getString("name"));
                                                            spinnerArray_options.add("" + option_val.getString("id"));
                                                            spinnerArray_form_ids.add("" + option_val.getString("form_id"));

                                                            if(selectedItems.indexOf(option_val.getString("id")) == -1){
                                                                // If val is not present
                                                                //Methods.toast_msg(Filter.this,"ok " + option_val.getString("id"));
                                                            }else{
                                                                // Present
                                                                // Get Index From Shared Pred


                                                                //Methods.toast_msg(Filter.this,"" + selectedItems.indexOf(option_val.getString("id")));
                                                                //Methods.toast_msg(Filter.this,"Present " + option_val.getString("id"));
//                                                            myList.setSelection(selectedItems.indexOf(option_val.getString("id")));
                                                                //myList.setItemChecked(selectedItems.indexOf(option_val.getString("id")), true);
                                                            }


                                                        }

                                                        final SearchableSpinner spinner = new SearchableSpinner(context);
                                                        //  spinner.setId();
                                                        spinner.setBackground(getResources().getDrawable(R.drawable.bottom_gray_line));
                                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                                                        spinner.setAdapter(spinnerArrayAdapter);

                                                        ///other_layout_linear.addView(spinner);

                                                        final ArrayAdapter<String> adapter

                                                                = new ArrayAdapter<String>(context,

                                                                android.R.layout.simple_list_item_multiple_choice,

                                                                spinnerArray);



                                                        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                                                        //                                                      myList.setCacheColorHint(Color.parseColor(Variables.Var_App_Config_header_bg_color));
//myList.setcol

                                                        //myList.setCacheColorHint(Color.parseColor(Variables.Var_App_Config_header_bg_color));
                                                        ///myList.setB

                                                        myList.setAdapter(adapter);

                                                        for(int op=0 ;op< selectedItems.size(); op++){
                                                            // Set by default Selection

                                                            String val = SharedPrefrence.get_offline(Filter.this,
                                                                    "" + option_selected + selectedItems.get(op)
                                                            );
                                                            if(val == null){
                                                                // If shared Pref Val Null

                                                            }else {

                                                                String value = SharedPrefrence.get_offline(Filter.this,
                                                                        "" + value_selected + selectedItems.get(op)
                                                                );

                                                                String main_index = SharedPrefrence.get_offline(Filter.this,
                                                                        "" + index_selected + selectedItems.get(op));



                                                                if( spinnerArray_options.indexOf(value) != -1){
                                                                    // If Shared Pref Val Returned a Index in List View
                                                                    myList.setItemChecked(Integer.parseInt(val), true);
                                                                    Methods.toast_msg(Filter.this,"Index New " + val + " V " + value + " Curr " + spinnerArray_options.toString());
                                                                    //myList.setSelection(Integer.parseInt(val));

                                                                }



                                                            }




                                                        }


                                                        // myList.setItemChecked(2, true);

                                                        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view,
                                                                                    int position, long id) {

                                                                SparseBooleanArray checked = myList.getCheckedItemPositions();
                                                                //Methods.alert_dialogue(Filter.this,"tit","" + checked.toString());
//                                                                myList.Ite
//                                                                myList.item[e.Index]
                                                                CheckBox cb = (CheckBox) parent.findViewById(android.R.id.checkbox);
                                                                //cb.setHighlightColor(Color.GREEN);

                                                                if (myList.isItemChecked(position)){
                                                                    // If checkbox select

                                                                    Methods.toast_msg(Filter.this,"Sele");
                                                                    selectedItems.add(spinnerArray_options.get(position));

                                                                    SharedPrefrence.save_info_share(
                                                                            Filter.this,
                                                                            "" + position,
                                                                            "" + option_selected + spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.save_info_share(
                                                                            Filter.this,
                                                                            ""  + spinnerArray_options.get(position),
                                                                            "" + value_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.save_info_share(
                                                                            Filter.this,
                                                                            ""  + spinnerArray_options.indexOf(spinnerArray_options.get(position)),
                                                                            "" + index_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                }else{
                                                                    // If check box is not Select
                                                                    Methods.toast_msg(Filter.this,"Unselect");

                                                                    selectedItems.remove(selectedItems.indexOf(spinnerArray_options.get(position)));

                                                                    SharedPrefrence.remove_value(
                                                                            Filter.this,
                                                                            "" + option_selected + spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.remove_value(
                                                                            Filter.this,

                                                                            "" + value_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.remove_value(
                                                                            Filter.this,

                                                                            "" + index_selected +  spinnerArray_options.get(position)
                                                                    );




                                                                }









                                                                //Methods.toast_msg(context,"Index Key " + option_selected + spinnerArray_options.get(position));
                                                                // ArrayList<String> selectedItems = new ArrayList<String>();
//                                                            for (int i = 0; i < checked.size(); i++) {
//                                                                // Item position in adapter
//                                                                int positio = checked.keyAt(i);
//                                                                // Add sport if it is checked i.e.) == TRUE!
//                                                                if (checked.valueAt(i))
//                                                                    selectedItems.add(spinnerArray_options.get(positio));
//                                                            }

//                                                           Methods.toast_msg(context,"Po " + position + " " + selectedItems.toString());

                                                            }

                                                        });


                                                        other_layout_linear.addView(myList);
                                                    }catch (Exception b){
                                                        Methods.toast_msg(context,"" + b.toString());
                                                    }
                                                    previousView = v;
                                                }

                                                //rowTextView.setBackgroundColor(getResources().getColor(R.color.white));
                                            }
                                        });


                                        rowTextView.setTextSize(17);
                                        // add the textview to the linearlayout
                                        ll.addView(rowTextView);




                                    } // End else of


                                } // End For Loop
                            }catch (Exception b){
                                Methods.toast_msg(context,"" + b.toString());
                            }







                        }
                    }


            );


        } catch (Exception v) {
            Methods.toast_msg(Filter.this,"" + v.toString());
        }




    } // end form for Filtering.


    // Calling Filter API

    public void ad_filter (){
        // Ad filter
        try {
            //JSONObject sendObj = new JSONObject("{ '': '' }");

            // Creating Objects


            for(int p=0;p< selectedItems.size();p++){
                JSONObject idsJsonObject = new JSONObject();
                idsJsonObject.put("option_id", selectedItems.get(p));
                recipients.put(idsJsonObject);


            } // End for Loop

//            selectedItems

            mainObject.put("filter",recipients);
           // Methods.alert_dialogue(context,"Title","" + mainObject.toString());


            JSONObject sendObj = new JSONObject();
//            sendObj.put("price_min" , "12");
//            sendObj.put("price_max" , "345");
//            sendObj.put("" , "" + mainObject);

            sendObj = new JSONObject("{'price_min': '12'," +
                    " 'price_max' : '12300' ,  " +
                    "  "+mainObject.toString().replaceFirst("\\{","")+"  " +
                    "} ");

            Methods.alert_dialogue(Filter.this,"Title","" + sendObj.toString());

            //Methods.toast_msg(context,"Sending " + sendObj);
//            Methods.alert_dialogue(context,"Title","" + sendObj);


            Volley_Requests.New_Volley(
                   context,
                    "" + API_LINKS.API_Show_filter_data,
                    sendObj,
                    "Filter",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            Methods.alert_dialogue(context,"Title" ," " + requestType+  " Sub cate Res " + response.toString());
                            // Maipulate Response
                            try{

                                All_Ads f = new All_Ads();
                                Bundle args = new Bundle();
                                args.putString("main_cate_id", "" + main_cate_id);
                                args.putString("sub_cate_id","" + sub_cate_id);
                                args.putString("section_id","filter");
                                args.putString("filter_resp","" + response.toString());

                                f.setArguments(args);
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction tx = fm.beginTransaction();
                                tx.addToBackStack(null);
                                tx.replace( R.id.Main_RL, f ).commit();

//                                SharedPrefrence.save_info_share(Filter.this,"" + main_cate_id,
//                                        "" + SharedPrefrence.share_filter_main_cate);
//
//                                SharedPrefrence.save_info_share(Filter.this,"" + sub_cate_id,
//                                        "" + SharedPrefrence.share_filter_sub_cate);
//
//                                SharedPrefrence.save_info_share(Filter.this,"" + response.toString(),
//                                        "" + SharedPrefrence.share_filter_result);
//
//                                SharedPrefrence.save_info_share(Filter.this,"filter",
//                                        "" + SharedPrefrence.share_section_filter);
//




                               finish();

                               // Methods.toast_msg(Filter.this,"Filter " + main_cate_id + " " + sub_cate_id);





                            }catch (Exception v){
                                Methods.toast_msg(Filter.this,"" + v.toString());
                            }






                        }
                    }


            );


        } catch (Exception v) {
            Methods.toast_msg(context,"Err " + v.toString());
        }





    } // End ad FIlter





}
