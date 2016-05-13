package com.funny.addworddemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funny.addworddemo.MyApplication;
import com.funny.addworddemo.R;
import com.funny.addworddemo.util.LogUtils;
import com.funny.addworddemo.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 2016/4/13.
 */
public class AddWordOutsideLinearLayout extends LinearLayout {
    private Paint paint;
    private int mImageHeight;
    private int mImageWidth;
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
    private float alpha = 1;

    //用于缩放
    public PointF leftTop = new PointF();// 图片左上角的坐标
    public PointF rightTop = new PointF();// 右上
    public PointF leftBottom = new PointF();// 左下
    public PointF rightBottom = new PointF();// 图片右下角的坐标
    private boolean isSelect = false;
    public Bitmap bitDelete = null;
    public Bitmap bitMove = null;

    private int bdeleteWidth;
    private int ziMarginCount = 0;

    private int maxCount = 0;
    private int hangMarginCount = 0;
    private int hangCount;
    private int layoutWidth;
    private boolean isHeng = false;
//    Matrix matrix = new Matrix();

    public AddWordOutsideLinearLayout(Context context) {
        super(context);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
        numberViews = new ArrayList<TextView>();
        setWillNotDraw(false);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#F77DA3"));
        paint.setStrokeWidth(5f);

        if (bitDelete == null) {
            bitDelete = BitmapFactory.decodeResource(getResources(),
                    R.drawable.btn_sticker_cancel_n);
        }
        if (bitMove == null) {
            bitMove = BitmapFactory.decodeResource(getResources(),
                    R.drawable.btn_sticker_word_turn_n);
        }

        bdeleteWidth = bitDelete.getHeight() / 2;
    }

    public AddWordOutsideLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(HORIZONTAL);
        this.context = context;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        this.setLayoutParams(params);
        numberViews = new ArrayList<TextView>();
        setWillNotDraw(false);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#F77DA3"));
        paint.setStrokeWidth(5f);

        if (bitDelete == null) {
            bitDelete = BitmapFactory.decodeResource(getResources(),
                    R.drawable.btn_sticker_cancel_n);
        }
        if (bitMove == null) {
            bitMove = BitmapFactory.decodeResource(getResources(),
                    R.drawable.btn_sticker_word_turn_n);
        }

        bdeleteWidth = bitDelete.getHeight() / 2;
    }

    public int getmImageHeight() {
        return mImageHeight;
    }

    public void setmImageHeight(int mImageHeight) {
        this.mImageHeight = mImageHeight;
    }

    public int getmImageWidth() {
        return mImageWidth;
    }

    public void setmImageWidth(int mImageWidth) {
        this.mImageWidth = mImageWidth;
    }

    public void setTextViewOrientation(int orientation) {

        //此时外层切换为横向 那内部就要切换为竖向
        if (orientation == LinearLayout.HORIZONTAL) {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();

                for (int b = addWordInsideLinearlayouts.length - 1; b >= 0; b--) {
                    LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (b < addWordInsideLinearlayouts.length - 1) {
                        params1.leftMargin = hangMarginCount;
                    }
                    addWordInsideLinearlayouts[b].setLayoutParams(params1);

                    /**
                     * 处理英文 切换为横排时还原
                     */
                    List<TextView> textViews = addWordInsideLinearlayouts[b].getTextViews();

                    for (int a = 0; a < textViews.size(); a++) {
                        AddWordTextView textView = (AddWordTextView) textViews.get(a);
                        if (StringUtils.isEnglish(textView.getText().toString())) {
                            textView.setRotation(90);
                        }

                        LayoutParams params = new LayoutParams(MyApplication.getInstance().getTextHeight(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (a > 0) {
                            params.topMargin = ziMarginCount;
//                            params.leftMargin = hangMarginCount;
                        }
                        textView.setLayoutParams(params);

                        for (int i = 0; i < smallNumbers.length; i++) {
                            if (smallNumbers[i].equals(textView.getText().toString())) {
                                textView.setText(bigNumbers[i]);
                                numberViews.add(textView);
                            }
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

                for (int b = 0; b < addWordInsideLinearlayouts.length; b++) {
                    LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (b > 0) {
                        params1.topMargin = hangMarginCount;
                    }
                    addWordInsideLinearlayouts[b].setLayoutParams(params1);

                    /**
                     * 处理英文 竖排的时候英文效果体验不好
                     */
                    List<TextView> textViews = addWordInsideLinearlayouts[b].getTextViews();

                    for (int a = 0; a < textViews.size(); a++) {
                        AddWordTextView textView = (AddWordTextView) textViews.get(a);
                        if (StringUtils.isEnglish(textView.getText().toString())) {
                            textView.setRotation(0);
                        }

                        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (a > 0) {
                            params.leftMargin = ziMarginCount;
//                            params.topMargin = hangMarginCount;
                        }
                        textView.setLayoutParams(params);

                        for (int i = 0; i < smallNumbers.length; i++) {
                            if (bigNumbers[i].equals(textView.getText().toString())) {
                                textView.setText(smallNumbers[i]);
                            }
                        }
                        numberViews.clear();
                    }
                }

                for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
                    addView(addWordInsideLinearlayouts[i]);
                }
            }
        }

        setOrientation(orientation);
//        setGravity(Gravity.CENTER);
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

    public String getText() {
        return text;
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
//                addView(addWordInsideLinearlayouts[i]);
            }

            // 如果竖排时候输入了数字或者字母 要旋转
            if (orientation == LinearLayout.HORIZONTAL) {
                if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                    for (int w = addWordInsideLinearlayouts.length - 1; w >= 0; w--) {
                        //如果是字母
                        for (TextView textView : addWordInsideLinearlayouts[w].getTextViews()) {
                            if (StringUtils.isEnglish(textView.getText().toString())) {
                                textView.setRotation(90);
                                textView.setLayoutParams(new LayoutParams(MyApplication.getInstance().getTextHeight(),
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                        }

                        //如果是数字
                        for (TextView textView : addWordInsideLinearlayouts[w].getTextViews()) {
                            for (int i = 0; i < smallNumbers.length; i++) {
                                if (smallNumbers[i].equals(textView.getText().toString())) {
                                    textView.setText(bigNumbers[i]);
                                    numberViews.add(textView);
                                }
                            }
                        }

                        addView(addWordInsideLinearlayouts[w]);
                    }
                }
            } else {
                for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
                    addView(addWordInsideLinearlayout);
                }
            }
        }

        setChildViewOrientation(addWordInsideLinearlayouts, orientation);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
//        invalidate();
    }

    public void setTextColor(int color) {
        this.color = color;
        if(addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0){
            for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
                for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                    AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                    text.setBorderColor(Color.TRANSPARENT, color, text.isStroke());
                }
            }
        }
    }

    public void setTextSize(int size) {
        this.size = size;
    }

    public void setShadow1() {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (TextView textView : addWordInsideLinearlayout.getTextViews()) {
                textView.setShadowLayer(0F, 0F, 0F, Color.TRANSPARENT);
            }
        }
    }

    public void setShadow2() {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (TextView textView : addWordInsideLinearlayout.getTextViews()) {
                textView.setShadowLayer(2F, 2F, 2F, Color.parseColor("#aaaaaa"));
            }
        }
    }

    public void setShadow3() {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (TextView textView : addWordInsideLinearlayout.getTextViews()) {
                textView.setShadowLayer(4F, 4F, 4F, Color.parseColor("#aaaaaa"));
            }
        }
    }

    public void have_Stroke() {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                text.setBorderColor(color, Color.TRANSPARENT, true);
            }
        }
    }

    public void no_stroke() {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                text.setBorderColor(Color.TRANSPARENT, color, false);
            }
        }

    }

    public void increaseAlpha() {
        LogUtils.e("increaseAlpha");
        if (alpha < 1) {
            alpha = alpha + 0.2f;
        }
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                text.setAlpha(alpha);
            }
        }
    }

    public void decreaseAlpha() {
        LogUtils.e("decreaseAlpha");
        if (alpha > 0) {
            alpha = alpha - 0.2f;
        }
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                text.setAlpha(alpha);
            }
        }

    }

    public int getBdeleteWidth() {
        return bdeleteWidth;
    }

    public void setBdeleteWidth(int bdeleteWidth) {
        this.bdeleteWidth = bdeleteWidth;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        invalidate();
    }

    public void setColor(int color) {
        setTextColor(color);
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                text.setTextColor(color);
            }
        }
    }

    public int decreaseZiJianJu() {
        if (ziMarginCount >= 0) {
            ziMarginCount = ziMarginCount - 2;
            for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
                if (maxCount <= addWordInsideLinearlayout.getTextViews().size()) {
                    maxCount = addWordInsideLinearlayout.getTextViews().size();
                }

                for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                    AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
//                    LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    LayoutParams params = (LayoutParams) text.getLayoutParams();
                    if (i > 0) {
                        if (orientation == LinearLayout.HORIZONTAL) {
                            params.topMargin = ziMarginCount;
                        } else {
                            params.leftMargin = ziMarginCount;
                        }
                    }
                    text.setLayoutParams(params);
                }

//                addWordInsideLinearlayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            }
        }
        return maxCount;
    }

    public int increaseZiJianJu() {
        ziMarginCount = ziMarginCount + 2;
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            if (maxCount <= addWordInsideLinearlayout.getTextViews().size()) {
                maxCount = addWordInsideLinearlayout.getTextViews().size();
            }

            for (int i = 0; i < addWordInsideLinearlayout.getTextViews().size(); i++) {
                AddWordTextView text = (AddWordTextView) addWordInsideLinearlayout.getTextViews().get(i);
                LayoutParams params = (LayoutParams) text.getLayoutParams();
//                LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                if (i > 0) {
                    if (orientation == LinearLayout.HORIZONTAL) {
                        params.topMargin = ziMarginCount;
                    } else {
                        params.leftMargin = ziMarginCount;
                    }
                }
                text.setLayoutParams(params);
            }

//            addWordInsideLinearlayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        }
        return maxCount;
    }

    public int increaseHangJianJu() {
        hangMarginCount = hangMarginCount + 2;
        for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                if (orientation == LinearLayout.HORIZONTAL) {
                    params.rightMargin = hangMarginCount;
                } else {
                    params.topMargin = hangMarginCount;
                }
            }
            addWordInsideLinearlayouts[i].setLayoutParams(params);
        }

        hangCount = addWordInsideLinearlayouts.length;
        return hangCount;
    }

    public int decreaseHangJianJu() {
        if (hangMarginCount >= 0) {
            hangMarginCount = hangMarginCount - 2;
            for (int i = 0; i < addWordInsideLinearlayouts.length; i++) {
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    if (orientation == LinearLayout.HORIZONTAL) {
                        params.rightMargin = hangMarginCount;
                    } else {
                        params.topMargin = hangMarginCount;
                    }
                }

                addWordInsideLinearlayouts[i].setLayoutParams(params);
            }
        }
        hangCount = addWordInsideLinearlayouts.length;
        return hangCount;
    }

    public interface OnLayoutWidth {
        void layout(int width, int height);
    }

    public void layoutWidthAndHeight(final AddWordOutsideLinearLayout addWordOutsideLinearLayout, final OnLayoutWidth onLayoutWidth) {
        addWordOutsideLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    addWordOutsideLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                onLayoutWidth.layout(addWordOutsideLinearLayout.getMeasuredWidth(), addWordOutsideLinearLayout.getMeasuredHeight());
            }
        });
    }

    public int layoutHeight() {
        int maxTextsize = 0;
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            if (maxTextsize <= addWordInsideLinearlayout.getTextViews().size()) {
                maxTextsize = addWordInsideLinearlayout.getTextViews().size();
            }
        }


        return MyApplication.getInstance().getTextHeight() * maxTextsize +
                (maxTextsize - 1) * ziMarginCount;
    }

    public void setTypeFace(Typeface face) {
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for (TextView view : addWordInsideLinearlayout.getTextViews()) {
                view.setTypeface(face);
            }
        }
    }

    public String getAllText(){
        String str = "";
        for (AddWordInsideLinearlayout addWordInsideLinearlayout : addWordInsideLinearlayouts) {
            for(TextView textView:addWordInsideLinearlayout.getTextViews()){
                str += textView.getText();
            }
            str += "\n";
        }
        return str;
    }
}
