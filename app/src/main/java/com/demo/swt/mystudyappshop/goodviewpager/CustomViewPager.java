package com.demo.swt.mystudyappshop.goodviewpager;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/16
 */

public class CustomViewPager extends ViewPager {
    long downTime;
    float LastX;
    float mSpeed;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                LastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                x = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                //计算得到手指从按下到离开的滑动速度
                mSpeed = (x - LastX) * 1000 / (System.currentTimeMillis() - downTime);
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float mSpeed) {
        this.mSpeed = mSpeed;
    }
}
