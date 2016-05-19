package com.funny.addworddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.funny.addworddemo.MyApplication;
import com.funny.addworddemo.R;
import com.funny.addworddemo.util.BitmapUtils;
import com.funny.addworddemo.util.LogUtils;
import com.funny.addworddemo.util.StatusBarHeightUtil;
import com.funny.addworddemo.view.AddFrameHolder;
import com.funny.addworddemo.view.AddWordFrame;
import com.funny.addworddemo.view.AddWordFrameState;
import com.funny.addworddemo.view.AddWordOutsideLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TextVerticalActivity extends Activity implements View.OnClickListener {
    private EditText edit_text;
    private FrameLayout frame;
    private int width;
    private int height;

    private AddWordOutsideLinearLayout layout;
    private AddWordFrame addWordFrame;
    private int AddWordMode;
    private Bitmap addWordBitmap;
    private int addWordWidth;
    private int addWordHeight;
    private int addWordx1;
    private int addWordy1;
    private Matrix addWordMatrix = new Matrix();
    private Matrix addWordSavedMatrix = new Matrix();
    private List<AddFrameHolder> addFrameHolders;
    private int AddWordSelectImageCount = -1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_text_vertical);
        width = MyApplication.getInstance().getScreenWidth();
        height = MyApplication.getInstance().getScreenHeight();
        initView();
        initLayout();
        initData();
    }

    private void initData() {
        addFrameHolders = new ArrayList<>();
        addMyFrame();
    }

    private void initView() {
        frame = (FrameLayout) findViewById(R.id.frame);
        findViewById(R.id.hengButton).setOnClickListener(this);
        findViewById(R.id.shuButton).setOnClickListener(this);
        findViewById(R.id.zuoduiqi).setOnClickListener(this);
        findViewById(R.id.juzhong).setOnClickListener(this);
        findViewById(R.id.youduiqi).setOnClickListener(this);
        findViewById(R.id.addView).setOnClickListener(this);
        edit_text = (EditText) findViewById(R.id.edit_text);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setText(s.toString());
                ajustAddWord();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    /**
     * 调整加字框的大小 以及删除和变换坐标
     */
    private void ajustAddWord(){
        if(AddWordSelectImageCount != -1){
            addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().layoutWidthAndHeight(addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout(), new AddWordOutsideLinearLayout.OnLayoutWidth() {
                @Override
                public void layout(int width, int height) {
                    addWordMatrix = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getMatrix();
                    addWordSavedMatrix.set(addWordMatrix);
                    addWordMatrix.postTranslate(1,1);
                    adjustLocation(addWordMatrix, addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hengButton:
                final AddWordFrame addWordFrame_heng = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame();
                addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setTextViewOrientation(LinearLayout.VERTICAL);

                addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().layoutWidthAndHeight(addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout(),new AddWordOutsideLinearLayout.OnLayoutWidth() {
                    @Override
                    public void layout(int width, int height) {
                        addWordMatrix = addWordFrame_heng.getMatrix();
                        adjustLocation(addWordMatrix, addWordFrame_heng);
                    }
                });
                break;

            case R.id.shuButton:
                final AddWordFrame addWordFrame_shu = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame();
                addWordFrame_shu.getLayout().setTextViewOrientation(LinearLayout.HORIZONTAL);

                addWordFrame_shu.getLayout().layoutWidthAndHeight(addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout(),
                        new AddWordOutsideLinearLayout.OnLayoutWidth() {
                            @Override
                            public void layout(int width, int height) {
                                addWordMatrix = addWordFrame_shu.getMatrix();
                                adjustLocation(addWordMatrix, addWordFrame_shu);
                            }
                        });
                break;

            case R.id.zuoduiqi:
                if (addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().getOrientation() == LinearLayout.HORIZONTAL) {
                    addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setMyGravity(Gravity.TOP);
                } else {
                    addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setMyGravity(Gravity.LEFT);
                }

                break;

            case R.id.juzhong:
                addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setMyGravity(Gravity.CENTER);
                break;

            case R.id.youduiqi:
                if (addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().getOrientation() == LinearLayout.HORIZONTAL) {
                    addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setMyGravity(Gravity.BOTTOM);
                } else {
                    addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getLayout().setMyGravity(Gravity.RIGHT);
                }
                break;

            case R.id.addView:
                addMyFrame();
                break;
        }
    }

    /**
     * 平移1个单位 只为调整位置
     * @param matrix
     * @param addWordFrame
     */
    private void adjustLocation(Matrix matrix, AddWordFrame addWordFrame){
        //将有缩放平移和旋转相关值的矩阵赋值到f中
        float[] f = new float[9];
        matrix.getValues(f);
        int bWidth = 0;
        int bHeight = 0;

        //取到view的宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Rect rect = new Rect();
            addWordFrame.getLayout().getGlobalVisibleRect(rect);
            bWidth = rect.width();
            bHeight = rect.height();
        } else {
            bWidth = addWordWidth;
            bHeight = addWordHeight;
        }

        //如果想知道这里这样设置值的具体算法那必须要了解9*9的矩阵每个坐标的含义了 有兴趣的可以查阅一下 资料很多
        // 原图左上角
        float x1 = f[2];
        float y1 = f[5];
        addWordFrame.leftTop.set(x1, y1);
        // 原图右上角
        float x2 = f[0] * bWidth + f[2];
        float y2 = f[3] * bWidth + f[5];
        addWordFrame.rightTop.set(x2, y2);
        // 原图左下角
        float x3 = f[1] * bHeight + f[2];
        float y3 = f[4] * bHeight + f[5];
        addWordFrame.leftBottom.set(x3, y3);
        // 原图右下角
        float x4 = f[0] * bWidth + f[1] * bHeight + f[2];
        float y4 = f[3] * bWidth + f[4] * bHeight + f[5];
        addWordFrame.rightBottom.set(x4, y4);


        //这里一定要这是图片的最左 最右 最上 和 最下的位置 用来判断是不是点击到了当前的view
        // 最左边x
        float minX = 0;
        // 最右边x
        float maxX = 0;
        // 最上边y
        float minY = 0;
        // 最下边y
        float maxY = 0;

        minX = Math.min(x4, Math.min(x3, Math.min(x1, x2))) - 30;
        maxX = Math.max(x4, Math.max(x3, Math.max(x1, x2))) + 30;
        minY = Math.min(y4, Math.min(y3, Math.min(y1, y2))) - 30;
        maxY = Math.max(y4, Math.max(y3, Math.max(y1, y2))) + 30;

        addFrameHolders.get(AddWordSelectImageCount).getState().setLeft(minX);
        addFrameHolders.get(AddWordSelectImageCount).getState().setTop(minY);
        addFrameHolders.get(AddWordSelectImageCount).getState().setRight(maxX);
        addFrameHolders.get(AddWordSelectImageCount).getState().setBottom(maxY);

        //将当前的view设置上矩阵对象
        addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().setMatrix(matrix);
    }

    private void addMyFrame() {
        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddWordFrame addWordFrame = addFrameHolders.get(i).getAddWordFrame();
            if (addWordFrame.isSelect()) {
                addWordFrame.setSelect(false);
                break;
            }
        }
        addWordFrame = new AddWordFrame(this);
        addWordFrame.setSelect(true);
        frame.addView(addWordFrame);

        layout = addWordFrame.getLayout();

        addWordBitmap = BitmapUtils.convertViewToBitmap(layout);

        addWordWidth = addWordBitmap.getWidth();
        addWordHeight = addWordBitmap.getHeight();

        //这里是想平移到屏幕比较好看的位置
        addWordx1 = width/2 - addWordWidth /2;
        addWordy1 = height/3;
        //原图左上角
        addWordFrame.leftTop.set(addWordx1, addWordy1);
        // 原图右上角
        addWordFrame.rightTop.set(addWordx1 + addWordWidth, addWordy1);
        // 原图左下角
        addWordFrame.leftBottom.set(addWordx1, addWordy1 + addWordHeight);
        // 原图右下角
        addWordFrame.rightBottom.set(addWordx1 + addWordWidth, addWordy1 + addWordHeight);

        addWordMatrix = new Matrix();
        addWordMatrix.postTranslate(addWordx1, addWordy1);
        addWordFrame.setMatrix(addWordMatrix);

        //这个类里面主要是存储当前view的区域
        AddWordFrameState addWordFrameState = new AddWordFrameState();
        addWordFrameState.setLeft(addWordx1);
        addWordFrameState.setTop(addWordy1);
        addWordFrameState.setRight(addWordx1 + addWordWidth);
        addWordFrameState.setBottom(addWordy1 + addWordHeight);

        AddFrameHolder addFrameHolder = new AddFrameHolder();
        addFrameHolder.setAddWordFrame(addWordFrame);
        addFrameHolder.setState(addWordFrameState);
        addFrameHolders.add(addFrameHolder);

        addWordFrame.setOnTouchListener(new AddWordMyOntouch());
        AddWordSelectImageCount = addFrameHolders.size() - 1;
    }

    private void selectMyFrame(float x, float y) {
        //选取消所有的选中 后面只有点击到的才是选中状态
        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddFrameHolder addFrameHolder = addFrameHolders.get(i);
            if (addFrameHolder.getAddWordFrame().isSelect()) {
                addFrameHolder.getAddWordFrame().setSelect(false);
                break;
            }
        }

        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddFrameHolder addFrameHolder = addFrameHolders.get(i);
            //创建一个矩形区域 这里的getLeft getTop等等的意思是当前view的最左边 最上边 最右边和最下边 只有点击到这个区域里面才是选中
            Rect rect = new Rect((int)addFrameHolder.getState().getLeft(),
                    (int)addFrameHolder.getState().getTop(),
                    (int)addFrameHolder.getState().getRight(),
                    (int)addFrameHolder.getState().getBottom());

            if (rect.contains((int) x, (int) y)) {
                //如果选中 当前view图层提到最上面
                addFrameHolder.getAddWordFrame().bringToFront();
                addFrameHolder.getAddWordFrame().setSelect(true);
                //记录选中了哪个
                AddWordSelectImageCount = i;
                LogUtils.e("选中");
                break;
            }
            AddWordSelectImageCount = -1;
            LogUtils.e("没选中");
        }
    }

    class AddWordMyOntouch implements View.OnTouchListener {
        //俩个手指间的距离
        private float baseValue = 0;
        //原来的角度
        private float oldRotation;
        //旋转和缩放的中点
        private PointF midP;
        //点中的要进行缩放的点与图片中点的距离
        private float imgLengHalf;
        //保存刚开始按下的点
        private PointF startPoinF = new PointF();

        private int NONE = 0; // 无
        private int DRAG = 1; // 移动
        private int ZOOM = 2; // 变换

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int eventaction = event.getAction();
            float event_x = (int) event.getRawX();
            float event_y = (int) event.getRawY() - StatusBarHeightUtil.getStatusBarHeight(context);

            //这里算是一个点击区域值 点中删除或者点中变换的100 * 100 的矩形区域 用这个区域来判断是否点中
            int tempInt = 100;
            int addint = 100;

            switch (eventaction & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: // touch down so check if the

                    baseValue = 0;

                    startPoinF.set(event_x, event_y);// 保存刚开始按下的坐标

                    //因为可能要添加多个这样的view 所以要按选中了哪个
                    selectMyFrame(event_x, event_y);

                    //如果有选中状态的额view
                    if (AddWordSelectImageCount != -1) {
                        addWordFrame = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame();
                        addWordMatrix = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame().getMatrix();
                        addWordSavedMatrix.set(addWordMatrix);
                        AddWordMode = DRAG;

                        //构造一个旋转按钮的矩形区域
                        Rect moveRect = new Rect((int) addWordFrame.rightBottom.x - tempInt,
                                (int) addWordFrame.rightBottom.y - tempInt, (int) addWordFrame.rightBottom.x + addint,
                                (int) addWordFrame.rightBottom.y + addint);
                        //删除按钮的矩形区域
                        Rect deleteRect = new Rect((int) addWordFrame.leftTop.x - tempInt,
                                (int) addWordFrame.leftTop.y - tempInt, (int) addWordFrame.leftTop.x + addint,
                                (int) addWordFrame.leftTop.y + addint);


                        //如果点中了变换
                        if(moveRect.contains((int)event_x, (int)event_y)){
                            LogUtils.e("点中了变换");
                            // 点中了变换
                            midP = midPoint(addWordFrame.leftTop, addWordFrame.rightBottom);
                            imgLengHalf = spacing(midP, addWordFrame.rightBottom);
                            oldRotation = rotation(midP, startPoinF);
                            AddWordMode = ZOOM;
                        }else if (deleteRect.contains((int)event_x, (int)event_y)) {
                            // 点中了删除
                            LogUtils.e("点中了删除");
                            deleteMyFrame();
                        }
                    }
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    midP = midPoint(addWordFrame.leftTop, addWordFrame.rightBottom);
                    imgLengHalf = spacing(midP, addWordFrame.rightBottom);
                    oldRotation = rotationforTwo(event);
                    break;

                case MotionEvent.ACTION_MOVE: // touch drag with the ball
                    //如果是双指点中
                    if (event.getPointerCount() == 2) {
                        if (AddWordSelectImageCount != -1) {
                            AddWordMode = NONE;
                            float x = event.getX(0) - event.getX(1);
                            float y = event.getY(0) - event.getY(1);
                            float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离

                            //旋转的角度
                            float newRotation = rotationforTwo(event) - oldRotation;
                            if (baseValue == 0) {
                                baseValue = value;
                            } else {
                                //旋转到一定角度再执行 不能刚点击就执行旋转或者缩放
                                if (value - baseValue >= 10 || value - baseValue <= -10) {
                                    float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
                                    addWordMatrix.set(addWordSavedMatrix);
                                    addWordMatrix.postScale(scale, scale, midP.x, midP.y);
                                    addWordMatrix.postRotate(newRotation, midP.x, midP.y);
                                }
                            }
                        }
                    } else if (event.getPointerCount() == 1) {
                        //单指点击
                        if (AddWordSelectImageCount != -1) {
                            if (AddWordMode == DRAG) {
                                if (event_x < MyApplication.getInstance().getScreenWidth() - 50 && event_x > 50
                                        && event_y > 100
                                        && event_y < MyApplication.getInstance().getScreenHeight() - 100) {
                                    addWordMatrix.set(addWordSavedMatrix);
                                    // 图片移动的距离
                                    float translateX = event_x - startPoinF.x;
                                    float translateY = event_y - startPoinF.y;
                                    addWordMatrix.postTranslate(translateX, translateY);
                                }
                            } else if (AddWordMode == ZOOM) {
                                //点击到了缩放旋转按钮
                                PointF movePoin = new PointF(event_x, event_y);

                                float moveLeng = spacing(startPoinF, movePoin);
                                float newRotation = rotation(midP, movePoin) - oldRotation;

                                if (moveLeng > 10f) {
                                    float moveToMidLeng = spacing(midP, movePoin);
                                    float scale = moveToMidLeng / imgLengHalf;

                                    addWordMatrix.set(addWordSavedMatrix);
                                    addWordMatrix.postScale(scale, scale, midP.x, midP.y);
                                    addWordMatrix.postRotate(newRotation, midP.x, midP.y);
                                }
                            }
                        }
                    }

                    if (AddWordSelectImageCount != -1) {
                        //最后在action_move 执行完前设置好矩阵 设置view的位置
                        addWordFrame = addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame();
                        adjustLocation(addWordMatrix, addWordFrame);
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP: //一只手指离开屏幕，但还有一只手指在上面会触此事件
                    //什么都没做
                    AddWordMode = NONE;
                    break;

                case MotionEvent.ACTION_UP:
                    AddWordMode = NONE;
                    break;
            }
            return true;
        }
    }


    private void deleteMyFrame() {
        if (AddWordSelectImageCount != -1) {
            frame.removeView(addFrameHolders.get(AddWordSelectImageCount).getAddWordFrame());
            addFrameHolders.remove(AddWordSelectImageCount);
            AddWordSelectImageCount = -1;
        }
    }

    private void initLayout() {
    }

    private float rotationforTwo(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    // 得到两个点的距离
    private float spacing(PointF p1, PointF p2) {
        float x = p1.x - p2.x;
        float y = p1.y - p2.y;
        return (float) Math.sqrt(x * x + y * y);
    }

    // 得到两个点的中点
    private PointF midPoint(PointF p1, PointF p2) {
        PointF point = new PointF();
        float x = p1.x + p2.x;
        float y = p1.y + p2.y;
        point.set(x / 2, y / 2);
        return point;
    }

    // 旋转
    private float rotation(PointF p1, PointF p2) {
        double delta_x = (p1.x - p2.x);
        double delta_y = (p1.y - p2.y);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

}
