package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Chat;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Inner_Rv_Adp;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Sub_Cate_hor_Adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Sub_cate_Get_Set;
import com.marius.valeyou.market_place.Login_Register.Login;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;
import com.marius.valeyou.market_place.Utils.RecyclerItemClickListener;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Post_Ads_Details extends AppCompatActivity implements View.OnClickListener {

    TextView spec_tv,desc_tv,vm,ask_ques,makeoffer_tv,buy_tv;
    LinearLayout spec_ll,desc_ll,gradient_ll;
    RelativeLayout ques_one,ques_two,ques_three,ques_four,ques_five,ques_six;
    private static final int REQUEST_PHONE_CALL = 1 ;
    RecyclerView rv1,rv2;
    Post_Rv1_Adp adp1;
    Post_Rv2_Adp adp2;
    JSONArray img_arr;
    String title, ad_id, main_cate_id;
    ImageView back_id, fav_id, share_id;
    public static TextView num_of_pics;
    String city_id;
    Context context;
    LinearLayout chat_now;
    LinearLayout dynamica_LI;
    String  user_info;

    // Images
    private List<Get_Set_post_img> list_ads_img = new ArrayList<>();
    Inner_Rv_Adp adp;
    //Toolbar header;
    Toolbar header;

    CollapsingToolbarLayout ctb_id;
    String main_cate_name, sub_cate_name, sub_cate_id;
    TextView ad_title, price, loc_and_date, ads_id_and_views, user_name, phone_num, desc, text_ad_id, since_mem;
    String rec_id, rec_name, rec_image, acc_created;
    ImageView prof_pic_id;
    RecyclerView sub_cate_RV;
    List<Sub_cate_Get_Set> sub_Cate_list = new ArrayList<>();
    Sub_Cate_hor_Adp sub_cate_adp;
    NestedScrollView nested_scroll;
    com.facebook.ads.InterstitialAd interstitialAd;
    // Save Data Offline Json Objects and Array
    JSONObject Main_save_ads_obj;
    JSONArray save_ads_arr;
    JSONObject save_ads_obj ;

    String price_st;

    // TODO: (MyAccount.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic (){

        try{
            ctb_id.setContentScrimColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }
            chat_now.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

            // TODO: Changing Glow Color of Scrolview
            Methods.setEdgeEffectL(nested_scroll, Color.parseColor(Variables.Var_App_Config_header_bg_color));


        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    } // End method to change Color Dynamically

    ProgressDialog pd;
    TextView text_view_chat_now;
    String is_present = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_ads_details);

        text_view_chat_now = findViewById(R.id.text_view_chat_now);
        // Progres bar;
        pd = new ProgressDialog(Post_Ads_Details.this);
        pd.setMessage(getResources().getString(R.string.loading));
        //pd.setCancelable(false);
       // pd.show();
        context = Post_Ads_Details.this;
        // TODO: Ads Dsipaly
//        Count_num_click(context);
//        display_fb_ad(Post_Ads_Details.this);

        user_info = SharedPrefrence.get_offline(Post_Ads_Details.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );

        Intent intent = getIntent();
        String value = intent.getStringExtra("img_arr"); //if it's a string you stored.
        title = intent.getStringExtra("title");
        ad_id = intent.getStringExtra("ad_id");
        main_cate_id = intent.getStringExtra("main_cate_id");
        rv1 = (RecyclerView) findViewById(R.id.rv_id); // Images Recycle View
        since_mem = findViewById(R.id.since_mem);
        // Top images
        top_images(value);


        // Check if User already view ad
        try{
            String viwed_posts = SharedPrefrence.get_offline(Post_Ads_Details.this,"" + SharedPrefrence.share_viwed_posts);

            JSONObject viewd_obj = new JSONObject(viwed_posts);
            JSONArray viwed_arr = viewd_obj.getJSONArray("viwed");
            for(int i=0; i< viwed_arr.length(); i++){
                JSONObject pos_obj = viwed_arr.getJSONObject(i);

                if(pos_obj.getString("id").equals("" + ad_id)){
                    // If id already exist
                    is_present = "present";
                }


            }// End For Loop

        }catch (Exception b){

        }


        /// End If not Check


        ad_title = findViewById(R.id.tv_id);
        price = findViewById(R.id.price);
        loc_and_date = findViewById(R.id.loc_and_date);
        chat_now = findViewById(R.id.chat_now);
        ctb_id = findViewById(R.id.ctb_id);
        ads_id_and_views = findViewById(R.id.ads_id_and_views);
        user_name = findViewById(R.id.inner_tv_id);
        prof_pic_id = findViewById(R.id.prof_pic_id);
        phone_num = findViewById(R.id.phone_num);
        sub_cate_RV = findViewById(R.id.sub_cate);
        desc = findViewById(R.id.desc);
        nested_scroll = findViewById(R.id.nested_scroll);
        text_ad_id = findViewById(R.id.text_ad_id);



        sub_cate_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    EdgeChanger.setEdgeGlowColor(sub_cate_RV, Color.parseColor(Variables.Var_App_Config_header_bg_color));
                }catch (Exception b){

                }

            }
        });

        //sub_cate_RV.setLayoutManager(new GridLayoutManager(context,1 ,GridLayoutManager.HORIZONTAL, false));
        sub_cate_RV.setHasFixedSize(false);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context,1 , GridLayoutManager.HORIZONTAL, false);
        // TODO: For Divider in RV
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(sub_cate_RV.getContext(),
                linearLayoutManager.getOrientation());
        sub_cate_RV.addItemDecoration(dividerItemDecoration);
        sub_cate_RV.setLayoutManager(linearLayoutManager);

        gradient_ll = (LinearLayout) findViewById(R.id.gradient_ll_id);

        //gradient_ll.setBackground(Methods.getColorScala());;

        num_of_pics = findViewById(R.id.num_of_pics);

        fav_id = findViewById(R.id.fav_id);
        share_id = findViewById(R.id.share_id);





        Methods.toast_msg(context,"Main Cate " + main_cate_id );
        get_Sub_Cate(main_cate_id);
        // Get Post Detail
        Get_Ad_Detail(ad_id);

        // End Get Post Detail






        back_id = findViewById(R.id.back_id);
        rv2 = (RecyclerView) findViewById(R.id.rv1_id);
        spec_tv = (TextView) findViewById(R.id.spec_tv_id);
        //spec_tv.setText("" + title);
        desc_tv = (TextView) findViewById(R.id.desc_tv_id);
        spec_ll = (LinearLayout) findViewById(R.id.spec_ll_id);
        desc_ll = (LinearLayout) findViewById(R.id.desc_ll_id);
        ques_one = (RelativeLayout) findViewById(R.id.ques_one_id);
        ques_two = (RelativeLayout) findViewById(R.id.ques_two_id);
        ques_three = (RelativeLayout) findViewById(R.id.ques_three_id);
        ques_four = (RelativeLayout) findViewById(R.id.ques_four_id);
        ques_five = (RelativeLayout) findViewById(R.id.ques_five_id);
        ques_six = (RelativeLayout) findViewById(R.id.ques_six_id);
        vm = (TextView) findViewById(R.id.vm_tv_id);
        ask_ques = (TextView) findViewById(R.id.ask_ques_tv_id);
        makeoffer_tv = (TextView) findViewById(R.id.makeoffer_tv_id);

        vm.setText("View More");
        makeoffer_tv.setText("Make Offer & Chat");

        spec_tv.setOnClickListener(this);
        desc_tv.setOnClickListener(this);
        vm.setOnClickListener(this);
        ask_ques.setOnClickListener(this);
        makeoffer_tv.setOnClickListener(this);
        chat_now.setOnClickListener(this);
        back_id.setOnClickListener(this);
        fav_id.setOnClickListener(this);
        share_id.setOnClickListener(this);
        chat_now.setOnClickListener(this);
        phone_num.setOnClickListener(this);

        Change_Color_Dynmic();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                if(interstitialAd != null && interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }


                break;

            case R.id.spec_tv_id:
                spec_tv.setBackgroundResource(R.drawable.bottom_blue_line);
                desc_tv.setBackgroundResource(R.color.transparent);
                spec_ll.setVisibility(View.VISIBLE);
                desc_ll.setVisibility(View.GONE);
                break;

            case R.id.desc_tv_id:
                desc_tv.setBackgroundResource(R.drawable.bottom_blue_line);
                spec_tv.setBackgroundResource(R.color.transparent);
                desc_ll.setVisibility(View.VISIBLE);
                spec_ll.setVisibility(View.GONE);
                break;

            case R.id.vm_tv_id:
                if (vm.getText().equals("View More")){
                    vm.setText("View Less");
                    ques_three.setVisibility(View.VISIBLE);
                    ques_four.setVisibility(View.VISIBLE);
                    ques_five.setVisibility(View.VISIBLE);
                    ques_six.setVisibility(View.VISIBLE);
                }else {
                    vm.setText("View More");
                    ques_three.setVisibility(View.GONE);
                    ques_four.setVisibility(View.GONE);
                    ques_five.setVisibility(View.GONE);
                    ques_six.setVisibility(View.GONE);
                }
                break;

            case R.id.ask_ques_tv_id:
                startActivity(new Intent(Post_Ads_Details.this, AskQuestion.class));
                break;

            case R.id.makeoffer_tv_id:
                startActivity(new Intent(Post_Ads_Details.this, Make_Offer_Chat.class));
                makeoffer_tv.setBackgroundResource(R.color.transparent);
                makeoffer_tv.setTextColor(getResources().getColor(R.color.white));
                buy_tv.setBackgroundResource(R.color.white);
                buy_tv.setTextColor(getResources().getColor(R.color.black));
                break;

            case R.id.chat_now:

                user_info = SharedPrefrence.get_offline(context,
                        "" + SharedPrefrence.shared_user_login_detail_key
                );

                if(rec_id.equals("" + SharedPrefrence.get_user_id_from_json(Post_Ads_Details.this))){
                    // If logged in user id equal to post User id
                    //   text_view_chat_now.setText("Your Post");


                    open_edit_ad(
                            "" + sub_cate_name,
                            "" + sub_cate_id,
                            "" + main_cate_name,
                            "" + main_cate_id,
                            "" + ad_id,
                            "" + city_id
                    );



                }else{
                    if(user_info != null){
                        // If user is already Login
                        Intent myIntent = new Intent(context, Chat.class);
                        myIntent.putExtra("receiver_id", "" + rec_id);
                        myIntent.putExtra("receiver_name", "" +  rec_name);
                        myIntent.putExtra("receiver_pic", "" + rec_image);
                        context.startActivity(myIntent);

                    }else{
                        // If user is not Login
                        context.startActivity(new Intent(context, Login.class));
                        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                        //  Methods.showDialog(Drawer.this,"Msg");

                    }

                }

                break;
            case R.id.share_id:
                // Share Code

                break;
            case R.id.fav_id:
                // Add Fav Code
                   String tag = fav_id.getTag().toString();
                   if(user_info != null){
                      boolean is_wifi_availeable = Methods.is_internet_avail(Post_Ads_Details.this);
                        if(is_wifi_availeable == true){
                            // If wifi Available
                            // If user Already Login
                            if(tag.equals("unlike")){
                                // Like Code is Here.
                                fav_id.setImageResource(R.drawable.ic_like_filled_red);
                                fav_id.setTag("like");
                                add_fav_ads("1");
                            }else if(tag.equals("like")){
                                // Unlike Code is here
                                fav_id.setImageResource(R.drawable.ic_like);
                                fav_id.setTag("unlike");
                                add_fav_ads("0");
                            } // End Inner IF
                        }else{
                            Methods.alert_dialogue(Post_Ads_Details.this,"Info","Please connect to internet.");
                        }



                   }else{
                       // If user Is not Login

                       Methods.showDialog(Post_Ads_Details.this,"Msg");

                   }



                break;
            case R.id.phone_num:
                make_call();

                break;





        }
    }


    public void add_fav_ads(String fav_or_not){
        // Add Fav Ad Code
        // Apply API

        try{
            //JSONObject sendObj = new JSONObject("{ '': '' }");
            String user_id = SharedPrefrence.get_user_id_from_json(Post_Ads_Details.this);

            //  JSONObject sendObj = new JSONObject();
            //sendObj.put("" , "");
            JSONObject sendObj = new JSONObject();
            sendObj.put("user_id","" + user_id);
            sendObj.put("post_id","" + ad_id);
            sendObj.put("favourite",fav_or_not);


            Volley_Requests.New_Volley(
                    Post_Ads_Details.this,
                    "" + API_LINKS.API_ADD_AD_FAV ,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            Methods.toast_msg(Post_Ads_Details.this,"Resp Fav " + response.toString());
                        }
                    }


            );



        }catch (Exception v){
            Methods.toast_msg(Post_Ads_Details.this,"" + v.toString());
        }


    }

    public void Get_Ad_Detail (String post_id){

        try{
           pd.show();
            JSONObject sendObj;
            sendObj = new JSONObject();
            sendObj.put("post_id" , "" + post_id);
            sendObj.put("user_id" , "" + SharedPrefrence.get_user_id_from_json(Post_Ads_Details.this));


            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Show_Post_Detail,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                             Methods.toast_msg(context ,"From Class " + requestType+  " " + response.toString());
                            // Maipulate Response
                            dynamica_LI = findViewById(R.id.spec_ll_id);
                            pd.hide();
                            show_ad();
                            //show_ad();  // TODO: Display Ad
                            try{
                                JSONObject msg_obj = response.getJSONObject("msg");

                                JSONObject post_obj = msg_obj.getJSONObject("Post");
                                JSONObject user_obj = msg_obj.getJSONObject("User");
                                JSONObject PostTranslation_obj = msg_obj.getJSONObject("PostTranslation");
                                JSONObject PostContact_obj = msg_obj.getJSONObject("PostContact");
                                city_id = PostContact_obj.getString("city_id");
//                                String city = PostContact_obj.getJSONObject("City").getString("name");

                                img_arr = msg_obj.getJSONArray("PostImage");
                                // Top images
                                top_images(msg_obj.getJSONArray("PostImage").toString());

                                JSONObject city_obj = PostContact_obj.optJSONObject("City");
                                String city = "";
                                if(city_obj!=null){
                                    city = city_obj.optString("name");

                                }



                                post_obj.getString("price");
                                post_obj.getString("created");
                                post_obj.getString("sub_category_id");

                                JSONObject SubCategory_obj = msg_obj.getJSONObject("SubCategory");
                                sub_cate_name = SubCategory_obj.getString("name");
                                sub_cate_id = SubCategory_obj.getString("id");
                                JSONObject MainCategory_obj = msg_obj.getJSONObject("MainCategory");
                                main_cate_name = MainCategory_obj.getString("label");

                                String time = Methods.get_time_ago_org(post_obj.getString("created"));



                                rec_id = user_obj.getString("id");
                                rec_name = user_obj.getString("full_name");
                                rec_image = user_obj.getString("image");
                                acc_created = user_obj.getString("created");

                                //acc_created = Methods.parseDateToddMMyyyy_only(user_obj.getString("created"),Post_Ads_Details.this);

                                acc_created = String.valueOf(Methods.getMonth_name(acc_created));
                                since_mem.setText("Member Since " + acc_created + " " + user_obj.getString("created").substring(0, 4));


                                if(rec_id.equals("" + SharedPrefrence.get_user_id_from_json(Post_Ads_Details.this))){
                                    // If logged in user id equal to post User id
                                    text_view_chat_now.setText("Edit Post");
                                }


                                // Get TITLe
                                PostTranslation_obj.getString("title");
                                PostTranslation_obj.getString("description");

                                // Check for ad present
                                if(is_present.equals("present")){

                                }else {
                                    // If already not presnt

                                    String views_local = SharedPrefrence.get_offline(Post_Ads_Details.this,"" + SharedPrefrence.share_viwed_posts);
                                    if(views_local != null){
                                        JSONObject save_ads_obj_n  = new JSONObject();
                                        JSONArray save_ads_arr_n = new JSONArray();
                                        //Methods.toast_msg(Post_Ads_Details.this," not null ");
                                        Main_save_ads_obj = new JSONObject(views_local);
                                        JSONArray viwed_arr = Main_save_ads_obj.getJSONArray("viwed");
                                        save_ads_obj_n.put("image", "" + PostTranslation_obj.getString("title"));
                                        save_ads_obj_n.put("price", "" + post_obj.getString("price"));
                                        save_ads_obj_n.put("id", "" + post_obj.getString("id"));
                                        save_ads_obj_n.put("ad_images", img_arr);
                                        save_ads_obj_n.put("title","" + PostTranslation_obj.getString("title"));
                                        save_ads_obj_n.put("city", city);
                                        viwed_arr.put(viwed_arr.length(), save_ads_obj_n);
                                        Main_save_ads_obj.put("viwed", viwed_arr);
                                        // TODO: Data Save into Shared Pref for Later Use.
                                        SharedPrefrence.save_info_share(Post_Ads_Details.this,"" + Main_save_ads_obj.toString(),
                                                "" + SharedPrefrence.share_viwed_posts);


                                    }else{
                                        Methods.toast_msg(Post_Ads_Details.this,"null " + views_local);
                                        save_ads_obj  = new JSONObject();
                                        save_ads_arr = new JSONArray();
                                        Main_save_ads_obj = new JSONObject();
                                        // Save View Ads
                                        save_ads_obj.put("image", "" + PostTranslation_obj.getString("title"));
                                        save_ads_obj.put("price", "" + post_obj.getString("price"));
                                        save_ads_obj.put("id", "" + post_obj.getString("id"));
                                        save_ads_obj.put("ad_images", img_arr);
                                        save_ads_obj.put("title","" + PostTranslation_obj.getString("title"));
                                        save_ads_obj.put("city", city);
                                        save_ads_arr.put(save_ads_obj);
                                        Main_save_ads_obj.put("viwed", save_ads_arr);
                                        //   Methods.toast_msg(Post_Ads_Details.this,"" + PostTranslation_obj.getString("title"));

                                        // TODO: Data Save into Shared Pref for Later Use.
                                        SharedPrefrence.save_info_share(Post_Ads_Details.this,"" + Main_save_ads_obj.toString(),
                                                "" + SharedPrefrence.share_viwed_posts);

                                    }
                                    String views_local_1 = SharedPrefrence.get_offline(Post_Ads_Details.this,"" + SharedPrefrence.share_viwed_posts);


                                } // End else of ad not present in viewd Ads

                                // Setting Up Data

                                title = PostTranslation_obj.getString("title");
                                price_st = post_obj.getString("price");
                                ad_id = post_obj.getString("id");


                               // Methods.toast_msg(Post_Ads_Details.this,"Like " + post_obj.getString("favourite"));
                                //fav_id;
                                if(post_obj.getString("favourite").equals("0")){
                                    // if post is not liked
                                    fav_id.setTag("unlike");
                                    fav_id.setImageResource(R.drawable.ic_like);



                                }else{
                                    // If Liked
                                    fav_id.setTag("like");
                                    fav_id.setImageResource(R.drawable.ic_like_filled_red);

                                }


                                ad_title.setText("" + PostTranslation_obj.getString("title"));
                                price.setText( SharedPrefrence.Currancy_name_from_Json(context) + "" + post_obj.getString("price"));
                                loc_and_date.setText("" + time + " | " + city);
                                ads_id_and_views.setText("Ad Views : " + post_obj.getString("view"));
                                text_ad_id.setText("" + "Ad id : " + post_obj.getString("id"));
                                user_name.setText("" + rec_name);
                                phone_num.setText("Need Help? Call us at " +Variables.App_Config_contact_us_phone_number+" (9 AM - 9 PM)");
                                desc.setText("" + PostTranslation_obj.getString("description"));

                                Picasso.get()
                                        .load(API_LINKS.BASE_URL + rec_image)
                                        .placeholder(R.drawable.ic_profile_gray)
                                        .error(R.drawable.ic_profile_gray)
                                        .into(prof_pic_id);



                                //// Now Manipulate About post Data

                                JSONArray PostValue_arr = msg_obj.getJSONArray("PostValue");
                                for(int p = 0;p< PostValue_arr.length(); p++ ){
                                    JSONObject get = PostValue_arr.getJSONObject(p);
                                    JSONObject form_name = get.getJSONObject("Form");
                                    form_name.getString("name");
                                    form_name.getString("type");


                                    // Create Dynamically Layouit
                                    RelativeLayout Main_RL = new RelativeLayout(context); // Main Relative Layout
                                    Main_RL.setBackground(getResources().getDrawable(R.drawable.bottom_gray_line_white));
                                    RelativeLayout.LayoutParams RL_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    RL_params.setMargins(25,20,25,20);
                                    //RL_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    Main_RL.setLayoutParams(RL_params); //causes layout updat

                                    //Main_RL.setPadding(15,5,15,5);

                                    TextView left_text = new TextView(context);   // ==> Left Text
                                    TextView right_text = new TextView(context);   // ==> Right Text
                                    right_text.setTextSize(14);
                                    left_text.setTextSize(14);
                                    right_text.setTextColor(getResources().getColor(R.color.black));
                                    left_text.setTextColor(getResources().getColor(R.color.black));
                                    //right_text.setText("Right");
                                    //left_text.setRigh

                                    RelativeLayout.LayoutParams right_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    right_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    right_params.setMargins(20,20,20,20);
                                    left_text.setLayoutParams(right_params); //causes layout updat


                                    RelativeLayout.LayoutParams left_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    left_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                    left_params.setMargins(20,20,20,20);
                                    right_text.setLayoutParams(left_params); //causes layout updat




                                    if(get.getString("option_id").equals("0")){
                                        // If input type Simple Text
                                        get.getString("value");
                                       // Methods.toast_msg(context,"" + form_name.getString("name") + " " + get.getString("value"));

                                        right_text.setText("" + form_name.getString("name"));
                                        left_text.setText("" + get.getString("value"));

                                    }else {
                                        // If input type Select i.e ==> DropDown Menu
                                        JSONObject selected_option_obj = get.getJSONObject("Option");
                                        selected_option_obj.getString("name");
                                       // Methods.toast_msg(context,"" + selected_option_obj.getString("name") + " " + form_name.getString("name"));

                                        right_text.setText("" + form_name.getString("name"));
                                        left_text.setText("" + selected_option_obj.getString("name"));


                                    }

                                    Main_RL.addView(right_text);
                                    Main_RL.addView(left_text);


                                    dynamica_LI.addView(Main_RL);


                                } // End For Loop

                            }catch (Exception n){
                                Methods.toast_msg(context,"" + n.toString());
                                pd.hide();
                            }





                        }
                    }


            );



        }catch (Exception v){
            pd.hide();
        }




    } // Get Post Detail from Post ID

    public void make_call(){
        //

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ Variables.Var_App_Config_contact_us_phone_number));//change the number
                startActivity(callIntent);
                //startActivity(intent);
            }

//Methods.toast_msg(ContactUs.this,"" + Variables.Var_App_Config_contact_us_phone_number);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ Variables.App_Config_contact_us_phone_number));//change the number
            startActivity(callIntent);



    } // End bracket of Make Call


    // API for Getting Sub Categoriries

    public void get_Sub_Cate (String main_cate_id){

        try {
            //JSONObject sendObj = new JSONObject("{ '': '' }");
                JSONObject sendObj = new JSONObject();
                sendObj.put("main_cat_id" , "" + main_cate_id);


            // Methods.toast_msg(context,"" + sendObj.toString());


            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Show_show_Categories,
                    sendObj,
                    "Show Cate ",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Methods.toast_msg(getContext()," " + requestType+  " Sub cate Res " + response.toString());
                            // Maipulate Response
                            try{
                                JSONArray sub_cate_arr = response.getJSONArray("msg");
                                //sub_cate_arr = new JSONArray(sub_cate);

                                if(sub_cate_arr.length() == 0 ){
                                    // If length is zeroo
                                    Methods.toast_msg(context,"No Recoed Yet.");
                                    gradient_ll.setVisibility(View.GONE);
                                }

                                for(int i = 0;i< sub_cate_arr.length(); i++){
                                    JSONObject obj = sub_cate_arr.getJSONObject(i);
                                    JSONObject sub_cate_obj = obj.getJSONObject("SubCategory");
                                    //Methods.toast_msg(getContext(),"Sub " + obj.getString("id"));

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


                                sub_cate_adp = new Sub_Cate_hor_Adp(
                                        sub_Cate_list,
                                        context,

                                        new Sub_Cate_hor_Adp.onclick() {
                                            @Override
                                            public void itemclick(int pos) {

                                                Sub_cate_Get_Set get = sub_Cate_list.get(pos);
                                                get.getName();


                                            }
                                        }

                                );
                                sub_cate_RV.setAdapter(sub_cate_adp);







                            }catch (Exception n){
                                Methods.toast_msg(context,"" + n.toString());
                            }






                        }
                    }


            );


        } catch (Exception v) {
            Methods.toast_msg(context,"" + v.toString());
        }




    } // End Get Sub Category

    public void open_edit_ad (String sub_cate_name, String Sub_cate_id, String main_cate_name, String main_cate_id, String post_id, String city_id ) {


        Bundle args = new Bundle();
        args.putString("main_cate_id", "" + main_cate_id);
        args.putString("main_cate_name", "" + main_cate_name);
        args.putString("sub_cate_name","" + sub_cate_name);
        args.putString("sub_cate_id","" + Sub_cate_id);
        args.putString("post_id","" + post_id);
        args.putString("city_id","" + city_id);


        Methods.toast_msg(Post_Ads_Details.this,"Post " + post_id);

        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();
        //ft.replace(R.id.Main_RL, f).commit();
    }



    public void open_images_slider (String title, String price, String all_images, String user_id, String user_name, String user_pic, String post_id ) {



        Bundle args = new Bundle();
        args.putString("ad_title", "" + title);
        args.putString("price", "" + price);
        args.putString("all_images","" + all_images);
        args.putString("user_id","" + user_id);
        args.putString("user_name","" + user_name);
        args.putString("user_pic","" + user_pic);
        args.putString("post_id","" + post_id);

        Methods.toast_msg(Post_Ads_Details.this,"Post " + post_id);

        Ads_img_slider f = new Ads_img_slider();
        f.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();
        //ft.replace(R.id.Main_RL, f).commit();
    }



    // Setting up top images

    public void top_images (String value){
        list_ads_img.clear();
        try{
            img_arr = new JSONArray(value);
            num_of_pics.setText("" + img_arr.length());
            //JSONArray images = img_arr.getImg_array();
            try{
                for(int a = 0; a < img_arr.length(); a++){
                    JSONObject img_obj = img_arr.getJSONObject(a);
                    ///JSONObject img_obj_get = img_obj.getJSONObject("PostImage");
                    img_obj.getString("id");
                    img_obj.getString("image");
                    img_obj.getString("post_id");



                    Get_Set_post_img add = new Get_Set_post_img(
                            "" + img_obj.getString("id"),
                            "" + img_obj.getString("post_id"),
                            "" + img_obj.getString("image")


                    );
                    list_ads_img.add(add);


                } // End for Loop
                adp1 = new Post_Rv1_Adp(getApplicationContext(),list_ads_img);
                //adp = new Inner_Rv_Adp(Post_Ads_Details.this,list_ads_img);
                rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                rv1.setHasFixedSize(false);
                rv1.setAdapter(adp1);
                // String title, ad_id, main_cate_id;
                rv1.addOnItemTouchListener(
                        new RecyclerItemClickListener(context, rv1 ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever

                                open_images_slider(
                                        "" + title,
                                        "" + price_st,
                                        "" + img_arr.toString(),
                                        "" + rec_id,
                                        "" + rec_name,
                                        "" + rec_image,
                                        "" + ad_id
                                );


                            }

                            @Override public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );


//                open_images_slider



            }catch (Exception v){
                Methods.toast_msg(Post_Ads_Details.this,"image Err " + v.toString());
            }


        }catch (Exception b){

        }

    }



    public void display_fb_ad (final Context context){
        try{
            pd.hide();
        }catch (Exception b){

        }
        final String  TAG = "Main";

        interstitialAd = new com.facebook.ads.InterstitialAd(context, "" + Variables.facebook_banner_ad);
        String num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);

        Methods.toast_msg(context,"Num Click " + num_click_post);

        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.d(TAG, "Interstitial ad displayed.");
                SharedPrefrence.save_info_share(context,"0","" + SharedPrefrence.share_num_home_visit);


            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.d(TAG, "Interstitial ad dismissed.");
                SharedPrefrence.save_info_share(context,"0","" + SharedPrefrence.share_num_home_visit);


            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.d(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
//                interstitialAd.show();
                try{


                }catch (Exception b){

                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");



            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown


        if(num_click_post != null){
            // If Num click Post is not Null
            if(Integer.parseInt(num_click_post) == Variables.Var_num_click_to_show_Ads){
                // Ad Show When Number of Click 3
                //Methods.toast_msg(context,"Num Click " + num_click_post);
                interstitialAd.loadAd();
//                if(pd != null){
//                    // If dialogue is not Null
//                    pd.dismiss();
//
//                }





            }

        }



        //  interstitialAd.loadAd();

    } // End of method to display fb Ads.


    public static int Count_num_click (Context context) {

        int num_click;
        // Count Click for FB Ads Displaying
        String num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);
        if(num_click_post == null){
            // If no value save
            num_click = 1;
            SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
        }else{
            num_click = Integer.parseInt(num_click_post) + 1;
            SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
        }

        if(num_click_post != null){
            if(Integer.parseInt(num_click_post) >= Variables.Var_num_click_to_show_Ads ){
                num_click = 1;
                SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
                // Shoe Ad
                //  display_fb_ad(context);
            }

        }

        num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);
        Methods.toast_msg(context,"Num Click " + num_click_post);



        return num_click;

    } // End method to count the number of click for FB Ads



    public void show_ad(){
        Count_num_click(context);
        display_fb_ad(Post_Ads_Details.this);

    }


}
