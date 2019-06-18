package com.demo.swt.mystudyappshop.Activity;

import androidx.fragment.app.Fragment;

import com.demo.swt.mystudyappshop.BasePackage.SWBaseActivity;
import com.demo.swt.mystudyappshop.Fragment.WelcomeFragment;

/**
 * introduce：这里写介绍
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 25/7/18
 */
public class WelcomeNineClockActivity extends SWBaseActivity {
    @Override
    protected Fragment getFragment() {
        return WelcomeFragment.newInstance();
    }
}
