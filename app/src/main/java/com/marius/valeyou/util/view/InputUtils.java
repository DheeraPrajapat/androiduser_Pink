package com.marius.valeyou.util.view;

import android.text.TextUtils;

public class InputUtils {


    public static boolean emailInvalid(String email) {
        if(email==null)
            return true;
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }


    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        if (TextUtils.isEmpty(s.trim()))
            return true;
        return false;

    }

    public static String getTrimString(String s) {
        if (s == null)
            return "";
        return s.trim();


    }
}
