package com.marius.valeyou.util.decoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SPACE_IN_DP = 10;
    private int space;
    @NonNull
    private final int column;

    public GridSpaceItemDecoration(@NonNull Context context, int column) {
        this.space = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_SPACE_IN_DP,
                context.getResources().getDisplayMetrics()));
        this.column = column;
    }

    public GridSpaceItemDecoration(@NonNull Context context, int column, @DimenRes int dimenResId) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
        this.column = column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view) + 1;

        if (position <= column) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }

        if(position % column == 1){
            outRect.left = space;
        } else {
            outRect.left = 0;
        }

        outRect.right = space;
        outRect.bottom = space;
    }
}
