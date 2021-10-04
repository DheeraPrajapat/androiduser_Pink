package com.marius.valeyou.localMarketModel.ui.fragment.message.chatview;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.chat.MessagesModel;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou.util.glide.CircleImageView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessagingAdapter extends RecyclerView.Adapter{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<MessagesModel>list;
    int login_id;
    String myImage;
    int i_block,he_block;

    public MessagingAdapter(Context context, List<MessagesModel>list, int login_id, String myImage, int i_block, int he_block){
        this.context = context;
        this.list = list;
        this.login_id = login_id;
        this.myImage = myImage;
        this.i_block = i_block;
        this.he_block =he_block;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_message_sent, parent, false);
            return new SentMessageHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_message_recive, parent, false);
            return new ReceivedMessageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MSG_TYPE_RIGHT:
                ((SentMessageHolder) holder).bind(list.get(position));
                break;
            case MSG_TYPE_LEFT:
                ((ReceivedMessageHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessagesModel message = list.get(position);
        if (message.getUserId()==login_id) {
            return MSG_TYPE_RIGHT ;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public void sendNewMessage(MessagesModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    public void updateList(List<MessagesModel> model){
        list.addAll(model);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,txtTime;
        CircleImageView profileImage;
        ImageView ivImage;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            txtTime=  itemView.findViewById(R.id.timetxt);
            profileImage = itemView.findViewById(R.id.profile);
            ivImage = itemView.findViewById(R.id.iv_image);

        }
        void bind(final MessagesModel message) {

            Log.d("message","=="+message.getMessage());

            if (message.getMsgType()==0) {
                messageText.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.GONE);
                messageText.setText(message.getMessage());
            }else{
                messageText.setVisibility(View.GONE);
                ivImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(Constants.IMAGE_BASE_URL+message.getMessage()).placeholder(R.drawable.image_placeholder).into(ivImage);
            }
            txtTime.setText(convertTimeStampIntoDateTime(message.getCreatedAt()));

            ImageViewBindingUtils.setProfileUrl(profileImage, myImage);




        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,txtTime;
        CircleImageView otherProfile;
        ImageView iv_image;
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText =  itemView.findViewById(R.id.text_message_body);
            txtTime=  itemView.findViewById(R.id.timetxt);
            otherProfile = itemView.findViewById(R.id.other_profile);
            iv_image = itemView.findViewById(R.id.iv_image);


        }
     void bind(MessagesModel message) {

         if (message.getMsgType()==0) {
             messageText.setVisibility(View.VISIBLE);
             iv_image.setVisibility(View.GONE);
             messageText.setText(message.getMessage());
         }else{
             messageText.setVisibility(View.GONE);
             iv_image.setVisibility(View.VISIBLE);
             Glide.with(context).load(Constants.IMAGE_BASE_URL+message.getMessage()).placeholder(R.drawable.image_placeholder).into(iv_image);
         }
            txtTime.setText(convertTimeStampIntoDateTime(message.getCreatedAt()));
            ImageViewBindingUtils.setProfileUrl(otherProfile,message.getUser_image());
        }
    }

    private String convertTimeStampIntoDateTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp*1000L);
        String date = DateFormat.format("MMM dd, hh:mm a", cal).toString();

        return date;
    }

}
