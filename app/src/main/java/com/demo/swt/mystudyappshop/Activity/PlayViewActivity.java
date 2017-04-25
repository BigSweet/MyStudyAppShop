package com.demo.swt.mystudyappshop.Activity;

import android.support.v4.app.Fragment;

import com.demo.swt.mystudyappshop.BasePackage.SWBaseActivity;
import com.demo.swt.mystudyappshop.Fragment.PlayViewFragment;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/25
 */
public class PlayViewActivity extends SWBaseActivity {
    @Override
    protected Fragment getFragment() {
        return Fragment.instantiate(this, PlayViewFragment.class.getName(),getIntent().getExtras());
    }


}
