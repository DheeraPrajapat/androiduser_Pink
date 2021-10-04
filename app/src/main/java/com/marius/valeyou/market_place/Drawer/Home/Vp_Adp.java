package com.marius.valeyou.market_place.Drawer.Home;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Slider;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Vp_Adp extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] imges;
    List<Get_Set_Slider> Home_Slider;

    public Vp_Adp(Context context, List<Get_Set_Slider> Home_Slider ) {
        this.context = context;
        this.Home_Slider = Home_Slider;
    }


    @Override
    public int getCount() {
        return Home_Slider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(RelativeLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.home_vp_item,container,false);

        final Get_Set_Slider slider = Home_Slider.get(position);

        slider.getImage();

        ImageView IV = view.findViewById(R.id.iv_id);




//        Picasso.get()
//                .load(API_LINKS.BASE_URL + slider.getImage())
//                .placeholder(R.drawable.ic_placeholder)
//                .error(R.drawable.ic_placeholder)
//                .into(IV);


        // Usage of Fressco
        try{
            Uri uri = Uri.parse(API_LINKS.BASE_URL + slider.getImage());
            IV.setImageURI(uri);  // Attachment
            //  viewHolder.prof_photo_id.setImageURI(API_LINKS.BASE_URL + Posts.getUser_img()); // Post Profile Pic
            // viewHolder.comment_prof_photo_id.setImageURI(SharedPrefrence.get_user_image_from_json(context));
        }catch (Exception v){

        }


//        try{
//            Uri uri = Uri.parse(API_LINKS.BASE_URL + slider.getImage());
//            IV.setImageURI(uri);  // Attachment
//            //  viewHolder.prof_photo_id.setImageURI(API_LINKS.BASE_URL + Posts.getUser_img()); // Post Profile Pic
//            // viewHolder.comment_prof_photo_id.setImageURI(SharedPrefrence.get_user_image_from_json(context));
//        }catch (Exception v){
//
//        }

      //  IV.setImageResource(imges[position]);


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }

}
