package com.funny.addworddemo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Wxcily on 15/10/29.
 * 获取设备信息
 */
@SuppressWarnings("deprecation")
public class DeviceInfoUtils {
    private DeviceInfoUtils() {
    }

    /**
     * 得到手机IMEI
     *
     * @param context
     * @return String
     */
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return int[widthPixels, heightPixels]
     */
    public static int[] getScreenSize(Context context) {
        int[] screens;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screens = new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return float
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return metrics.density;
    }

    /**
     * 判断是否是平板电脑
     *
     * @param context
     * @return boolean
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获得屏幕尺寸 如4.5寸
     *
     * @param ctx
     * @return
     */
    public static double getScreenPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
                + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }


    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
            return root;
        }
        return root;
    }

    /**
     * 输出可用内存
     *
     * @param context
     * @return String
     */

    public static String getAvailMemory(Context context) {

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);
    }

    /**
     * 输出总内存
     *
     * @param context
     * @return String
     */
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        int initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split(":");
            str2 = arrayOfString[1].trim();
            arrayOfString = str2.split(" ");
            initial_memory = Integer.valueOf(arrayOfString[0].trim())
                    .intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 手机自带闪存信息
     *
     * @param context
     * @return String
     */
    public static String getCapacity(Context context) {
        File data = Environment.getDataDirectory();
        StatFs statFs = new StatFs(data.getPath());
        int availableBlocks = statFs.getAvailableBlocks();// 可用存储块的数量
        int blockCount = statFs.getBlockCount();// 总存储块的数量
        int size = statFs.getBlockSize();// 每块存储块的大小
        int totalSize = blockCount * size;// 总存储量
        int availableSize = availableBlocks * size;// 可用容量
        if (availableSize > totalSize) {
            int tmp;
            tmp = totalSize;
            totalSize = availableSize;
            availableSize = tmp;
        }
        String phoneCapacity = Formatter.formatFileSize(context, availableSize)
                + " / " + Formatter.formatFileSize(context, totalSize);
        return phoneCapacity;
    }

    /**
     * 判断是否存在SD卡
     *
     * @return boolean
     */
    public static boolean hasSDcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回SD卡信息
     *
     * @param context
     * @return String
     */
    public static String getSDcardCapacity(Context context) {
        File sdData = Environment.getExternalStorageDirectory();
        StatFs sdStatFs = new StatFs(sdData.getPath());
        int sdAvailableBlocks = sdStatFs.getAvailableBlocks();// 可用存储块的数量
        int sdBlockcount = sdStatFs.getBlockCount();// 总存储块的数量
        int sdSize = sdStatFs.getBlockSize();// 每块存储块的大小
        int sdTotalSize = sdBlockcount * sdSize;
        int sdAvailableSize = sdAvailableBlocks * sdSize;
        if (sdAvailableSize > sdTotalSize) {
            int tmp;
            tmp = sdTotalSize;
            sdTotalSize = sdAvailableSize;
            sdAvailableSize = tmp;
        }
        String sdcardCapacity = Formatter.formatFileSize(context,
                sdAvailableSize)
                + " / "
                + Formatter.formatFileSize(context, sdTotalSize);
        return sdcardCapacity;
    }

    /**
     * 返回CPU信息
     *
     * @return String
     */

    public static String getCpuInfos() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            @SuppressWarnings("resource")
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return strInfo;
    }

    /**
     * 返回CPU类型
     *
     * @return String
     */

    public static String getCpuType() {
        String strInfo = getCpuInfos();
        String strType = null;

        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }
        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }

        return strType;
    }

    /**
     * 返回CPU ABI
     *
     * @returnString
     */
    public static String getCPU_ABI() {
        return Build.CPU_ABI;
    }

    /**
     * 返回安卓版本号
     *
     * @return String;
     */
    public static String getSDK() {
        return Build.VERSION.SDK;
    }

    /**
     * 返回OS版本号
     *
     * @return String;
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 返回设备名
     *
     * @return String;
     */
    public static String getDeviceName() {
        return Build.DEVICE;
    }

    /**
     * 返回OS名
     *
     * @return String;
     */
    public static String getOSName() {
        return Build.DISPLAY;
    }

    /**
     * 返回Mac地址
     *
     * @param context
     * @return String
     */
    public static String getMac(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String mac = wifi.getConnectionInfo().getMacAddress();
        return mac;
    }

    /**
     * 获取摄像头数量
     *
     * @return int
     */
    public static int getCameraNumber() {
        return Camera.getNumberOfCameras();
    }
}
