package com.marius.valeyou.localMarketModel.ui.fragment.myShop;

import android.annotation.SuppressLint;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.marius.valeyou.R;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.glide.CircleImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyShopAdapter extends RecyclerView.Adapter<MyShopAdapter.MyViewHolder> {

    private MyShopAdapter.OnItemClickListener onItemClickListener;
    List<ShopProfile> list;
    MyShopFragment myShopFragment;
    com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator dotsIndicator;
    String selected_tab;

    //    BannerPagerAdapter bannerPagerAdapter;
    public interface OnItemClickListener {
        void onItemClick(int item, String type);
    }

    public MyShopAdapter(MyShopFragment myShopFragment,
                         List<ShopProfile> list,
                         MyShopAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.myShopFragment = myShopFragment;
        this.list = list;
    }

    @NotNull
    @Override
    public MyShopAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_my_shop, parent, false);
        return new MyShopAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MyShopAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.llPost.setOnClickListener(v -> onItemClickListener.onItemClick(position, "post"));
        if(list.get(position).getCompanyName()!=null){
            String name= AppUtils.capitalize(list.get(position).getCompanyName());
            holder.title.setText(myShopFragment.getResources().getString(R.string.company_name) + " " +name );
        }
        holder.tvPhone.setText(myShopFragment.getResources().getString(R.string.phone_num) + " " + list.get(position).getPhone());

        if(list.get(position).getMarketShopImages().size()>0) {
            Glide.with(myShopFragment).load(Constants.IMAGE_BASE_URL + list.get(position).getMarketShopImages().get(0).getImage()).placeholder(R.drawable.image_placeholder).into(holder.ivCompany);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout llPost;
        TextView title;
        TextView tvPhone;
ImageView ivCompany;
        MyViewHolder(View itemView) {
            super(itemView);
            llPost = itemView.findViewById(R.id.llPost);
            title = itemView.findViewById(R.id.title);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivCompany = itemView.findViewById(R.id.ivCompany);
        }
    }

}

