package com.demo.swt.mystudyappshop.Util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class LogUtils {


    private static String TAG = "SWT";

  

    /*正常日志*/
    public static void i(final String tag, final String s){
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s)){
            Log.i(tag, s);
        }
    }

    public static void d(final String tag, final String s){
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s)){
            Log.d(tag, s);
        }
    }

    public static void v(final String tag, final String s){
        if (  !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s)){
            Log.v(tag, s);
        }
    }

    public static void e(final String tag, final String s){
        if (  !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s)){
            Log.e(tag, s);
        }
    }

    public static void w(final String tag, final String s){
        if ( !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s)){
            Log.w(tag, s);
        }
    }

    /*加Throwable日志*/
    public static void i(final String tag, final String s, final Throwable tr){
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s) && null != tr){
            Log.i(tag, s, tr);
        }
    }

    public static void d(final String tag, final String s, final Throwable tr){
        if ( !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s) && null != tr){
            Log.d(tag, s, tr);
        }
    }

    public static void v(final String tag, final String s, final Throwable tr){
        if ( !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s) && null != tr){
            Log.v(tag, s, tr);
        }
    }

    public static void e(final String tag, final String s, final Throwable tr){
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s) && null != tr){
            Log.e(tag, s, tr);
        }
    }

    public static void w(final String tag, final String s, final Throwable tr){
        if ( !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s) && null != tr){
            Log.w(tag, s, tr);
        }
    }




    public static void i(final String s){
        if ( !TextUtils.isEmpty(s)){
            Log.i(TAG, s);
        }
    }

    public static void d(final String s){
        if (  !TextUtils.isEmpty(s)){
            Log.d(TAG, s);
        }
    }

    public static void v(final String s){
        if (  !TextUtils.isEmpty(s)){
            Log.v(TAG, s);
        }
    }

    public static void e(final String s){
        if (  !TextUtils.isEmpty(s)){
            Log.e(TAG, s);
        }
    }

    public static void w(final String s){
        if (  !TextUtils.isEmpty(s)){
            Log.w(TAG, s);
        }
    }

    /*加Throwable日志*/
    public static void i(final String s, final Throwable tr){
        if ( !TextUtils.isEmpty(s) && null != tr){
            Log.i(TAG, s, tr);
        }
    }

    public static void d(final String s, final Throwable tr){
        if (  !TextUtils.isEmpty(s) && null != tr){
            Log.d(TAG, s, tr);
        }
    }

    public static void v(final String s, final Throwable tr){
        if (  !TextUtils.isEmpty(s) && null != tr){
            Log.v(TAG, s, tr);
        }
    }

    public static void e(final String s, final Throwable tr){
        if (  !TextUtils.isEmpty(s) && null != tr){
            Log.e(TAG, s, tr);
        }
    }

    public static void w(final String s, final Throwable tr){
        if (  !TextUtils.isEmpty(s) && null != tr){
            Log.w(TAG, s, tr);
        }
    }
}
