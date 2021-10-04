package com.marius.valeyou.market_place.Drawer.MyAds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Display_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.My_ads_adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.PostFreeAd;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Inactvie_Ads extends Fragment {

    View view;
    TextView tv;
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic () {
        try{
            tv.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            tv.setTextColor(getResources().getColor(R.color.white));
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    } // End method to change Color Dynamically

    String ads;
    List<Get_Set_Display_Ads> list_ads = new ArrayList<>();
    List<Get_Set_Display_Ads> list_ads_img = new ArrayList<>();
    SwipeRefreshLayout pullToRefresh;
    My_ads_adp adp;
    RecyclerView rv;
    ImageView no_record_img;
    ProgressDialog pd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inactive_ads, container, false);

        tv = (TextView) view.findViewById(R.id.tv_id);

        ads = SharedPrefrence.get_offline(getContext(),"" + SharedPrefrence.share_inactive_ads);

        no_record_img = view.findViewById(R.id.no_record_img);
        tv = (TextView) view.findViewById(R.id.tv_id);

        pullToRefresh = view.findViewById(R.id.srl_id);


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Get_response(ads);
                Get_response_new();
            }
        });


        rv = (RecyclerView) view.findViewById(R.id.rv_id);
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

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(false);

//        Get_response(ads);
        Get_response_new();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getContext(), PostFreeAd.class);
                myIntent.putExtra("post_id",""); //Optional parameters
                startActivity(myIntent);
            }
        });
        Change_Color_Dynmic();
        return view;
    }

    public void Get_response (String ads) {
        try {
            // Save Response into Shared Pref

            JSONObject response = new JSONObject(ads);
            pullToRefresh.setRefreshing(false);
            JSONArray msg = response.getJSONArray("msg");


            if (msg.length() == 0) {
                // If no Recoed


                Methods.No_Record_method(getContext(),no_record_img,tv);


            } else {
                ArrayList<Get_Set_Display_Ads> temp_list = new ArrayList<>();
                // Recod Present
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject json = msg.getJSONObject(i);
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
                    if(Post_obj.getString("active").equals("0")){
                        // If posts are Active
                        Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                                "" + Post_obj.getString("id"),
                                "" + Post_obj.getString("price"),
                                "" + Post_obj.getString("created"),
                                "" + Post_Translation_obj.getString("title"),
                                "" + User_obj.getString("id"),
                                "" + User_obj.getString("full_name"),
                                "" + User_obj.getString("email"),
                                "" + User_obj.getString("image"),
                                post_img,
                                "" + Post_obj.getString("sub_category_id"),
                                "" + Post_obj.getString("main_category_id"),
                                "" + sub_cate_obj.getString("name"),
                                "" + main_cate_obj.getString("name"),
                                "" + Post_obj.getString("favourite"),
                                "" + city,
                                "" + PostContact_obj.getString("city_id")
                        );
                        temp_list.add(Ads);

                    }else{
                        // If posts are not Active

                    }


                } // End for Loop
                list_ads.clear();
                list_ads = temp_list;
                // Setting Up Adapter
                adp = new My_ads_adp(getContext(), new My_ads_adp.click() {
                    @Override
                    public void onclick(final int pos, View v) {
                        final Get_Set_Display_Ads ads = list_ads.get(pos);
                        ads.getImg_array();
                        //startActivity(new Intent(getActivity(), Post_Ads_Details.class));
                    }
                }, list_ads);

                rv.setAdapter(adp);


            }


//                                msg.getJSONObject(0);

        } catch (Exception b) {
            pullToRefresh.setRefreshing(false);
            Methods.Log_d_msg(getContext(), "" + b.toString());
        }

                    }

    // End method to get inactive Ads

    public  void  delete_post(String post_id, final int pos, final Context context) {
        // Method to delete Ads Post

        try {
            JSONObject sendObj = new JSONObject();
            sendObj.put("id", "" + post_id);
            pd = new ProgressDialog(getContext());
            pd.setMessage("loading");
            pd.show();

            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Delete_ad_post,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            Methods.Log_d_msg(context,"From Class " + requestType+  " " + response.toString());
                            // Maipulate Response
                            pd.hide();
                            try {
                                JSONArray msg = response.getJSONArray("msg");
                                Methods.Log_d_msg(getContext(),"" + msg);

                                list_ads.remove(pos);
                                //list_ads.remove(pos);
                                adp.notifyItemRemoved(pos);
                                adp.notifyItemRangeRemoved(pos, list_ads.size());
                                adp.notifyDataSetChanged();


                            } catch (Exception b) {
                                pd.hide();
                            }


                        }
                    }


            );


        }catch (Exception b){
            pd.hide();
        }

    }

    public void Get_response_new () {
        try {
            String user_id = SharedPrefrence.get_user_id_from_json(getContext());
            pullToRefresh.setRefreshing(true);
            JSONObject sendObj = new JSONObject();

            sendObj.put("user_id", "" + SharedPrefrence.get_user_id_from_json(getContext()));


            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_LINKS.API_Show_User_Ad_Posts,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            // Maipulate Response

                            pullToRefresh.setRefreshing(false);
                            try {
                                // Save Response into Shared Pref

                                Methods.toast_msg(getContext(),"" + response.toString());
                                SharedPrefrence.save_info_share(getContext(),
                                        "" + response.toString(),
                                        "" + SharedPrefrence.share_inactive_ads
                                );

                                JSONArray msg = response.getJSONArray("msg");


                                if (msg.length() == 0) {
                                    // If no Recoed


                                    Methods.No_Record_method(getContext(),no_record_img,tv);


                                } else {
                                    // Recod Present

                                    ArrayList<Get_Set_Display_Ads> temp_list=new ArrayList<>();


                                    for (int i = 0; i < msg.length(); i++) {
                                        JSONObject json = msg.getJSONObject(i);
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

                                        if(Post_obj.getString("active").equals("0")){
                                            // If posts are Active
                                            Get_Set_Display_Ads Ads = new Get_Set_Display_Ads(
                                                    "" + Post_obj.getString("id"),
                                                    "" + Post_obj.getString("price"),
                                                    "" + Post_obj.getString("created"),
                                                    "" + Post_Translation_obj.getString("title"),
                                                    "" + User_obj.getString("id"),
                                                    "" + User_obj.getString("full_name"),
                                                    "" + User_obj.getString("email"),
                                                    "" + User_obj.getString("image"),
                                                    post_img,
                                                    "" + Post_obj.getString("sub_category_id"),
                                                    "" + Post_obj.getString("main_category_id"),
                                                    "" + sub_cate_obj.getString("name"),
                                                    "" + main_cate_obj.getString("name"),
                                                    "" + Post_obj.getString("favourite"),
                                                    "" + city,
                                                    "" + PostContact_obj.getString("city_id")
                                            );
                                            temp_list.add(Ads);

                                        }else{
                                            // If posts are not Active

                                        }


                                    } // End for Loop

                                    list_ads.clear();
                                    list_ads = temp_list;
                                    // Setting Up Adapter
                                    adp = new My_ads_adp(getContext(), new My_ads_adp.click() {
                                        @Override
                                        public void onclick(final int pos, View v) {
                                            final Get_Set_Display_Ads ads = list_ads.get(pos);
                                            ads.getImg_array();


                                            //startActivity(new Intent(getActivity(), Post_Ads_Details.class));
                                        }
                                    }, list_ads);

                                    rv.setAdapter(adp);


                                }


//                                msg.getJSONObject(0);

                            } catch (Exception b) {
                                pullToRefresh.setRefreshing(false);
                                Methods.Log_d_msg(getContext(), "" + b.toString());
                            }


                        }
                    }


            );


        } catch (Exception v) {
            pullToRefresh.setRefreshing(false);
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
           Methods.toast_msg(getContext(),"Visible");
           Get_response_new();
        }else{
            // fragment is not visible
        }
    }

}
