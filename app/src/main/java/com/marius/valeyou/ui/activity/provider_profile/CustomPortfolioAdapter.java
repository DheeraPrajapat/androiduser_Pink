package com.marius.valeyou.ui.activity.provider_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.ProviderPortfolioBean;
import com.marius.valeyou.databinding.HolderPortfolioItemsBinding;

import java.util.List;

public class CustomPortfolioAdapter  extends RecyclerView.Adapter<CustomPortfolioAdapter.MyViewHolder> {

    Context context;
    boolean isAll;
    ClickListner mcallback;
    List<ProviderPortfolioBean> list;

    public CustomPortfolioAdapter(Context context,boolean isAll,List<ProviderPortfolioBean> list){
        this.context = context;
        this.list = list;
        this.isAll = isAll;
    }

    public interface ClickListner {
        void onItemClick(View v, List<ProviderPortfolioBean> list, int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderPortfolioItemsBinding holderPortfolioItemsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.holder_portfolio_items, parent, false);
        holderPortfolioItemsBinding.setVariable(BR.callback, mcallback);
        return new CustomPortfolioAdapter.MyViewHolder(holderPortfolioItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProviderPortfolioBean portfoliosBean = list.get(position);
        holder.holderPortfolioItemsBinding.setBean(portfoliosBean);
    }

    @Override
    public int getItemCount() {
        if (isAll) {
            return list.size();
        } else {
            if (list.size() < 2) {
                return list.size();
            } else {
                return 2;

            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderPortfolioItemsBinding holderPortfolioItemsBinding;
        public MyViewHolder(@NonNull HolderPortfolioItemsBinding itemView) {
            super(itemView.getRoot());
            holderPortfolioItemsBinding = itemView;
        }
    }
}
