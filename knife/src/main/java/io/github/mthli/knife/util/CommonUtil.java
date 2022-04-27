package io.github.mthli.knife.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class CommonUtil {
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
