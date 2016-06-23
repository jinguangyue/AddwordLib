package com.funny.addworddemo.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by yue on 15/10/29.
 */
@SuppressWarnings("deprecation")
public class DeviceInfoUtils {
    /**
     * @param context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * @param context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

}
