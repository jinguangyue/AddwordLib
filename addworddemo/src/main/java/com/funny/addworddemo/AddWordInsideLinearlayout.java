package com.funny.addworddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yue on 2016/4/13.
 */
public class AddWordInsideLinearlayout extends LinearLayout {
    private String text;
    private Context context;
    private int color;
    private int size;

    public AddWordInsideLinearlayout(Context context) {
        super(context);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public AddWordInsideLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
        if (text != null) {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                TextView myText = new TextView(context);
                myText.setTextColor(color);
                if (size > 0) {
                    myText.setTextSize(size);
                }
                myText.setText(text.substring(i, i + 1));
                addView(myText);
            }
        }

    }
/*

    public void setMyGravity(int gravity){
        setGravity(gravity);
        this.requestLayout();
    }
*/

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

}
