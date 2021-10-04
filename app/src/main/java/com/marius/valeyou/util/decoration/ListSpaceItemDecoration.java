package com.marius.valeyou.util.decoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class ListSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SPACE_IN_DP = 16;
    private int space;

    public ListSpaceItemDecoration(@NonNull Context context) {
        this.space = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_SPACE_IN_DP,
                context.getResources().getDisplayMetrics()));
    }

    public ListSpaceItemDecoration(@NonNull Context context, @DimenRes int dimenResId) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
