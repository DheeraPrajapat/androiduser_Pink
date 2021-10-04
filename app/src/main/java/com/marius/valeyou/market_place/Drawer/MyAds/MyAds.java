package com.marius.valeyou.market_place.Drawer.MyAds;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class MyAds extends AppCompatActivity implements View.OnClickListener {

    public static ImageView iv;
    public static TabLayout tl;
    ViewPager vp;
    Vp_Adp adp;
    public static TextView toolbar_text, buy_cred;
    public static LinearLayout Main_Linear_My_ads;
    public static Toolbar header;

    // TODO: (MyAccount.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic (){

        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, MyAds.this);
            tl.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception v){

        } // End Catch of changing Toolbar

    } // End method to change Color Dynamically


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ads);


        iv = (ImageView) findViewById(R.id.back_id);
        Main_Linear_My_ads = findViewById(R.id.Main_Linear);
        toolbar_text = findViewById(R.id.toolbar_text);
        buy_cred = findViewById(R.id.buy_cred);



        tl = (TabLayout) findViewById(R.id.tl_id);
        vp = (ViewPager) findViewById(R.id.vp_id);

        adp = new Vp_Adp(getSupportFragmentManager());
        adp.addfragment(new Active_Ads(),"Active Ads");
        adp.addfragment(new Inactvie_Ads(), "Inactive ads");

        vp.setAdapter(adp);
        tl.setupWithViewPager(vp);

        // Change Tab bar color
        iv.setOnClickListener(this);
        Change_Color_Dynmic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;

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
        ft.replace(R.id.Main_Linear, f).commit();
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
        ft.replace( R.id.main_frame, f ).addToBackStack( "All_ads" ).commit();
    }


}
