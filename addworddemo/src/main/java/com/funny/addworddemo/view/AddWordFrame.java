package com.funny.addworddemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.funny.addworddemo.R;
import com.funny.addworddemo.util.BitmapUtils;
import com.funny.addworddemo.util.LogUtils;

/**
 * Created by yue on 2016/4/22.
 */
public class AddWordFrame extends FrameLayout {
    private Paint paint;
    private int mImageHeight = 50, mImageWidth = 200;

    private Bitmap bitmap;

    private Context context;
    private AddWordOutsideLinearLayout layout;

    private float sx;
    private float sy;
    private float dx;
    private float dy;
    private float degrees;
    private float px;
    private float py;
    private float centerX;
    private float centerY;

    //用于缩放
    public PointF leftTop = new PointF();// 图片左上角的坐标
    public PointF rightTop = new PointF();// 右上
    public PointF leftBottom = new PointF();// 左下
    public PointF rightBottom = new PointF();// 图片右下角的坐标
    private boolean isSelect = false;
    public Bitmap bitDelete = null;
    public Bitmap bitMove = null;

    Matrix matrix = new Matrix();

    private int bdeleteWidth;
    private boolean isHeng = true;
    private String allText;

    public AddWordFrame(Context context) {
        super(context);
        this.context = context;
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordOutsideLinearLayout getLayout() {
        return layout;
    }

    public String getAllText() {
        return allText;
    }

    public void setAllText(String allText) {
        this.allText = allText;
    }

    private void addWordView() {
        layout = new AddWordOutsideLinearLayout(context);

        layout.setTextColor(Color.BLACK);
        layout.setTextSize(30);
        //这里的LinearLayout.VERTICAL实际上是每一行是竖排 但是里面的每一个字都是横排 所以它标识的其实是横向的 请结合效果理解一下 实在没看明白可以留言给我
        layout.setTextViewOrientation(LinearLayout.VERTICAL);
        layout.setText("双击编辑");
//        layout.setText("jin\n靳广越靳广越\n666");
        bitmap = BitmapUtils.convertViewToBitmap(layout);

        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();

        LogUtils.i("mImageWidth===" + mImageWidth);
        LogUtils.i("mImageHeight===" + mImageHeight);

        layout.setmImageWidth(mImageWidth);
        layout.setmImageHeight(mImageHeight);

        layout.setSelect(true);
        layout.setBackgroundResource(R.drawable.img_font_frame);
        addView(layout);
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

    public void postScale(float sx, float sy, float centerX, float centerY) {
        this.sx = sx;
        this.sy = sy;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void postTranslate(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void postRotate(float degrees, float px, float py) {
        this.degrees = degrees;
        this.px = px;
        this.py = py;
    }

    public boolean isHeng() {
        return isHeng;
    }

    public void setHeng(boolean heng) {
        isHeng = heng;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        canvas.concat(matrix);
        this.setLayerType(View.LAYER_TYPE_NONE, null);

        /*if (isSelect) {
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

            if (isHeng) {
                canvas.drawLine(0, 0, mImageWidth, 0, paint);
                canvas.drawLine(0, mImageHeight, mImageWidth, mImageHeight, paint);
                canvas.drawLine(0, 0, 0, mImageHeight, paint);
                canvas.drawLine(mImageWidth, 0, mImageWidth, mImageHeight, paint);

                canvas.drawBitmap(bitDelete, 0 - bdeleteWidth, 0 - bdeleteWidth, paint);
                canvas.drawBitmap(bitMove, mImageWidth - bdeleteWidth, mImageHeight - bdeleteWidth, paint);
            } else {
                canvas.drawLine(0, 0, mImageHeight, 0, paint);
                canvas.drawLine(0, mImageWidth, mImageHeight, mImageWidth, paint);
                canvas.drawLine(0, 0, 0, mImageWidth, paint);
                canvas.drawLine(mImageHeight, 0, mImageHeight, mImageWidth, paint);

                canvas.drawBitmap(bitDelete, 0 - bdeleteWidth, 0 - bdeleteWidth, paint);
                canvas.drawBitmap(bitMove, mImageHeight - bdeleteWidth, mImageWidth - bdeleteWidth, paint);
            }
        }*/
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        if(select == false){
            this.getLayout().setBackgroundResource(0);
            /*Intent intent = new Intent(AppConstant.ACTION.POPVIEWADDWORD_CLOSE);
            context.sendBroadcast(intent);*/
        }else{
            this.getLayout().setBackgroundResource(R.drawable.img_font_frame);
            /*Intent intent = new Intent(AppConstant.ACTION.POPVIEWADDWORD_SHOW);
            context.sendBroadcast(intent);*/
        }
//        invalidate();
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                *//*handler.post(new Runnable() {
                    @Override
                    public void run() {*//*
                postInvalidate();
                *//*    }
                });*//*
                // 使用postInvalidate可以直接在线程中更新界面
            }
        }).start();*/
    }

    public int getBdeleteWidth() {
        return bdeleteWidth;
    }
}
