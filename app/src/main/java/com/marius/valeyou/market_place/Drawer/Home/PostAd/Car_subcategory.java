package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.All_Ads;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Sub_cate_adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Sub_cate_Get_Set;
import com.marius.valeyou.market_place.Login_Register.Login;
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

import static com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details.Ad_detail_activity;

//import static dinosoftlabs.com.olx.Drawer.Home.All_Ads.All_Ads.no_record_img;

public class Car_subcategory extends Fragment implements View.OnClickListener {

    View view;
    TextView bikes_tv, tb_title_id;
    RelativeLayout rl1,rl2,rl3,rl4,rl5;
    String main_cate_id, sub_cate, main_cate_name;
    JSONArray sub_cate_arr;
    RecyclerView Category_RV;
    List<Sub_cate_Get_Set> sub_Cate_list = new ArrayList<>();
    Sub_cate_adp sub_cate_adp;
    String post_id, where = "";
    Toolbar tb;
    ImageView back_id;
    SwipeRefreshLayout pullToRefresh;
    public Car_subcategory(){

    }


    Fragment_Callback fragment_callback;
    @SuppressLint("ValidFragment")
    public Car_subcategory(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }


ImageView no_record;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.car_subcatg, container, false);

        tb_title_id = view.findViewById(R.id.tb_title_id);
        pullToRefresh = view.findViewById(R.id.srl_id);

        try{
            tb = view.findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(tb, getActivity());
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
        back_id = view.findViewById(R.id.back_id);
        no_record = view.findViewById(R.id.no_record);
       /// no_record_img = view.findViewById(R.id.no_record_img);
        Category_RV = view.findViewById(R.id.sub_category_adp);

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


        Category_RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Category_RV.setHasFixedSize(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            main_cate_id = bundle.getString("main_cate_id");
            //sub_cate = bundle.getString("sub_cate");
            main_cate_name = bundle.getString("main_cate_name");
            post_id = bundle.getString("post_id");
            where = bundle.getString("where");

//            PostFreeAd.tb_title.setText("" +  Html.fromHtml(main_cate_name) );

            Methods.toast_msg(getContext(),"Main " + main_cate_id);
        }
        if(where.equals("" + Variables.Var_com_where_page_all_ads)) {
            // If user come from All Ads page
            tb_title_id.setText("Select Sub-category");
        }else{
            tb_title_id.setText("Post Free Ad");
        }
         getSub_Category();



        rl1 = (RelativeLayout) view.findViewById(R.id.cars_rl1_id);

        bikes_tv = (TextView) view.findViewById(R.id.bikes_tv_id);
      //  bikes_tv.setText("Bikes & Scooters");

        rl1.setOnClickListener(this);
        back_id.setOnClickListener(this);
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

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sub_Cate_list.clear();
                pullToRefresh.setRefreshing(false);
                getSub_Category();
            }
        });




        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        PostFreeAd.tb_title.setText("Post Free Ad");
//        PostFreeAd.tv.setText("Select Category");
       // Methods.No_Record_method(getContext(),no_record_img,bikes_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cars_rl1_id:
                PostFreeAd.tv.setVisibility(View.GONE);
                Ad_Details f = new Ad_Details();
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.fl_id, f).commit();
                break;
            case R.id.back_id:
                getFragmentManager().popBackStack();
        }
    }


    //  Get Subcategory

    public void getSub_Category(){

        try{
            JSONObject sendObj;
            pullToRefresh.setRefreshing(true);

                sendObj = new JSONObject();
                sendObj.put("main_cat_id" , "" + main_cate_id);

            Methods.Log_d_msg(getContext(),"" + sendObj.toString());


            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_LINKS.API_Show_show_Categories,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Maipulate Response
                            try{

                                pullToRefresh.setRefreshing(false);

                                JSONArray sub_cate_arr = response.getJSONArray("msg");
                                //sub_cate_arr = new JSONArray(sub_cate);

                                if(sub_cate_arr.length() == 0 ){
                                    // If length is zeroo
                                    Methods.toast_msg(getContext(),"No Recoed Yet.");
                                    Methods.No_Record_method(getContext(),no_record,tb_title_id);


                                }

                                for(int i = 0;i< sub_cate_arr.length(); i++){
                                    JSONObject obj = sub_cate_arr.getJSONObject(i);
                                    JSONObject sub_cate_obj = obj.getJSONObject("SubCategory");

                                    Sub_cate_Get_Set sub_cate = new Sub_cate_Get_Set(
                                            "" + sub_cate_obj.getString("id"),
                                            "" + sub_cate_obj.getString("main_category_id"),
                                            "" + sub_cate_obj.getString("language_id"),
                                            "" + sub_cate_obj.getString("name"),
                                            "" + sub_cate_obj.getString("label"),
                                            "" + sub_cate_obj.getString("image")

                                    );

                                    sub_Cate_list.add(sub_cate);



                                }// End for Loop

                                // Setting up adp;


                                sub_cate_adp = new Sub_cate_adp(
                                        sub_Cate_list,
                                        getContext(),

                                        new Sub_cate_adp.onclick() {
                                            @Override
                                            public void itemclick(int pos) {


                                                if(sub_Cate_list.size() > 0) {

                                                    String user_info = SharedPrefrence.get_offline(getContext(),
                                                            "" + SharedPrefrence.shared_user_login_detail_key
                                                    );

                                                    if (user_info != null) {
                                                        // If User Login
                                                        Sub_cate_Get_Set get = sub_Cate_list.get(pos);
                                                        get.getName();
                                                        //  Car_subcategory f = new Car_subcategory();


                                                        if (where.equals("" + Variables.Var_com_where_page_all_ads)) {
                                                            // If user come from All Ads page

                                                            // getFragmentManager().popBackStack();
                                                            try {
                                                                All_Ads.sub_cate_name.setText("" + get.getName());
                                                            } catch (Exception b) {

                                                            }

                                                            if (fragment_callback != null) {
                                                                Bundle args = new Bundle();
                                                                args.putString("main_cate_id", get.getMain_category_id());
                                                                args.putString("sub_cate_id", get.getId());
                                                                args.putString("main_cate_name", main_cate_name);
                                                                args.putString("sub_cate_name", get.getName());
                                                                args.putString("section_id", "sub_name");
                                                                fragment_callback.Responce(args);
                                                                getFragmentManager().popBackStack();
                                                            } else {

                                                            }

                                                        } else {
                                                            // Basic Flow As usal

                                                            Bundle args = new Bundle();
                                                            args.putString("main_cate_id", get.getMain_category_id());
                                                            args.putString("main_cate_name", main_cate_name);

                                                            args.putString("sub_cate_name", get.getName());
                                                            args.putString("sub_cate_id", get.getId());
                                                            args.putString("post_id", "" + post_id);
                                                            args.putString("sub_cates_all", sub_cate);

                                                            Ad_Details f = new Ad_Details();
                                                            f.setArguments(args);
                                                            FragmentManager fm = getChildFragmentManager();
                                                            FragmentTransaction ft = fm.beginTransaction();
                                                            ft.addToBackStack(null);
                                                            ft.replace(R.id.fl_id, f).commit();
                                                            try{
                                                                Ad_detail_activity.finish();
                                                            }catch (Exception b){

                                                            }


                                                        }


                                                    } else {
                                                        // If user is not Login

                                                        //Methods.alert_dialogue(getContext(),"Info","Please first Login.");
                                                        startActivity(new Intent(getContext(), Login.class));
                                                        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                                    }


                                                }




                                            }
                                        }

                                );
                                Category_RV.setAdapter(sub_cate_adp);







                            }catch (Exception n){
                                pullToRefresh.setRefreshing(false);
                                Methods.Log_d_msg(getContext(),"" + n.toString());
                            }





                        }
                    }


            );



        }catch (Exception v){
            pullToRefresh.setRefreshing(false);
        }

    }


}
