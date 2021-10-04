package com.marius.valeyou.localMarketModel.ui;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.databinding.HolderSelectGalleryImageBinding;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narad on 26-05-2021 at 04:49 PM.
 */
public class SelectGalleryImageAdapter extends RecyclerView.Adapter<SelectGalleryImageAdapter.MyViewHolder> {


    private final SimpleCallback callback;
    private final List<MoreBean> dataList = new ArrayList<>();

    public SelectGalleryImageAdapter(SimpleCallback callback) {
        this.callback = callback;
    }

    public interface SimpleCallback {
        void onItemClick(View v, MoreBean m);
    }

    public void setList(@Nullable List<MoreBean> newDataList) {
        dataList.clear();
        if (newDataList != null)
            dataList.addAll(newDataList);
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        HolderSelectGalleryImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.holder_select_gallery_image, parent, false);
        binding.setVariable(com.marius.valeyou.BR.callback, callback);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        MoreBean bean = dataList.get(position);
        holder.binding.setVariable(BR.bean, bean);
        holder.binding.executePendingBindings();

        if (bean.getImage() == 1) {
            holder.binding.imgCamera.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgCamera.setVisibility(View.GONE);
            holder.binding.imgGallery.setImageURI(Uri.parse(bean.getName()));
//            ImageViewBindingUtils.setImagePathUrl(holder.binding.imgGallery, bean.getName());
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        HolderSelectGalleryImageBinding binding;

        public MyViewHolder(HolderSelectGalleryImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
