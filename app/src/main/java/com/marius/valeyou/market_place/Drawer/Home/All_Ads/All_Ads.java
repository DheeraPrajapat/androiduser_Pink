package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Display_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details.Post_Ads_Details;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Car_subcategory;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;
import com.marius.valeyou.market_place.Utils.Fragment_Callback;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class All_Ads extends Fragment implements View.OnClickListener{

    View view;
    RecyclerView rv;
    Rv_Adp adp;
    TextView sort_tv,filter_tv, section_name_text;
    public static TextView sub_cate_name;
    RelativeLayout filter_bar;
    String[] list = {"Recently Posted","Nearest","Lowest Price","Highest Price"};
    List<Get_Set_Display_Ads> list_ads = new ArrayList<>();
    String pre_main_cate_id, pre_sub_cate_id;
    SwipeRefreshLayout pullToRefresh;
    String main_cate_id, sub_cate_id, section_id, API_url, filter_response, section_name;
    JSONObject sendObj, filter_obj;
    ImageView arrow_id, ic_back;
    public static ImageView no_record_img;
    Toolbar header;
    String sub_cate_name_text = "";
    FrameLayout Main_frame_Layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_ads, container, false);

        try{
            header = view.findViewById(R.id.header);
            Methods.Change_header_color(header, getActivity());

        }catch (Exception v){

        } // End Catch of changing Toolbar Color

        // TODO: Ads Dsipaly
        Methods.display_fb_ad(getContext(),null);


        no_record_img = view.findViewById(R.id.no_record_img);
        pullToRefresh = view.findViewById(R.id.srl_id);
        sub_cate_name = view.findViewById(R.id.sub_cate_name);
        arrow_id = view.findViewById(R.id.arrow_id);
        filter_bar = view.findViewById(R.id.filter_bar);
        ic_back = view.findViewById(R.id.ic_back);
        section_name_text = view.findViewById(R.id.section_name);
        Main_frame_Layout = view.findViewById(R.id.Main_frame_Layout);
        rv = (RecyclerView) view.findViewById(R.id.rv_id);
        // TODO: Change Recycle View Edge Glow Color
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try{
                    EdgeChanger.setEdgeGlowColor(rv, Color.parseColor(Variables.Var_App_Config_header_bg_color));
                }catch (Exception v){

                } // End Catch Statement
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Get_response(pre_main_cate_id,pre_sub_cate_id);


            }
        });
        sort_tv =  view.findViewById(R.id.sort_id);
        filter_tv = view.findViewById(R.id.filter_id);



        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        //pre_main_cate_id, pre_sub_cate_id;
        Bundle bundle = getArguments();
        if (bundle != null) {
            pre_main_cate_id = bundle.getString("main_cate_id");
            pre_sub_cate_id = bundle.getString("sub_cate_id");
            section_id = bundle.getString("section_id");
            filter_response = bundle.getString("filter_resp");
            section_name = bundle.getString("section_name");
            sub_cate_name_text = bundle.getString("sub_cate_name");
            Methods.Log_d_msg(getContext(),"Sub " + sub_cate_name_text);
            if(section_name != null){
                sub_cate_name.setText("" + section_name);
            }
            sub_cate_name.setText("" + section_name);
            Methods.toast_msg(getContext(),"Sec " + section_id + " " + filter_response);
        }


        Methods.toast_msg(getContext(),"Sec " + pre_main_cate_id + " " + pre_sub_cate_id);

        sort_tv.setOnClickListener(this);
        filter_tv.setOnClickListener(this);
        sub_cate_name.setOnClickListener(this);
        ic_back.setOnClickListener(this);

        Get_Sub_Categories();

        //You need to add the following line for this solution to work; thanks skayred
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {

                    getFragmentManager().popBackStack();

                    return true;
                }
                return false;
            }
        } );

        Get_response(pre_main_cate_id,pre_sub_cate_id);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        no_record_img.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_id:
                // Finish current Fragment
                Filter_frag filter = new Filter_frag(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        rv.setVisibility(View.VISIBLE);
                        list_ads.clear();
                        String obj = bundle.getString("filter_obj");
                        main_cate_id = bundle.getString("main_cate_id");
                        sub_cate_id = bundle.getString("sub_cate_id");
                        ad_filter(obj);
                        Methods.Log_d_msg(getContext(),"Filter " + main_cate_id + " " + sub_cate_id + " \n " + obj);

                    }
                });
                Bundle args_filter = new Bundle();
                args_filter.putString("sub_cate_id", "" + sub_cate_id);
                args_filter.putString("main_cate_id", "" + main_cate_id);

                filter.setArguments(args_filter);
                FragmentManager fm_1 = getChildFragmentManager();
                FragmentTransaction ft_1 = fm_1.beginTransaction();
                ft_1.addToBackStack(null);
                ft_1.replace(R.id.Main_frame_Layout, filter).addToBackStack( "All_ads" ).commit();

                break;

            case R.id.sort_id:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Sort By");

                View d_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_lv_item, null);
                TextView tv = (TextView) d_view.findViewById(R.id.tv_id);

                ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),R.layout.dialog_lv_item,R.id.tv_id,list);
                builder.setAdapter(adp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
                break;

            case R.id.sub_cate_name:
                Car_subcategory f = new Car_subcategory(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        String obj = bundle.getString("sub_cate_name");
                        sub_cate_id = bundle.getString("sub_cate_id");
                       // sub_cate_id = pre_sub_cate_id;
                        pre_sub_cate_id = sub_cate_id;
                        main_cate_id =  bundle.getString("main_cate_id");
                        list_ads.clear();
                        // Get Response
                        Get_response("0",sub_cate_id);
                        Methods.Log_d_msg(getContext(),"" + sub_cate_id + " " + main_cate_id);
                        sub_cate_name.setText(Html.fromHtml( obj) );



                    }
                });
                Bundle args = new Bundle();
                args.putString("main_cate_id", "" + main_cate_id);
                args.putString("main_cate_name", "" + sub_cate_id);
                args.putString("post_id", "");
                args.putString("where", "" + Variables.Var_com_where_page_all_ads);

                f.setArguments(args);
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace( R.id.Main_frame_Layout, f ).commit();

                break;
            case R.id.ic_back:
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Methods.Log_d_msg(getContext(), "Resume.");
    }


    public void Get_response (String main_cate_id, String sub_cate_id) {
        try{


            // showing refresh animation before making http call
            pullToRefresh.setRefreshing(true);

            if(main_cate_id.equals("") || sub_cate_id.equals("")){
                // If Section id is  Null
                sendObj = new JSONObject();
                sendObj.put("section_id" , "" + section_id);
                sendObj.put("country_id", "" + SharedPrefrence.Country_id_from_Json(getContext()));

                if(SharedPrefrence.get_user_id_from_json(getContext()) == null){
                    sendObj.put("user_id" , "0");
                }else{
                    sendObj.put("user_id" , "" + SharedPrefrence.get_user_id_from_json(getContext()));
                }


                API_url = API_LINKS.API_Show_Section_Against_SectionID;

                // Hide Filter Bar
                filter_bar.setVisibility(View.GONE);
                arrow_id.setVisibility(View.GONE);
                sub_cate_name.setVisibility(View.GONE); // Sub cate Name Visible Gone
                section_name_text.setVisibility(View.VISIBLE);
                section_name_text.setText("" + section_name); // Set the section name TextView

            }else{

                // If section id null
                sendObj = new JSONObject();
                sendObj.put("main_cat_id" , "" + main_cate_id);
                sendObj.put("sub_cat_id", "" + sub_cate_id);
                sendObj.put("user_id", "" + SharedPrefrence.get_user_id_from_json(getContext()));
                sendObj.put("country_id", "" + SharedPrefrence.Country_id_from_Json(getContext()));
                API_url = API_LINKS.API_show_Posts_AgainstCategory;

            }

            pullToRefresh.setRefreshing(true);

            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_url,
                    sendObj,
                    "All Ads",
                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            Methods.Log_d_msg(getContext(),"Section Ads " + requestType+  " " + response.toString());
                            // Maipulate Response

                            pullToRefresh.setRefreshing(false);
                            try{
                                JSONArray msg = response.getJSONArray("msg");

                                if(msg.length() == 0){

                                    // If no Record

                                    no_record_img.setVisibility(View.VISIBLE);

                                }else{
                                    Methods.Log_d_msg(getContext(),"Sec in Resp " + section_id);
                                    if(section_id.equals("")){
                                        Response_handle_for_sub_cate_ads(response);
                                    } else if(section_id.equals("filter")) {
                                        Response_handle_for_filtered_data(response);
                                    } else {
                                        Response_handle_for_sectioned_ads(response);
                                    }

                                    // Calling Methods
                                }


                            }catch (Exception b){
                                try{
                                    Methods.Log_d_msg(getContext(),"e " + b.toString());
                                    pullToRefresh.setRefreshing(false);

                                }catch (Exception bb){

                                }

                            }

                        }
                    }


            );



        }catch (Exception v){
            pullToRefresh.setRefreshing(false);
            Methods.Log_d_msg(getContext(),"e " + v.toString());
        }



    }

    // This is ok.
    public void Response_handle_for_sectioned_ads (JSONObject response){
        Methods.Log_d_msg(getContext(),"Response Handle okk " + response.toString());

        try {
            JSONArray msg = response.getJSONArray("msg");
            if(msg.length() == 0){
                // If no Record
                Methods.alert_dialogue(getContext(),
                        "Info",
                        "No Record Yet"
                );
            }else {

                ArrayList<Get_Set_Display_Ads> temp_list=new ArrayList<>();
                for(int i=0; i< msg.length();i++) {
                    JSONObject json = msg.getJSONObject(i);
                    JSONObject Post_obj = json.getJSONObject("Post");
                    JSONObject User_obj = Post_obj.getJSONObject("User");
                    JSONObject Post_Translation_obj = Post_obj.getJSONObject("PostTranslation");
                    JSONArray post_img = Post_obj.getJSONArray("PostImage");
                    JSONObject PostContact_obj = Post_obj.getJSONObject("PostContact");
                    // todo: Here Sectioned Ads.....
                    JSONObject Sub_cate_obj =  Post_obj.optJSONObject("SubCategory");
                    String main_cate_name = "";
                    if(Sub_cate_obj != null){
                        JSONObject main_cate_obj = Sub_cate_obj.getJSONObject("MainCategory");
                        main_cate_name = main_cate_obj.getString("name");
                    }else{

                    }

                    JSONObject city_obj = PostContact_obj.optJSONObject("City");
                    String city="";
                    if(city_obj!=null){
                        city=city_obj.optString("name");
                    }

                    if(Post_obj.getString("active").equals("1")){
                        // If posts are Active
                        Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                                "" + Post_obj.getString("id"),
                                "" + Post_obj.getString("price"),
                                "" + Post_obj.getString("created"),
                                "" + Post_Translation_obj.getString("title"),
                                "" + User_obj.getString("id"),
                                "" + User_obj.getString("full_name") ,
                                "" + User_obj.getString("email") ,
                                "" +  User_obj.getString("image"),
                                post_img,
                                "" + Post_obj.getString("sub_category_id"),
                                "" + Post_obj.getString("main_category_id"),
                                "" ,
                                "" + main_cate_name,
                                "" + Post_obj.getString("favourite"),
                                "" + city,
                                "" + "" + PostContact_obj.getString("city_id")
                        );
                        temp_list.add(Ads);
                    }else{
                        // If posts are not Active

                    }
                } // End For Loop

                list_ads.clear();
                list_ads = temp_list;
                adp = new Rv_Adp(getContext(), new Rv_Adp.click() {
                    @Override
                    public void onclick(int pos) {

                        if(list_ads.size() > 0){
                            Get_Set_Display_Ads ads = list_ads.get(pos);
                            ads.getImg_array();

                            Intent myIntent = new Intent(getContext(), Post_Ads_Details.class);
                            myIntent.putExtra("img_arr", ads.getImg_array().toString()); //Optional parameters
                            myIntent.putExtra("title", ads.getTitle());
                            myIntent.putExtra("ad_id", ads.getPost_id());
                            myIntent.putExtra("main_cate_id", ads.getMain_category_id());
                            startActivity(myIntent);
                        }




                    }
                },list_ads);

                rv.setAdapter(adp);



            } // End else part




        }catch (Exception b){
            Methods.Log_d_msg(getContext(),"" +  b.toString());
        }





    }


    public void Response_handle_for_sub_cate_ads(JSONObject response){

        try{
            JSONArray msg = response.getJSONArray("msg");

            if(msg.length() == 0){
                // If no Record
                Methods.alert_dialogue(getContext(),
                        "Info",
                        "No Record Yet"
                );
            }else {

                ArrayList<Get_Set_Display_Ads> temp_list=new ArrayList<>();
                for(int i=0; i< msg.length();i++){
                    JSONObject json =  msg.getJSONObject(i);
                    JSONObject Post_obj = json.getJSONObject("Post");
                    JSONObject User_obj = json.getJSONObject("User");
                    JSONObject sub_cate_obj = json.getJSONObject("SubCategory");
                    JSONObject main_cate_obj = json.getJSONObject("MainCategory");

                    JSONObject Post_Translation_obj = json.getJSONObject("PostTranslation");
                    JSONObject PostContact_obj = json.getJSONObject("PostContact");
                    JSONArray post_img = json.getJSONArray("PostImage");

                    JSONObject city_obj = PostContact_obj.optJSONObject("City");
                    String city="";

                    if(city_obj!=null){
                        city=city_obj.optString("name");
                    }

                    if(Post_obj.getString("active").equals("1")){
                        // If posts are Active
                        Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                                "" + Post_obj.getString("id"),
                                "" + Post_obj.getString("price"),
                                "" + Post_obj.getString("created"),
                                "" + Post_Translation_obj.getString("title"),
                                "" + User_obj.getString("id"),
                                "" + User_obj.getString("full_name") ,
                                "" + User_obj.getString("email") ,
                                "" +  User_obj.getString("image"),
                                post_img,
                                "" + Post_obj.getString("sub_category_id"),
                                "" + Post_obj.getString("main_category_id"),
                                "" + sub_cate_obj.getString("name"),
                                "" + main_cate_obj.getString("name"),
                                "" +  Post_obj.getString("favourite"),
                                "" + city,
                                "" + "" + PostContact_obj.getString("city_id")

                        );
                        temp_list.add(Ads);
                    } // End Else Condition




                } // End for Loop

                list_ads.clear();
                list_ads = temp_list;
                adp = new Rv_Adp(getContext(), new Rv_Adp.click() {
                    @Override
                    public void onclick(int pos) {

                        if(list_ads.size() > 0){
                            Get_Set_Display_Ads ads = list_ads.get(pos);
                            ads.getImg_array();

                            Intent myIntent = new Intent(getContext(), Post_Ads_Details.class);
                            myIntent.putExtra("img_arr", ads.getImg_array().toString()); //Optional parameters
                            myIntent.putExtra("title", ads.getTitle());
                            myIntent.putExtra("ad_id", ads.getPost_id());
                            myIntent.putExtra("main_cate_id", ads.getMain_category_id());
                            startActivity(myIntent);
                        }




                    }
                },list_ads);

                rv.setAdapter(adp);



            }

        }catch (Exception b){
            Methods.Log_d_msg(getContext(),"e " + b.toString());
        }



    }


    //onStart

    @Override
    public void onStart() {
        super.onStart();
    }






        public void Get_Sub_Categories (){
        if(section_id.equals("sub_name")){

        }else{

            try {
                JSONObject sendObj = new JSONObject();
                if(pre_main_cate_id.equals("0")){
                    // If Main_Cate_Id is zero then we will use sub cate_id

                    sendObj.put("sub_cat_id" , "" + pre_sub_cate_id);
                    sendObj.put("country_id" , "" + SharedPrefrence.Country_id_from_Json(getContext()));
                }else if(pre_sub_cate_id.equals("0")){
                    //  if sub category id is zeroooo

                    sendObj.put("main_cat_id" , "" + pre_main_cate_id);
                    sendObj.put("country_id" , "" + SharedPrefrence.Country_id_from_Json(getContext()));

                }else{

                    sendObj.put("main_cat_id" , "" + pre_main_cate_id); //todo: Remember this....
                    sendObj.put("country_id" , "" + SharedPrefrence.Country_id_from_Json(getContext()));
                }




                Volley_Requests.New_Volley(
                        getContext(),
                        "" + API_LINKS.API_Show_show_Categories,
                        sendObj,
                        "Show Cate ",

                        new CallBack() {
                            @Override
                            public void Get_Response(String requestType, JSONObject response) {
                                Methods.Log_d_msg(getContext()," " + requestType+  " Sub cate Res " + response.toString());
                                // Maipulate Response
                                try{

                                    if(pre_main_cate_id.equals("0")){
                                        // If Main Category is zero then different response
                                        // Sub_cate_id  is sending params
                                        JSONObject msg_obj = response.getJSONObject("msg");
                                        JSONObject SubCategory_obj = msg_obj.getJSONObject("SubCategory");
                                        JSONObject MainCategory_obj = msg_obj.getJSONObject("MainCategory");
                                        SubCategory_obj.getString("name");
                                        sub_cate_id = SubCategory_obj.getString("id");
                                        sub_cate_name.setText(Html.fromHtml( SubCategory_obj.getString("name")));
                                        main_cate_id = MainCategory_obj.getString("id");



                                    }else if(pre_sub_cate_id .equals("0")){
                                        // if sub cate id is zero then different response

                                        JSONArray msg_arr = response.getJSONArray("msg");
                                        for(int i=0;i< msg_arr.length();i++){
                                            JSONObject get_obj = msg_arr.getJSONObject(i);
                                            JSONObject SubCategory_obj = get_obj.getJSONObject("SubCategory");
                                            SubCategory_obj.getString("name");
                                            if(i==0){
                                                sub_cate_id = SubCategory_obj.getString("id");
                                            }
                                        } // End For Loop

                                    }

                                }catch (Exception v){
                                    Methods.Log_d_msg(getContext(),"ety " + v.toString());
                                }
                            }
                        }


                );


            } catch (Exception v) {
                Methods.Log_d_msg(getContext(),"Rre" + v.toString());
            }
        }

    }  // End get Section News


    public void Response_handle_for_filtered_data (JSONObject response){


        try {
            JSONArray msg = response.getJSONArray("msg");
//            Methods.toast_msg(getContext(),"");
            if(msg.length() == 0){
                // If no Record
                Methods.alert_dialogue(getContext(),
                        "Info",
                        "No Record Yet"
                );
            }else {

                ArrayList<Get_Set_Display_Ads> temp_list=new ArrayList<>();

                for(int i=0; i< msg.length();i++) {
                    JSONObject json = msg.getJSONObject(i);
                    JSONObject Post_obj = json.getJSONObject("Post");
                    JSONObject User_obj = Post_obj.getJSONObject("User");
                    JSONObject Post_Translation_obj = Post_obj.getJSONObject("PostTranslation");
                    JSONArray post_img = Post_obj.getJSONArray("PostImage");
                    JSONObject PostContact_obj = Post_obj.getJSONObject("PostContact");
                    JSONObject main_cate_obj = json.getJSONObject("MainCategory");
                    JSONObject city_obj = PostContact_obj.optJSONObject("City");
                    String city="";
                    if(city_obj!=null){
                        city=city_obj.optString("name");
                    }

                    Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                            "" + Post_obj.getString("id"),
                            "" + Post_obj.getString("price"),
                            "" + Post_obj.getString("created"),
                            "" + Post_Translation_obj.getString("title"),
                            "" + User_obj.getString("id"),
                            "" + User_obj.getString("full_name") ,
                            "" + User_obj.getString("email") ,
                            "" +  User_obj.getString("image"),
                            post_img,
                            "" + Post_obj.getString("sub_category_id"),
                            "" + Post_obj.getString("main_category_id"),
                            ""  ,
                            "" + main_cate_obj.getString("name") ,
                            "0",
                            "" + city,
                            "" + "" + PostContact_obj.getString("city_id")
                    );
                    list_ads.add(Ads);


                }

                list_ads.clear();
                list_ads = temp_list;
                adp.notifyDataSetChanged();

            } // End else part

        }catch (Exception b){
            Methods.Log_d_msg(getContext(),"Err " +  b.toString());
        }




    }


    public void open_edit_ad (String sub_cate_name, String Sub_cate_id, String main_cate_name, String main_cate_id, String post_id ){

        Bundle args = new Bundle();
        args.putString("main_cate_id", "" + main_cate_id);
        args.putString("main_cate_name", "" + main_cate_name);
        args.putString("sub_cate_name","" + sub_cate_name);
        args.putString("sub_cate_id","" + Sub_cate_id);
        args.putString("post_id","" + post_id);

        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.Main_RL, f).commit();
    }


// TODO : Original Filter Method.


    public void ad_filter (String obj){
        // Ad filter
        pullToRefresh.setRefreshing(true);
        try {
            JSONObject sendObj = new JSONObject(obj);
            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_LINKS.API_Show_filter_data,
                    sendObj,
                    "Filter",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, final JSONObject response) {
                            // Maipulate Response
                            pullToRefresh.setRefreshing(false);
                            Methods.toast_msg(getContext(),"" + response.toString());
                            try {
                                JSONArray msg = response.getJSONArray("msg");
                                if(msg.length() == 0){
                                    // If no Record
                                    Methods.alert_dialogue(getContext(),
                                            "Info",
                                            "No Record Yet"
                                    );
                                }else {
                                    for(int i=0; i< msg.length();i++) {
                                        JSONObject json = msg.getJSONObject(i);
                                        JSONObject Post_obj = json.getJSONObject("Post");
                                        JSONObject User_obj = Post_obj.getJSONObject("User");
                                        JSONObject Post_Translation_obj = Post_obj.getJSONObject("PostTranslation");
                                        JSONArray post_img = Post_obj.getJSONArray("PostImage");
                                        JSONObject PostContact_obj = Post_obj.getJSONObject("PostContact");
                                        JSONObject city_obj = PostContact_obj.optJSONObject("City");
                                        String city="";
                                        if(city_obj!=null){
                                            city=city_obj.optString("name");
                                        }

                                        Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                                                "" + Post_obj.getString("id"),
                                                "" + Post_obj.getString("price"),
                                                "" + Post_obj.getString("created"),
                                                "" + Post_Translation_obj.getString("title"),
                                                "" + User_obj.getString("id"),
                                                "" + User_obj.getString("full_name") ,
                                                "" + User_obj.getString("email") ,
                                                "" +  User_obj.getString("image"),
                                                post_img,
                                                "" + Post_obj.getString("sub_category_id"),
                                                "" + Post_obj.getString("main_category_id"),
                                                "" ,
                                                "cc" ,
                                                "0",
                                                "" + city,
                                                "" + "" + PostContact_obj.getString("city_id")
                                        );
                                        list_ads.add(Ads);






                                    } // End For Loop

                                    // Setting Up Adapter
                                    adp = new Rv_Adp(getContext(), new Rv_Adp.click() {
                                        @Override
                                        public void onclick(int pos) {

                                            if(list_ads.size() > 0){
                                                Get_Set_Display_Ads ads = list_ads.get(pos);
                                                ads.getImg_array();

                                                Intent myIntent = new Intent(getContext(), Post_Ads_Details.class);
                                                myIntent.putExtra("img_arr", ads.getImg_array().toString()); //Optional parameters
                                                myIntent.putExtra("title", ads.getTitle());
                                                myIntent.putExtra("ad_id", ads.getPost_id());
                                                myIntent.putExtra("main_cate_id", ads.getMain_category_id());
                                                startActivity(myIntent);
                                            }


                                        }
                                    },list_ads);

                                    rv.setAdapter(adp);

                                } // End else part

                            }catch (Exception b){
                                pullToRefresh.setRefreshing(false);
                                Methods.Log_d_msg(getContext(),"Err " +  b.toString());
                            }

                        }
                    }


            );


        } catch (Exception v) {
            pullToRefresh.setRefreshing(false);
            Methods.Log_d_msg(getContext(),"Err " + v.toString());
        }





    } // End ad FIlter



}
