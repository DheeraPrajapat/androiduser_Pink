package com.marius.valeyou.util.databinding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class ViewBindingUtils {
    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleHidden")
    public static void visibleHidden(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("onFocusChange")
    public static void onFocusChange(EditText text, final View.OnFocusChangeListener listener) {
        text.setOnFocusChangeListener(listener);
    }

    @BindingAdapter("fadeVisible")
    public static void setFadeVisible(final View view, boolean visible) {
        view.animate().cancel();
        if (visible) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0);
            view.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setAlpha(1);
                }
            });
        } else {

            view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setAlpha(1);
                    view.setVisibility(View.GONE);
                }
            });
        }
    }


    @BindingAdapter("changetextfade")
    public static void setFadeVisible(final TextView view, final String text) {
        if (text == null)
            return;
        view.animate().cancel();
        view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setText(text);
                view.animate().alpha(1);
            }
        });

    }


    @BindingAdapter("amount")
    public static void amount(final TextView view, final double amount) {
        try {
            NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
            view.setText(us.format(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
