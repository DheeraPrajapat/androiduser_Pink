package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_post_img;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Slider_img_adp extends RecyclerView.Adapter<Slider_img_adp.ViewHolder> {

    Context context;
    Integer[] list = {R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder};

    List<Get_Set_post_img> list_ads_img;

    public Slider_img_adp(Context context, List<Get_Set_post_img> list_ads_img ) {
        this.context = context;
        this.list_ads_img = list_ads_img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item_layout, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final Get_Set_post_img Ads_img = list_ads_img.get(i);
        try{
            String image_name_from_url = Methods.getFileNameFromURL (API_LINKS.BASE_URL + Ads_img.getImg_url());
            Methods.toast_msg(context,"File name " + image_name_from_url );

            String image_150_thumb = Ads_img.getImg_url().replace(image_name_from_url ,
                    Variables.Var_thumb_prefix +  image_name_from_url
            );
            Methods.toast_msg(context," File name thumb " + image_150_thumb );
            Methods.toast_msg(context,"Thumb Url " + API_LINKS.BASE_URL + image_150_thumb);

//            try{
//                Uri uri = Uri.parse(API_LINKS.BASE_URL + image_150_thumb);
//                viewHolder.iv.setImageURI(uri);  // Attachment
//                //viewHolder.iv.getHierarchy().setPlaceholderImage(context.getResources().getDrawable(R.drawable.placeholder_2));
//
//            }catch (Exception v){
//                Methods.toast_msg(context,"Error to Load image first");
//            }
//            Uri imgURI = Uri.parse("");
//            viewHolder.iv.setImageURI(imgURI);

             // todo: New Image:
            viewHolder.loading.setVisibility(View.VISIBLE);
            ControllerListener listener = new BaseControllerListener<ImageInfo>() {

                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                    //Action on final image load
                    viewHolder.loading.setVisibility(View.GONE);
                    viewHolder.iv.setBackgroundColor(context.getResources().getColor(R.color.black));
                    Methods.toast_msg(context,"Load");

                }
                @Override
                public void onFailure(String id, Throwable throwable) {
                    //Action on failure
                }

            };




            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(API_LINKS.BASE_URL + Ads_img.getImg_url())
                    .setControllerListener(listener)
                    .build();
            viewHolder.iv.setController(controller);

            Methods.toast_msg(context,"" + API_LINKS.BASE_URL + Ads_img.getImg_url());
            // TODO: Old Image

//            Uri uri = Uri.parse( API_LINKS.BASE_URL + Ads_img.getImg_url());
//            viewHolder.iv.setImageURI(uri);  // Attachment
            //viewHolder.iv.getHierarchy().setPlaceholderImage(context.getResources().getDrawable(R.drawable.placeholder_2));

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
        SimpleDraweeView iv;
        ProgressBar loading;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv_id);
            loading = itemView.findViewById(R.id.loading);
        }
    }


}
