package com.funny.addworddemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funny.addworddemo.AppConst;
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
    private int color = Color.WHITE;
    private int size;
    private AddWordInsideLinearlayout[] addWordInsideLinearlayouts;
    private List<Integer> ns;
    private int orientation;
    private int gravity;
    private List<TextView> numberViews;
    private float alpha = 1;

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

        if (orientation == LinearLayout.HORIZONTAL) {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();

                for (int b = addWordInsideLinearlayouts.length - 1; b >= 0; b--) {
                    LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (b < addWordInsideLinearlayouts.length - 1) {
                        params1.leftMargin = hangMarginCount;
                    }
                    addWordInsideLinearlayouts[b].setLayoutParams(params1);

                    List<TextView> textViews = addWordInsideLinearlayouts[b].getTextViews();

                    for (int a = 0; a < textViews.size(); a++) {
                        AddWordTextView textView = (AddWordTextView) textViews.get(a);
                        if (StringUtils.isEnglish(textView.getText().toString())) {
                            textView.setRotation(90);
                        }

                        LayoutParams params = new LayoutParams(AppConst.textHeight,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (a > 0) {
                            params.topMargin = ziMarginCount;
//                            params.leftMargin = hangMarginCount;
                        }
                        textView.setLayoutParams(params);

                        /*for (int i = 0; i < smallNumbers.length; i++) {
                            if (smallNumbers[i].equals(textView.getText().toString())) {
                                textView.setText(bigNumbers[i]);
                                numberViews.add(textView);
                            }
                        }*/
                    }
                }

                for (int i = addWordInsideLinearlayouts.length - 1; i >= 0; i--) {
                    addView(addWordInsideLinearlayouts[i]);
                }

            }
        } else {
            if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                removeAllViews();

                for (int b = 0; b < addWordInsideLinearlayouts.length; b++) {
                    LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (b > 0) {
                        params1.topMargin = hangMarginCount;
                    }
                    addWordInsideLinearlayouts[b].setLayoutParams(params1);

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

                        /*for (int i = 0; i < smallNumbers.length; i++) {
                            if (bigNumbers[i].equals(textView.getText().toString())) {
                                textView.setText(smallNumbers[i]);
                            }
                        }*/
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
        removeAllViews();
//        map.clear();
        ns = new ArrayList<Integer>();
        if (text != null && !text.equals("")) {
            char[] chars = text.toCharArray();
            ns.add(0);
            for (int i = 0; i < chars.length; i++) {
                if (String.valueOf(text.charAt(i)).equals("\n")) {
                    ns.add(i);
                }
            }

            ns.add(text.length());
            addWordInsideLinearlayouts = new AddWordInsideLinearlayout[ns.size() - 1];
            for (int i = 0; i < ns.size() - 1; i++) {
                addWordInsideLinearlayouts[i] = new AddWordInsideLinearlayout(context);
                addWordInsideLinearlayouts[i].setTextColor(color);
                addWordInsideLinearlayouts[i].setTextSize(size);
                String temp = text.substring(ns.get(i), ns.get(i + 1)).trim();
                addWordInsideLinearlayouts[i].setText(temp);

//                addView(addWordInsideLinearlayouts[i]);
            }

            if (orientation == LinearLayout.HORIZONTAL) {
                if (addWordInsideLinearlayouts != null && addWordInsideLinearlayouts.length > 0) {
                    for (int w = addWordInsideLinearlayouts.length - 1; w >= 0; w--) {
                        for (TextView textView : addWordInsideLinearlayouts[w].getTextViews()) {
                            if (StringUtils.isEnglish(textView.getText().toString())) {
                                textView.setRotation(90);
                                textView.setLayoutParams(new LayoutParams(AppConst.textHeight,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                        }

                       /* for (TextView textView : addWordInsideLinearlayouts[w].getTextViews()) {
                            for (int i = 0; i < smallNumbers.length; i++) {
                                if (smallNumbers[i].equals(textView.getText().toString())) {
                                    textView.setText(bigNumbers[i]);
                                    numberViews.add(textView);
                                }
                            }
                        }*/

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


        return AppConst.textHeight * maxTextsize +
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
