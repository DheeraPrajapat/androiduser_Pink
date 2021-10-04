package com.marius.valeyou.util.theme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.R;

public class ThemeUtils {

    private static final String KEY_THEME = "key_theme";
    private final Activity activity;
    private final SharedPreferences preferences;
    private final Theme DEFAULT = Theme.BLUE;

    private ThemeUtils(Activity activity) {
        this.activity = activity;
        preferences = activity.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    public static ThemeUtils getInstance(@NonNull Activity activity) {
        return new ThemeUtils(activity);
    }

    public boolean setCurrentTheme(Theme currentTheme) {
        return preferences.edit().putString(KEY_THEME, currentTheme.name()).commit();

    }

    public Theme getCurrentTheme() {
        String s = preferences.getString(KEY_THEME, DEFAULT.name());
        return Theme.valueOf(s);
    }

    public void apply() {
        Theme theme = Theme.valueOf(preferences.getString(KEY_THEME, DEFAULT.name()));
        activity.setTheme(theme.getTheme());
    }


    public enum Theme {
        BLUE(R.style.AppTheme_Blue),
        YELLOW(R.style.AppTheme_Yellow);


        private final int theme;

        Theme(@StyleRes int theme) {
            this.theme = theme;
        }

        @StyleRes
        public int getTheme() {
            return this.theme;
        }

    }
}
