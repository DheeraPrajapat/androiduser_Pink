package com.marius.valeyou.market_place.Chat_pkg.Adapters;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Chat;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders.Alertviewholder;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders.ChatVideoviewholder;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders.Chatimageviewholder;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders.Chatviewholder;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_GetSet;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class Msg_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Chat_GetSet> mDataSet;
    String myID;
    private static final int mychat = 1;

    private static final int friendchat = 2;

    private static final int mychatimage = 3;
    private static final int otherchatimage=  4;

    private static final int mygifimage = 5;
    private static final int othergifimage = 6;

    private static final int alert_message = 7;


    private static final int my_video_message = 10;
    private static final int other_video_message = 11;


    Context context;
    Integer today_day=0;

    private OnItemClickListener listener;
    private OnLongClickListener long_listener;

    public interface OnItemClickListener {
        void onItemClick(Chat_GetSet item, View view);
    }

    public interface OnLongClickListener {
        void onLongclick(Chat_GetSet item, View view);
    }

    /**
     * Called when a view has been clicked.
     *
//     * @param dataSet Message list
//     *      Device id
//     */



//    @Override
//    public int getItemViewType(int position){
//        Msg_list.get(position).getMsg()
//    }
    public Msg_adapter(List<Chat_GetSet> dataSet, String id, Context context, OnItemClickListener listener, OnLongClickListener long_listener) {
        mDataSet = dataSet;
        this.myID=id;
        this.context=context;
        this.listener = listener;
        this.long_listener=long_listener;
        Calendar cal = Calendar.getInstance();
        today_day = cal.get(Calendar.DAY_OF_MONTH);
    }


    // this is the all types of view that is used in the chat
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View v = null;

        switch (viewtype){
             // we have 4 type of layout in chat activity text chat of my and other and also
            // image layout of my and other
            case mychat:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_my, viewGroup, false);
                Chatviewholder mychatHolder = new Chatviewholder(v);
                return mychatHolder;
            case friendchat:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_other, viewGroup, false);
                Chatviewholder friendchatHolder = new Chatviewholder(v);
                return friendchatHolder;
            case mychatimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_my, viewGroup, false);
                Chatimageviewholder mychatimageHolder = new Chatimageviewholder(v);
                return mychatimageHolder;
            case otherchatimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_other, viewGroup, false);
                Chatimageviewholder otherchatimageHolder = new Chatimageviewholder(v);
                return otherchatimageHolder;



            case mygifimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_gif_my, viewGroup, false);
                Chatimageviewholder mychatgigHolder = new Chatimageviewholder(v);
                return mychatgigHolder;

            case othergifimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_gif_other, viewGroup, false);
                Chatimageviewholder otherchatgifHolder = new Chatimageviewholder(v);
                return otherchatgifHolder;

            case my_video_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_video_my, viewGroup, false);
                ChatVideoviewholder mychatVideoviewholder = new ChatVideoviewholder(v);
                return mychatVideoviewholder;

            case other_video_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_video_other, viewGroup, false);
                ChatVideoviewholder otherchatVideoviewholder = new ChatVideoviewholder(v);
                return otherchatVideoviewholder;


            case alert_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_alert, viewGroup, false);
                Alertviewholder alertviewholder = new Alertviewholder(v);
                return alertviewholder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat_GetSet chat = mDataSet.get(position);
        holder.setIsRecyclable(false);

        Log.d("resp","" + position);

        if(chat.getType().equals("text")){
            Chatviewholder chatviewholder=(Chatviewholder) holder;
            // check if the message is from sender or receiver
            // changeDrawableColor
            if(chat.getSender_id().equals(myID)){
                Methods.getRoundRect();
                chatviewholder.message.setBackground(Methods.getRoundRect());
                if(chat.getStatus().equals("1"))
                    chatviewholder.message_seen.setText(" Seen at "+ChangeDate_to_time(chat.getTime()));

                else
                    chatviewholder.message_seen.setText("Sent");
            }else {

                chatviewholder.message_seen.setText("");
            }

            chatviewholder.message.setText(chat.getText());
            chatviewholder.msg_date.setText(Show_Message_Time(chat.getTimestamp()));

            // make the group of message by date set the gap of 1 min
            // means message send with in 1 min will show as a group
            if (position != 0) {
                Chat_GetSet chat2 = mDataSet.get(position - 1);
                if (chat2.getTimestamp().substring(0, 2).equals(chat.getTimestamp().substring(0, 2))) {
                    chatviewholder.datetxt.setVisibility(View.GONE);
                } else {
                    chatviewholder.datetxt.setVisibility(View.VISIBLE);
                    chatviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                }

            }else {
                chatviewholder.datetxt.setVisibility(View.VISIBLE);
                chatviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
            }

            chatviewholder.bind(chat,long_listener);

        }

        else if(chat.getType().equals("image")){

            final Chatimageviewholder chatimageholder=(Chatimageviewholder) holder;
            // check if the message is from sender or receiver
            if(chat.getSender_id().equals(myID)){
                if(chat.getStatus().equals("1"))
                    chatimageholder.message_seen.setText("Seen at " + ChangeDate_to_time(chat.getTime()));
                else
                    chatimageholder.message_seen.setText("Sent");

            }else {
                chatimageholder.message_seen.setText("" + ChangeDate_to_time(chat.getTime()));
            }

            if(chat.getPic_url().equals("none")){
                if(Chat.uploading_image_id.equals(chat.getChat_id())){
                    chatimageholder.p_bar.setVisibility(View.VISIBLE);
                    chatimageholder.message_seen.setText("");
                }else {
                    chatimageholder.p_bar.setVisibility(View.GONE);
                    chatimageholder.not_send_message_icon.setVisibility(View.VISIBLE);
                    chatimageholder.message_seen.setText("Not delivered. This is bug");
                }
            }else {
                chatimageholder.not_send_message_icon.setVisibility(View.GONE);
                chatimageholder.p_bar.setVisibility(View.GONE);
            }

            // make the group of message by date set the gap of 1 min
            // means message send with in 1 min will show as a group
            if (position != 0) {
                Chat_GetSet chat2 = mDataSet.get(position - 1);
                if (chat2.getTimestamp().substring(0, 2).equals(chat.getTimestamp().substring(0, 2))) {
                    chatimageholder.datetxt.setVisibility(View.GONE);
                } else {
                    chatimageholder.datetxt.setVisibility(View.VISIBLE);
                    chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                }
                try{
                    Picasso.get().load(chat.getPic_url()).placeholder(R.drawable.image_placeholder).resize(400,400).centerCrop().into(chatimageholder.chatimage);
                }catch (Exception n){

                }

            }else {
                chatimageholder.datetxt.setVisibility(View.GONE);
                chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()) + " Bug ");
                try{
                    Picasso.get().load(chat.getPic_url()).placeholder(R.drawable.image_placeholder).resize(400,400).centerCrop().into(chatimageholder.chatimage);
                }catch (Exception b){
//
                     }


    }


            chatimageholder.bind(mDataSet.get(position),listener,long_listener);
        }


        else if(chat.getType().equals("video")){

            final ChatVideoviewholder viewholder=(ChatVideoviewholder) holder;

            // check if the message is from sender or receiver
            if(chat.getSender_id().equals(myID)){
                if(chat.getStatus().equals("1"))
                    viewholder.message_seen.setText("Seen at "+ChangeDate_to_time(chat.getTime()));
                else
                    viewholder.message_seen.setText("Sent");

            }else {
                viewholder.message_seen.setText("");
            }


//            String downloadid= Chat.download_pref.getString(chat.getChat_id(),"");
//            if(!downloadid.equals("")){
//                String status= Methods.Check_Image_Status(context, Long.parseLong(downloadid));
//                if(status.equals("STATUS_FAILED") || status.equals("STATUS_SUCCESSFUL")){
//                    viewholder.p_bar.setVisibility(View.GONE);
//                    Chat.download_pref.edit().remove(chat.getChat_id()).commit();
//                }
//                else{
//                    viewholder.p_bar.setVisibility(View.VISIBLE);
//                }
//            }
//            else {
//                viewholder.p_bar.setVisibility(View.GONE);
//            }



            if (position != 0) {
                Chat_GetSet chat2 = mDataSet.get(position - 1);
                if (chat2.getTimestamp().substring(14, 16).equals(chat.getTimestamp().substring(14, 16))) {
                    viewholder.datetxt.setVisibility(View.GONE);
                } else {
                    viewholder.datetxt.setVisibility(View.VISIBLE);
                    viewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                }

            }else {
                viewholder.datetxt.setVisibility(View.VISIBLE);
                viewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
            }


//            File fullpath = new File(Environment.getExternalStorageDirectory() +"/Chatbuzz/"+chat.chat_id+".mp4");
//            if(fullpath.exists()) {
//                Glide.with( context )
//                        .load(Uri.fromFile(fullpath) )
//                        .into(viewholder.chatimage);
//            }else {
//                ColorDrawable drawable= new ColorDrawable(context.getResources().getColor(R.color.black));
//                Glide.with( context )
//                        .load(drawable)
//                        .into(viewholder.chatimage);
//            }

            viewholder.bind(mDataSet.get(position),listener,long_listener);

        }


        else if(chat.getType().equals("gif")){
//            final Chatimageviewholder chatimageholder=(Chatimageviewholder) holder;
//            // check if the message is from sender or receiver
//            if(chat.getSender_id().equals(myID)){
//                if(chat.getStatus().equals("1"))
//                    chatimageholder.message_seen.setText("Seen at "+ChangeDate_to_time(chat.getTime()));
//                else
//                    chatimageholder.message_seen.setText("Sent");
//
//            }else {
//                chatimageholder.message_seen.setText("");
//            }
//            // make the group of message by date set the gap of 1 min
//            // means message send with in 1 min will show as a group
//            if (position != 0) {
//                Chat_GetSet chat2 = mDataSet.get(position - 1);
//                if (chat2.getTimestamp().substring(0, 2).equals(chat.getTimestamp().substring(0, 2))) {
//                    chatimageholder.datetxt.setVisibility(View.GONE);
//                } else {
//                    chatimageholder.datetxt.setVisibility(View.VISIBLE);
//                    chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
//                }
//                Glide.with(context)
//                        .load(Variables.gif_firstpart_chat+chat.getPic_url()+Variables.gif_secondpart_chat)
//                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                        .into(chatimageholder.chatimage);
//            }else {
//                chatimageholder.datetxt.setVisibility(View.VISIBLE);
//                chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
//                Glide.with(context)
//                        .load(Variables.gif_firstpart_chat+chat.getPic_url()+Variables.gif_secondpart_chat)
//                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                        .into(chatimageholder.chatimage);
//
//            }
//
//            chatimageholder.bind(mDataSet.get(position),listener,long_listener);
        }


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    @Override
    public int getItemViewType(int position) {
        // get the type it view ( given message is from sender or receiver)
        if(mDataSet.get(position).getType().equals("text")){
            if (mDataSet.get(position).getSender_id().equals(myID)) {
                return mychat;
            }
            return friendchat;
        }

        else if(mDataSet.get(position).getType().equals("image") ) {

            if (mDataSet.get(position).getSender_id().equals(myID)) {
                return mychatimage;
            }
            return otherchatimage;
        }

        else if(mDataSet.get(position).getType().equals("video") ) {
            if (mDataSet.get(position).getSender_id().equals(myID)) {
                return my_video_message;
            }

            return other_video_message;
        }



        else if(mDataSet.get(position).getType().equals("gif")) {
            if (mDataSet.get(position).getSender_id().equals(myID)) {
                return mygifimage;
            }

            return othergifimage;
        }
        else {
            return alert_message;
        }
    }

    /**
     * Inner Class for a recycler view
     */


    // change the date into (today ,yesterday and date)
    public String ChangeDate(String date){
        //current date in millisecond
        long currenttime= System.currentTimeMillis();

        //database date in millisecond
        long databasedate = 0;
        Date d = null;
        try {
            d = Variables.df.parse(date);
            databasedate = d.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference=currenttime-databasedate;
        if(difference<86400000){
            int chatday= Integer.parseInt(date.substring(0,2));
            if(today_day==chatday)
                return "Today";
            else if((today_day-chatday)==1)
                return "Yesterday";
        }
        else if(difference<172800000){
            int chatday= Integer.parseInt(date.substring(0,2));
            if((today_day-chatday)==1)
                return "Yesterday";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");

        if(d!=null)
            return sdf.format(d);
        else
            return "";
    }


    public String Show_Message_Time(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = Variables.df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(d!=null)
            return sdf.format(d);
        else
            return "null";
    }



    // change the date into (today ,yesterday and date)
    public String ChangeDate_to_time(String date){


        Date d = null;
        try {
            d = Variables.df2.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

        if(d!=null)
            return sdf.format(d);
        else
            return "";
    }



    // get the audio file duration that is store in our directory
    public String getfileduration(Uri uri) {
        try {

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(context, uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            final int file_duration = Integer.parseInt(durationStr);

            long second = (file_duration / 1000) % 60;
            long minute = (file_duration / (1000 * 60)) % 60;

            return String.format("%02d:%02d", minute, second);
        }
        catch (Exception e){

        }
        return null;
    }






}
