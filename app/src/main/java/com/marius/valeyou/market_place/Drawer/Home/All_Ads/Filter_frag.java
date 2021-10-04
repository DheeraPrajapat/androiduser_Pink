package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.marius.valeyou.market_place.Utils.Fragment_Callback;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Filter_frag extends Fragment implements View.OnClickListener {
    View view;
    TextView save_tv,local_tv,quikr_tv,online_tv,brand_tv,model_tv,price_tv,sim_tv, reset_all_id;
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
    ProgressDialog pd;
    ImageView no_record;

    public Filter_frag(){

    }


    Fragment_Callback fragment_callback;
    @SuppressLint("ValidFragment")
    public Filter_frag(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter, container, false);

        // Progres bar;
        pd = new ProgressDialog(getContext());
        pd.setMessage(getContext().getResources().getString(R.string.loading));
        pd.setCancelable(false);
        no_record = view.findViewById(R.id.no_record_img);
        reset_all_id = view.findViewById(R.id.reset_all_id);

        try{
            header = view.findViewById(R.id.header);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, getActivity());
        }catch (Exception b){

        }
        context = getContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            main_cate_id = bundle.getString("main_cate_id");
            sub_cate_id = bundle.getString("sub_cate_id");
        }

        save_tv = (TextView) view.findViewById(R.id.save_id);
        try{
            save_tv.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){

        }

        save_tv.setText("Save & Apply");
        iv = (ImageView) view.findViewById(R.id.cross_id);

        local_tv = (TextView) view.findViewById(R.id.local_tv_id);
        quikr_tv = (TextView) view.findViewById(R.id.quikr_tv_id);
        online_tv = (TextView) view.findViewById(R.id.online_tv_id);
        brand_tv = (TextView) view.findViewById(R.id.brand_tv_id);
        model_tv = (TextView) view.findViewById(R.id.model_tv_id);
        price_tv = (TextView) view.findViewById(R.id.price_tv_id);
        sim_tv = (TextView) view.findViewById(R.id.sim_tv_id);

        rl1 = (RelativeLayout) view.findViewById(R.id.rl1_id);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl2_id);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl3_id);
        rl4 = (RelativeLayout) view.findViewById(R.id.rl4_id);
        rl5 = (RelativeLayout) view.findViewById(R.id.rl5_id);
        rl6 = (RelativeLayout) view.findViewById(R.id.rl6_id);
        rl7 = (RelativeLayout) view.findViewById(R.id.rl7_id);

        quikr_rl = (RelativeLayout) view.findViewById(R.id.quikr_rl_id);
        price_rl = (RelativeLayout) view.findViewById(R.id.price_rl_id);
        sim_ll = (LinearLayout) view.findViewById(R.id.sim_ll_id);

        local_ll = (LinearLayout) view.findViewById(R.id.local_ll_id);
        online_ll = (LinearLayout) view.findViewById(R.id.online_ll_id);
        brand_ll = (LinearLayout) view.findViewById(R.id.brand_ll_id);
        model_ll = (LinearLayout) view.findViewById(R.id.model_ll_id);

        lv = (ListView) view.findViewById(R.id.lv_id);
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
        reset_all_id.setOnClickListener(this);
        get_form_for_filter(sub_cate_id);


        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cross_id:
                getFragmentManager().popBackStack();
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
                if(selectedItems.size() == 0){
                    // If no filter select

                    Methods.alert_dialogue(context,"Info","Please select atleast one filter.");
                }else{
                    ad_filter();
                }
                // End else part of Filter compulsary Check
                break;
            case R.id.reset_all_id:
                if(selectedItems.size() == 0){

                }else{

                    selectedItems.clear();
                    selected.clear();
                    other_layout_linear.removeAllViews();
                    ll.removeAllViews();
                    get_form_for_filter(sub_cate_id);

                }

                break;

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
            pd.show();
            ll = (LinearLayout) view.findViewById(R.id.main_filter);
            other_layout_linear = (LinearLayout) view.findViewById(R.id.local_ll_id_n);
//            myList = (ListView) findViewById(R.id.list);
            rowTextView_1 = new TextView(context);
            JSONObject sendObj = new JSONObject();
            sendObj.put("sub_category_id", "" + sub_cate_id);

            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_DYNAMIC_FORM,
                    sendObj,
                    "Dynamic",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response

                            //pd.hide();


                            try {
                                JSONArray Msg_arr = response.getJSONArray("msg");
                                //Msg_arr.put(0,"Price");
                                Methods.Log_d_msg(getContext(),"Size " + Msg_arr.length());

                                if(Msg_arr.length() == 0){
                                    // If size is zeroo


                                    no_record.setVisibility(View.VISIBLE);
                                    save_tv.setVisibility(View.GONE);
                                    reset_all_id.setVisibility(View.GONE);
                                   // Methods.showDialog_2(getActivity(),"msg");
                                    pd.hide();

                                }else {
                                    // If size is not zerooo





                                for (int i = 0; i < Msg_arr.length(); i++) {

                                    JSONObject obj = Msg_arr.getJSONObject(i);
                                    final JSONObject Form_obj = obj.getJSONObject("Form");
                                    //   int order = i+1;

                                    Form_obj.getString("id");
                                    Form_obj.getString("type");

                                    if (Form_obj.getString("type").equals("select")) {
                                        // If Type is Select ie Multiple
                                        final TextView rowTextView = new TextView(getContext());
                                        // set some properties of rowTextView or something
                                        //  rowTextView.setText("Price");
                                        //rowTextView.setTextSize(50);
                                        float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18F, context.getResources().getDisplayMetrics());
                                        //rowTextView.setTextSize(pxFromDp(20, getContext()));


                                        rowTextView.setPadding(25,25,25,25);
//                                    if(i==0){
                                        rowTextView.setBackgroundColor(getResources().getColor(R.color.white));
//                                        rowTextView.setTextSize(0);

                                        final String name = Form_obj.getString("name");

//                                    }else{
//                                        rowTextView.setBackgroundColor(getResources().getColor(R.color.gray));
//                                    }
//                                        rowTextView.setSelected(true);

                                        rowTextView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT));

                                        final int finalI = i;
                                        Methods.toast_msg(getContext(),"i " + i + " finali " +finalI);
                                        pd.hide();
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                if(finalI == 1){

                                                    rowTextView.performClick();
                                                    try{
                                                        pd.hide();
                                                    }catch (Exception b){

                                                    }


                                                }

                                                rowTextView.setText("" + name);
                                                rowTextView.setTextSize(13);
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
                                                    //curText.setClickable(false);  // todo New



                                                } else { // If this isn't selected, deselect  the previous one (if any)
                                                    if (previousText != null && previousText.isSelected()) {
                                                        previousText.setSelected(false);
                                                        //previousText.setTextColor(Color.RED);
                                                        previousText.setBackgroundColor(getResources().getColor(R.color.white));
//                                                        curText.setClickable(true); // todo :  New Logic
                                                    }
                                                    curText.setSelected(true);
                                                    //curText.setClickable(true);
//                                                    curText.setClickable(false);  // todo :  New Logic
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

                                                            }else{
                                                                // Present
                                                                // Get Index From Shared Pred

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

                                                            String val = SharedPrefrence.get_offline(context,
                                                                    "" + option_selected + selectedItems.get(op)
                                                            );
                                                            if(val == null){
                                                                // If shared Pref Val Null

                                                            }else {

                                                                String value = SharedPrefrence.get_offline(context,
                                                                        "" + value_selected + selectedItems.get(op)
                                                                );

                                                                String main_index = SharedPrefrence.get_offline(context,
                                                                        "" + index_selected + selectedItems.get(op));



                                                                if( spinnerArray_options.indexOf(value) != -1){
                                                                    // If Shared Pref Val Returned a Index in List View
                                                                    myList.setItemChecked(Integer.parseInt(val), true);

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

                                                                    Methods.Log_d_msg(context,"Sele");
                                                                    selectedItems.add(spinnerArray_options.get(position));

                                                                    SharedPrefrence.save_info_share(
                                                                            context,
                                                                            "" + position,
                                                                            "" + option_selected + spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.save_info_share(
                                                                            context,
                                                                            ""  + spinnerArray_options.get(position),
                                                                            "" + value_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.save_info_share(
                                                                            context,
                                                                            ""  + spinnerArray_options.indexOf(spinnerArray_options.get(position)),
                                                                            "" + index_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                }else{
                                                                    // If check box is not Select
                                                                    Methods.Log_d_msg(context,"Unselect");

                                                                    selectedItems.remove(selectedItems.indexOf(spinnerArray_options.get(position)));

                                                                    SharedPrefrence.remove_value(
                                                                            context,
                                                                            "" + option_selected + spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.remove_value(
                                                                            context,

                                                                            "" + value_selected +  spinnerArray_options.get(position)
                                                                    );

                                                                    SharedPrefrence.remove_value(
                                                                            context,

                                                                            "" + index_selected +  spinnerArray_options.get(position)
                                                                    );




                                                                }
                                                            }

                                                        });


                                                        other_layout_linear.addView(myList);
                                                    }catch (Exception b){
                                                        Methods.Log_d_msg(context,"" + b.toString());
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


                            } // End else of Size


                            }catch (Exception b){
                                pd.hide();
                                Methods.toast_msg(context,"Filter " + b.toString());
                            }







                        }
                    }


            );


        } catch (Exception v) {
            pd.hide();

            Methods.toast_msg(context,"Ad Filter " + v.toString());
        }




    } // end form for Filtering.


    // Calling Filter API

    public void ad_filter (){
        // Ad filter
        try {
            // Creating Objects

            for(int p=0;p< selectedItems.size();p++){
                JSONObject idsJsonObject = new JSONObject();
                idsJsonObject.put("option_id", selectedItems.get(p));
                recipients.put(idsJsonObject);


            } // End for Loop

//            selectedItems

            mainObject.put("filter",recipients);



            JSONObject sendObj = new JSONObject();


            sendObj = new JSONObject("{'price_min': '12'," +
                    " 'price_max' : '16000' ,  " +
                    "  "+mainObject.toString().replaceFirst("\\{","")+"  " +
                    "} ");

           // Methods.Log_d_msg(getContext(),"Send \n " + sendObj.toString());
           // Methods.alert_dialogue(getContext(),"Se","" + sendObj.toString());

            if(fragment_callback!=null){
                Bundle bundle=new Bundle();
                bundle.putString("filter_obj","" + sendObj.toString());
                bundle.putString("main_cate_id","" + main_cate_id);
                bundle.putString("sub_cate_id","" + sub_cate_id);
                fragment_callback.Responce(bundle);
                getFragmentManager().popBackStack();
            }

        } catch (Exception v) {
            Methods.Log_d_msg(context,"Err " + v.toString());
        }





    } // End ad FIlter

    @Override
    public void onResume() {
        super.onResume();



    }


    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }


}
