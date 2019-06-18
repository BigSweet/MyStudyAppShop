package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.demo.swt.mystudyappshop.Util.CstViewPagerScroller;

/**
 * 作者：xjzhao
 * 时间：2014-09-12 下午2:00
 */
public final class CstAutoSlideViewPager extends ViewPager {
    private CstViewPagerScroller scroller;
    private boolean isScrollable = true;

    public CstAutoSlideViewPager(Context context) {
        super(context);
        init();

    }

    public CstAutoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        scroller = new CstViewPagerScroller(getContext());
        scroller.initViewPagerScroll(this);
        isScrollable = true;
    }

    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getParent()!=null){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                // 计算在X和Y方向的偏移量
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                ViewParent parent = getParent();
                if (parent != null) {
                    // 横向滑动小于纵向滑动时不截断事件
                    if (xDistance < yDistance) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    public void setIsScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }
}
