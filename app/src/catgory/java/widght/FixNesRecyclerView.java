package widght;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ScrollerCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 13/4/18
 */
public class FixNesRecyclerView extends RecyclerView {
    private static final String TAG = "NesRecyclerView";
    private ScrollerCompat scrollerCompat;
    private OverScroller overScroller;
    private int startY;
    public FixNesRecyclerView(Context context) {
        this(context,null);
    }

    public FixNesRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FixNesRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createScroller();
    }

    /**
     * 滑动后触发，嵌套滑动流程
     * startNestedScroll开始滑动
     * dispatchNestedPreFling分发滑动
     * stopNestedScroll结束滑动
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        startY += dy;

        if (getScrollState() == SCROLL_STATE_SETTLING && (startY == 0 || !canScrollVertically(-1))) {
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, 0);
            if (overScroller != null) {
                dispatchNestedPreFling(0, -overScroller.getCurrVelocity());
            }
            if (scrollerCompat != null) {
                dispatchNestedPreFling(0, -scrollerCompat.getCurrVelocity()); //手动添加方向
            }
            stopNestedScroll();
        }
        Log.d("FixNesRecyclerView","startY="+startY+"overScroller="+(-overScroller.getCurrVelocity()));
    }


    /**
     **分发滑动，如果自身没有滑动到最上面则返回fasle，不传递给父类
     * canScrollVertically(-1)是否可以下拉 fasle为不能下拉，true为可以下拉
     * dispatchNestedPreFling方法会触发父类的onNestedPreFling方法
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (velocityY < 0 && canScrollVertically(-1)) {//如果我没有滚动到最顶上，则不传递给parent
            return false;
        } else {
            return super.dispatchNestedPreFling(velocityX, velocityY);
        }
    }

    private void createScroller() {
        try {
            Field viewFlinger = this.getClass().getSuperclass().getDeclaredField("mViewFlinger");
            viewFlinger.setAccessible(true);
            Object viewFlingerObject = viewFlinger.get(this);
            Class<?> viewFlingerClazz = Class.forName("android.support.v7.widget.RecyclerView$ViewFlinger");
            if (viewFlingerClazz.isInstance(viewFlingerObject)) {
                Object scrollerCompatObject = viewFlingerClazz.cast(viewFlingerObject);
                Field mScrollerCompat = viewFlingerClazz.getDeclaredField("mScroller");
                mScrollerCompat.setAccessible(true);

                Object scroller = mScrollerCompat.get(scrollerCompatObject);
                if (scroller instanceof OverScroller) {
                    overScroller = (OverScroller) scroller;
                } else if (scroller instanceof ScrollerCompat) {
                    scrollerCompat = (ScrollerCompat) scroller;
                }

                Log.d(TAG, "release check ok");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.d(TAG, "release check failed->NoSuchFieldException:" + e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d(TAG, "release check failed->IllegalAccessException:" + e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "release check failed->ClassNotFoundException:" + e.toString());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
