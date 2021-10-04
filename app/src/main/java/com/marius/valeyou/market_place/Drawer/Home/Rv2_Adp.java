package com.marius.valeyou.market_place.Drawer.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_home_ads;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details.Post_Ads_Details;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Rv2_Adp extends RecyclerView.Adapter<Rv2_Adp.ViewHolder> {

    Integer[] list = {R.drawable.image_placeholder, R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder};

    Context context;
    List<Get_Set_home_ads> posts;

    public Rv2_Adp(Context context, List<Get_Set_home_ads> posts){
        this.context = context;
        this.posts = posts;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_rv2_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Get_Set_home_ads ads = posts.get(i);

        double height = Variables.width/4;

        viewHolder.iv.getLayoutParams().height = (int) (height*1.2);
        viewHolder.iv.getLayoutParams().width = (int) (height*1.5);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) viewHolder.rl.getLayoutParams();
        lp1.width = (int) (height*1.5);

        viewHolder.rl.setLayoutParams(lp1);


        String image_name_from_url = Methods.getFileNameFromURL (API_LINKS.BASE_URL + ads.getImage_url());
        Methods.toast_msg(context,"File name " + image_name_from_url );

        String image_150_thumb = ads.getImage_url().replace(image_name_from_url ,
                Variables.Var_thumb_prefix +  image_name_from_url
        );
        Methods.toast_msg(context," File name thumb " + image_150_thumb );

        // Usage of Fressco
        try{
            Uri uri = Uri.parse(API_LINKS.BASE_URL + image_150_thumb);
            viewHolder.iv.setImageURI(uri);  // Attachment
            viewHolder.iv.getHierarchy().setPlaceholderImage(context.getResources().getDrawable(R.drawable.placeholder_2));

        }catch (Exception v){

        }

        viewHolder.city.setText("" + ads.getCity());
        viewHolder.price.setText(SharedPrefrence.Currancy_name_from_Json(context) + "" + ads.getPrice());
        // Change Price Color:
        try{
            viewHolder.price.setTextColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){
            Methods.Log_d_msg(context,"" + b.toString());
        }

        viewHolder.title.setText("" + ads.getTitle());

        viewHolder.rl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(context, Post_Ads_Details.class);
                myIntent.putExtra("img_arr", ads.getImage_arr().toString()); //Optional parameters
                myIntent.putExtra("title", ads.getTitle());
                myIntent.putExtra("ad_id", ads.getId());

                context.startActivity(myIntent);


            }
        });


//
//        if (i == 5){
//            viewHolder.iv.setVisibility(View.INVISIBLE);
//            viewHolder.city.setVisibility(View.INVISIBLE);
//            viewHolder.title.setVisibility(View.INVISIBLE);
//            viewHolder.price.setVisibility(View.INVISIBLE);
//            viewHolder.ll.setVisibility(View.VISIBLE);
//            viewHolder.rl.setBackgroundResource(R.color.off_white);
//        }else {
//            viewHolder.iv.setVisibility(View.VISIBLE);
//            viewHolder.city.setVisibility(View.VISIBLE);
//            viewHolder.title.setVisibility(View.VISIBLE);
//            viewHolder.price.setVisibility(View.VISIBLE);
//            viewHolder.ll.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView iv;
        TextView tv,tv1,tv2;
        TextView title, city, price;
        LinearLayout ll;
        RelativeLayout rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv_id);
            title = (TextView) itemView.findViewById(R.id.tv_id);
            city = (TextView) itemView.findViewById(R.id.tv1_id);
            price = (TextView) itemView.findViewById(R.id.tv2_id);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_id);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_id);

        }
    }
}
