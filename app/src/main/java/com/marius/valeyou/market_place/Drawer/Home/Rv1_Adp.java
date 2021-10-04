package com.marius.valeyou.market_place.Drawer.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_Section_Posts;
import com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel.Get_Set_home_ads;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Rv1_Adp extends RecyclerView.Adapter<Rv1_Adp.ViewHolder> {

    Integer[] list = {R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder};

    Context context;
    List<Get_Set_Section_Posts> section_posts;
    List<Get_Set_home_ads> ads_posts = new ArrayList<>();

    public click itemclick;
    //private Object click;

    public interface click{
        void onclick(int pos, View view);
    }

    Rv2_Adp adp2;
   public Rv1_Adp(Context context, List<Get_Set_Section_Posts> section_posts, click itemclick){
       this.context = context;
       this.section_posts = section_posts;
       this.itemclick = itemclick;


   } // End Constructer

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ads_posts =  new ArrayList<>();
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_rv1_item, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       Get_Set_Section_Posts posts = section_posts.get(i);
//        Methods.toast_msg(context,"" + posts.getSection_name());
       viewHolder.section_name.setText("" + posts.getSection_name());

       try {
           JSONArray posts_arr = posts.getSection_posts_arr();

           Log.d("print_array", ""+posts_arr);

           for(int p=0;p< posts_arr.length(); p++) {
               try {
                   JSONObject pos_obj = posts_arr.getJSONObject(p);
                   JSONObject post_about = pos_obj.getJSONObject("Post");

                   JSONObject PostTranslation = post_about.getJSONObject("PostTranslation");
                   JSONArray imag = post_about.getJSONArray("PostImage");
                   // Getting first Img
                   imag.getJSONObject(0).getString("image");
                   // Get data About post

                   //PostContact
                   post_about.getString("price");
                   PostTranslation.getString("title");

                   Log.d("resp_title" + i, PostTranslation.getString("title"));
                   Log.d("resp_title" + i, PostTranslation.toString());

                   Get_Set_home_ads ad = new Get_Set_home_ads(
                           "" + post_about.getString("id"),
                           "" + PostTranslation.getString("title"),
                           "" + post_about.getString("price"),
                           "" + post_about.getJSONObject("PostContact").getJSONObject("City").getString("name"),
                           "" + imag.getJSONObject(0).getString("image"),
                           imag
                   );

                   ads_posts.add(ad);

               } catch (Exception b) {
                   Methods.toast_msg(context, "err ad " + b.toString());

                   JSONArray viewd_arr = posts.getSection_posts_arr();
                   try {

                       for (int ii = 0; ii < viewd_arr.length(); ii++) {

                           JSONObject pos_obj = posts_arr.getJSONObject(p);
                           pos_obj.getString("image");
                           pos_obj.getString("price");
                           pos_obj.getString("id");
                           Get_Set_home_ads ad = new Get_Set_home_ads(
                                   "" + pos_obj.getString("id"),
                                   "titl",
                                   "" + pos_obj.getString("price"),
                                   "City",
                                   "" + pos_obj.getString("image"),
                                   viewd_arr
                           );

                           ads_posts.add(ad);
                       } // End For Loop


                   } catch (Exception d) {

                   }

               }
           }
       }catch (Exception b){

       }


       // End for Loop

        adp2 = new Rv2_Adp(context,ads_posts);
        viewHolder.section_posts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        viewHolder.section_posts.setHasFixedSize(false);

        viewHolder.section_posts.setAdapter(adp2);
        viewHolder.itemclick(i,itemclick);

    }

    @Override
    public int getItemCount() {
        return section_posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv,tv1,tv2;
        TextView section_name, view_all ;

        LinearLayout ll;
        RecyclerView section_posts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            iv = (ImageView) itemView.findViewById(R.id.iv_id);
            section_name = (TextView) itemView.findViewById(R.id.tv1_id);
            view_all = (TextView) itemView.findViewById(R.id.tv2_id);
            section_posts = itemView.findViewById(R.id.rv1_id);

//            ll = (LinearLayout) itemView.findViewById(R.id.ll_id);
//            rl = (RelativeLayout) itemView.findViewById(R.id.rl_id);

        }

        public void itemclick(final int pos, final click item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onclick(pos,itemView);
                }
            });
        }



    }
}
