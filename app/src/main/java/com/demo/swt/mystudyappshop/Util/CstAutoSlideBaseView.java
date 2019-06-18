package com.demo.swt.mystudyappshop.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Adapter.CstAutoSlideViewPager;
import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;



/**
 * 可自动切换的View基类
 * <p/>
 * 使用：1、可直接在xml中使用也可在代码中直接new；
 * 2、获取数据后直接调用setData方法便可;
 * 3、子类中必须返回图片的高宽等；
 * <p/>
 */
public abstract class CstAutoSlideBaseView<T> extends RelativeLayout {

    protected List<T> list;
    protected int size;
    protected int realSize;


    protected Context context;
    protected CstAutoSlideViewPager viewPager;
    protected CstAutoSlideViewAdapter adapter;
    private Handler handler;
    protected int autoPlayTime = 3000;
    protected boolean isAutoPlay = true;
    private boolean isAutoPlaying;
    protected int height;
    protected int width;
    private int margin;
    private boolean isAddList;
    private ImageView defaultImg;
    private OnItemClickListener<T> onItemListener;
    private LinearLayout indicatorLayout;

    public CstAutoSlideBaseView(Context context) {
        super(context);
        init(context);
    }

    public CstAutoSlideBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public CstAutoSlideBaseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CstAutoSlideBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected abstract int getImageHeight();

    protected abstract int getImageWidth();

    private void init(Context context) {
        this.context = context;
        width = getImageWidth();
        height = getImageHeight();
        setMinimumHeight(height);
        setMinimumWidth(width);
        this.context = context;
        if (null == context) return;
        setFocusImageParams(width, height);
        viewPager = new CstAutoSlideViewPager(context);
        viewPager.setOnPageChangeListener(onPageChangeListener);
        addView(viewPager, new LayoutParams(width, height));

        handler = new Handler();
        margin = convertDIP2PX(10);

        defaultImg = new ImageView(context);
        defaultImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(defaultImg, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        defaultImg.setImageResource(R.drawable.default_banner);
        addIndeCatorLayout();
    }

    private void addIndeCatorLayout(){
        indicatorLayout = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp.bottomMargin = margin;
        addView(indicatorLayout, lp);
    }


    /**
     * 下载好数据后
     *
     * @param list
     */
    public void setData(List<T> list) {
        if (null == list) {
            list = new ArrayList<>();
            list.add(null);
        } else if (list.isEmpty()) {
            list.add(null);
        }

        defaultImg.setVisibility(GONE);
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (null != lp) {
            lp.height = height;
            lp.width = width;
        }

        this.list = list;
        if (null == list || list.isEmpty()) {
            viewPager.setBackgroundResource(R.drawable.default_banner);
        } else {
        }
        realSize = list != null ? list.size() : 0;
        initList();
        size = list != null ? list.size() : 0;
        //默认位置给到尺寸的1000倍
        int num = 1000;
        if (size >= 5) {
            num = 50;
        } else if (size == 4) {
            num = 150;
        } else if (size == 3) {
            num = 200;
        } else if (size == 2) {
            num = 300;
        }

        // 设置indicator
        setPageIndicator();

        adapter = null;
        viewPager.setAdapter(adapter = getAdapter());
        viewPager.setCurrentItem(size = size * num);


        if ((list != null ? list.size() : 0) > 1) {
//            startAutoPlay();
        }
    }

    public void notifyDataSetChanged() {
        if (null != adapter) {
            realSize = list.size();
            initList();
            size = list.size();
            adapter.notifyDataSetChanged();
            if (list.size() > 1 && !isAutoPlaying) {
                startAutoPlay();
            }
        }
    }

    public abstract CstAutoSlideViewAdapter getAdapter();

    /**
     * 设置是否自动切换，默认自动切换
     *
     * @param isAutoPlay
     */
    public void setAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    /**
     * 设置自动切换时间
     *
     * @param autoPlayTime
     */
    public void setAutoPlayTime(int autoPlayTime) {
        this.autoPlayTime = autoPlayTime;
    }


    /**
     * 启动自动切换，在使用页面的onResume中调用
     */
    public void startAutoPlay() {

        if ((list != null ? list.size() : 0) > 1 && !isAutoPlaying) {
            isAutoPlaying = true;
            handler.postDelayed(autoPlayRunnable, autoPlayTime);
        }
    }

    /**
     * 停止自动切换，在使用页面的onPause中调用
     */
    public void stopAutoPlay() {
        if ((list != null ? list.size() : 0) > 1) {
            isAutoPlaying = false;
            handler.removeCallbacks(autoPlayRunnable);
        }
    }

    /**
     * 设置当前组件的最小宽度和高度值
     *
     * @param w
     * @param h
     */
    public void setFocusImageParams(int w, int h) {
        if (w > -1 && h > -1) {
            setMinimumWidth(w);
            setMinimumHeight(h);
            initParams(w, h);
        }
    }

    /**
     * 设置当前组件的LayoutParams
     *
     * @param layoutParams
     */
    public void setFocusImageParams(ViewGroup.LayoutParams layoutParams) {
        if (null != layoutParams) {
            setLayoutParams(layoutParams);
            setFocusImageParams(layoutParams.width, layoutParams.height);
        }
    }

    /**
     * 设置组件默认背景
     *
     * @param resId
     */
    public void setFocusImageBg(int resId) {
        setBackgroundResource(resId);
    }

    /**
     * 设置组件默认背景
     *
     * @param drawable
     */
    public void setFocusImageBg(Drawable drawable) {
        if (null != drawable) {
            setBackgroundDrawable(drawable);
        }
    }

    public int getCount() {
        if (null != list) {
            return list.size();
        }
        return 0;
    }

    private Runnable autoPlayRunnable = new Runnable() {
        @Override
        public void run() {

            if (isAutoPlay && null != viewPager) {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                if (currentItem >= getMax()) {
                    currentItem = 0;
                } else if (currentItem <= 0) {
                    currentItem = getMax();
                }
                viewPager.setCurrentItem(currentItem, true);
                isAutoPlaying = false;
                startAutoPlay();
            }
        }
    };

    protected void onChanged(int currentPosition) {

    }

    //对list处理，防止只有两张图时切换闪白
    private void initList() {
        if (null != list && 2 == list.size()) {
            list.add(2, list.get(0));
            list.add(3, list.get(1));
            isAddList = true;
        }
    }


    private void initParams(int w, int h) {
        this.width = w;
        this.height = h;
    }


    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.onItemListener = itemClickListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null != list && null != viewPager && realSize > 0) {
                int realPosition = viewPager.getCurrentItem() % realSize;
                if (list.size() > realPosition) {
                    onClickItem(realPosition, list.get(realPosition));
                    if (null != onItemListener) {
                        onItemListener.onClickItem(realPosition, list.get(realPosition));
                    }
                }
            }
        }
    };

    protected void onClickItem(int position, T t) {

    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int index) {
            if (0 != realSize) {
                onChanged(index % realSize);
            }

            for (int i = 0; i < realSize; i++) {
                if (i != (index % realSize)){
                    indecatorViews.get(i).setImageResource(pageIndecatorIds[0]);
                }
            }

            indecatorViews.get(index % realSize).setImageResource(pageIndecatorIds[1]);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private int getMax() {
        return Integer.MAX_VALUE / 10;
    }

    public abstract class CstAutoSlideViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getView(position);
            if (null != view) {
                container.addView(view);
                container.getLayoutParams().height = height;
                container.getLayoutParams().width = width;
                view.setOnClickListener(onClickListener);
            }
            return view;
        }


        protected abstract View getView(int position);

        protected T getItem(int position) {

            if (realSize > 0 && list != null && (position % realSize) < list.size()) {
                return list.get(position % realSize);
            }
            return null;
        }


        @Override
        public int getCount() {
            return getMax();
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * POSITION_NONE : This element neither has attached source nor attached
         * Javadoc and hence no Javadoc could be found.
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        protected void setTextView(TextView t, String s) {
            if (null != t && null != s) {
                t.setText(s);
            }
        }

    }

    /**
     * item的点击事件
     */
    public interface OnItemClickListener<T> {
        void onClickItem(int position, T focus);
    }


    private int convertDIP2PX(float dip) {
        if (null != context) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
        }
        return (int) dip;
    }

    private int getViewWidth(final View v) {
        if (null != v) {
            int w = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            return v.getMeasuredWidth();
        }
        return 0;
    }


    public void setDataNotStart(List<T> list) {
        if (isAutoPlaying) {
            stopAutoPlay();
        }
        if (null == list) {
            list = new ArrayList<>();
            list.add(null);
        } else if (list.isEmpty()) {
            list.add(null);
        }
        defaultImg.setVisibility(GONE);
        getLayoutParams().height = height;
        getLayoutParams().width = width;
        this.list = list;
        if (null == list || list.isEmpty()) {
//            setVisibility(View.GONE);
//            return;
            viewPager.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
//            setVisibility(View.VISIBLE);
        }
        realSize = list != null ? list.size() : 0;
        initList();
        size = list != null ? list.size() : 0;
        //默认位置给到尺寸的1000倍
        int num = 1000;
        if (size >= 5) {
            num = 50;
        } else if (size == 4) {
            num = 150;
        } else if (size == 3) {
            num = 200;
        } else if (size == 2) {
            num = 300;
        }
        adapter = null;
        viewPager.setAdapter(adapter = getAdapter());
        viewPager.setCurrentItem(size = size * num);
    }


    private ArrayList<ImageView> indecatorViews = new ArrayList<ImageView>();
    int[] pageIndecatorIds = new int[]{R.drawable.slide_indicator_focus, R.drawable.slide_indicator_normal};

    /**
     * 底部指示器资源图片
     *
     * @param
     */
    public void setPageIndicator() {
        indicatorLayout.removeAllViews();
        indecatorViews.clear();
        if (realSize == 0) return;
        int size = realSize;
        if(isAddList&&realSize==4){
            realSize = size = 2;
        }
        for (int count = 0; count < size; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (indecatorViews.isEmpty())
                pointView.setImageResource(pageIndecatorIds[1]);
            else
                pointView.setImageResource(pageIndecatorIds[0]);
            indecatorViews.add(pointView);
            indicatorLayout.addView(pointView);
        }

    }


    public void setDefaultImage(int resId){
        if(defaultImg!=null){
            defaultImg.setImageResource(resId);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoPlay();
    }
}
