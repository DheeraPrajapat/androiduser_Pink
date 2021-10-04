package com.marius.valeyou.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.marius.valeyou.R;

public class CustomMarkerInfoWindowView implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;
    private Context context;
    String name;

    public CustomMarkerInfoWindowView(Context context,String name){
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.context = context;
        this.name = name;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.info_window, null);
        TextView nametxt = v.findViewById(R.id.tvname);
        nametxt.setText(name);

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
