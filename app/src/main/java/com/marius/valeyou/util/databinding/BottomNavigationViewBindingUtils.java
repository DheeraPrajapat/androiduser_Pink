package com.marius.valeyou.util.databinding;

import androidx.databinding.BindingAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Created by Arvind Poonia on 11/29/2018.
 */
public class BottomNavigationViewBindingUtils {
    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }
}
