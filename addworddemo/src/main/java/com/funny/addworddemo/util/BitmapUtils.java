package com.funny.addworddemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Wxcily on 15/10/29.
 * Bitmap处理工具
 */
public class BitmapUtils {
    private BitmapUtils() {
    }

    public static final int Config_480P = 1;// 800*480
    public static final int Config_720P = 2;// 1280*720
    public static final int Config_1080P = 3;// 1920*1080
    public static final int Config_2K = 4;// 2560*1440

    private static int getSize(int config) {
        int size = 0;
        switch (config) {
            case Config_480P:
                size = 480;
                break;
            case Config_720P:
                size = 720;
                break;
            case Config_1080P:
                size = 1080;
                break;
            case Config_2K:
                size = 1440;
                break;
        }
        return size;
    }

    /**
     * 返回适应屏幕尺寸的位图
     *
     * @param bit
     * @param config
     */
    public static Bitmap getRightSzieBitmap(Bitmap bit, int config) {
        // 得到理想宽度
        int ww = getSize(config);
        // 获取图片宽度
        int w = bit.getWidth();
        // 计算缩放率
        float rate = 1f;
        if (w > ww) {
            rate = (float) ww / (float) w;
        }
        // 重新绘图
        Bitmap bitmap = Bitmap.createBitmap((int) (bit.getWidth() * rate),
                (int) (bit.getHeight() * rate), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect rect = new Rect(0, 0, (int) (bit.getWidth() * rate),
                (int) (bit.getHeight() * rate));
        canvas.drawBitmap(bit, null, rect, null);
        return bitmap;
    }

    /**
     * 返回适应屏幕的位图 更节省内存
     *
     * @param fileName
     * @param config
     * @return
     */
    public static Bitmap getRightSzieBitmap(String fileName, int config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int ww = getSize(config);
        if ((ww * 2) < w) {
            options.inSampleSize = 2;
        }
        // 重新绘图
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
        return getRightSzieBitmap(bitmap, config);
    }


    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    /** */
    /**
     * 去色同时加圆角
     *
     * @param bmpOriginal 原图
     * @param pixels      圆角弧度
     * @return 修改后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayscale(bmpOriginal), pixels);
    }

    /** */
    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /** */
    /**
     * 使圆角功能支持BitampDrawable
     *
     * @param bitmapDrawable
     * @param pixels
     * @return
     */
    @SuppressWarnings("deprecation")
    public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
                                               int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 读取路径中的图片，然后将其转化为缩放后的bitmap返回
     *
     * @param path
     */
    public static Bitmap saveBefore(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 200);
        if (be <= 0)
            be = 1;
        options.inSampleSize = 2; // 图片长宽各缩小二分之一
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
        bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        System.out.println(w + "   " + h);
        // savePNG_After(bitmap,path);
        saveJPGE_After(bitmap, path, 90);
        return bitmap;
    }

    /**
     * 将Bitmap转换成指定大小
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    // 图片按比例大小压缩方法
    public static Bitmap getImageFromPath(String srcPath, float maxWidth, float maxHeight) {
        /*if (!isFileAtPath(srcPath)) {
            return null;
        }*/
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            Log.d("getImageFromPath", "bSize:newOpts.out.w=" + w + " h=" + h);

            float aBili = (float) maxHeight / (float) maxWidth;
            float bBili = (float) h / (float) w;
            // be=1表示不缩放，be=2代表大小变成原来的1/2，注意be只能是2的次幂，即使算出的不是2的次幂，使用时也会自动转换成2的次幂
            int be = 1;
            if (aBili > bBili) {
                if (w > maxWidth) {
                    be = (int) (w / maxWidth);
                }
            } else {
                if (h > maxHeight) {
                    be = (int) (h / maxHeight);
                }
            }
            if (be <= 1) {//如果是放大，则不放大
                be = 1;
            }
            newOpts.inSampleSize = be;// 设置缩放比例
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

            int degree = readPictureDegree(srcPath);
            if (degree != 0) {
                bitmap = rotaingImageView(degree, bitmap);
            }
            if (bitmap == null) {
                return null;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 保存图片为PNG
     *
     * @param bitmap
     * @param name
     */
    public static void savePNG_After(Bitmap bitmap, String name) {
        File file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param path
     */
    public static boolean saveJPGE_After(Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param path
     */
    public static void saveJPGE_After(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为PNG
     *
     * @param bitmap
     * @param path
     */
    public static void saveJPGE_After_PNG(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为PNG
     *
     * @param bitmap
     * @param path
     */
    public static void saveJPGE_After_WebP(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeDir(File file) {
        File tempPath = new File(file.getParent());
        if (!tempPath.exists()) {
            tempPath.mkdirs();
        }
    }

    /**
     * 图片合成
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * Bitmap 转 Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * 将图片转换成byte[]以便能将其存到数据库
     */
    public static byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Log.e(TAG, "transform byte exception");
        }
        return out.toByteArray();
    }

    /**
     * 将数据库中的二进制图片转换成位图
     *
     * @param temp
     * @return
     */
    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            // Bitmap bitmap=BitmapFactory.decodeResource(getResources(),
            // R.drawable.contact_add_icon);
            return null;
        }
    }

    /**
     * 将手机中的文件转换为Bitmap类型
     *
     * @param f
     * @return
     */
    public static Bitmap getBitemapFromFile(File f) {
        if (!f.exists())
            return null;
        try {
            return BitmapFactory.decodeFile(f.getAbsolutePath());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 镜像水平翻转
     *
     * @param bmp
     * @return
     */
    public Bitmap convertMirrorBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }

    /**
     * 将手机中的文件转换为Bitmap类型
     *
     * @param fileName
     * @return
     */
    public static Bitmap getBitemapFromFile(String fileName) {

        try {
            return BitmapFactory.decodeFile(fileName);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 把资源图片转换成Bitmap
     *
     * @param drawable 资源图片
     * @return 位图
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getBounds().width();
        int height = drawable.getBounds().height();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 把被系统旋转了的图片，转正
     *
     * @param angle 旋转角度
     * @return bitmap 图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * <<<<<<< HEAD
     * 翻转
     *
     * @param bitmap
     * @return
     */
    public static Bitmap flip(Bitmap bitmap) {
        // 点中了翻转
        Matrix m = new Matrix();
        m.postScale(-1, 1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        return bitmap;
    }

    /**
     * view turnto bitmap
     * =======
     * 将View转为Bitmap
     * >>>>>>> master
     *
     * @param view
     * @return
     */
    public static Bitmap getViewBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);

        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);

        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }


    /**
     * 将View转为Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static void updateResources(Context context, String path) {
        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }

    public static Bitmap returnSaturationBitmap(Bitmap bitmap){
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        // 设置饱和度
        cMatrix.setSaturation(0.0f);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }

    /**
     * 圆形Bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        final int color =0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth()/2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }

    /**
     * 改变bitmap 对比度
     * @param bitmap
     * @param progress
     * @return
     */
    public static Bitmap returnContrastBitmap(Bitmap bitmap, int progress){
        //曝光度
        Bitmap contrast_bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);
        // int brightness = progress - 127;
        float contrast = (float) ((progress + 64) / 128.0);
        ColorMatrix contrast_cMatrix = new ColorMatrix();
        contrast_cMatrix.set(new float[]{contrast, 0, 0, 0, 0, 0,
                contrast, 0, 0, 0,// 改变对比度
                0, 0, contrast, 0, 0, 0, 0, 0, 1, 0});

        Paint contrast_paint = new Paint();
        contrast_paint.setColorFilter(new ColorMatrixColorFilter(contrast_cMatrix));

        Canvas contrast_canvas = new Canvas(contrast_bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        contrast_canvas.drawBitmap(bitmap, 0, 0, contrast_paint);

        return contrast_bmp;
    }

}