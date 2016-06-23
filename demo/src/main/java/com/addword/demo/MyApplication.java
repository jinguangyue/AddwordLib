package com.addword.demo;

import android.app.Application;

import com.funny.addworddemo.AppConst;
import com.funny.addworddemo.util.DeviceInfoUtils;

/**
 * Created by yue on 2016/4/18.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private int textHeight;
    private int screenWidth;
    private int screenHeight;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        screenWidth = DeviceInfoUtils.getScreenWidth(this);//获取屏幕宽度
        screenHeight = DeviceInfoUtils.getScreenHeight(this);//获取屏幕高度
    }

    public int getTextHeight() {
        if(textHeight == 0){
            textHeight = AppConst.textHeight;
        }
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
