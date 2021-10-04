package com.marius.valeyou.ui.fragment;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;

import com.marius.valeyou.databinding.HolderNewCategoryLayoutBinding;
import com.marius.valeyou.di.module.GlideApp;
import com.marius.valeyou.util.Constants;


import java.util.List;

public class HomePageCategoryAdapter extends RecyclerView.Adapter<HomePageCategoryAdapter.MyViewHolder> {

    private List<CategoryListBean> moreBeans;
    private HomePageCategoryAdapter.ProductCallback mcallback;
    private Context baseContext;

    public HomePageCategoryAdapter(Context baseContext, HomePageCategoryAdapter.ProductCallback callback, List<CategoryListBean> moreBeans) {
        this.baseContext = baseContext;
        this.mcallback = callback;
        this.moreBeans = moreBeans;
    }

    public interface ProductCallback {
        void onItemClick(View v, List<CategoryListBean> moreBeans,int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderNewCategoryLayoutBinding holderNewCategoryLayoutBinding =   DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_new_category_layout, parent, false);

        holderNewCategoryLayoutBinding.setVariable(BR.callback, mcallback);
        return new HomePageCategoryAdapter.MyViewHolder(holderNewCategoryLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryListBean moreBean = moreBeans.get(position);
        holder.holderNewCategoryLayoutBinding.setBean(moreBean);

        holder.holderNewCategoryLayoutBinding.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.onItemClick(holder.holderNewCategoryLayoutBinding.mainLayout,moreBeans,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (moreBeans != null) {
            return moreBeans.size();
        } else {
            return 0;
        }
    }

    public void setProductList(List<CategoryListBean> moreBeans) {
        this.moreBeans = moreBeans;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderNewCategoryLayoutBinding holderNewCategoryLayoutBinding;
        public MyViewHolder(@NonNull HolderNewCategoryLayoutBinding itemView) {
            super(itemView.getRoot());
            holderNewCategoryLayoutBinding = itemView;

        }
    }
}
