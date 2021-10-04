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
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class Active_Ads extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {

    View view;
    TextView tv;

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
        view = inflater.inflate(R.layout.active_ads, container, false);


        no_record_img = view.findViewById(R.id.no_record_img);
        tv = (TextView) view.findViewById(R.id.tv_id);

        pullToRefresh = view.findViewById(R.id.srl_id);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Get_response();


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

        Get_response();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostFreeAd.class));
            }
        });

        return view;
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        //   fetchMovies();
        list_ads.clear();
               list_ads_img.clear();
        Get_response();

    }





    public void Get_response() {
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

                                        if(Post_obj.getString("active").equals("1")){
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


    public static void  delete_post(String post_id, final int pos, final Context context) {
        // Method to delete Ads Post
//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setTitle("Loading");
        final ProgressDialog pd = new ProgressDialog(context);;
        try {
            JSONObject sendObj = new JSONObject();
            sendObj.put("id", "" + post_id);

            pd.setMessage("" + context.getResources().getString(R.string.loading));
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
                                Methods.toast_msg(context,"" + msg);

                                // Remove

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


    public void open_edit_ad (String sub_cate_name, String Sub_cate_id, String main_cate_name, String main_cate_id, String post_id ){

        MyAds.tl.setVisibility(View.GONE);
        MyAds.buy_cred.setVisibility(View.GONE);
//        MyAds.toolbar_text.setText("" + Html.fromHtml( main_cate_name));
        MyAds.toolbar_text.setText("Edit Ad");
        Bundle args = new Bundle();
        args.putString("main_cate_id", "" + main_cate_id);
        args.putString("main_cate_name", "" + main_cate_name);

        args.putString("sub_cate_name","" + sub_cate_name);
        args.putString("sub_cate_id","" + Sub_cate_id);
        args.putString("post_id","" + post_id);

        Methods.Log_d_msg(getContext(),"Post " + post_id);

        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.Main_RL, f).commit();
    }


}