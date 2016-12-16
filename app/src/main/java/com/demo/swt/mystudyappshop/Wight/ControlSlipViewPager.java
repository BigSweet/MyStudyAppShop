package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 定制ViewPager，实现可以控制其滑动效果
 * 
 * @author xjzhao
 *
 */
public class ControlSlipViewPager extends ViewPager {

	private boolean enabled = true;
	public ControlSlipViewPager(Context context) {
		super(context);
//        postInitViewPager(context);
    }

	public ControlSlipViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
//        postInitViewPager(context);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (enabled) {
			try{
				return super.onTouchEvent(event);
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (enabled) {
			try {
				return super.onInterceptTouchEvent(event);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}


//    private PhotosViewPagerScroll mScroller;
//
//    /**
//     * Override the Scroller instance with our own class so we can change the
//     * duration
//     */
//    private void postInitViewPager(Context context) {
//        try {
//            Field scroller = ViewPager.class.getDeclaredField("mScroller");
//            scroller.setAccessible(true);
//            PhotosViewPagerScroll mScroller = new PhotosViewPagerScroll(context, new AccelerateInterpolator());
//            scroller.set(this, mScroller);
//        } catch (Exception e) {
//        }
//    }


    /**
	 * 设置viewpager滑动是否有效
	 * @param enabled
	 */
	public void setViewPagerSlidEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
