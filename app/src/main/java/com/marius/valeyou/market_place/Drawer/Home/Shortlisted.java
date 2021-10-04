package com.marius.valeyou.market_place.Drawer.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Display_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details.Post_Ads_Details;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Rv_Adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Shortlisted extends AppCompatActivity implements View.OnClickListener {
    Rv_Adp adp;
    ImageView back;
    TextView tv;
    List<Get_Set_Display_Ads> list_ads = new ArrayList<>();
    List<Get_Set_Display_Ads> list_ads_img = new ArrayList<>();
    SwipeRefreshLayout pullToRefresh;
    LinearLayout LI_no_recoed;
    RecyclerView rv;
    Toolbar header;
    ImageView no_record_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortlisted);

        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, Shortlisted.this);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color


        no_record_img = findViewById(R.id.no_record_img);
        back = (ImageView) findViewById(R.id.back_id);
        tv = (TextView) findViewById(R.id.tv_id);
        LI_no_recoed = findViewById(R.id.no_record);

        pullToRefresh = findViewById(R.id.srl_id);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Get_response();
                pullToRefresh.setRefreshing(false);
                pullToRefresh.setEnabled(false);

            }
        });

        rv = (RecyclerView) findViewById(R.id.rv_id);

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

        rv.setLayoutManager(new LinearLayoutManager(Shortlisted.this));
        rv.setHasFixedSize(false);

        back.setOnClickListener(this);
        tv.setOnClickListener(this);

        Get_response();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back_id:
                finish();
                break;

            case R.id.tv_id:
                startActivity(new Intent(Shortlisted.this, Drawer.class));
                break;

        }
    }


    public void Get_response (){
        try{
            pullToRefresh.setRefreshing(true);

            JSONObject sendObj = new JSONObject();

            String user_id = SharedPrefrence.get_user_id_from_json(Shortlisted.this);
            sendObj.put("user_id" , "" + user_id);

            Volley_Requests.New_Volley(
                    Shortlisted.this,
                    "" + API_LINKS.API_Show_Fav_Ads,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response
                            pullToRefresh.setEnabled(true);
                            pullToRefresh.setRefreshing(false);
                            try{
                                JSONArray msg = response.getJSONArray("msg");

                                if(response.getString("code").equals("201") || msg.length() == 0){
                                    // If code is 201 i.e Error
                                    Methods.No_Record_method(Shortlisted.this,no_record_img, tv);


                                }else{

                                    ArrayList<Get_Set_Display_Ads> temp_list=new ArrayList<>();

                                for(int i=0; i< msg.length();i++){
                                    JSONObject json =  msg.getJSONObject(i);
                                    JSONObject Post_obj = json.getJSONObject("Post");
                                    JSONObject User_obj = Post_obj.getJSONObject("User");
                                    JSONObject Post_Translation_obj = Post_obj.getJSONObject("PostTranslation");
                                    JSONObject like_stutus = json.getJSONObject("Favourite");
                                    like_stutus.getString("favourite");

                                    JSONObject sub_cate_obj = Post_obj.getJSONObject("SubCategory");
                                    JSONObject main_cate_obj = Post_obj.getJSONObject("MainCategory");
                                    JSONObject PostContact_obj = Post_obj.getJSONObject("PostContact");
                                    JSONArray post_img = Post_obj.getJSONArray("PostImage");
                                    //Methods.toast_msg(getContext(),"Img Length " + post_img.length());

                                    JSONObject city_obj = PostContact_obj.optJSONObject("City");
                                    String city="", city_id = "";
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
                                            "" + sub_cate_obj.getString("name"),
                                            "" + main_cate_obj.getString("name"),
                                            "" + like_stutus.getString("favourite"),
                                            "" + city,
                                            "" + PostContact_obj.getString("city_id")
                                    );
                                    temp_list.add(Ads);

                                } // End for Loop
                                    list_ads.clear();
                                    list_ads = temp_list;
                                // Setting Up Adapter
                                adp = new Rv_Adp(Shortlisted.this, new Rv_Adp.click() {
                                    @Override
                                    public void onclick(int pos) {

                                        if(list_ads.size() > 0) {
                                            Get_Set_Display_Ads ads = list_ads.get(pos);
                                            ads.getImg_array();


                                            Intent myIntent = new Intent(Shortlisted.this, Post_Ads_Details.class);
                                            myIntent.putExtra("img_arr", ads.getImg_array().toString()); //Optional parameters
                                            myIntent.putExtra("title", ads.getTitle());
                                            myIntent.putExtra("ad_id", ads.getPost_id());
                                            myIntent.putExtra("main_cate_id", ads.getMain_category_id());

                                            startActivity(myIntent);

                                        }

                                    }
                                }, list_ads );

                                rv.setAdapter(adp);
                                } // id else part of comparing code.....

//                                msg.getJSONObject(0);

                            }catch (Exception b){
                                Methods.toast_msg(Shortlisted.this," " + b.toString());
                                Methods.Log_d_msg(Shortlisted.this,"" + b.toString());
                                Methods.No_Record_method(Shortlisted.this,no_record_img, tv);

                            }





                        }
                    }


            );



        }catch (Exception v){
            //Methods.No_Record_method(Shortlisted.this,no_record_img, tv);
        }



    }

    public static void open_edit_ad (String sub_cate_name, String Sub_cate_id, String main_cate_name, String main_cate_id, String post_id, Context context, String city_id){
        Bundle args = new Bundle();
        args.putString("main_cate_id", "" + main_cate_id);
        args.putString("main_cate_name", "" + main_cate_name);
        args.putString("sub_cate_name","" + sub_cate_name);
        args.putString("sub_cate_id","" + Sub_cate_id);
        args.putString("post_id","" + post_id);
        args.putString("city_id","" + city_id);


        Methods.Log_d_msg(context,"Post " + post_id);

        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm =((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace( R.id.Main_RL, f ).addToBackStack( "All_ads" ).commit();
        //ft.replace(R.id.Main_RL, f).commit();
    }



}
