package com.funny.addworddemo.util;

import android.content.Context;

/**
 * Created by yue on 2016/1/5.
 */
public class StatusBarHeightUtil {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
