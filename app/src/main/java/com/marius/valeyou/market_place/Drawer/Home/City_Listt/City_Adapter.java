package com.marius.valeyou.market_place.Drawer.Home.City_Listt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marius.valeyou.R;

import java.util.List;


public class City_Adapter  extends ArrayAdapter<City_Get_Set> {

    private final Context context;
   // private final String[] values;
    List<City_Get_Set> City_list;
    public City_Adapter(Context context, List<City_Get_Set> City_list ) {
        super(context, -1, City_list);
        this.context = context;
        this.City_list = City_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.city_loc_item, parent, false);
        TextView city_name = (TextView) rowView.findViewById(R.id.tv_id);

        City_Get_Set city = City_list.get(position);
        city.getCity_name();
        city_name.setText("" + city.getCity_name());





        return rowView;
    }

}
