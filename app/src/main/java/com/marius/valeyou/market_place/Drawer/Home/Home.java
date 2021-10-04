package com.marius.valeyou.market_place.Drawer.Home;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.All_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Section_Posts;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Slider;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_home_ads;
import com.marius.valeyou.market_place.Drawer.Home.Get_Set.Home_get_set;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.PostFreeAd;
import com.marius.valeyou.market_place.Drawer.Home.Search.Search;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.DetectSwipeGestureListener;
import com.marius.valeyou.market_place.Utils.FixedSpeedScroller;
import com.marius.valeyou.market_place.Utils.OnSwipeTouchListener;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.IResult;
import com.marius.valeyou.market_place.Volley_Package.VolleyService;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class Home extends Fragment implements View.OnClickListener {

    View view;
    ImageView post_ad;
    CardView cv,cv2,cv3;
    LinearLayout rl1,rl2,rl3,rl4,rl5,ll1,ll2,ll3,ll4,ll5,ll6,ll7;
    RelativeLayout search_rl;
    GridView gv;
    GridLayout gl;
//    ViewPager vp;
    ViewPager vp;
    Vp_Adp adp;
    RecyclerView rv1,rv2;
    Rv1_Adp adp1;
    Rv2_Adp adp2;
    Timer timer;
    List<Home_get_set> Categories = new ArrayList<>();
    List<Get_Set_Slider> Home_Slider = new ArrayList<>();
    // Section List
    List<Get_Set_Section_Posts> Section_list = new ArrayList<>();

    // News Inside Sections
    List<Get_Set_home_ads> ads_inside_Section = new ArrayList<>();


    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    int[] list = {R.color.blue,R.color.black,R.color.colorAccent};
    Timer swipeTimer;
    /// This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        Toast.makeText(getContext(), "Call3", Toast.LENGTH_SHORT).show();

        cv = (CardView) view.findViewById(R.id.cv1_id);
        cv2 = (CardView) view.findViewById(R.id.cv2_id);
        cv3 = (CardView) view.findViewById(R.id.cv3_id);

        rl1 = (LinearLayout) view.findViewById(R.id.rl1_id);
        rl2 = (LinearLayout) view.findViewById(R.id.rl2_id);
        rl3 = (LinearLayout) view.findViewById(R.id.rl3_id);
        rl4 = (LinearLayout) view.findViewById(R.id.rl4_id);
        rl5 = (LinearLayout) view.findViewById(R.id.rl5_id);

        ll1 = (LinearLayout) view.findViewById(R.id.ll1_id);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2_id);
        ll3 = (LinearLayout) view.findViewById(R.id.ll3_id);
        ll4 = (LinearLayout) view.findViewById(R.id.ll4_id);
        ll5 = (LinearLayout) view.findViewById(R.id.ll5_id);
        ll6 = (LinearLayout) view.findViewById(R.id.ll6_id);
        ll7 = (LinearLayout) view.findViewById(R.id.ll7_id);

        search_rl = (RelativeLayout) view.findViewById(R.id.search_rl_id);

        post_ad = (ImageView) view.findViewById(R.id.post_ad_iv_id);

        gl = (GridLayout) view.findViewById(R.id.gl_id);
        gv = (GridView) view.findViewById(R.id.gv_id);

        // Get App config Data from Local Storage
        Variables.Var_App_Config = SharedPrefrence.get_offline(getContext(),
                "" + SharedPrefrence.share_app_Config_key);

       // Methods.alert_dialogue(getContext(),"App Config", "" + Variables.Var_App_Config);

        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        //gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), gestureListener);


        vp = view.findViewById(R.id.vp_id_1);
        vp.setClipToPadding(false);
        //vp.setStopScrollWhenTouch(true);
       // vp.setPadding(40,0, 40,0);
//        adp = new Vp_Adp(getContext(), list);
//        vp.setAdapter(adp);
//        vp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.w("touched","down");
//                        vp.stopAutoScroll();
//                        return true;
//                    //break;
//
//                    case MotionEvent.ACTION_UP:
//                        Log.w("touched","up");
//                        vp.startAutoScroll();
//                        return true;
//                    //break;
//                }
//
//                return false;
//            }
//        });

        rv1 = (RecyclerView) view.findViewById(R.id.rv1_id);
        //rv2 = (RecyclerView) view.findViewById(R.id.rv2_id);

       // adp2 = new Rv2_Adp();

        rv1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv1.setHasFixedSize(false);

//        rv2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        rv2.setHasFixedSize(false);

       // rv2.setAdapter(adp2);


//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                vp.post(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        vp.setCurrentItem((vp.getCurrentItem()+1)%Home_Slider.size());
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 3000, 3000);
//            timer.schedule(timerTask,0);
        METHOD_LayoutParam();

        post_ad.setOnClickListener(this);
        search_rl.setOnClickListener(this);

        getFeaturedCategories();
        Get_Home_Slider();

        get_Section_news();

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_rl_id:
                startActivity(new Intent(getActivity(), Search.class));
                break;

            case R.id.post_ad_iv_id:
               //startActivity(new Intent(getActivity(), PostFreeAd.class));
                Intent myIntent = new Intent(getContext(), PostFreeAd.class);
                myIntent.putExtra("post_id",""); //Optional parameters
                startActivity(myIntent);
                break;
        }
    }

    public void METHOD_LayoutParam(){

        double width = Variables.width / 4;

        CardView.LayoutParams lp6 = (CardView.LayoutParams) gl.getLayoutParams();
        lp6.height = (int) (width*4);

        gl.setLayoutParams(lp6);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) rl1.getLayoutParams();
        lp1.width = (int) width;

        rl1.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) rl2.getLayoutParams();
        lp2.width = (int) width;

        rl2.setLayoutParams(lp2);

        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) rl3.getLayoutParams();
        lp3.width = (int) width;

        rl3.setLayoutParams(lp3);

        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) rl4.getLayoutParams();
        lp4.width = (int) width;

        rl4.setLayoutParams(lp4);

        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) rl5.getLayoutParams();
        lp5.width = (int) width;

        rl5.setLayoutParams(lp5);

        LinearLayout.LayoutParams lp8 = (LinearLayout.LayoutParams) ll1.getLayoutParams();
        lp8.width = (int) (width*1.5);

        ll1.setLayoutParams(lp8);

        LinearLayout.LayoutParams lp9 = (LinearLayout.LayoutParams) ll2.getLayoutParams();
        lp9.width = (int) (width*1.5);

        ll2.setLayoutParams(lp9);

        LinearLayout.LayoutParams lp10 = (LinearLayout.LayoutParams) ll3.getLayoutParams();
        lp10.width = (int) (width*1.5);

        ll3.setLayoutParams(lp10);

        LinearLayout.LayoutParams lp11 = (LinearLayout.LayoutParams) ll4.getLayoutParams();
        lp11.width = (int) (width*1.5);

        ll4.setLayoutParams(lp11);

        LinearLayout.LayoutParams lp12 = (LinearLayout.LayoutParams) ll5.getLayoutParams();
        lp12.width = (int) (width*1.5);

        ll5.setLayoutParams(lp12);

        LinearLayout.LayoutParams lp13 = (LinearLayout.LayoutParams) ll6.getLayoutParams();
        lp13.width = (int) (width*1.5);

        ll6.setLayoutParams(lp13);

        CardView.LayoutParams lp7 = (CardView.LayoutParams) ll7.getLayoutParams();
        lp7.height = (int) (width*2.4);

        ll7.setLayoutParams(lp7);

    }


    // Apply API for Featured Categories
    public void getFeaturedCategories(){

        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,getContext());
        try{

            JSONObject sendObj = new JSONObject("{ '': '' }");

            Variables.pDialog = new ProgressDialog(getContext());
            Variables.pDialog.setMessage(getContext().getResources().getString(R.string.loading_text));
            Variables.pDialog.setCancelable(false);

            Variables.pDialog.show();

            Variables.mVolleyService.postDataVolley("POSTCALL", API_LINKS.API_Show_Featured_Categories, sendObj);

        }catch (Exception v){
            v.printStackTrace();
            //Methods.toast_msg(getContext(), "" + v.toString());
        }



        // End for

}

    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.pDialog.hide();
               // Methods.toast_msg(getContext(),"Res " + response.toString());

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

                    gv.setAdapter(new Grid_Adp(getContext(),Categories  , new Grid_Adp.click() {
                            @Override
                            public void onclick(int pos) {
                                // if (pos == 0){

                                Home_get_set get = Categories.get(pos);
                                get.getMain_cat_id();


                                All_Ads f = new All_Ads();
                                Bundle args = new Bundle();
                                args.putString("main_cate_id", get.getMain_cat_id());
                                args.putString("sub_cate_id",get.getSub_cat_id());
                                args.putString("section_id","");

                                f.setArguments(args);

                                FragmentManager fm = getChildFragmentManager();
                                FragmentTransaction tx = fm.beginTransaction();
                                tx.replace( R.id.main_RL, f ).addToBackStack( "All_ads" ).commit();

                            }
                    }

                    )

                    );



                }catch (Exception b){
                    //Methods.toast_msg(getContext(),"Err " + b.toString());
                }







            }
            @Override
            public void notifyError(String requestType, VolleyError error) {

                Variables.pDialog.hide();
                //Methods.toast_msg(getContext(),"Err " + error.toString());

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



//                                    Get_Set_Slider slider = new Get_Set_Slider(
//                                            "" + Slider_obj.getString("id"),
//                                            "http://bringthings.com/foodies/mobileapp_api/app/webroot/uploads/2/5c5c8f64a9d39.png",
//                                            "" + Slider_obj.getString("type")
//                                    );
//                                    Home_Slider.add(slider);



            } // End for Loop
          init_slider();





//                                msg.getJSONObject(0);

        }catch (Exception b){
            //Methods.toast_msg(getContext(),"" + b.toString());
        }
    }

    private void init_slider() {
        ///Variables.toast_msg(getContext(),"" + Slider_List.size());

        for(int i=0;i< Home_Slider.size(); i++)
//               ImagesArray.add(IMAGES[i]);


        //vp = (ViewPager) view.findViewById(R.id.pager);
        adp = new Vp_Adp(getContext(), Home_Slider);
        vp.setAdapter(adp);

        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(vp);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(3 * density);
        /// indicator.setPaddingRelative(3,3,3,3);

        NUM_PAGES = Home_Slider.size();

        // Sliders_data_model News = Slider_List.get(pos);

        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                vp.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();

//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);

        start_slider();
      //  vp.setPageTransformer(true, 34);
       // Field mScroller;
      //  vp.setOffScreenLimit(page.length);
        try {
            Interpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(vp.getContext(), sInterpolator);
            // scroller.setFixedDuration(5000);
            mScroller.set(vp, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        Log.w("touched","down");
//                        stopScroll();
                       stop_slider();
                        return true;
                    //break;

                    case MotionEvent.ACTION_UP:
                        Log.w("touched","up");
                        //startScroll();
                            start_slider();
                        return true;
                    //break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        Log.w("touched","Move");
                        return true;
                }

                return false;
            }
        });


        vp.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
               // Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
                Log.w("touched","TOP");
            }
            public void onSwipeRight() {
                Log.w("touched","Right");
                int oo = vp.getCurrentItem();
                vp.setCurrentItem(oo-1, true);
            }
            public void onSwipeLeft() {

                Log.w("touched","Lrft");
                int oo = vp.getCurrentItem();
                vp.setCurrentItem(oo+1, true);
                // Toast.makeText(getContext(), "left " + oo + " " + oo++, Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                //Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                Log.w("touched","Bottom ");
            }

        });


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

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, false);
            }
        };

        swipeTimer = new Timer();

        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);



    }

    public void stop_slider(){
        swipeTimer.cancel();
    }


    public void get_Section_news (){

        Toast.makeText(getContext(), "Call1", Toast.LENGTH_SHORT).show();

            try {
                JSONObject sendObj = new JSONObject();

                Volley_Requests.New_Volley(
                        getContext(),
                        "" + API_LINKS.API_Show_Sections_At_Home,
                        sendObj,
                        "OK",

                        new CallBack() {
                            @Override
                            public void Get_Response(String requestType, JSONObject response) {
                                Toast.makeText(getContext(), "Call", Toast.LENGTH_SHORT).show();
                                Log.d("resp",response.toString());
              try{
                JSONArray Msg_Arr = response.getJSONArray("msg");

                Log.d("resp",Msg_Arr.toString());

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

                  adp1 = new Rv1_Adp(getContext(),Section_list,  new Rv1_Adp.click() {
                      @Override
                      public void onclick(final int pos, View view) {
                          // if (pos == 0){
                          //List<Get_Set_home_ads> ads_inside_Section = new ArrayList<>();

                          view.findViewById(R.id.tv2_id).setOnClickListener(new View.OnClickListener() {
                              public void onClick(View v)
                              {

                                  Get_Set_Section_Posts get = Section_list.get(pos);

                                  All_Ads f = new All_Ads();
                                  Bundle args = new Bundle();
                                  args.putString("main_cate_id", "");
                                  args.putString("sub_cate_id","");
                                  args.putString("section_id","" + get.getId());
                                  f.setArguments(args);
                                  FragmentManager fm = getChildFragmentManager();
                                  FragmentTransaction tx = fm.beginTransaction();

                                  tx.replace( R.id.fl_id, f ).commit();



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
                //Methods.toast_msg(getContext(),"" + v.toString());
            }




    }








}
