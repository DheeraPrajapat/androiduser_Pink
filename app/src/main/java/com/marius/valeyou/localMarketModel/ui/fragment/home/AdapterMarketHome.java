package com.marius.valeyou.localMarketModel.ui.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.databinding.HolderMarketHomeListBinding;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.ui.GoogleAds;
import com.marius.valeyou.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterMarketHome extends RecyclerView.Adapter<AdapterMarketHome.MyHolder> {

    private ClickCallback callback;
    private List<MarketPostModel> dataList = new ArrayList<>();
    GoogleAds googleAds = new GoogleAds();
    boolean isShowAd = true;

    public AdapterMarketHome(ClickCallback callback) {
        this.callback = callback;
    }

    public AdapterMarketHome(boolean isShowAd, ClickCallback callback) {
        this.callback = callback;
        this.isShowAd = isShowAd;
    }


    public void setList(@Nullable List<MarketPostModel> newDataList) {
        dataList.clear();
        if (newDataList != null)
            dataList.addAll(newDataList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderMarketHomeListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.holder_market_home_list, parent, false);
        binding.setVariable(BR.callback, callback);
        return new AdapterMarketHome.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.setVariable(BR.bean, dataList.get(position));
        holder.binding.executePendingBindings();

        if(dataList.get(position).getTitle()!=null){
            String title= AppUtils.capitalize(dataList.get(position).getTitle());
            holder.binding.tvTitle.setText(title);
        }
        if(dataList.get(position).getProduct_type()!=null){
            String type= AppUtils.capitalize(dataList.get(position).getProduct_type());
            holder.binding.txtType.setText(type);
        }
        if(dataList.get(position).getDescription()!=null){
            String desc= AppUtils.capitalize(dataList.get(position).getTag());
            holder.binding.txtDesc.setText(desc);
        }
        if ((position == 2 || (position > 3 && (position % 4) == 0)) && isShowAd) {
            googleAds.loadBannerAds(holder.binding.adView);
        }

    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }


    public interface ClickCallback {
        void onItemClick(View v, MarketPostModel m);
    }


    class MyHolder extends RecyclerView.ViewHolder {
        HolderMarketHomeListBinding binding;
         public MyHolder(@NonNull HolderMarketHomeListBinding mBinding) {
            super(mBinding.getRoot());

            binding = mBinding;
        }
    }
}
