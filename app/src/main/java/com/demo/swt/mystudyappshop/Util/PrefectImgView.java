package com.demo.swt.mystudyappshop.Util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/12
 */
public class PrefectImgView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener {

    private final static int SINGLE_TOUCH = 1; //单指
    private final static int DOUBLE_TOUCH = 2; //双指

    //多指触控模式，单指，双指
    private int mode;

    //两指触碰点之间的距离
    private float oldDist;
    private float newDist;

    /**
     * 最大缩放级别
     */
    private static final float MAX_SCALE = 5f;
    /**
     * 双击时的缩放级别
     */
    private float mDoubleClickScale = 2;


    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;
    private boolean once = true;
    private RectF rectF;

    /**
     * 用于双击检测
     */
    private GestureDetector mGestureDetector;
    private int x = 0;
    private int y = 0;

    private Point mPoint = new Point();

    private final Matrix matrix = new Matrix();
    private Matrix oldMatrix = new Matrix();

    private ValueAnimator animator;
    int width = DisplayUtils.getScreenWidth();
    int height = DisplayUtils.getScreenHeight();

    public PrefectImgView(Context context) {
        this(context, null);
    }

    public PrefectImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
        /**
         * 双击实现图片放大缩小
         */
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        changeViewSize(e);
                        return true;
                    }


                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Log.d("ss", "单击了一下");
                        mOnSingleTapConfirmedClick.click();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        mOnSingleTapConfirmedClick.onlongClick();
                    }
                });
    }


    public interface onSingleTapConfirmedClick {
        void click();

        void onlongClick();
    }

    public onSingleTapConfirmedClick mOnSingleTapConfirmedClick;

    public void setonSingleTapConfirmedClick(onSingleTapConfirmedClick singleTapConfirmedClick) {
        mOnSingleTapConfirmedClick = singleTapConfirmedClick;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        rectF = getMatrixRectF(); //获取图片边界范围
        if (mGestureDetector.onTouchEvent(event))
            return true;

        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //如果放大后图片的边界超出了屏幕，那么就拦截事件，不让viewPager处理
                if (rectF.width() > width || rectF.height() > height) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mode = SINGLE_TOUCH;

                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode >= DOUBLE_TOUCH) //双指缩放
                {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    newDist = calculateDist(event); //计算距离
                    Point point = getMiPoint(event); //获取两手指间的中点坐标
                    if (newDist > oldDist + 1) //放大(加一是为了防止抖动)
                    {
                        changeViewSize(oldDist, newDist, point); //根据距离实现放大缩小
                        oldDist = newDist;
                    }
                    if (oldDist > newDist + 1) //缩小
                    {
                        changeViewSize(oldDist, newDist, point);
                        oldDist = newDist;
                    }
                }
                if (mode == SINGLE_TOUCH) //单指拖拽
                {
                    float dx = event.getRawX() - x;
                    float dy = event.getRawY() - y;

                    //如果移动过程中图片的边界超出了屏幕，那么就拦截事件，不让viewPager处理
                    if (rectF.width() > width || rectF.height() > height) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    //如果向右移动图片到了尽头，那么就不要拦截事件，让viewPager处理
                    if (rectF.left >= 0 && dx > 0)
                        getParent().requestDisallowInterceptTouchEvent(false);

                    //如果向左移动到了尽头，那么就不要拦截事件，让viewPager处理
                    if (rectF.right <= width && dx < 0)
                        getParent().requestDisallowInterceptTouchEvent(false);

                    if (getDrawable() != null) {
                        //如果图片宽度或高度没有超出屏幕，那么就禁止左右或上下滑动
                        if (rectF.width() <= width)
                            dx = 0;
                        if (rectF.height() < height)
                            dy = 0;

                        //如果图片向下移动到了尽头，不让它继续移动
                        if (rectF.top >= 0 && dy > 0)
                            dy = 0;
                        //如果图片向上移动到了尽头，不让它继续移动
                        if (rectF.bottom <= height && dy < 0)
                            dy = 0;

                        //当移动距离大于1的时候再移动，因为ACTION_MOVE比较灵敏，
                        // 手指即使只是放在上面，依然能够检测到手指的抖动，然后让图片移动。
                        if (Math.abs(dx) > 1 || Math.abs(dy) > 1)
                            matrix.postTranslate(dx, dy);
                        setImageMatrix(matrix);
                    }
                }
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode += 1;
                oldDist = calculateDist(event);
                Log.e("q", "" + "a");

                Log.e(":::", "" + event.getPointerCount() + "   " + event.getActionIndex() + "   " + event.findPointerIndex(0));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode -= 1;
                break;
            case MotionEvent.ACTION_UP:
                backToPosition();
                mode = 0;
                break;
            //在ACTION_MOVE中，事件被拦截了之后，有时候ACTION_UP无法触发，所以加上了ACTION_CANCEL
            case MotionEvent.ACTION_CANCEL:
                backToPosition();
                mode = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 计算两指触摸点之间的距离
     */
    private float calculateDist(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 若是在移动后图片的边界脱离屏幕边界，那么就让图片边界与屏幕边界重合
     * 若手指快速移动，停止后会出现图片距离屏幕有一段空白距离，然后经过判断不能再移动，
     * 但是在进行下一次判断是否可以继续移动之前就已经出现了。
     * 所以需要复位
     */
    private void backToPosition() {
        if (rectF.left >= 0) { //图片左边界与屏幕出现距离
            matrix.postTranslate(-rectF.left, 0);
            setImageMatrix(matrix);
        }
        if (rectF.right <= width) { //图片右边界与屏幕出现距离
            matrix.postTranslate(width - rectF.right, 0);
            setImageMatrix(matrix);
        }

//        if (rectF.height() < width || (rectF.width() <= width)) {
//            return;
//        }
        if (rectF.top >= 0) { //图片上边界与屏幕出现距离
            matrix.postTranslate(0, -rectF.top);
            setImageMatrix(matrix);
        }
        if (rectF.bottom <= height) { //图片下边界与屏幕出现距离
            matrix.postTranslate(0, height - rectF.bottom);
            setImageMatrix(matrix);
        }
    }


    /**
     * 获取双指缩放时候的缩放中点
     *
     * @return
     */
    private Point getMiPoint(MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);

        mPoint.set((int) x / 2, (int) y / 2);
        return mPoint;
    }

    /**
     * 双指缩放图片
     */
    private void changeViewSize(float oldDist, float newDist, Point mPoint) {
        float scale = newDist / oldDist; //缩放比例

        matrix.postScale(scale, scale, mPoint.x, mPoint.y);
        checkBorderAndCenterWhenScale();
        setImageMatrix(matrix);

        //防止缩小的时候小于初始的图片大小，需要重置
        reSetMatrix();
        //如果缩放已经大于目标倍数，停止，因为有可能已经超出，那么就直接缩放到目标大小
        if (getMatrixValueX() >= MAX_SCALE) {
            matrix.postScale(MAX_SCALE / getMatrixValueX(), MAX_SCALE / getMatrixValueX(), x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);
            return;
        }
    }

    /**
     * 双击缩放图片
     */
    private void changeViewSize(MotionEvent e) {

        //获取双击的坐标
        final float x = e.getX();
        final float y = e.getY();

        //如果此时还在缩放那就直接返回
        if (animator != null && animator.isRunning())
            return;

        //判断是处于放大还是缩小的状态
        if (!isZoomChanged()) {
            animator = ValueAnimator.ofFloat(1.0f, 2.0f);
        } else {
            animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        }
        animator.setTarget(this);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                Float value = (Float) animator.getAnimatedValue();
                matrix.postScale(value, value, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(matrix);

                /**
                 * 控制缩小的范围
                 * 如果已经小于初始大小，那么恢复到初始大小，然后停止
                 */
                if (checkRestScale()) {
                    matrix.set(oldMatrix);
                    setImageMatrix(matrix);
                    return;
                }
                /**
                 * 控制放大的范围
                 * 如果已经大于目标的放大倍数，那么直接置为目标的放大倍数
                 * 然后停止
                 */
                if (getMatrixValueX() >= mDoubleClickScale) {
                    matrix.postScale(mDoubleClickScale / getMatrixValueX(), mDoubleClickScale / getMatrixValueX(), x, y);
                    checkBorderAndCenterWhenScale();
                    setImageMatrix(matrix);
                    return;
                }
            }
        });
    }

    /**
     * 判断缩放级别是否是改变过
     *
     * @return true表示非初始值, false表示初始值
     */
    private boolean isZoomChanged() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        oldMatrix.getValues(values);
        return scale != values[Matrix.MSCALE_X];
    }

    /**
     * 重置Matrix
     */
    private void reSetMatrix() {
        if (checkRestScale()) {
            matrix.set(oldMatrix);
            setImageMatrix(matrix);
            return;
        }
    }

    /**
     * 设置双击放大的倍数
     */
    private void setDoubleClickScale(RectF rectF) {
        if (rectF.height() < height - 100) {
            mDoubleClickScale = height / rectF.height();
        } else
            mDoubleClickScale = 2f;
    }

    /**
     * 判断是否需要重置
     *
     * @return 当前缩放级别小于模板缩放级别时，重置
     */
    private boolean checkRestScale() {
        // TODO Auto-generated method stub
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        oldMatrix.getValues(values);
        return scale < values[Matrix.MSCALE_X];
    }

    private float getMatrixValueX() {
        // TODO Auto-generated method stub
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        oldMatrix.getValues(values);
        return scale / values[Matrix.MSCALE_X];
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e("TAG", "deltaX = " + deltaX + " , deltaY = " + deltaY);

        matrix.postTranslate(deltaX, deltaY);
        setImageMatrix(matrix);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect); //如果没有这个，那么下面Log的输出将会与上一句的一样。
        }
        Log.e("aaaa", "" + rect.bottom + "  " + rect.left + "   " + rect.right + "  " + rect.top);
        return rect;
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            Log.e("TAG", d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            int dy = (height - dh) / 2;
            int py = height / 2;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dw < width) {
                scale = width * 1.0f / dw;
//                if (dh * scale > height){
//                    dy = 0;
//                }
            }
            if (dh > height && dw <= width) {
                py = 0;
                dy = 0;
                scale = width * 1.0f / dw;
                Log.e("TAG", "scale = " + scale + "scale1=" + scale);
            }

            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            initScale = scale;

            matrix.postTranslate((width - dw) / 2, dy);
            matrix.postScale(scale, scale, width / 2,
                    py);
            // 图片移动至屏幕中心
            setImageMatrix(matrix);

            oldMatrix.set(getImageMatrix());
            once = false;

            RectF rectF = getMatrixRectF();
            setDoubleClickScale(rectF);

        }
    }
}
