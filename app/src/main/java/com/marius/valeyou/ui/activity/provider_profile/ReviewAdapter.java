package com.marius.valeyou.ui.activity.provider_profile;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.ProviderReviewBean;
import com.marius.valeyou.databinding.HolderReviewsBinding;
import com.marius.valeyou.ui.activity.selectcategory.CategoryAdapter;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private List<ProviderReviewBean> providerReviewBeansList;

    private Context baseContext;

    public ReviewAdapter(Context baseContext,List<ProviderReviewBean> providerReviewBeansList) {
        this.baseContext = baseContext;
        this.providerReviewBeansList = providerReviewBeansList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderReviewsBinding holderReviewsBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_reviews, parent, false);

        return new ReviewAdapter.MyViewHolder(holderReviewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.holderReviewsBinding.tvName.setText(providerReviewBeansList.get(position).getUser().getFirstName()+providerReviewBeansList.get(position).getUser().getLastName());
        ImageViewBindingUtils.setProviderImage(holder.holderReviewsBinding.ivImage,providerReviewBeansList.get(position).getUser().getImage());
        holder.holderReviewsBinding.tvDescription.setText(providerReviewBeansList.get(position).getDescription());
        holder.holderReviewsBinding.myRatingBar.setRating(Float.parseFloat(providerReviewBeansList.get(position).getRatings()));
        holder.holderReviewsBinding.tvDate.setText(convertTimeStampToTime(providerReviewBeansList.get(position).getCreatedAt()));



    }

    @Override
    public int getItemCount() {
        return providerReviewBeansList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderReviewsBinding holderReviewsBinding;
        public MyViewHolder(@NonNull HolderReviewsBinding itemView) {
            super(itemView.getRoot());
            holderReviewsBinding = itemView;
        }
    }

    private String convertTimeStampToTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("MMM dd, yyyy ", cal).toString();
        return date;
    }

}
