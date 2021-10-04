package com.marius.valeyou.util.databinding;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.appcompat.widget.Toolbar;

@BindingMethods({
        @BindingMethod(type = Toolbar.class, attribute = "onMenuItemClick", method = "setOnMenuItemClickListener"),
        @BindingMethod(type = Toolbar.class, attribute = "onNavigationClick", method = "setNavigationOnClickListener"),
})
public class SupportToolbarBindingAdapter {
}
