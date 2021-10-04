package com.marius.valeyou.util.databinding;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ViewListionerUtils {


    @BindingAdapter("onrefresh")
    public static void swiperefresh(SwipeRefreshLayout layout, SwipeRefreshLayout.OnRefreshListener listener) {
        layout.setOnRefreshListener(listener);
    }
}

