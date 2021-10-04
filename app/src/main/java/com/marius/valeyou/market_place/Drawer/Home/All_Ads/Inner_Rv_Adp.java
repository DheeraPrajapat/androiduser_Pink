package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Inner_Rv_Adp extends RecyclerView.Adapter<Inner_Rv_Adp.ViewHolder> {

    List<Get_Set_post_img> list_ads_img;
    Context context;
      public Inner_Rv_Adp(Context context, List<Get_Set_post_img> list_ads_img ){
            this.context = context;
            this.list_ads_img = list_ads_img;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_ads_inner_item, null);
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
        Methods.toast_msg(context,"Thumb Url " + API_LINKS.BASE_URL + image_150_thumb);

        try{
            Uri uri = Uri.parse(API_LINKS.BASE_URL + image_150_thumb);
            viewHolder.iv.setImageURI(uri);  // Attachment
            viewHolder.iv.getHierarchy().setPlaceholderImage(context.getResources().getDrawable(R.drawable.placeholder_2));

        }catch (Exception v){

        }




    }

    @Override
    public int getItemCount() {
        return list_ads_img.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView iv;
        SimpleDraweeView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv =  itemView.findViewById(R.id.iv_id);

        }
    }
}
