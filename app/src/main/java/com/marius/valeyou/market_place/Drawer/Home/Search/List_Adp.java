package com.marius.valeyou.market_place.Drawer.Home.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marius.valeyou.R;

import java.util.ArrayList;


public class List_Adp extends ArrayAdapter<DataModels> implements View.OnClickListener {

    private ArrayList<DataModels> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txt;
        ImageView img;
    }

    public List_Adp(ArrayList<DataModels> data, Context context) {
        super(context, R.layout.search_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModels dataModel=(DataModels)object;

        switch (v.getId())
        {
            case R.id.ll_id:
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModels dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.search_item, parent, false);
            viewHolder.txt = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.iv_id);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txt.setText(dataModel.getName());
        viewHolder.img.setImageResource(dataModel.getIv());

        return convertView;
    }
}
