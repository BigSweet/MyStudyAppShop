package com.demo.swt.mystudyappshop.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.demo.swt.mystudyappshop.Fragment.PassWordFragment;

/**
 * 介绍：这是设置密码界面
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/8
 */

public class NineLockActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, PassWordFragment.newInstance(PassWordFragment.TYPE_SETTING)).commit();
    }

}
