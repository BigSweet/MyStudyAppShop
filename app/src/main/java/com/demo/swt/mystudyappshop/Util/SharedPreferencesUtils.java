package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public class SharedPreferencesUtils {
    public static final String UPDATE_KEY = "Anlaiye_Update";
    public static final String COMMENT_DRAFT = "commentDraft";//保存评论草稿

    private static Context context;

    public static void setContext(Context Context) {
        SharedPreferencesUtils.context = Context;
    }

    public static void setPreferences(String preference, String key, boolean value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setPreferences(String preference, String key, long value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setPreferences(String preference, String key, String value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(String preference, String key, String defaultValue) {
        if (context == null) return null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setPreferences(String preference, String key, int value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static boolean getPreference(String preference, String key, boolean defaultValue) {
        if (context == null) return defaultValue;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static long getPreference(String preference, String key, long defaultValue) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static int getPreference(String preference, String key, int defaultValue) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void clearPreference(String preference) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}