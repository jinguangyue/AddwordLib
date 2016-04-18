package com.funny.addworddemo;

import android.app.Application;

/**
 * Created by yue on 2016/4/18.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private int textHeight;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }
}
