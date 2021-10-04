package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.SnapHelperOneByOne;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class Ads_img_slider extends Fragment {
    View view;
    Context context;
    String rec_id,rec_name, rec_img, ad_title, all_images, price, post_id;
    Slider_img_adp adp1;
    ImageView cross;
    TextView t_title, t_num_img, t_price, t_current_img;
    RecyclerView img_RV;
    LinearLayout chat_now;
    JSONArray img_arr;
    // Images
    private List<Get_Set_post_img> list_ads_img = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.ads_img_slider, container, false);
        context = getContext();
        // Get params

        Bundle bundle = getArguments();
        if (bundle != null) {

            ad_title = bundle.getString("ad_title");
            price = bundle.getString("price");
            all_images = bundle.getString("all_images");
            post_id = bundle.getString("post_id");
            rec_id =  bundle.getString("user_id");
            rec_name = bundle.getString("user_name");
            rec_img = bundle.getString("user_pic");
        }
        // init Views
        cross = view.findViewById(R.id.id_cross);
        t_title = view.findViewById(R.id.ad_title);

        cross.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                getFragmentManager().popBackStack();

            }
        });


        t_num_img = view.findViewById(R.id.ad_title);
        t_price = view.findViewById(R.id.ad_price);
        t_current_img = view.findViewById(R.id.current_img_num);
        img_RV = view.findViewById(R.id.img_RV_slider);
        chat_now = view.findViewById(R.id.chat_now);
        t_current_img.setText("");
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(img_RV);
        try{
            img_arr = new JSONArray(all_images);
            t_current_img.setText( "1/" +  img_arr.length());
        }catch (Exception b){

        }



        img_RV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = getCurrentItem() + 1;
                    //onPageChanged(position);
                   // Methods.toast_msg(getContext()," Current " + position);
                    t_current_img.setText("" + position + "/" +  img_arr.length());
                }
            }
        });


        try{
            chat_now.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

        }catch (Exception b){

        }

        if(ad_title.length() < 17){
            // If Number of words greater than 25 then
            t_title.setText("" + ad_title);
        }else{
            t_title.setText(ad_title.substring(0,17) + " ...");
        }


        t_price.setText(SharedPrefrence.Currancy_name_from_Json(context) + "" + price);


        list_ads_img.clear();

        // Check if Json Array is not Null
        if(all_images != null){
            // If ads images are not Null
            try{
                img_arr = new JSONArray(all_images);
                //t_current_img.setText("" + img_arr.length());

                    for(int a = 0; a < img_arr.length(); a++){
                        JSONObject img_obj = img_arr.getJSONObject(a);
                        ///JSONObject img_obj_get = img_obj.getJSONObject("PostImage");
                        img_obj.getString("id");
                        img_obj.getString("image");
                        img_obj.getString("post_id");
//                     if(a == 0){
//                         // Save First Image
//                         save_ads_obj.put("image", "" + img_obj.getString("image"));
//                     }


                        Get_Set_post_img add = new Get_Set_post_img(
                                "" + img_obj.getString("id"),
                                "" + img_obj.getString("post_id"),
                                "" + img_obj.getString("image")


                        );
                        list_ads_img.add(add);


                    } // End for Loop
                    adp1 = new Slider_img_adp(context,list_ads_img);
                    //adp = new Inner_Rv_Adp(Post_Ads_Details.this,list_ads_img);
                    img_RV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    img_RV.setHasFixedSize(false);
                    img_RV.setAdapter(adp1);


            }catch (Exception b){

            }


        }










        return view;
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)img_RV.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

}
