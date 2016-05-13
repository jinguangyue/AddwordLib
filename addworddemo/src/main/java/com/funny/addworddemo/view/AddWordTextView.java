package com.funny.addworddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yue on 2016/4/28.
 */
public class AddWordTextView extends TextView {
    private int borderColor = Color.TRANSPARENT;
    private int textColor = Color.BLACK;
    private boolean isStroke = false;

    public AddWordTextView(Context context) {
        super(context);
    }

    public AddWordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddWordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isStroke() {
        return isStroke;
    }

    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }

    public void setBorderColor(int borderColor, int textColor, boolean isStroke) {
        this.borderColor = borderColor;
        this.textColor = textColor;
        this.isStroke = isStroke;
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isStroke){
            this.setTextColor(borderColor);
            TextPaint tp = this.getPaint();
            tp.setFakeBoldText(true);
            tp.setStrokeWidth(5);
            tp.setStyle(Paint.Style.STROKE);
            tp.setColor(borderColor);
            super.onDraw(canvas);

            tp.setColor(textColor);
            tp.setStyle(Paint.Style.STROKE);
            this.setTextColor(textColor);
            super.onDraw(canvas);
        }else{
            this.setTextColor(textColor);
            TextPaint tp = this.getPaint();
            tp.setFakeBoldText(false);
            tp.setStrokeWidth(0);
            super.onDraw(canvas);
        }

    }
}
