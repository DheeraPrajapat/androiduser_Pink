package com.marius.valeyou.ui.activity.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.databinding.HolderNotificationItemBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    List<GetNotificationList> notificationModelsList;
    public Listner listner;

    public interface Listner {
        void onItemClick(View v, int position, GetNotificationList model);
    }

    public NotificationAdapter(Context context, List<GetNotificationList> notificationModelsList, Listner listner){
        this.context =context;
        this.notificationModelsList = notificationModelsList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderNotificationItemBinding holderNotificationItemBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_notification_item, parent, false);
        holderNotificationItemBinding.setVariable(BR.callback, listner);

        return new NotificationAdapter.MyViewHolder(holderNotificationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetNotificationList moreBean = notificationModelsList.get(position);
        holder.holderNotificationItemBinding.setBean(moreBean);
    }

    @Override
    public int getItemCount() {
        return notificationModelsList.size();
    }

    public void removeAt(int position) {
        notificationModelsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notificationModelsList.size());
    }

    public void notifyList(int pos){
        notifyItemChanged(pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderNotificationItemBinding holderNotificationItemBinding;
        public MyViewHolder(@NonNull HolderNotificationItemBinding itemView) {
            super(itemView.getRoot());
            holderNotificationItemBinding = itemView;

            holderNotificationItemBinding.suggestTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listner.onItemClick(view,getAdapterPosition(),notificationModelsList.get(getAdapterPosition()));

                }
            });


            holderNotificationItemBinding.hireBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listner.onItemClick(view,getAdapterPosition(),notificationModelsList.get(getAdapterPosition()));

                }
            });

            holderNotificationItemBinding.yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onItemClick(view,getAdapterPosition(),notificationModelsList.get(getAdapterPosition()));
                }
            });

            holderNotificationItemBinding.tvRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onItemClick(view,getAdapterPosition(),notificationModelsList.get(getAdapterPosition()));
                }
            });

            holderNotificationItemBinding.cvItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listner.onItemClick(view,getAdapterPosition(),notificationModelsList.get(getAdapterPosition()));

                }
            });


        }
    }
}
