package com.funny.addworddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 2016/4/13.
 */
public class AddWordOutsideLinearLayout extends LinearLayout {
    private String text;
    private Context context;
    private int color;
    private int size;
    private AddWordInsideLinearlayout[] addWordInsideLinearlayouts;
    private List<Integer> ns;
    private int orientation;
    private int gravity;

    public AddWordOutsideLinearLayout(Context context) {
        super(context);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
    }

    public AddWordOutsideLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
    }

    public void setTextViewOrientation(int orientation) {
        if (orientation == LinearLayout.HORIZONTAL) {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();
                for (int i = addWordInsideLinearlayouts.length - 1; i >= 0; i--) {
                    addView(addWordInsideLinearlayouts[i]);
                }
            }
        } else {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();
                for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
                    addView(addWordInsideLinearlayouts[i]);
                }
            }
        }

        setOrientation(orientation);
        this.orientation = orientation;
        if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
            setChildViewOrientation(addWordInsideLinearlayouts, orientation);
        }

        if (orientation == LinearLayout.HORIZONTAL) {
            if (getGravity() == Gravity.LEFT) {
                setMyGravity(Gravity.TOP);
            } else if (getGravity() == Gravity.RIGHT) {
                setMyGravity(Gravity.BOTTOM);
            }
        } else {
            if (getGravity() == Gravity.TOP) {
                setMyGravity(Gravity.LEFT);
            } else if (getGravity() == Gravity.BOTTOM) {
                setMyGravity(Gravity.RIGHT);
            }
        }
    }

    @Override
    public int getOrientation() {
        return orientation;
    }


    public void setChildViewOrientation(AddWordInsideLinearlayout[] addWordInsideLinearlayouts, int orientation) {
        if (orientation == LinearLayout.HORIZONTAL) {
            for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
                addWordInsideLinearlayouts[i].setTextViewOrientation(LinearLayout.VERTICAL);
            }
        } else {
            for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
                addWordInsideLinearlayouts[i].setTextViewOrientation(LinearLayout.HORIZONTAL);
            }
        }
    }

    public void setMyGravity(int gravity) {
        this.gravity = gravity;
        setGravity(gravity);
    }

    public int getGravity() {
        return gravity;
    }


    /**
     * 提供给外部调用 设置文字
     *
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
                if (String.valueOf(text.charAt(i)).equals("\n")) {
                    ns.add(i);
                }
            }
            //最后加到字符末尾 注意是length()-1
            ns.add(text.length());
            //我们不能确定有多少换行符， 那也不能确定有多少个Linearlayout 所以这里根据换行符个数来写
            addWordInsideLinearlayouts = new AddWordInsideLinearlayout[ns.size() - 1];
            for (int i = 0; i < ns.size() - 1; i++) {
                addWordInsideLinearlayouts[i] = new AddWordInsideLinearlayout(context);
                addWordInsideLinearlayouts[i].setTextColor(color);
                addWordInsideLinearlayouts[i].setTextSize(size);
                //将每个字符设置到一个Textview上并且添加到customLinearlayouts中
                String temp = text.substring(ns.get(i), ns.get(i + 1)).trim();
                Log.e("yue", temp);
                addWordInsideLinearlayouts[i].setText(temp);
                //最后将所有的customLinearlayouts 加入到最大的 Linearlayout中
                addView(addWordInsideLinearlayouts[i]);
            }


            setChildViewOrientation(addWordInsideLinearlayouts, orientation);
        }
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

}
