package com.funny.addworddemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yue on 2016/4/8.
 */
public class MyTextView extends TextView {
    private TextView borderText = null;///用于描边的TextView

    private int borderColor;
    private float rotate;
    private boolean isheng = true;
    private boolean isFirst = true;

    public MyTextView(Context context) {
        super(context);
        borderText = new TextView(context);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        borderText = new TextView(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
        borderText = new TextView(context, attrs, defStyle);
        init();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        init();
        invalidate();
    }

    public boolean isheng() {
        return isheng;
    }

    public void setIsheng(boolean isheng) {
        this.isheng = isheng;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void init() {
        TextPaint tp1 = borderText.getPaint();
        tp1.setStrokeWidth(5);                                  //设置描边宽度
        tp1.setStyle(Paint.Style.STROKE);                             //对文字只描边
        borderText.setTextColor(borderColor);  //设置描边颜色
        borderText.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        borderText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence tt = borderText.getText();

        //两个TextView上的文字必须一致
        if (tt == null || !tt.equals(this.getText())) {
            borderText.setText(getText());
            borderText.setTextSize(getTextSize()/2);
            this.postInvalidate();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        borderText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        borderText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        borderText.draw(canvas);
        /*if(!isFirst){
            if(isheng){
                canvas.rotate(rotate);
                canvas.translate(-getWidth()/2, 0);
            }else{
                canvas.rotate(rotate);
                canvas.translate(getWidth()/2, 0);
            }

        }*/
        super.onDraw(canvas);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if ("".equals(text) || text == null || text.length() == 0) {
            return;
        }
        int m = text.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < m; i++) {
            CharSequence index = text.toString().subSequence(i, i + 1);
            sb.append(index + "\n");
        }
        super.setText(sb, type);
    }
}
