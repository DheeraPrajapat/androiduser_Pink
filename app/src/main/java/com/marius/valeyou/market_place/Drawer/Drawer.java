package com.marius.valeyou.market_place.Drawer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.facebook.ads.InterstitialAd;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.marius.valeyou.R;
import com.marius.valeyou.di.base.MyApplication;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox.Chat_Inbox;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.App_Settigs.AppSettings;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.All_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Section_Posts;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Slider;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_home_ads;
import com.marius.valeyou.market_place.Drawer.Home.City_Listt.City_Get_Set;
import com.marius.valeyou.market_place.Drawer.Home.City_Listt.City_loc;
import com.marius.valeyou.market_place.Drawer.Home.Get_Set.Home_get_set;
import com.marius.valeyou.market_place.Drawer.Home.Grid_Adp;
import com.marius.valeyou.market_place.Drawer.Home.Home;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Category_Adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Sub_cate_adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Cate_Get_Set;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Sub_cate_Get_Set;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.PostFreeAd;
import com.marius.valeyou.market_place.Drawer.Home.Rv1_Adp;
import com.marius.valeyou.market_place.Drawer.Home.Rv2_Adp;
import com.marius.valeyou.market_place.Drawer.Home.Search.Search;
import com.marius.valeyou.market_place.Drawer.Home.Shortlisted;
import com.marius.valeyou.market_place.Drawer.Home.Vp_Adp;
import com.marius.valeyou.market_place.Drawer.MyAccount.MyAccount;
import com.marius.valeyou.market_place.Drawer.MyAds.MyAds;
import com.marius.valeyou.market_place.Login_Register.Login;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.DetectSwipeGestureListener;
import com.marius.valeyou.market_place.Utils.FixedSpeedScroller;
import com.marius.valeyou.market_place.Utils.ImageRotationDetectionHelper;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.IResult;
import com.marius.valeyou.market_place.Volley_Package.VolleyService;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

public class Drawer extends AppCompatActivity implements View.OnClickListener {
    CardView cv,cv2,cv3;
    LinearLayout rl1,rl02,rl3,rl4,rl5,ll1,ll2,ll3,ll4,ll5,ll6,ll7;
    RelativeLayout s0earch_rl, viewed_posts_RL;
    ImageView post_ad;
    GridView gv;
    GridLayout gl;
    //    ViewPager vp;
    Sub_cate_adp sub_cate_adp;
    ViewPager vp;
    Vp_Adp adp;
    RecyclerView rv1,rv2;
    FloatingActionButton fab;
    Rv1_Adp adp1;
    Rv2_Adp adp2;
    Timer timer;
    RelativeLayout main_RL,search_rl;
    List<Home_get_set> Categories = new ArrayList<>();
    List<Get_Set_Slider> Home_Slider = new ArrayList<>();
    // Section List
    List<Get_Set_Section_Posts> Section_list = new ArrayList<>();
    JSONArray viwed_arr;
    // News Inside Sections
    List<Get_Set_home_ads> ads_inside_Section = new ArrayList<>();

    List<Sub_cate_Get_Set> sub_Cate_list = new ArrayList<>();
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    int[] list = {R.color.blue,R.color.black,R.color.colorAccent};
    Timer swipeTimer;
    /// This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;

    Toolbar tb;
    TextView tv;
    ImageView iv,arrow,iv2,pt_back, pt_back_third;
    DrawerLayout drawer;
    NavigationView nv;
    public static ViewFlipper vf;
    LinearLayout backtoValeyou,home_ll,order_ll,cash_ll,busines_ll,ratting_ll,setting_ll,contact_ll, logout_ll_id;
    RelativeLayout profile_rl,categor_rl,ads_rl,msgs_rl,loc_rl;
    FrameLayout fl;
    String name, email, user_info, country_id ;
    TextView default_city, nav_location;
    Toolbar header;
    String cate_sub_text;
    // Category Lists
    RecyclerView Category_RV, Sub_Category_RV, viewd_RV;
    List<Cate_Get_Set> Cate_list = new ArrayList<>();
    Category_Adp cate_adp;
    TextView sub_cate_names;
    ImageView profile_pic, header_iv_id;
    TextView user_email, user_name;
    ImageView no_record_img, header_iv1_id;
    SwipeRefreshLayout pullToRefresh;
    LinearLayout my_activity_linear;
    NestedScrollView scrollview_id;
    Handler handler;
    Runnable Update;

    AdView ad_view;
    private InterstitialAd interstitialAd;
    // TODO: Google Analytics
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    int num_click;
    // TODO: Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    private FirebaseAnalytics mFirebaseAnalytics;
    ScrollView first_scrol_drawer;

    SimpleDraweeView targetView, profile_drawer_img;

    public void Change_Color_Dynmic (){

        try{
            Methods.Change_header_color(header, Drawer.this);
            post_ad.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            nav_location.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            profile_rl.setBackground(Methods.getColorScala());
            // TODO: Changing Glow Color of Scrolview
            Methods.setEdgeEffectL(scrollview_id, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            Methods.changeGlowColor_Nested_Scrol(Color.parseColor(Variables.Var_App_Config_header_bg_color),scrollview_id);


            Methods.setEdgeEffectL(first_scrol_drawer, Color.parseColor(Variables.Var_App_Config_header_bg_color));
           // Methods.changeGlowColor_Nested_Scrol(Color.parseColor(Variables.Var_App_Config_header_bg_color),first_scrol_drawer);



             }catch (Exception b){
          //  Methods.toast_msg(Drawer.this,"" + b.toString());
        } // End Try catch Body

    } // End method to change Color Dynamically

    // TODO: Clear Lists
    public void Clear_Lists(){
        Categories.clear();
        Home_Slider.clear();
        Section_list.clear();
        sub_Cate_list.clear();
        currentPage = 0;
    }

    public void Run_time_Permissions(){
        //  TODO: (Drawer.java) Run Time Permissions
        ActivityCompat.requestPermissions(Drawer.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE


                },
                1);
    }
        String TAG = "Drawer.java";
    int start, length;
    String viwed_posts;
    JSONArray viewd_posts_array;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        header = findViewById(R.id.header);
        swipeTimer = new Timer();
//        swipeTimer = new Timer();
        String num_click_post = SharedPrefrence.get_offline(Drawer.this, "" + SharedPrefrence.share_num_home_visit);
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Run_time_Permissions();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Methods.toast_msg(Drawer.this,"FB ID " + Variables.facebook_banner_ad);
        Methods.display_fb_ad(Drawer.this,null);

        sAnalytics = GoogleAnalytics.getInstance(this);
        // Make sure global_tracker.xml is configured
        if (!checkConfiguration()) {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, "Bad Configuration.", Snackbar.LENGTH_INDEFINITE).show();
        }

        MyApplication application = (MyApplication) getApplication();
        sTracker = application.getDefaultTracker();
        // [END shared_tracker]


        pullToRefresh = findViewById(R.id.srl_id);

        profile_drawer_img = findViewById(R.id.header_iv_id);
        header_iv1_id = findViewById(R.id.header_iv1_id);
        main_RL = findViewById(R.id.main_RL);
        scrollview_id = findViewById(R.id.scrollview_id);
        viewed_posts_RL = findViewById(R.id.viewed_posts_RL);
        targetView = findViewById(R.id.profile_pic);


        try {

            Methods.setEdgeEffectL_nested (scrollview_id,Color.parseColor(Variables.Var_App_Config_header_bg_color), Drawer.this);

            EdgeEffect edgeEffectTop = new EdgeEffect(this);
            edgeEffectTop.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

            EdgeEffect edgeEffectBottom = new EdgeEffect(this);
            edgeEffectBottom.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

            Field f1 = NestedScrollView.class.getDeclaredField("mEdgeGlowTop");
            f1.setAccessible(true);
            f1.set(scrollview_id, edgeEffectTop);

            Field f2 = NestedScrollView.class.getDeclaredField("mEdgeGlowBottom");
            f2.setAccessible(true);
            f2.set(scrollview_id, edgeEffectBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // todo: Display Ads Method


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Clear_Lists();
                Add_Home();
                get_Category();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefresh.setRefreshing(false);

                    }
                }, 3000);



            }
        });

        try{
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.ads_RL);
            Methods.Show_Banner_ad(Drawer.this, layout);
            Methods.toast_msg(Drawer.this,"Admob Ads" + Variables.admob_banner_300x250);
        }catch (Exception c){

        }



        Add_Home();

        default_city = findViewById(R.id.tv_id);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        tb = (Toolbar) findViewById(R.id.tb_id);
        tv = (TextView) findViewById(R.id.tv_id);
        arrow = (ImageView) findViewById(R.id.arrow_id);
        iv = (ImageView) findViewById(R.id.menu_option_id);
        iv2 = (ImageView) findViewById(R.id.iv2_id);

        user_info = SharedPrefrence.get_offline(Drawer.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );

     ///   Methods.get_default_country_id_1(Drawer.this,Drawer.this,user_info);


        drawer = (DrawerLayout) findViewById(R.id.drawerlayout_id);
        my_activity_linear = drawer.findViewById(R.id.my_activity_linear);
        no_record_img = drawer.findViewById(R.id.no_record_img);
        user_email = drawer.findViewById(R.id.header_tv_id);
        user_name = drawer.findViewById(R.id.user_name);
        sub_cate_names = drawer.findViewById(R.id.sub_cate_names);
        nav_location = drawer.findViewById(R.id.nav_location);
        Category_RV = drawer.findViewById(R.id.ver_category_adp);
        Category_RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Category_RV.setHasFixedSize(false);


        Sub_Category_RV = drawer.findViewById(R.id.ver_sub_category_adp);
        Sub_Category_RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Sub_Category_RV.setHasFixedSize(false);
        first_scrol_drawer = drawer.findViewById(R.id.first_scrol);
        logout_ll_id = findViewById(R.id.logout_ll_id);
        get_Category();

        if(user_info != null){
            // If User is Already Login
            try{
                JSONObject user_obj = new JSONObject(user_info);
                name = user_obj.getString("full_name");
                email = user_obj.getString("email");
                user_name.setText("My Account");
                user_email.setText("" + name);

                try{

                    ImageRequest request =
                            ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(Drawer.this)))
                                    .setProgressiveRenderingEnabled(false)
                                    .build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setAutoPlayAnimations(false)
                            .build();
                    RoundingParams roundingParams = new RoundingParams();
                    roundingParams.setRoundAsCircle(true);
                   // profile_drawer_img.setRotation(90);
                    targetView.getHierarchy().setPlaceholderImage(R.drawable.ic_profile_gray);
                    targetView.getHierarchy().setFailureImage(R.drawable.ic_profile_gray);
                    targetView.getHierarchy().setRoundingParams(roundingParams);
                    targetView.setController(controller);



                }catch (Exception v){

                }


                ImageRequest request =
                        ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(Drawer.this)))
                                .setProgressiveRenderingEnabled(false)
                                .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setAutoPlayAnimations(false)
                        .build();

                RoundingParams roundingParams = new RoundingParams();
                roundingParams.setRoundAsCircle(true);
                //profile_drawer_img.setRotation(90);
                targetView.getHierarchy().setPlaceholderImage(R.drawable.ic_profile_gray);
                targetView.getHierarchy().setFailureImage(R.drawable.ic_profile_gray);
                profile_drawer_img.getHierarchy().setRoundingParams(roundingParams);
                profile_drawer_img.setController(controller);

            }catch (Exception b){

            }


        }else{
             // If User Is not Login
            user_email.setText(getResources().getString(R.string.signup));
            header_iv1_id.setVisibility(View.GONE);
            my_activity_linear.setVisibility(View.GONE);
            logout_ll_id.setVisibility(View.GONE);

        }

        nv = (NavigationView) findViewById(R.id.nav_view_id);
        vf = (ViewFlipper) findViewById(R.id.vf_id);

        vf.setDisplayedChild(0);
        fl = (FrameLayout) findViewById(R.id.fl_id);


        profile_rl = (RelativeLayout) findViewById(R.id.profile_rl_id);


        home_ll = (LinearLayout) findViewById(R.id.home_ll_id);
        categor_rl = (RelativeLayout) findViewById(R.id.category_rl_id);
        order_ll = (LinearLayout) findViewById(R.id.order_ll_id);
        ads_rl = (RelativeLayout) findViewById(R.id.myads_rl_id);
        msgs_rl = (RelativeLayout)findViewById(R.id.msgs_rl_id);
        cash_ll = (LinearLayout) findViewById(R.id.quikr_ll_id);
        busines_ll = (LinearLayout) findViewById(R.id.business_ll_id);
        ratting_ll = (LinearLayout) findViewById(R.id.rating_ll_id);
        loc_rl = (RelativeLayout) findViewById(R.id.loc_rl_id);
        setting_ll = (LinearLayout) findViewById(R.id.setting_ll_id);
        contact_ll = (LinearLayout) findViewById(R.id.contact_ll_id);
        backtoValeyou = (LinearLayout) findViewById(R.id.backtoValeyou);
        logout_ll_id = findViewById(R.id.logout_ll_id);

        pt_back = (ImageView) findViewById(R.id.ptback_id);
        pt_back_third = findViewById(R.id.ptback_id_back);
        // METHOD_openHome();




        try{
            String default_city_val = SharedPrefrence.get_offline(Drawer.this,
                    SharedPrefrence.share_default_city_info
            );

            if(default_city_val != null){
                // If default city is not null
                JSONObject de = new JSONObject(default_city_val);
                default_city.setText("" + de.getString("city_name"));
                nav_location.setText("" + de.getString("city_name"));

            }else{
                // If Values are null
              //  get_default_city();
            }

            }catch (Exception b){

                } // End catch Statement



        tv.setOnClickListener(this);
        arrow.setOnClickListener(this);
        iv.setOnClickListener(this);
        iv2.setOnClickListener(this);
        profile_rl.setOnClickListener(this);
        home_ll.setOnClickListener(this);
        categor_rl.setOnClickListener(this);
        order_ll.setOnClickListener(this);
        ads_rl.setOnClickListener(this);
        msgs_rl.setOnClickListener(this);
        cash_ll.setOnClickListener(this);
        busines_ll.setOnClickListener(this);
        loc_rl.setOnClickListener(this);
        setting_ll.setOnClickListener(this);
        contact_ll.setOnClickListener(this);
        pt_back.setOnClickListener(this);
        post_ad.setOnClickListener(this);
        search_rl.setOnClickListener(this);
        pt_back_third.setOnClickListener(this);
        fab.setOnClickListener(this);
        logout_ll_id.setOnClickListener(this);
        backtoValeyou.setOnClickListener(this);

        Change_Color_Dynmic();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backtoValeyou:
                onBackPressed();
                break;
            // Home Click
            case R.id.search_rl_id:
                startActivity(new Intent(Drawer.this, Search.class));
                break;

            case R.id.fab:

                Intent myIntent = new Intent(Drawer.this, PostFreeAd.class);
                myIntent.putExtra("post_id",""); //Optional parameters
                startActivity(myIntent);
                break;
            // End Home Click
            case R.id.tv_id:
                startActivity(new Intent(Drawer.this, City_loc.class));
                finish();
                break;

            case R.id.arrow_id:
                startActivity(new Intent(Drawer.this, City_loc.class));
                finish();
                break;

            case R.id.menu_option_id:
                drawer.openDrawer(Gravity.LEFT);
                break;

            case R.id.iv2_id:

                if(user_info != null){
                    // If user is already Login
                    startActivity(new Intent(Drawer.this, Shortlisted.class));

                }else{
                    // If user is not Login
                    startActivity(new Intent(Drawer.this, Login.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                }


                break;

            case R.id.profile_rl_id:
                drawer.closeDrawer(Gravity.LEFT);


                if(user_info != null){
                    // If user is already Login
                    startActivity(new Intent(Drawer.this, MyAccount.class));

                }else{
                    // If user is not Login
                    startActivity(new Intent(Drawer.this, Login.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                }
                break;
            case R.id.home_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                Clear_Lists();
                Add_Home();
                get_Category();
                break;

            case R.id.category_rl_id:
                vf.setAnimation(AnimationUtils.loadAnimation(Drawer.this,R.anim.in_from_right));
                vf.showNext();
                break;

            case R.id.order_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(Drawer.this, OrdersPayments.class));
                break;

            case R.id.myads_rl_id:
                drawer.closeDrawer(Gravity.LEFT);
                if(user_info != null){
                    // If user is already Login
                    startActivity(new Intent(Drawer.this, MyAds.class));

                }else{
                    // If user is not Login
                    startActivity(new Intent(Drawer.this, Login.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                }

                break;

            case R.id.msgs_rl_id:
                drawer.closeDrawer(Gravity.LEFT);
                //startActivity(new Intent(Drawer.this, MessagesNotifications.class));
                startActivity(new Intent(Drawer.this, Chat_Inbox.class));
                break;

            case R.id.quikr_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                break;

            case R.id.business_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(Drawer.this, ForBusiness.class));
                break;

            case R.id.rating_ll_id:
                break;

            case R.id.loc_rl_id:
                drawer.closeDrawer(Gravity.LEFT);

//                try{
//
//                    String default_country = SharedPrefrence.get_offline(Drawer.this,
//                            SharedPrefrence.share_default_country_info
//                    );
//                    JSONObject de = new JSONObject(default_country);
//                    country_id = de.getString("country_id");
//                    //  country_id_ok = country_id;
//
//
////            String default_country = SharedPrefrence.get_offline(City_loc.this,
////                    SharedPrefrence.share_default_country_info
////                    );
////            JSONObject de = new JSONObject(default_country);
//                   // country_name = findViewById(R.id.country_name);
//                    //country_name.setText("" + de.getString("country_name"));
////            de.getString("");
//                    // country_id = de.getString("country_id");
//
//                   // Methods.Log_d_msg(City_loc.this,"" + de.getString("country_name"));
//                }catch (Exception b){
//                   // Methods.Log_d_msg(City_loc.this,"" + b.toString());
//                }
//
//                Intent myIntent = new Intent(context, City_loc.class);
//                myIntent.putExtra("country_id", country.getId()); //Optional parameters
//                myIntent.putExtra("country_name", country.getName()); //Optional parameters
//                context.startActivity(myIntent);
                   startActivity(new Intent(Drawer.this, City_loc.class));
                break;

            case R.id.setting_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(Drawer.this, AppSettings.class));
                break;

            case R.id.contact_ll_id:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(Drawer.this, ContactUs.class));
                break;

            case R.id.ptback_id:
                vf.setAnimation(AnimationUtils.loadAnimation(Drawer.this, R.anim.in_from_left));
                vf.showPrevious();
                break;
            case R.id.ptback_id_back:
                vf.setAnimation(AnimationUtils.loadAnimation(Drawer.this, R.anim.in_from_left));
                vf.showPrevious();
                break;
            case R.id.logout_ll_id:
                SharedPrefrence.logout_user(Drawer.this);
                break;
        }
    }



    public void METHOD_openHome(){
        Home f = new Home();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
     //  ft.addToBackStack(null);
        ft.replace(R.id.fl_id, f).commit();
    }

    // Get default city against country

    public void get_default_city (){
        try{

            String default_country = SharedPrefrence.get_offline(Drawer.this,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
            country_id = de.getString("country_id");

            JSONObject sendObj = new JSONObject();
            sendObj.put("country_id" , "" + country_id);



            Volley_Requests.New_Volley(
                    Drawer.this,
                    "" + API_LINKS.API_CITY_LIST,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try{
                                JSONArray msg = response.getJSONArray("msg");
                                for(int i=0; i< msg.length();i++){
                                    JSONObject json =  msg.getJSONObject(i);
                                    JSONObject City_obj = json.getJSONObject("City");

                                    City_obj.getString("name");
                                    City_obj.getString("id");
                                    City_obj.getString("country_id");

                                    City_Get_Set City = new City_Get_Set(
                                            "" + City_obj.getString("id"),
                                            "" + City_obj.getString("name"),
                                            "" +  City_obj.getString("country_id"),
                                            "" +  City_obj.getString("default"),
                                            ""

                                    );

                                    try{

                                        final JSONObject def_city_info = new JSONObject();
                                        def_city_info.put("city_id" , "" +   City_obj.getString("id"));
                                        def_city_info.put("city_name" , "" + City_obj.getString("name"));

                                        // Save into Shared Pref...
                                        SharedPrefrence.save_info_share(
                                                Drawer.this,
                                                "" + def_city_info.toString(),
                                                "" + SharedPrefrence.share_default_city_info
                                        );


                                    }catch (Exception b){

                                    }

                                    if(City_obj.getString("default").equals("1")){
                                        default_city.setText("" + City_obj.getString("name"));
                                        nav_location.setText("" + City_obj.getString("name"));
                                        Variables.Var_default_city =  City_obj.getString("name");

                                    }
                                } // End for Loop

                            }catch (Exception b){
                                Methods.toast_msg(Drawer.this,"ett " + b.toString());
                            }





                        }
                    }


            );



        }catch (Exception v){
//            Methods.toast_msg(Drawer.this,"Get Country" +
//                    " " + v.toString());
        }

    }

    public void get_Category (){
        try{
            JSONObject sendObj = new JSONObject();
            sendObj.put("" , "");

            Volley_Requests.New_Volley(
                    Drawer.this,
                    "" + API_LINKS.API_MAIN_CATEGORIES,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try{
                                JSONArray msg = response.getJSONArray("msg");
                                ArrayList<Cate_Get_Set> tem_list=new ArrayList<>();
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
                                    tem_list.add(cate);
                                    if(i< 3){
                                        cate_sub_text = "" + Main_cate_obj.getString("name") + " " + cate_sub_text;
                                    }




                                } // End for Loop

                                Cate_list=tem_list;

                                if(cate_sub_text.length()> 25){
                                    cate_sub_text = cate_sub_text.substring(0,25) + " ...";
                                }

                                sub_cate_names.setText("" + Html.fromHtml(cate_sub_text));

                                // Setting up adapters
                                cate_adp = new Category_Adp(
                                        Cate_list,
                                        Drawer.this,

                                        new Category_Adp.onclick() {
                                            @Override
                                            public void itemclick(int pos) {

                                                Cate_Get_Set get = Cate_list.get(pos);
                                                get.getSub_cate();
//

                                                // todo: Display Sub Category Response

                                                vf.setAnimation(AnimationUtils.loadAnimation(Drawer.this,R.anim.in_from_right));
                                                vf.showNext();
                                                display_sub_category_response(get.getSub_cate().toString());

                                            }
                                        }

                                );
                                Category_RV.setAdapter(cate_adp);


                            }catch (Exception b){
                                Methods.toast_msg(Drawer.this,"" + b.toString());
                            }




                        }
                    }


            );



        }catch (Exception v){

        }



    }

    @Override
    public void onStart() {
        super.onStart();  // Always call the superclass method first
        Methods.display_fb_ad(Drawer.this,null);
        currentPage = 0;

        try{
            user_info = SharedPrefrence.get_offline(Drawer.this,
                    "" + SharedPrefrence.shared_user_login_detail_key
            );

            JSONObject user_obj = new JSONObject(user_info);

            name = user_obj.getString("full_name");
            email = user_obj.getString("email");

            user_name.setText("" + name);
            user_email.setText("" + email);

            // Fresco Code
            try{
                ImageRotationDetectionHelper check_rotate = new ImageRotationDetectionHelper();
                check_rotate.getCameraPhotoOrientation(SharedPrefrence.get_user_img_org(Drawer.this));

              Methods.Log_d_msg(Drawer.this, "Angel " +  check_rotate.getCameraPhotoOrientation(SharedPrefrence.get_user_img_org(Drawer.this)));

                ImageRequest request =
                        ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(Drawer.this)))
                                .setProgressiveRenderingEnabled(false)
                                .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setAutoPlayAnimations(false)
                        .build();
                RoundingParams roundingParams = new RoundingParams();
                roundingParams.setRoundAsCircle(true);
                targetView.getHierarchy().setPlaceholderImage(R.drawable.ic_profile_gray);
                targetView.getHierarchy().setFailureImage(R.drawable.ic_profile_gray);
                targetView.getHierarchy().setRoundingParams(roundingParams);
                targetView.setController(controller);



            }catch (Exception v){

            }


            ImageRequest request =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(Drawer.this)))
                            .setProgressiveRenderingEnabled(false)
                            .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(false)
                    .build();

            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setRoundAsCircle(true);
            profile_drawer_img.getHierarchy().setPlaceholderImage(R.drawable.ic_profile_gray);
            profile_drawer_img.getHierarchy().setFailureImage(R.drawable.ic_profile_gray);
            profile_drawer_img.getHierarchy().setRoundingParams(roundingParams);
            profile_drawer_img.setController(controller);

        }catch (Exception b){
            Methods.Log_d_msg(Drawer.this,"Please try again later. " + user_info + " " + b.toString());
        }

        // Setting up Adapterf Viewed Posts

        List<Get_Set_home_ads> ads_posts = new ArrayList<>();
        try{
            viwed_posts = SharedPrefrence.get_offline(Drawer.this,"" + SharedPrefrence.share_viwed_posts);

            if(viwed_posts != null){
                // If Array is null

            }else{
                // If arr is not Null
                viewed_posts_RL.setVisibility(View.GONE);
            }

        }catch (Exception b){

        }
        try{
            JSONObject viewd_obj = new JSONObject(viwed_posts);

            viwed_arr = viewd_obj.getJSONArray("viwed");

                for(int op=viwed_arr.length()-1; op >= 0; op--){
                    JSONObject pos_obj = viwed_arr.getJSONObject(op);

                    Get_Set_home_ads ad = new Get_Set_home_ads(
                            "" + pos_obj.getString("id"),
                            "" + pos_obj.getString("title"),
                            "" + pos_obj.getString("price"),
                            "" + pos_obj.getString("city"),
                            "" + pos_obj.getJSONArray("ad_images").getJSONObject(0).getString("image"),
                            pos_obj.getJSONArray("ad_images")
                    );

                    ads_posts.add(ad);
                }// End For Loop

        }catch (Exception b){
            Methods.Log_d_msg(Drawer.this,"Err " + b.toString());
        }


        // Setting Up Adapters

        adp2 = new Rv2_Adp(Drawer.this,ads_posts);
        viewd_RV = findViewById(R.id.viewd_RV);
        viewd_RV.setLayoutManager(new LinearLayoutManager(Drawer.this, LinearLayoutManager.HORIZONTAL, false));
        viewd_RV.setHasFixedSize(false);
        viewd_RV.setAdapter(adp2);



    }

    public void Add_Home(){

    search_rl = (RelativeLayout) findViewById(R.id.search_rl_id);
    fab = (FloatingActionButton) findViewById(R.id.fab);
    post_ad = (ImageView) findViewById(R.id.post_ad_iv_id);

    //fab.set(Color.parseColor(Variables.Var_App_Config_header_bg_color));
    gl = (GridLayout) findViewById(R.id.gl_id);
    gv = (GridView) findViewById(R.id.gv_id);

    // Get App config Data from Local Storage
    Variables.Var_App_Config = SharedPrefrence.get_offline(Drawer.this,
            "" + SharedPrefrence.share_app_Config_key);

    // Create a common gesture listener object.
    DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

    // Set activity in the listener.
    gestureListener.setActivity(Drawer.this);

    // Create the gesture detector with the gesture listener.
    gestureDetectorCompat = new GestureDetectorCompat(Drawer.this, gestureListener);


    vp = findViewById(R.id.vp_id_1);
    vp.setClipToPadding(false);

    rv1 = (RecyclerView) findViewById(R.id.rv1_id);
    rv1.setLayoutManager(new LinearLayoutManager(Drawer.this, LinearLayoutManager.VERTICAL, false));
    rv1.setHasFixedSize(false);

    METHOD_LayoutParam();

    post_ad.setOnClickListener(this);
    search_rl.setOnClickListener(this);

    getFeaturedCategories();
    Get_Home_Slider();
    get_Section_news();
} // End data for Home

    public void METHOD_LayoutParam(){

        double width = Variables.width / 4;

        CardView.LayoutParams lp6 = (CardView.LayoutParams) gl.getLayoutParams();
        lp6.height = (int) (width*4);

        gl.setLayoutParams(lp6);


    }


    // Apply API for Featured Categories
    public void getFeaturedCategories(){

        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,Drawer.this);
        try{

            JSONObject sendObj = new JSONObject("{ '': '' }");
            pullToRefresh.setRefreshing(true);


            Variables.mVolleyService.postDataVolley("POSTCALL", API_LINKS.API_Show_Featured_Categories, sendObj);

        }catch (Exception v){
            v.printStackTrace();
            Methods.Log_d_msg(Drawer.this, "" + v.toString());
        }



        // End for

    }

    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                pullToRefresh.setRefreshing(false);
                try{
                    JSONArray msg = response.getJSONArray("msg");

                    for (int i=0;i< msg.length(); i++ ){
                        JSONObject obj = msg.getJSONObject(i);
                        JSONObject cate_obj = obj.getJSONObject("Category");
                        cate_obj.getString("id");
                        cate_obj.getString("name");
                        cate_obj.getString("label");
                        cate_obj.getString("language_id");

                        Home_get_set cate = new Home_get_set(
                                "" + cate_obj.getString("id"),
                                "" + cate_obj.getString("name"),
                                "" +  cate_obj.getString("label"),
                                "" +  cate_obj.getString("language_id"),
                                "" + cate_obj.getString("main_cat_id"),
                                "" +  cate_obj.getString("sub_cat_id"),
                                "" +  cate_obj.getString("image")
                        );

                        Categories.add(cate);


                    } //End For Loop

                    // Setting up adapters

                    gv.setAdapter(new Grid_Adp(Drawer.this,Categories  , new Grid_Adp.click() {
                                @Override
                                public void onclick(int pos) {
                                    // if (pos == 0){

                                    if(Categories.size() > 0){

                                        Home_get_set get = Categories.get(pos);
                                        All_Ads f = new All_Ads();
                                        Bundle args = new Bundle();
                                        args.putString("main_cate_id", get.getMain_cat_id());
                                        args.putString("sub_cate_id",get.getSub_cat_id());
                                        args.putString("section_id","");
                                        args.putString("section_name","" + get.getCate_name());


                                        f.setArguments(args);

                                        FragmentManager fm = getSupportFragmentManager();
                                        FragmentTransaction tx = fm.beginTransaction();
                                        tx.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();

                                    }


                                }
                            }

                            )

                    );



                }catch (Exception b){
                    pullToRefresh.setRefreshing(false);
                    Methods.Log_d_msg(Drawer.this,"Err " + b.toString());
                }







            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                pullToRefresh.setRefreshing(false);
            }
        };
    }

    public void Get_Home_Slider(){

        try{
            JSONObject response = new JSONObject(Variables.Var_App_Config);
            JSONArray msg = response.getJSONArray("msg");
            for(int i=0; i< msg.length();i++) {
                JSONObject json = msg.getJSONObject(i);
                JSONObject Slider_obj = json.getJSONObject("Setting");
                Slider_obj.getString("image");

                if(Slider_obj.getString("type").equals("" + Variables.Var_App_Config_mob_slider)){
                    // If Type is Mobile Slider
                    Get_Set_Slider slider = new Get_Set_Slider(
                            "" + Slider_obj.getString("id"),
                            "" +  Slider_obj.getString("image"),
                            "" + Slider_obj.getString("type")
                    );
                    Home_Slider.add(slider);
                }else if(Slider_obj.getString("type").equals("" + Variables.Var_App_Config_header_bg_col)){
                    // Header bg Color


                }else if(Slider_obj.getString("type").equals("" + Variables.Var_App_Config_header_bg_col)){
                    // Header Font Color


                }

            } // End for Loop
            init_slider();


        }catch (Exception b){
            Methods.toast_msg(Drawer.this,"" + b.toString());
        }
    }

    private void init_slider() {
        for(int i=0;i< Home_Slider.size(); i++)

            adp = new Vp_Adp(Drawer.this, Home_Slider);
        vp.setAdapter(adp);

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
        indicator.setFillColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        indicator.setViewPager(vp);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(3 * density);


        NUM_PAGES = Home_Slider.size();
        start_slider();
        try {
            Interpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(vp.getContext(), sInterpolator);

            mScroller.set(vp, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

//        vp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.w("touched","down");
////                        stopScroll();
//                        stop_slider();
//                        return true;
//                    //break;
//
//                    case MotionEvent.ACTION_UP:
//                        Log.w("touched","up");
//                        //startScroll();
//                        start_slider();
//                        return true;
//                    //break;
//                    case MotionEvent.ACTION_HOVER_MOVE:
//                        Log.w("touched","Move");
//                        return true;
//                }
//
//                return false;
//            }
//        });
//
//
//        vp.setOnTouchListener(new OnSwipeTouchListener(Drawer.this) {
//            public void onSwipeTop() {
//
//                Log.w("touched","TOP");
//            }
//            public void onSwipeRight() {
//                Log.w("touched","Right");
//                int oo = vp.getCurrentItem();
//                vp.setCurrentItem(oo-1, true);
//            }
//            public void onSwipeLeft() {
//
//                Log.w("touched","Lrft");
//                int oo = vp.getCurrentItem();
//                vp.setCurrentItem(oo+1, true);
//
//            }
//            public void onSwipeBottom() {
//
//                Log.w("touched","Bottom ");
//            }
//
//
//        });


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


    public void start_slider(){

        if(handler!=null && Update!=null){
            handler.removeCallbacks(Update);
        }
        else {
            handler = new Handler();
            Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    vp.setCurrentItem(currentPage++, false);
                    handler.postDelayed(Update, 3000);
                }
            };
        }

        handler.postDelayed(Update,3000);



    }


    public void get_Section_news (){


        try {
            JSONObject sendObj = new JSONObject();

            Volley_Requests.New_Volley(
                    Drawer.this,
                    "" + API_LINKS.API_Show_Sections_At_Home,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                             Methods.Log_d_msg(Drawer.this,"Section " + requestType+  " " + response.toString());
                            // Maipulate Response
                            try{
                                JSONArray Msg_Arr = response.getJSONArray("msg");
                                for (int i=0;i< Msg_Arr.length();i++){
                                    JSONObject Main_obj = Msg_Arr.getJSONObject(i);

                                    Log.d("resp"+i,Main_obj.toString());

                                    JSONObject Section_obj = Main_obj.getJSONObject("Section");
                                    JSONArray Section_Posts_arr = Main_obj.getJSONArray("SectionPost");
                                    Section_obj.getString("id");
                                    Section_obj.getString("name");
                                    Section_obj.getString("order");

                                    if(Section_Posts_arr.length() == 0){
                                        // If no Posts

                                    }else{

                                        // If posts are greater than zero
                                        Get_Set_Section_Posts secion = new Get_Set_Section_Posts(
                                                "" + Section_obj.getString("id"),
                                                "" + Section_obj.getString("name"),
                                                "" + Section_obj.getString("order"),
                                                Section_Posts_arr
                                        );
                                        Section_list.add(secion);

                                    } // End else condition

                                } // End For Loop

                                // Setting Up Adapters

                                adp1 = new Rv1_Adp(Drawer.this,Section_list,  new Rv1_Adp.click() {
                                    @Override
                                    public void onclick(final int pos, View view) {

                                        view.findViewById(R.id.tv2_id).setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v)
                                            {

                                                Get_Set_Section_Posts get = Section_list.get(pos);

                                                All_Ads f = new All_Ads();
                                                Bundle args = new Bundle();
                                                args.putString("main_cate_id","");
                                                args.putString("sub_cate_id","");
                                                args.putString("section_id","" + get.getId());
                                                args.putString("section_name","" + get.getSection_name());
                                                f.setArguments(args);

                                                FragmentManager fm = getSupportFragmentManager();
                                                FragmentTransaction tx = fm.beginTransaction();
                                                tx.addToBackStack(null);
                                                tx.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();
                                            }
                                        });

                                        // }
                                    }
                                });
                                rv1.setAdapter(adp1);

                            }catch (Exception v){

                            }






                        }
                    }


            );


        } catch (Exception v) {
            Methods.Log_d_msg(Drawer.this,"" + v.toString());
        }




    }  // End get Section News

    public void display_sub_category_response (String sub_cate_arr_ok){
        sub_Cate_list.clear();
        try{
            JSONArray sub_cate_arr = new JSONArray(sub_cate_arr_ok);

            if(sub_cate_arr.length() == 0 ){
                // If length is zeroo
                no_record_img.setVisibility(View.VISIBLE);

            }else{
                no_record_img.setVisibility(View.GONE);
            }

            for(int i = 0;i< sub_cate_arr.length(); i++){
                JSONObject sub_cate_obj = sub_cate_arr.getJSONObject(i);

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
                   Drawer.this,

                    new Sub_cate_adp.onclick() {
                        @Override
                        public void itemclick(int pos) {

                            Sub_cate_Get_Set get = sub_Cate_list.get(pos);
                            get.getName();

                            All_Ads f = new All_Ads();
                            Bundle args = new Bundle();
                            args.putString("main_cate_id","" + get.getMain_category_id());
                            args.putString("sub_cate_id","" + get.getId());
                            args.putString("section_id","");
                            args.putString("section_name","" + get.getName());
                            f.setArguments(args);

                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction tx = fm.beginTransaction();
                            tx.addToBackStack(null);
                            tx.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();

                            drawer.closeDrawer(Gravity.LEFT);

                            vf.setDisplayedChild(0);




                        }
                    }

            );
            Sub_Category_RV.setAdapter(sub_cate_adp);





        }catch (Exception n){
            Methods.Log_d_msg(Drawer.this,"" + n.toString());
        }




    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }



    /// Method

    public static void open_edit_ad (String sub_cate_name, String Sub_cate_id, String main_cate_name, String main_cate_id, String post_id, Context context, String city_id){
        Bundle args = new Bundle();
        args.putString("main_cate_id", "" + main_cate_id);
        args.putString("main_cate_name", "" + main_cate_name);
        args.putString("sub_cate_name","" + sub_cate_name);
        args.putString("sub_cate_id","" + Sub_cate_id);
        args.putString("post_id","" + post_id);
        args.putString("city_id","" + city_id);
        args.putString("class","" + Variables.Var_fragment);
        Methods.Log_d_msg(context,"Post " + post_id);

        Ad_Details f = new Ad_Details();
        f.setArguments(args);
        FragmentManager fm =((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();
        //ft.replace(R.id.Main_RL, f).commit();
    }





}
