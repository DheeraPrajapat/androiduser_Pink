package com.marius.valeyou.util.decoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class ListVerticalItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SPACE_IN_DP = 16;
    private int space;
    private boolean addtopspace = true;

    public ListVerticalItemDecoration(@NonNull Context context) {
        this.space = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_SPACE_IN_DP,
                context.getResources().getDisplayMetrics()));
    }

    public ListVerticalItemDecoration(@NonNull Context context, @DimenRes int dimenResId) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
    }

    public ListVerticalItemDecoration(@NonNull Context context, @DimenRes int dimenResId, boolean addtopspace) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
        this.addtopspace = addtopspace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if (addtopspace) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }

        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;
    }
}
