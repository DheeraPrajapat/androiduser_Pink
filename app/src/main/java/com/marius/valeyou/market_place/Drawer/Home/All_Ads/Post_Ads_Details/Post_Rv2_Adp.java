package com.marius.valeyou.market_place.Drawer.Home.All_Ads.Post_Ads_Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Post_Rv2_Adp extends RecyclerView.Adapter<Post_Rv2_Adp.ViewHolder> {

    Context context;
    Integer[] list = {R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder,R.drawable.image_placeholder,
            R.drawable.image_placeholder,R.drawable.image_placeholder};

    public Post_Rv2_Adp(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_rv1_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        double height = Variables.width/4;

        viewHolder.iv.getLayoutParams().height = (int) (height*1.2);
        viewHolder.iv.getLayoutParams().width = (int) (height*1.5);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) viewHolder.rl.getLayoutParams();
        lp1.width = (int) (height*1.5);

        viewHolder.rl.setLayoutParams(lp1);

        viewHolder.iv.setImageResource(list[i]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        RelativeLayout rl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_id);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_id);
        }
    }
}
