package com.marius.valeyou.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arvind Poonia on 11/26/2018.
 */
public class AppUtils {

    public static void setTypefaceToInputLayout(TextInputLayout inputLayout, Typeface typeFace) {
        try {
            inputLayout.getEditText().setTypeface(typeFace);

            // Retrieve the CollapsingTextHelper Field
            final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            collapsingTextHelperField.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
            final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(typeFace);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }


    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String getTimeFromUtc(long l) {
        if (l == 0)
            return "";
        try {
            return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }


    public static String getChatInboxTimeFromUtc(long l) {
        if (l == 0)
            return "";
        try {
            Calendar current = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

            Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            date.setTimeInMillis(l);

            if (current.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
                return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date.getTime());
            else
                return new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault()).format(date.getTime());

        } catch (Exception e) {
            return "";
        }
    }

    public static String getTimeWithFormatFromUtc(long l, String format) {
        if (l == 0)
            return "";
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrencyFormat(double amount) {
        try {
            // NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
            return String.valueOf(amount);
        } catch (Exception e) {
            return "Error";
        }

    }


    public static String getHeaderAgodate(long s) {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        time.setTimeInMillis(s);
        StringBuilder builder = new StringBuilder();
        if (now.get(Calendar.DATE) == time.get(Calendar.DATE)) {
            builder.append("Today");
        } else if (now.get(Calendar.DATE) - time.get(Calendar.DATE) == 1) {
            builder.append("Yesterday");
        } else
            builder.append(new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date(s)));
        return builder.toString();
    }


    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static synchronized int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static synchronized int convertPxTodp(int px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId(@NonNull Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }

    }


    public static Bitmap createBitmapFromView(@NonNull Activity context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static String getDateTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy, hh:mm a", cal).toString();
        return date;
    }

    public static String getDateTime(long time, String format) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format(format, cal).toString();
        return date;
    }

}
