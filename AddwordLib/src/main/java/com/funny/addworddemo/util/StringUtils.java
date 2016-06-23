package com.funny.addworddemo.util;

/**
 * Created by yue on 2016/4/14.
 */
public class StringUtils {
    //判断是不是英文字母
    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }
}
