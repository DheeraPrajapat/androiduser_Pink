package com.marius.valeyou.market_place.Drawer.Msgs_Notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marius.valeyou.R;

public class Dialog_Listview_Adp extends BaseAdapter {

    Context ctx;
    String[] list;

    public Dialog_Listview_Adp(Context ctx, String[] list) {
        this.list = list;
        this.ctx = ctx;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(ctx).inflate(R.layout.dialog_lv_item, null);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_id);
        tv.setText(list[position]);

        return convertView;
    }
}
