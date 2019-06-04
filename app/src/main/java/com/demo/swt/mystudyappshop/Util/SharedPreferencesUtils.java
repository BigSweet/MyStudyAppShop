package com.demo.swt.mystudyappshop.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.demo.swt.mystudyappshop.MyApplication;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;

    private SharedPreferences.Editor editor;
    private SharedPreferences prefer;

    public SharedPreferencesUtils() {
        this.prefer = PreferenceManager.getDefaultSharedPreferences(MyApplication.getmContext());
        this.editor = this.prefer.edit();
    }

    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils();
                }
            }
        }

        return instance;
    }

    public void saveString(String name, String data) {
        this.editor.putString(name, data);
        this.editor.commit();
    }

    public String getString(String name) {
        return this.prefer.getString(name, null);
    }

}