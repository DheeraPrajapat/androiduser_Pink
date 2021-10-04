/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.marius.valeyou.util.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marius.valeyou.R;

public class MessageUtils {
    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#D50000");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#08aad2");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");

    private static boolean tintIcon = false;

    public static void showSnackbar(View view, String message) {
        if (view == null || message == null) {
            return;
        }

        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Show message using toast
     *
     * @param context
     * @param message
     */
    public static void normal(Context context, String message) {
        if (context != null && message != null) {
            Toast t = custom(context, message, null, Toast.LENGTH_SHORT, false);
            if (t != null)
                t.show();
        }

    }

    public static void success(@NonNull Context context, @NonNull String message) {
        Toast t = custom(context, message, getDrawable(context, R.drawable.ic_checked), SUCCESS_COLOR, Toast.LENGTH_SHORT, true, true);
        if (t != null)
            t.show();
    }

    public static void warning(@NonNull Context context, @NonNull String message) {
        Toast t = custom(context, message, getDrawable(context, R.drawable.ic_warn), WARNING_COLOR, Toast.LENGTH_SHORT, true, true);
        if (t != null)
            t.show();
    }

    public static void info(@NonNull Context context, @NonNull String message) {
        Toast t = custom(context, message, getDrawable(context, R.drawable.ic_info), INFO_COLOR, Toast.LENGTH_SHORT, true, true);
        if (t != null)
            t.show();
    }

    public static void error(@NonNull Context context, @NonNull String message) {
        Toast t = custom(context, message, getDrawable(context, R.drawable.ic_block), ERROR_COLOR, Toast.LENGTH_SHORT, true, true);
        if (t != null)
            t.show();
    }


    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, int duration, boolean withIcon) {
        return custom(context, message, icon, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, getDrawable(context, iconRes), tintColor, duration, withIcon, shouldTint);
    }

    @SuppressLint("ShowToast")
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = getDrawable(context, R.drawable.toast_frame);
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            if (tintIcon)
                icon = tintIcon(icon, DEFAULT_TEXT_COLOR);
            setBackground(toastIcon, icon);

        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText("" + message);
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        currentToast.setView(toastLayout);
        return currentToast;
    }

    private static void setBackground(@NonNull View view, Drawable drawable) {
        /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)*/
        view.setBackground(drawable);
      /*  else
            view.setBackgroundDrawable(drawable);*/
    }

    private static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    private static Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        return tintIcon(toastDrawable, tintColor);
    }


    private static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

}
