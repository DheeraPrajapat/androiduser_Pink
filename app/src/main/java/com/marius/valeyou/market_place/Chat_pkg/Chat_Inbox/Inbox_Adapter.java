package com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox.Inbox_Get_Set.Inbox_Get_Set_List;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class Inbox_Adapter extends RecyclerView.Adapter<Inbox_Adapter.CustomViewHolder > implements Filterable {

    public Context context;
    ArrayList<Inbox_Get_Set_List> inbox_dataList = new ArrayList<>();
    ArrayList<Inbox_Get_Set_List> inbox_dataList_filter = new ArrayList<>();
    private OnItemClickListener listener;
    private OnLongItemClickListener longlistener;

    Integer today_day=0;

    // meker the onitemclick listener interface and this interface is impliment in Chatinbox activity
    // for to do action when user click on item
    public interface OnItemClickListener {
        void onItemClick(Inbox_Get_Set_List item);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(Inbox_Get_Set_List item);
    }

    public Inbox_Adapter(Context context, ArrayList<Inbox_Get_Set_List> user_dataList, OnItemClickListener listener, OnLongItemClickListener longlistener) {
        this.context = context;
        this.inbox_dataList=user_dataList;
        this.inbox_dataList_filter=user_dataList;
        this.listener = listener;
        this.longlistener=longlistener;

        // get the today as a integer number to make the dicision the chat date is today or yesterday
        Calendar cal = Calendar.getInstance();
        today_day = cal.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inbox_list,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return inbox_dataList_filter.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView user_name,last_message,date_created;
        ImageView user_image;
        RelativeLayout mainlayout;

        public CustomViewHolder(View view) {
            super(view);
            this.mainlayout=view.findViewById(R.id.mainlayout);
            this.user_name=view.findViewById(R.id.username);
            this.last_message=view.findViewById(R.id.message);
            this.date_created=view.findViewById(R.id.datetxt);
            this.user_image=view.findViewById(R.id.userimage);

        }

        public void bind(final Inbox_Get_Set_List item, final OnItemClickListener listener,final  OnLongItemClickListener longItemClickListener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            mainlayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longlistener.onLongItemClick(item);
                    return false;
                }
            });
        }

    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int i) {
        final Inbox_Get_Set_List item=inbox_dataList_filter.get(i);

        holder.bind(item,listener,longlistener);
       // ChangeDate(item.getTimestamp())
        holder.bind(item,listener,longlistener);

        String check = Methods.parseDateToddMMyyyy(item.getDate(),context);

        holder.date_created.setText(Methods.get_time_ago_org(check));
        //holder.date_created.setText("" + ChangeDate(item.getTimestamp()));

        holder.last_message.setText(item.getMsg());
        holder.user_name.setText(item.getName());

        try{
            Picasso.get().load(API_LINKS.BASE_URL + item.getPic())
                    .placeholder(R.drawable.ic_profile_gray).
                    resize(100, 100)
                    .into(holder.user_image);

        }catch (Exception b){

        }


    }



    // this method will cahnge the date to  "today", "yesterday" or date
    public String ChangeDate(String date){
        try {
            //current date in millisecond
            long currenttime = System.currentTimeMillis();

            //database date in millisecond
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            long databasedate = 0;
            Date d = null;
            try {
                d = f.parse(date);
                databasedate = d.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            long difference = currenttime - databasedate;
            if (difference < 86400000) {
                int chatday = Integer.parseInt(date.substring(0, 2));
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                if (today_day == chatday)
                    return sdf.format(d);
                else if ((today_day - chatday) == 1)
                    return "Yesterday";
            } else if (difference < 172800000) {
                int chatday = Integer.parseInt(date.substring(0, 2));
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                if ((today_day - chatday) == 1)
                    return "Yesterday";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            return sdf.format(d);
        }catch (Exception e){
            Methods.toast_msg(context,"" + e.toString());
        }finally {
            return "";
        }
    }


    // that function will filter the result
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    inbox_dataList_filter = inbox_dataList;
                } else {
                    ArrayList<Inbox_Get_Set_List> filteredList = new ArrayList<>();
                    for (Inbox_Get_Set_List row : inbox_dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    inbox_dataList_filter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = inbox_dataList_filter;
                return filterResults;

            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                inbox_dataList_filter = (ArrayList<Inbox_Get_Set_List>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




}
