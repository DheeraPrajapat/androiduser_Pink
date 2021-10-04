package com.marius.valeyou.market_place.Drawer.Home.Electronics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marius.valeyou.R;


public class Elec_Search_Adp extends BaseAdapter {

    Context context;
    String[] list;

    public Elec_Search_Adp(Context context, String[] list) {
        this.context = context;
        this.list = list;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.elec_search_item, null);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_id);
        tv.setText(list[position]);

        return convertView;
    }
}
