package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Display_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details.Post_Ads_Details;
import com.marius.valeyou.market_place.Drawer.MyAds.Active_Ads;
import com.marius.valeyou.market_place.Drawer.MyAds.MyAds;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.RecyclerItemClickListener;
import com.marius.valeyou.market_place.Volley_Package.API_Calling_methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class My_ads_adp extends RecyclerView.Adapter<My_ads_adp.ViewHolder> {

    Context context;
    Inner_Rv_Adp adp;

    public click buyclick;
    List<Get_Set_Display_Ads> list_ads = new ArrayList<>();
    private List<Get_Set_post_img> list_ads_img ;



    public interface click{
        void onclick(int pos, View view);
    }

    public My_ads_adp(Context context, click buyclick,  List<Get_Set_Display_Ads> list_ads  ) {
        this.context = context;
        this.buyclick = buyclick;
        this.list_ads = list_ads;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_ads_item,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        list_ads_img = new ArrayList<>();
        final Get_Set_Display_Ads Ads_all = list_ads.get(i);

//        if(Ads_all.getTitle().length() < 25){
//            // If Number of words greater than 25 then
//            viewHolder.tv.setText(Ads_all.getTitle());
//        }else{
//            viewHolder.tv.setText(Ads_all.getTitle().substring(0,25) + " ...");
//        }
        viewHolder.tv.setText(Ads_all.getTitle());
        try{
            viewHolder.price.setText(SharedPrefrence.Currancy_name_from_Json(context) + "" + Ads_all.getPrice());
            viewHolder.price.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){
             Methods.toast_msg(context,"" + b.toString());
        }

        viewHolder.tv.setText(Ads_all.getTitle());
        viewHolder.time.setText("" + Methods.get_time_ago_org(Ads_all.getCreated()) + " | " + Ads_all.getCity_name() );


        try{
            GradientDrawable gd = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            //viewHolder.open_edit_option.setBackground(gd);
            viewHolder.open_chat.setBackground(gd);


            GradientDrawable gd_1 = new GradientDrawable();
            // gd.setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color)); // Changes this drawbale to use a single color instead of a gradient
            gd_1.setCornerRadius(5);
            gd_1.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));
            viewHolder.open_edit_option.setBackground(gd_1);

        }catch (Exception T){

        }

        if (Ads_all.getIs_like().equals("0")) {
            // If no Like
            viewHolder.fav_id.setTag("like");
            viewHolder.fav_id.setImageResource(R.drawable.ic_like);

        }else{

            try{
                //viewHolder.fav_id.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

                // viewHolder.fav_id.setColorFilter(Color.parseColor(Variables.Var_App_Config_header_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            }catch (Exception vy){
                Methods.toast_msg(context,"Error " + vy.toString());
            }

            try{
                //viewHolder.fav_id.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

                // viewHolder.fav_id.setColorFilter(Color.parseColor(Variables.Var_App_Config_header_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            }catch (Exception vy){
                Methods.toast_msg(context,"Error " + vy.toString());
            }

            viewHolder.fav_id.setTag("unlike");
            viewHolder.fav_id.setImageResource(R.drawable.ic_like_filled_red);
        }



        String sourceString = "<b> 24,3453 </b> /month on EMI | <b> <font color='blue'> 432,253 </font></b>";
        viewHolder.price_desc.setText(Html.fromHtml(sourceString));
        viewHolder.fav_id.setTag("unlike");
        viewHolder.fav_id.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                String tag = viewHolder.fav_id.getTag().toString();
                if(tag.equals("unlike")){
                    // Like Code is Here.

                    try{
                        //viewHolder.fav_id.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

                        // viewHolder.fav_id.setColorFilter(Color.parseColor(Variables.Var_App_Config_header_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                        ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
                    }catch (Exception vy){
                        Methods.toast_msg(context,"Error " + vy.toString());
                    }

                    viewHolder.fav_id.setImageResource(R.drawable.ic_like_filled_red);
                    viewHolder.fav_id.setTag("like");
                    API_Calling_methods.add_fav_ads("1", Ads_all.getPost_id() ,context);
                }else if(tag.equals("like")){
                    // Unlike Code is here
                    ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor("#6F000000")));

                    viewHolder.fav_id.setImageResource(R.drawable.ic_like);
                    viewHolder.fav_id.setTag("unlike");
                    API_Calling_methods.add_fav_ads("0", Ads_all.getPost_id()  ,context);
                    //add_fav_ads("1");
                }

            }
        });




        // Manipulating Post Images....
        JSONArray images = Ads_all.getImg_array();
        try{
            for(int a = 0; a < images.length(); a++){
                JSONObject img_obj = images.getJSONObject(a);
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


            }

        }catch (Exception v){
            Methods.toast_msg(context,"" + v.toString());
        }



        viewHolder.rv.addOnItemTouchListener(
                new RecyclerItemClickListener(context, viewHolder.rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                      //  Methods.toast_msg(context,"Er");
                Intent myIntent = new Intent(context, Post_Ads_Details.class);
                        myIntent.putExtra("img_arr", Ads_all.getImg_array().toString()); //Optional parameters
                        myIntent.putExtra("title", Ads_all.getTitle());
                        myIntent.putExtra("ad_id", Ads_all.getPost_id());
                        myIntent.putExtra("main_cate_id", Ads_all.getMain_category_id());

                        context.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        adp = new Inner_Rv_Adp(context,list_ads_img);
        viewHolder.rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        viewHolder.rv.setHasFixedSize(false);
        viewHolder.rv.setAdapter(adp);

        viewHolder.open_edit_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here


                MyAds.open_edit_ad("" + Ads_all.getSub_category_name(),
                        "" + Ads_all.getSub_category_id(),
                        "" + Ads_all.getMain_category_name(),
                        "" + Ads_all.getMain_category_id(),
                        Ads_all.getPost_id(),
                        context,
                        "" + Ads_all.getCity_id()

                );

            }
        });


        viewHolder.open_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                boolean is_wifi_availeable = Methods.is_internet_avail(context);

                if(is_wifi_availeable == true){
                    // If wifi Available

                    list_ads.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, list_ads.size());
                   // viewHolder.itemView.setVisibility(View.GONE);
                    notifyDataSetChanged();
                    Active_Ads.delete_post("" + Ads_all.getPost_id()
                            , i,
                            context
                    );
                }else{
                    // no WIfi
                    Methods.alert_dialogue(context,"Info","Please connect to internet.");
                }



            }
        });

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here


                Intent myIntent = new Intent(context, Post_Ads_Details.class);
                myIntent.putExtra("img_arr", Ads_all.getImg_array().toString()); //Optional parameters
                myIntent.putExtra("title", Ads_all.getTitle());
                myIntent.putExtra("ad_id", Ads_all.getPost_id());
                myIntent.putExtra("main_cate_id", Ads_all.getMain_category_id());

                context.startActivity(myIntent);

            }
        });





        viewHolder.itemclick(i, buyclick);
    }

    @Override
    public int getItemCount() {
        return list_ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, price, time, price_desc, open_chat, open_edit_option;
        RecyclerView rv;
        ImageView fav_id ;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_id);
            rv = (RecyclerView) itemView.findViewById(R.id.rv_id);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);
            price_desc = itemView.findViewById(R.id.price_desc);
            open_chat = itemView.findViewById(R.id.open_chat);
            fav_id = itemView.findViewById(R.id.fav_id);
            open_edit_option = itemView.findViewById(R.id.edit_ad);
            card = itemView.findViewById(R.id.card);

        }

        public void itemclick(final int pos, final click item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onclick(pos,v);
                }
            });


        }

        private void deleteItem(int i) {

        }


    }






}
