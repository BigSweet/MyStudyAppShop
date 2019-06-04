package widght;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FixedNestedScrollView extends NestedScrollView {

    public FixedNestedScrollView(Context context) {
        super(context);
    }

    public FixedNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

  /*  @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        boolean flag = super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        try {
            if (dy == consumed[1] && dy != 0) {//发现如果NestedScrollView作为child，并且dy被parent消费了，则mIsBeingDragged为false，导致没有fling
                Field field = getClass().getSuperclass().getDeclaredField("mIsBeingDragged");
                field.setAccessible(true);
                field.set(this, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }*/


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {


        //先parent消费
        super.onNestedPreScroll(target, dx, dy, consumed);
        //我再消费
        if (dy > 0 && canScrollVertically(1)) {
            scrollBy(0, dy - consumed[1]);//减去parent消费的距离
            consumed[1] = dy;
        }
    }

    /**
     * 由子view的dispatchNestedPreFling方法触发
     * 嵌套滑动子View fling(滑行)前的准备工作
     *
     * @param target    实现嵌套滑动的子View
     * @param velocityX 水平方向上的速度
     * @param velocityY 竖直方向上的速度
     * @return true 父View是否消耗了fling
     * velocityY判断子view的滑动方向>0为向上滑，接下里判断自身是否可以向上滑，可以就消耗
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //先让parent处理
        boolean flag = super.onNestedPreFling(target, velocityX, velocityY);
        //如果parent没有处理，我再处理
        if (!flag) {
            if (velocityY > 0) {
                if (canScrollVertically(1)) {
                    fling((int) Math.max(velocityY * 2 / 3, 1000));//最小加速度1000，防止加速度太小，感觉不流畅
                    flag = true;
                }
            } else if (velocityY < 0) {
                if (canScrollVertically(-1)) {
                    fling((int) Math.min(velocityY * 2 / 3, -1000));//同上
                    flag = true;
                }
            }
        }
        Log.d("FixedNestedScrollView", "velocityY=" + velocityY);
        return flag;
    }

}