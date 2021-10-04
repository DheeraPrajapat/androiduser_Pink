package com.marius.valeyou.market_place.Drawer.Home;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.Get_Set.Home_get_set;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import java.util.ArrayList;
import java.util.List;

public class Grid_Adp extends BaseAdapter {

    Context context;
    Home_get_set categories;
    List<Home_get_set> Cate_list = new ArrayList<>();
    Integer[] list = {R.drawable.ic_mobiles,R.drawable.ic_electronics,
            R.drawable.ic_furniture,R.drawable.ic_pgflatmates,R.drawable.ic_delivery,
            R.drawable.ic_cars,R.drawable.ic_bikes,R.drawable.ic_jobs,R.drawable.ic_homes,
            R.drawable.ic_pets,R.drawable.ic_education,R.drawable.ic_entertainment,
            R.drawable.ic_matrimonial,R.drawable.ic_community,R.drawable.ic_events,R.drawable.ic_suppliers};

    String[] title = {"Mobiles","Electronics","Furniture","PG/Flatmates","Pickup/Dropoff",
                "Cars","Bikes","Jobs","Homes","Pets","Education","Entertainment","Matrimonial",
                "Community","Events","B2B Suppliers"};

    public click itemclick;

    public interface click{
        void onclick(int pos);
    }

    public Grid_Adp(Context context, List<Home_get_set> Cate_list , click itemclick) {
        this.context = context;
        this.itemclick = itemclick;
        this.Cate_list = Cate_list;
    }

    @Override
    public int getCount() {
        return Cate_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_grid_item, null);

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_id);

        double height = Variables.width/4;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll.getLayoutParams();
        lp.height = (int) height;
        ll.setLayoutParams(lp);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclick.onclick(position);
            }
        });

        final Home_get_set Cate = Cate_list.get(position);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_id);
        TextView tv = (TextView) view.findViewById(R.id.tv_id);

//        Picasso.get()
//                .load(API_LINKS.BASE_URL + Cate.getImage())
//                .placeholder(R.drawable.round)
//                .error(R.drawable.round).
//                into(iv);

        // Usage of Fressco
        try{
            Uri uri = Uri.parse(API_LINKS.BASE_URL + Cate.getImage());
            iv.setImageURI(uri);  // Attachment
            //  viewHolder.prof_photo_id.setImageURI(API_LINKS.BASE_URL + Posts.getUser_img()); // Post Profile Pic
            // viewHolder.comment_prof_photo_id.setImageURI(SharedPrefrence.get_user_image_from_json(context));
        }catch (Exception v){

        }



       // iv.setImageResource(list[5]);
        //String sourceString = "<b> 24,3453 </b> /month on EMI | <b> <font color='blue'> 432,253 </font></b>";
        tv.setText(Html.fromHtml(Cate.getCate_label()));
        //tv.setText(Cate.getCate_name());

        return view;
    }

    }
