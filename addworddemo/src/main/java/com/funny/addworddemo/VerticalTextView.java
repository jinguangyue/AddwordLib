package com.funny.addworddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 2016/4/13.
 */
public class VerticalTextView extends LinearLayout {
    private String text;
    private Context context;
    private int color;
    private int size;
    private CustomLinearlayout[] customLinearlayouts;
    private List<Integer> ns;
    private int orientation;

    public VerticalTextView(Context context) {
        super(context);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setTextViewOrientation(int orientation) {
        setOrientation(orientation);
        this.orientation = orientation;
        if(customLinearlayouts != null && customLinearlayouts.length > 0){
            setChildViewOrientation(customLinearlayouts, orientation);
        }
    }

    public void setChildViewOrientation(CustomLinearlayout[] customLinearlayouts, int orientation){
        if(orientation == LinearLayout.HORIZONTAL){
            for(int i=0; i<customLinearlayouts.length; i++){
                customLinearlayouts[i].setTextViewOrientation(LinearLayout.VERTICAL);
            }
        }else{
            for(int i=0; i<customLinearlayouts.length; i++){
                customLinearlayouts[i].setTextViewOrientation(LinearLayout.HORIZONTAL);
            }
        }
    }

    /**
     * 提供给外部调用 设置文字
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        addText();
    }

    private void addText() {
        //在添加字之前移除所有已经添加的View
        removeAllViews();
        //存储换行字符的位置
        ns = new ArrayList<Integer>();
        if (text != null && !text.equals("")) {
            //转成char[] 可遍历
            char[] chars = text.toCharArray();
            //后面要截取从0到换行符的字符添加到LinearLayout中 所以要加0
            ns.add(0);
            for (int i = 0; i < chars.length; i++) {
                //当碰到换行符 add it
                if(String.valueOf(text.charAt(i)).equals("\n")){
                    ns.add(i);
                }
            }
            //最后加到字符末尾 注意是length()-1
            ns.add(text.length());
            //我们不能确定有多少换行符， 那也不能确定有多少个Linearlayout 所以这里根据换行符个数来写
            customLinearlayouts = new CustomLinearlayout[ns.size()-1];
            Log.e("yue","ns.size()===" + ns.size());
            for(int i=0; i<ns.size()-1; i++){
                customLinearlayouts[i] = new CustomLinearlayout(context);
                customLinearlayouts[i].setTextColor(color);
                customLinearlayouts[i].setTextSize(size);
                //将每个字符设置到一个Textview上并且添加到customLinearlayouts中
                String temp = text.substring(ns.get(i), ns.get(i+1)).trim();
                Log.e("yue", temp);
                customLinearlayouts[i].setText(temp);
                //最后将所有的customLinearlayouts 加入到最大的 Linearlayout中
                addView(customLinearlayouts[i]);
            }
            setChildViewOrientation(customLinearlayouts, orientation);
        }
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

}
