package com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Adapters.Msg_adapter;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_GetSet;

import androidx.recyclerview.widget.RecyclerView;

public class Chatimageviewholder extends RecyclerView.ViewHolder {

    public ImageView chatimage;
    public TextView datetxt,message_seen;
    public ProgressBar p_bar;
    public ImageView not_send_message_icon;
    public View view;


    public ImageView user_image;
    public TextView user_name,time_txt;
    public LinearLayout upperlayout;

    public Chatimageviewholder(View itemView) {
        super(itemView);
        view = itemView;

        this.chatimage = view.findViewById(R.id.chatimage);
        this.datetxt=view.findViewById(R.id.datetxt);
        message_seen=view.findViewById(R.id.message_seen);
        not_send_message_icon=view.findViewById(R.id.not_send_messsage);
        p_bar=view.findViewById(R.id.p_bar);


       // user_name=view.findViewById(R.id.user_name);
        this.upperlayout=view.findViewById(R.id.upperlayout);


    }

    public void bind(final Chat_GetSet item, final Msg_adapter.OnItemClickListener listener, final Msg_adapter.OnLongClickListener long_listener) {

        chatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item,v);
            }
        });

        chatimage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                long_listener.onLongclick(item,v);
                return false;
            }
        });
    }


}
