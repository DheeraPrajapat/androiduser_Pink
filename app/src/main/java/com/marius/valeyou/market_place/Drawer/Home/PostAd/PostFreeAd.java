package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.InterstitialAd;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Category_Adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Cate_Get_Set;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class  PostFreeAd extends AppCompatActivity implements View.OnClickListener{

    public static Toolbar tb;
    public static TextView tv,tb_title;
    FrameLayout fl;
    RelativeLayout car_rl,mbl_rl,elec_rl,real_estate_rl,home_rl,job_rl,ser_rl,enter_rl,edu_rl,pet_rl,comm_rl,event_rl,matrimonial_rl;
    TextView car_tv,mbl_tv,elec_tv,real_estate_tv,home_tv,job_tv,ser_tv,enter_tv,edu_tv,pet_tv,comm_tv,event_tv,matrimonial_tv;

    RecyclerView Category_RV;
    List<Cate_Get_Set> Cate_list = new ArrayList<>();
    ImageView iv;
    Category_Adp cate_adp;
    String post_id, from_ad;
    SwipeRefreshLayout pullToRefresh;
    private InterstitialAd interstitialAd;
    String TAG = "PostFreeAd.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_free_ad);

        String num_click_post = SharedPrefrence.get_offline(PostFreeAd.this, "" + SharedPrefrence.share_num_home_visit);


        // TODO: FB Ad Display
      // Methods.display_fb_ad(PostFreeAd.this);



        tb = (Toolbar) findViewById(R.id.tb_id);
        try{
            tb = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(tb, PostFreeAd.this);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color

        pullToRefresh = findViewById(R.id.srl_id);
        tb_title = (TextView) findViewById(R.id.tb_title_id);
        tv = (TextView) findViewById(R.id.tv_id);
        fl = (FrameLayout) findViewById(R.id.fl_id);


        Category_RV = (RecyclerView) findViewById(R.id.ver_category_adp);

        // TODO: Change Recycle View Edge Glow Color
        Category_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try{
                    EdgeChanger.setEdgeGlowColor(Category_RV, Color.parseColor(Variables.Var_App_Config_header_bg_color));
                }catch (Exception v){

                } // End Catch Statement

            }
        });


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Cate_list.clear();
                pullToRefresh.setRefreshing(false);
                get_Category();
            }
        });

        Category_RV.setLayoutManager(new LinearLayoutManager(PostFreeAd.this, LinearLayoutManager.VERTICAL, false));
        Category_RV.setHasFixedSize(false);

        Intent intent = getIntent();
        post_id = intent.getStringExtra("post_id"); //if it's a string you stored.

        tv.setText("Select Category");

        PostFreeAd.tb.setVisibility(View.VISIBLE);

        car_rl = (RelativeLayout) findViewById(R.id.cars_rl_id);
        mbl_rl = (RelativeLayout) findViewById(R.id.mbls_rl_id);
        elec_rl = (RelativeLayout) findViewById(R.id.elec_rl_id);
        real_estate_rl = (RelativeLayout) findViewById(R.id.real_estate_rl_id);
        home_rl = (RelativeLayout) findViewById(R.id.home_rl_id);
        job_rl = (RelativeLayout) findViewById(R.id.jobs_rl_id);
        ser_rl = (RelativeLayout) findViewById(R.id.services_rl_id);
        enter_rl = (RelativeLayout) findViewById(R.id.entertainment_rl_id);
        edu_rl = (RelativeLayout) findViewById(R.id.edu_rl_id);
        pet_rl = (RelativeLayout) findViewById(R.id.pet_rl_id);
        comm_rl = (RelativeLayout) findViewById(R.id.community_rl_id);
        event_rl = (RelativeLayout) findViewById(R.id.event_rl_id);
        matrimonial_rl = (RelativeLayout) findViewById(R.id.matrimonial_rl_id);

        car_tv = (TextView) findViewById(R.id.cars_tv_id);
        mbl_tv = (TextView) findViewById(R.id.mbls_tv_id);
        elec_tv = (TextView) findViewById(R.id.elec_tv_id);
        real_estate_tv = (TextView) findViewById(R.id.real_estate_tv_id);
        home_tv = (TextView) findViewById(R.id.home_tv_id);
        job_tv = (TextView) findViewById(R.id.jobs_tv_id);
        ser_tv = (TextView) findViewById(R.id.services_tv_id);
        enter_tv = (TextView) findViewById(R.id.entertainment_tv_id);
        edu_tv = (TextView) findViewById(R.id.edu_tv_id);
        pet_tv = (TextView) findViewById(R.id.pet_tv_id);
        comm_tv = (TextView) findViewById(R.id.community_tv_id);
        event_tv = (TextView) findViewById(R.id.event_tv_id);
        matrimonial_tv = (TextView) findViewById(R.id.matrimonial_tv_id);

        iv = (ImageView) findViewById(R.id.back_id);

        car_tv.setText("Cars & Bikes");
        mbl_tv.setText("Mobiles & Tablets");
        elec_tv.setText("Electronics & Appliances");
        home_tv.setText("Home & Lifestyle");
        edu_tv.setText("Education & Training");
        pet_tv.setText("Pets & Pet Care");

        iv.setOnClickListener(this);
        car_rl.setOnClickListener(this);
        mbl_rl.setOnClickListener(this);
        elec_rl.setOnClickListener(this);
        real_estate_rl.setOnClickListener(this);
        home_rl.setOnClickListener(this);
        job_rl.setOnClickListener(this);
        ser_rl.setOnClickListener(this);
        enter_rl.setOnClickListener(this);
        edu_rl.setOnClickListener(this);
        pet_rl.setOnClickListener(this);
        comm_rl.setOnClickListener(this);
        event_rl.setOnClickListener(this);
        matrimonial_rl.setOnClickListener(this);

        get_Category();

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:

                finish();

                break;

            case R.id.cars_rl_id:
                Car_subcategory f = new Car_subcategory();
                METHOD_openfrag(f);
                break;

            case R.id.mbls_rl_id:
                Mbl_subcategory f1 = new Mbl_subcategory();
                METHOD_openfrag(f1);
                break;

            case R.id.elec_rl_id:
                Elec_subcategory f2 = new Elec_subcategory();
                METHOD_openfrag(f2);
                break;

            case R.id.real_estate_rl_id:
                Realestate_subcategory f3 = new Realestate_subcategory();
                METHOD_openfrag(f3);
                break;

            case R.id.home_rl_id:
                Home_subcategory f4 = new Home_subcategory();
                METHOD_openfrag(f4);
                break;

            case R.id.jobs_rl_id:
//                Jobs_subcategory f5 = new Jobs_subcategory();
//                METHOD_openfrag(f5);
                break;

            case R.id.services_rl_id:
//                Service_subcategory f6 = new Service_subcategory();
//                METHOD_openfrag(f6);
                break;

            case R.id.entertainment_rl_id:
                Entertainment_subcategory f7 = new Entertainment_subcategory();
                METHOD_openfrag(f7);
                break;

            case R.id.edu_rl_id:
                Edu_subcategory f8 = new Edu_subcategory();
                METHOD_openfrag(f8);
                break;

            case R.id.pet_rl_id:
                Pets_subcategory f9 = new Pets_subcategory();
                METHOD_openfrag(f9);
                break;

            case R.id.community_rl_id:
                Community_subcategory f10 = new Community_subcategory();
                METHOD_openfrag(f10);
                break;

            case R.id.event_rl_id:
                Event_subcategory f11 = new Event_subcategory();
                METHOD_openfrag(f11);
                break;

            case R.id.matrimonial_rl_id:
                Matrimonial_subcategory f12 = new Matrimonial_subcategory();
                METHOD_openfrag(f12);
                break;

        }
    }

    public void METHOD_openfrag(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fl_id,f).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tb.setVisibility(View.VISIBLE);
        tb_title.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tb.setVisibility(View.VISIBLE);
        tb_title.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tb.setVisibility(View.VISIBLE);
        tb_title.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
    }


    public void get_Category (){
        try{
            //JSONObject sendObj = new JSONObject("{ '': '' }");

            pullToRefresh.setRefreshing(true);

            JSONObject sendObj = new JSONObject();
            sendObj.put("" , "");



            Volley_Requests.New_Volley(
                    PostFreeAd.this,
                    "" + API_LINKS.API_MAIN_CATEGORIES,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response

                            try{
                                JSONArray msg = response.getJSONArray("msg");
                                pullToRefresh.setRefreshing(false);
                                for(int i =0; i< msg.length(); i++){
                                    JSONObject obj = msg.getJSONObject(i);
                                    JSONObject Main_cate_obj = obj.getJSONObject("MainCategory");
                                    JSONArray sub_cate_aarray = obj.getJSONArray("SubCategory");

                                    Cate_Get_Set cate = new Cate_Get_Set(
                                            "" + Main_cate_obj.getString("id"),
                                            "" +  Main_cate_obj.getString("name"),
                                            "" + Main_cate_obj.getString("label"),
                                            "" + Main_cate_obj.getString("language_id"),
                                            "" + Main_cate_obj.getString("image"),
                                            sub_cate_aarray
                                    );

                                    Cate_list.add(cate);




                                } // End for Loop

                                // Setting up adapters
                                cate_adp = new Category_Adp(
                                        Cate_list,
                                        PostFreeAd.this,

                                        new Category_Adp.onclick() {
                                            @Override
                                            public void itemclick(int pos) {

                                                if(Cate_list.size() > 0 ){
                                                    Cate_Get_Set get = Cate_list.get(pos);
                                                    get.getSub_cate();
                                                    Car_subcategory f = new Car_subcategory();
                                                    Bundle args = new Bundle();
                                                    args.putString("main_cate_id", get.getMain_cate_id());
                                                    args.putString("sub_cate",get.getSub_cate().toString());
                                                    args.putString("main_cate_name",get.getCate_name());
                                                    args.putString("post_id",post_id);
                                                    args.putString("where","");



                                                    f.setArguments(args);
                                                    tv.setText("Select Subcategory");
                                                    FragmentManager fm = getSupportFragmentManager();
                                                    FragmentTransaction ft = fm.beginTransaction();
                                                    ft.addToBackStack(null);
                                                    ft.replace(R.id.fl_id,f).commit();

                                                }



                                            }
                                        }

                                );
                                Category_RV.setAdapter(cate_adp);


                            }catch (Exception b){
                                pullToRefresh.setRefreshing(false);
                                Methods.Log_d_msg(PostFreeAd.this,"" + b.toString());
                            }




                        }
                    }


            );



        }catch (Exception v){
            pullToRefresh.setRefreshing(false);
        }



    }


    public void open_edit_ad (String main_cate_id){



        Bundle args = new Bundle();
        args.putString("main_cate_id", "");
        args.putString("main_cate_name", "");

        args.putString("sub_cate_name","");
        args.putString("sub_cate_id","");



        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fl_id, f).commit();
    }



}
