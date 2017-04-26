package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.demo.swt.mystudyappshop.MyApplication;


public class DisplayUtils {

    private static Context context;

    public static void setContext(Context Context) {
        DisplayUtils.context = Context;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (null == context) return (int) dpValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //获取屏幕的宽
    public static int getScreenWidth() {
        return MyApplication.getmContext().getResources().getDisplayMetrics().widthPixels;
    }
    //获取屏幕的高度
    public static int getScreenHeight() {
        final int height = MyApplication.getmContext().getResources().getDisplayMetrics().heightPixels;
        return height;
    }


    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }


    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        if (null == context) return (int) pxValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px（正常字体下，1sp=1dp）
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        if (null == context) return (int) spValue;
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * sp转dp
     *
     * @param spValue
     * @return
     */
    public static int sp2dp(float spValue) {
        int sp2Px = sp2px(spValue);
        return px2dip(sp2Px);
    }


    public static int getQToPx(int rid) {
        return (int) context.getResources().getDimension(rid);
    }

    public static Drawable getDrawable(int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }
}
