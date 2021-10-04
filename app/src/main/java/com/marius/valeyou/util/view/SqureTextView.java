package com.marius.valeyou.util.view;

import android.content.Context;
import android.util.AttributeSet;

public class SqureTextView extends androidx.appcompat.widget.AppCompatTextView {
    public SqureTextView(Context context) {
        super(context);
    }

    public SqureTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec)
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        else
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
