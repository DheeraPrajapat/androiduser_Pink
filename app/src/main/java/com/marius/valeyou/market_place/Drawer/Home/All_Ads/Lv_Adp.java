package com.marius.valeyou.market_place.Drawer.Home.All_Ads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marius.valeyou.R;


public class Lv_Adp extends BaseAdapter {

    Context context;
    String[] list = {"Title 1","Title 2","Title 3","Title 4","Title 5","Title 6","Title 7","Title 8",
                "Title 9","Title 10","Title 11","Title 12"};

    public Lv_Adp(Context context) {
        this.context = context;
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_item, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_id);
        tv.setText(list[position]);

        return convertView;
    }
}
