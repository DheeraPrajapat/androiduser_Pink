package com.marius.valeyou.util.decoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SPACE_IN_DP = 10;
    private int space;

    public GridItemDecoration(@NonNull Context context) {
        this.space = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_SPACE_IN_DP,
                context.getResources().getDisplayMetrics()));
    }

    public GridItemDecoration(@NonNull Context context, @DimenRes int dimenResId) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
