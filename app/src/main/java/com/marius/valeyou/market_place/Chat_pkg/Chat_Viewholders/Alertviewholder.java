package com.marius.valeyou.market_place.Chat_pkg.Chat_Viewholders;

import android.view.View;
import android.widget.TextView;

import com.marius.valeyou.R;

import androidx.recyclerview.widget.RecyclerView;

public class Alertviewholder extends RecyclerView.ViewHolder {

    public TextView message,datetxt;
    public View view;
    public Alertviewholder(View itemView) {
        super(itemView);
        view = itemView;
        this.message = view.findViewById(R.id.message);
        this.datetxt = view.findViewById(R.id.datetxt);
    }

}
