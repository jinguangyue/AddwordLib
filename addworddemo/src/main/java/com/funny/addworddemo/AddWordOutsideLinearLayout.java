package com.funny.addworddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String[] bigNumbers = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private String[] smallNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private List<TextView> numberViews;
    private Map<TextView, MyTextViewBean> map;

    public AddWordOutsideLinearLayout(Context context) {
        super(context);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
        numberViews = new ArrayList<TextView>();
        map = new HashMap<>();
    }

    public AddWordOutsideLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
        numberViews = new ArrayList<TextView>();
        map = new HashMap<>();
    }

    public void setTextViewOrientation(int orientation) {

        //此时外层切换为横向 那内部就要切换为竖向
        if (orientation == LinearLayout.HORIZONTAL) {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();

                /**
                 * 处理英文 切换为横排时还原
                 */
                List<TextView> textViews = addWordInsideLinearlayouts[0].getTextViews();

                if (textViews != null && textViews.size() != 0) {
                    for (TextView textView : textViews) {
                        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }

                for (TextView textView : textViews) {
                    if (StringUtils.isEnglish(textView.getText().toString())) {
                        textView.setRotation(90);
                        textView.setLayoutParams(new LayoutParams(MyApplication.getInstance().getTextHeight(),
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }
                /**
                 * 处理数字
                 */
                for (TextView textView : textViews) {
                    for (int i = 0; i < smallNumbers.length; i++) {
                        if (smallNumbers[i].equals(textView.getText().toString())) {
                            textView.setText(bigNumbers[i]);
                            numberViews.add(textView);
                        }
                    }
                }

                for (int i = addWordInsideLinearlayouts.length - 1; i >= 0; i--) {
                    addView(addWordInsideLinearlayouts[i]);
                }

            }
        } else {
            //此时外层切换为竖向 那内部就要切换为横向
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();

                /**
                 * 处理英文 竖排的时候英文效果体验不好
                 */
                List<TextView> textViews = addWordInsideLinearlayouts[0].getTextViews();

                if (textViews != null && textViews.size() != 0) {
                    for (TextView textView : textViews) {
                        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }

                for (TextView textView : textViews) {
                    if (StringUtils.isEnglish(textView.getText().toString())) {
                        textView.setRotation(0);
                        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }

                if (numberViews != null && numberViews.size() > 0) {
                    for (TextView textView : numberViews) {
                        for (int i = 0; i < smallNumbers.length; i++) {
                            if (bigNumbers[i].equals(textView.getText().toString())) {
                                textView.setText(smallNumbers[i]);
                            }
                        }
                    }
                    numberViews.clear();
                }

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
//        map.clear();
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
            //最后加到字符末尾 注意是length()
            ns.add(text.length());
            //我们不能确定有多少换行符， 那也不能确定有多少个Linearlayout 所以这里根据换行符个数来写
            addWordInsideLinearlayouts = new AddWordInsideLinearlayout[ns.size() - 1];
            for (int i = 0; i < ns.size() - 1; i++) {
                addWordInsideLinearlayouts[i] = new AddWordInsideLinearlayout(context);
                addWordInsideLinearlayouts[i].setTextColor(color);
                addWordInsideLinearlayouts[i].setTextSize(size);
                //将每个字符设置到一个Textview上并且添加到customLinearlayouts中
                String temp = text.substring(ns.get(i), ns.get(i + 1)).trim();
                addWordInsideLinearlayouts[i].setText(temp);

                //最后将所有的customLinearlayouts 加入到最大的 Linearlayout中
                addView(addWordInsideLinearlayouts[i]);
            }

            setChildViewOrientation(addWordInsideLinearlayouts, orientation);

            // 如果竖排时候输入了数字或者字母 要旋转
            if (orientation == LinearLayout.HORIZONTAL) {
                if(addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0){

                }
                //如果是字母
                    for (TextView textView : addWordInsideLinearlayouts[0].getTextViews()) {
                        if (StringUtils.isEnglish(textView.getText().toString())) {
                            textView.setRotation(90);
                            textView.setLayoutParams(new LayoutParams(MyApplication.getInstance().getTextHeight(),
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                    }

                //如果是数字
                for (TextView textView : addWordInsideLinearlayouts[0].getTextViews()) {
                    for (int i = 0; i < smallNumbers.length; i++) {
                        if (smallNumbers[i].equals(textView.getText().toString())) {
                            textView.setText(bigNumbers[i]);
                            numberViews.add(textView);
                        }
                    }
                }
            }
        }
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

}
