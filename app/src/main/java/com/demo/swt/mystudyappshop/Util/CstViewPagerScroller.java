package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * ViewPager 滚动速度设置
 * 
 * 作者：xjzhao
 * 时间：2015-03-27 下午5:21
 */
public class CstViewPagerScroller extends Scroller {
    private int mScrollDuration = 1500;             // 滑动速度
 
    /**
     * 设置速度速度
     * @param duration
     */
    public void setScrollDuration(int duration){
        this.mScrollDuration = duration;
    }
     
    public CstViewPagerScroller(Context context) {
        super(context);
    }
 
    public CstViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
 
    public CstViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }
 
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
 
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
 
     
     
    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
 
