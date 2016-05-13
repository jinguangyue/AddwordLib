package com.funny.addworddemo.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funny.addworddemo.AppConst;
import com.funny.addworddemo.MyApplication;
import com.funny.addworddemo.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 2016/4/13.
 */
public class AddWordInsideLinearlayout extends LinearLayout {
    private String text;
    private Context context;
    private int color;
    private int size;
    private List<TextView> textViews;
    private AddWordTextView myText;

    public AddWordInsideLinearlayout(Context context) {
        super(context);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textViews = new ArrayList<TextView>();
    }

    public AddWordInsideLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textViews = new ArrayList<TextView>();
    }

    public void setTextViewOrientation(int orientation) {
        setOrientation(orientation);
    }

    public void setText(String text) {
        this.text = text;
        addText();
    }

    private void addText() {
        removeAllViews();
        textViews.clear();

        if (!TextUtils.isEmpty(text)) {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                myText = new AddWordTextView(context);
                myText.setTextColor(color);
                if (size > 0) {
                    myText.setTextSize(size);
                }
                myText.setText(text.substring(i, i + 1));
                myText.setIncludeFontPadding(false);
                textViews.add(myText);
                myText.setGravity(Gravity.CENTER);
                addView(myText);
            }

            myText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        myText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    /*int height = 0;
                    if(height < myText.getHeight()){
                        height = myText.getHeight();
                    }*/
                    if(myText.getHeight() != 0){
                        AppConst.textHeight = myText.getHeight();
                        LogUtils.i("AppConst.textHeight===" + AppConst.textHeight);
                    }
                    MyApplication.getInstance().setTextHeight(myText.getHeight());
//                    MyApplication.getInstance().setTextWidth(60);
                }
            });
        }
    }

   /* @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        MyApplication.getInstance().setTextHeight(myText.getHeight());
        Log.e("yue", "myText.getHeight()onWindowFocusChanged" + myText.getHeight());
    }*/

    public List<TextView> getTextViews() {
        return textViews;
    }

    public void setTextViews(List<TextView> textViews) {
        this.textViews = textViews;
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }
}
