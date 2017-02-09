package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.demo.swt.mystudyappshop.Fragment.PassWordFragment;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/9
 */

public class WelcomeAcitivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
                String password = sp.getString("password", "");
                //没有设置密码
                if (TextUtils.isEmpty(password)) {
                    startActivity(new Intent(WelcomeAcitivity.this, NineLockActivity.class));
                    finish();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(android.R.id.content, PassWordFragment.newInstance(PassWordFragment.TYPE_CHECK)).commit();
                }
            }
        }, 1000);

    }
}
