package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Chat;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Display_Ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details.Post_Ads_Details;
import com.marius.valeyou.market_place.Drawer.Home.Shortlisted;
import com.marius.valeyou.market_place.Login_Register.Login;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.RecyclerItemClickListener;
import com.marius.valeyou.market_place.Volley_Package.API_Calling_methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Rv_Adp extends RecyclerView.Adapter<Rv_Adp.ViewHolder> {

    Context context;

    Inner_Rv_Adp adp;
    public click buyclick;
    List<Get_Set_Display_Ads> list_ads;
    private List<Get_Set_post_img> list_ads_img;

    public interface click{
        void onclick(int pos);
    }

    public Rv_Adp(Context context, click buyclick,  List<Get_Set_Display_Ads> list_ads  ) {
        this.context = context;
        this.buyclick = buyclick;
        this.list_ads = list_ads;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_ads_item,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        // TODO: Remember this Line   :-)

        list_ads_img = new ArrayList<>();
        final Get_Set_Display_Ads Ads_all = list_ads.get(i);
        String user_id = SharedPrefrence.get_user_id_from_json(context);

        if(user_id.equals("" + Ads_all.getUser_id())) {
            // If user's post
            viewHolder.open_chat.setText("Edit Post");
        }
        viewHolder.tv.setText(Ads_all.getTitle());

        viewHolder.time.setText("" + Methods.get_time_ago_org(Ads_all.getCreated()) + " |  " + Ads_all.getCity_name());

        try{
            viewHolder.price_ok.setText( SharedPrefrence.Currancy_name_from_Json(context) + "" + Ads_all.getPrice() );
            viewHolder.price_ok.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){
            Methods.Log_d_msg(context,"" + b.toString());
        }



        // Gradiant

        try{
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.parseColor(Variables.Var_App_Config_header_bg_color));

            viewHolder.open_chat.setBackground(gd);

        }catch (Exception b){
            Methods.Log_d_msg(context,"" + b.toString());
        }

        viewHolder.open_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                String user_info = SharedPrefrence.get_offline(context,
                        "" + SharedPrefrence.shared_user_login_detail_key
                );
                if(user_info != null){
                    // If user is already Login
                    String user_id = SharedPrefrence.get_user_id_from_json(context);
                        if(user_id.equals("" + Ads_all.getUser_id())){
                            // If user's post
                           // viewHolder.open_chat.setText("Edit");
                            Methods.Log_d_msg(context,"Cat " + Ads_all.getSub_category_name() + " Main " + Ads_all.getMain_category_name());
                            try{
                                ((Drawer) context).open_edit_ad (
                                        "" + Ads_all.getSub_category_name(),
                                        "" + Ads_all.getSub_category_id(),
                                        "" + Ads_all.getMain_category_name(),
                                        "" + Ads_all.getMain_category_id(),
                                        "" + Ads_all.getPost_id(),
                                        context,
                                        "" +  Ads_all.getCity_id()
                                );



                            }catch (Exception b){
                                ((Shortlisted) context).open_edit_ad (
                                        "" + Ads_all.getSub_category_name(),
                                        "" + Ads_all.getSub_category_id(),
                                        "" + Ads_all.getMain_category_name(),
                                        "" + Ads_all.getMain_category_id(),
                                        "" + Ads_all.getPost_id(),
                                        context,
                                        "" + Ads_all.getCity_id()
                                );

                                Methods.Log_d_msg(context,"" + b.toString());

                            }



                        }else{
                            // If user is not post

                            Intent myIntent = new Intent(context, Chat.class);
                            myIntent.putExtra("receiver_id", "" + Ads_all.getUser_id());
                            myIntent.putExtra("receiver_name", "" + Ads_all.getUser_name());
                            myIntent.putExtra("receiver_pic", "" + Ads_all.getUser_pic());
                            context.startActivity(myIntent);

                        }


                }else{
                     // If user is not Login

                    Intent intent = new Intent(context, Login.class);
                    context.startActivity(intent);
                    // Start Animation
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );


                }







            }
        });


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


        if (Ads_all.getIs_like().equals("0")) {
            // If no Like
            viewHolder.fav_id.setTag("like");
            viewHolder.fav_id.setImageResource(R.drawable.ic_like);

        }else{
            viewHolder.fav_id.setTag("unlike");

            try{
                //viewHolder.fav_id.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

               // viewHolder.fav_id.setColorFilter(Color.parseColor(Variables.Var_App_Config_header_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            }catch (Exception v){
                Methods.toast_msg(context,"Error " + v.toString());
            }

            viewHolder.fav_id.setImageResource(R.drawable.ic_like_filled_red);
        }


        String sourceString = "<b> 24,3453 </b> /month on EMI | <b> <font color='blue'> 432,253 </font></b>";
        viewHolder.price_desc.setText(Html.fromHtml(sourceString));
        viewHolder.fav_id.setTag("unlike");
        viewHolder.fav_id.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

           // Setting up Tag for Like




                String tag = viewHolder.fav_id.getTag().toString();

                String  user_info = SharedPrefrence.get_offline(context,
                        "" + SharedPrefrence.shared_user_login_detail_key
                );

                if(user_info != null){
                    // > If user is  Login

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
                        API_Calling_methods.add_fav_ads("1", Ads_all.getPost_id() , context);
                    }else if(tag.equals("like")){
                        // Unlike Code is here
                        ImageViewCompat.setImageTintList(viewHolder.fav_id, ColorStateList.valueOf(Color.parseColor("#6F000000")));

                        viewHolder.fav_id.setImageResource(R.drawable.ic_like);
                        viewHolder.fav_id.setTag("unlike");
                        API_Calling_methods.add_fav_ads("0", Ads_all.getPost_id() ,context);
                        //add_fav_ads("1");
                    }
                }else{
                    // > If user is not Login

                    Intent intent = new Intent(context, Login.class);
                    context.startActivity(intent);
                    // Start Animation
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );


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
            Methods.Log_d_msg(context,"" + v.toString());
        }





        adp = new Inner_Rv_Adp(context,list_ads_img);

        viewHolder.rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        viewHolder.rv.setAdapter(adp);
        viewHolder.itemclick(i, buyclick);


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return list_ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, price, time, price_desc, open_chat, price_ok;
        RecyclerView rv;
        ImageView  fav_id ;
        CardView cardView;
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_id);
            rv = (RecyclerView) itemView.findViewById(R.id.rv_id);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);
            price_desc = itemView.findViewById(R.id.price_desc);
            open_chat = itemView.findViewById(R.id.open_chat);
            fav_id = itemView.findViewById(R.id.fav_id);
            price_ok = itemView.findViewById(R.id.price_ok);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_id);


        }

        public void itemclick(final int pos, final click item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onclick(pos);




                }
            });




        }

    }
}
