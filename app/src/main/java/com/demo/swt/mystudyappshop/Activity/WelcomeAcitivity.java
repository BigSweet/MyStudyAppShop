package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;

import com.demo.swt.mystudyappshop.MainActivity;
import com.demo.swt.mystudyappshop.Util.SharedPreferencesUtils;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/9
 */

public class WelcomeAcitivity extends FragmentActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesUtils.getInstance().getString("lock") != null && SharedPreferencesUtils.getInstance().getString("lock").equals("false")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeAcitivity.this, WelcomeNineClockActivity.class));
                    finish();
                }
            }, 1000);
        }


    }
}
