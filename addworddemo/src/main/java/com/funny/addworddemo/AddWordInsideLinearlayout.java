package com.funny.addworddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView myText;

    public AddWordInsideLinearlayout(Context context) {
        super(context);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViews = new ArrayList<TextView>();
    }

    public AddWordInsideLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        if (text != null) {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                myText = new TextView(context);
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
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        MyApplication.getInstance().setTextHeight(myText.getHeight());
        Log.e("yue", "myText.getHeight()" + myText.getHeight());
    }

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
