package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Post_Rv1_Adp extends RecyclerView.Adapter<Post_Rv1_Adp.ViewHolder> {

    Context context;
    Integer[] list = {R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder};

    List<Get_Set_post_img> list_ads_img;

    public Post_Rv1_Adp(Context context, List<Get_Set_post_img> list_ads_img ) {
        this.context = context;
        this.list_ads_img = list_ads_img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Get_Set_post_img Ads_img = list_ads_img.get(i);

        String image_name_from_url = Methods.getFileNameFromURL (API_LINKS.BASE_URL + Ads_img.getImg_url());
        Methods.toast_msg(context,"File name " + image_name_from_url );

        String image_150_thumb = Ads_img.getImg_url().replace(image_name_from_url ,
                Variables.Var_thumb_prefix +  image_name_from_url
        );
        Methods.toast_msg(context," File name thumb " + image_150_thumb );

        try{
            Uri uri = Uri.parse(API_LINKS.BASE_URL + image_150_thumb);
            viewHolder.iv.setImageURI(uri);  // Attachment

            //  viewHolder.prof_photo_id.setImageURI(API_LINKS.BASE_URL + Posts.getUser_img()); // Post Profile Pic
            // viewHolder.comment_prof_photo_id.setImageURI(SharedPrefrence.get_user_image_from_json(context));
        }catch (Exception v){

        }


       // viewHolder.iv.setImageResource(list[i]);
    }

    @Override
    public int getItemCount() {
        return list_ads_img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv_id);

        }
    }

}
