package com.demo.swt.mystudyappshop.GoodDraweeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/8
 */

public class LockPatternView extends View {
    //创建一个3行3列的矩阵

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //矩阵
    private Matrix mMatrix = new Matrix();
    private Point[][] mPoints = new Point[3][3];
    private boolean isInit, isSelect, isFinish, movingButNoPoint;
    private float width, height, offsetx, offsety, bitmapR, moveX, moveY;
    private List<Point> pressPoint = new ArrayList<>();
    private Bitmap Line_Error, Line_Pressed, Point_Nomal, Point_Error, Point_Pressed;

    public LockPatternView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) {
            initPoint();
        }

        //画点
        point2Canvas(canvas);

        if (pressPoint.size() > 0) {
            Point a = pressPoint.get(0);
            //绘制9宫格里面的点
            for (int i = 0; i < pressPoint.size(); i++) {
                Point b = pressPoint.get(i);
                line2Canvas(canvas, a, b);
                a = b;


            }
            //绘制鼠标里面的坐标点
            if (movingButNoPoint) {
                line2Canvas(canvas, a, new Point(moveX, moveY));
            }
        }

    }

    private void point2Canvas(Canvas canvas) {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                if (point.state == Point.State_nomal) {
                    canvas.drawBitmap(Point_Nomal, point.x - bitmapR, point.y - bitmapR, mPaint);
                } else if (point.state == Point.State_pressed) {
                    canvas.drawBitmap(Point_Pressed, point.x - bitmapR, point.y - bitmapR, mPaint);
                } else if (point.state == Point.State_error) {
                    canvas.drawBitmap(Point_Error, point.x - bitmapR, point.y - bitmapR, mPaint);
                }
            }
        }

    }

    private void line2Canvas(Canvas canvas, Point a, Point b) {
        float linelength = (float) Point.distance(a, b);
        float degress = getDegrees(a, b);
        canvas.rotate(degress, a.x, a.y);
        if (a.state == Point.State_pressed) {
            mMatrix.setScale(linelength / Line_Pressed.getWidth(), 1);
            mMatrix.postTranslate(a.x, a.y - Line_Pressed.getHeight() / 2);
            canvas.drawBitmap(Line_Pressed, mMatrix, mPaint);
        } else {
            mMatrix.setScale(linelength / Line_Error.getWidth(), 1);
            mMatrix.postTranslate(a.x, a.y - Line_Error.getHeight() / 2);
            canvas.drawBitmap(Line_Error, mMatrix, mPaint);
        }

        canvas.rotate(-degress, a.x, a.y);
    }


    public float getDegrees(Point pointA, Point pointB) {

        return (float) Math.toDegrees(Math.atan2(pointB.y - pointA.y, pointB.x - pointA.x));

    }

    private void initPoint() {
        //1,获取布局的宽度和高度
        width = getWidth();

        height = getHeight();
        //2,xy的偏移量
        //如果屏幕的宽度大于高度那就是横屏
        if (width > height) {
            offsetx = (width - height) / 2;
            width = height;
        } else//否则就是竖屏
        {
            offsety = (height - width) / 2;
            height = width;
        }
        //3,图片资源
        Point_Error = BitmapFactory.decodeResource(getResources(), R.mipmap.oval_error);
        Point_Nomal = BitmapFactory.decodeResource(getResources(), R.mipmap.oval_pressed);
        Point_Pressed = BitmapFactory.decodeResource(getResources(), R.mipmap.oval_normal);
        Line_Error = BitmapFactory.decodeResource(getResources(), R.mipmap.line_error);
        Line_Pressed = BitmapFactory.decodeResource(getResources(), R.mipmap.line_pressed);

        //4,点的坐标
        mPoints[0][0] = new Point(offsetx + width / 4, offsety + width / 4);
        mPoints[0][1] = new Point(offsetx + width / 2, offsety + width / 4);
        mPoints[0][2] = new Point(offsetx + width * 3 / 4, offsety + width / 4);

        mPoints[1][0] = new Point(offsetx + width / 4, offsety + width / 2);
        mPoints[1][1] = new Point(offsetx + width / 2, offsety + width / 2);
        mPoints[1][2] = new Point(offsetx + width * 3 / 4, offsety + width / 2);

        mPoints[2][0] = new Point(offsetx + width / 4, offsety + width * 3 / 4);
        mPoints[2][1] = new Point(offsetx + width / 2, offsety + width * 3 / 4);
        mPoints[2][2] = new Point(offsetx + width * 3 / 4, offsety + width * 3 / 4);
        bitmapR = Point_Nomal.getWidth() / 2;

        int index = 1;
        for (Point[] points : this.mPoints) {
            for (Point point : points) {
                point.index = index;
                index++;
            }
        }
        isInit = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        movingButNoPoint = false;
        isFinish = false;
        moveX = event.getX();
        moveY = event.getY();
        Point point = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnPatterChangeListener != null) {
                    mOnPatterChangeListener.OnPatterStart(true);
                }
                resertPoint();
                point = checkSelectPoint();
                if (point != null) {
                    isSelect = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelect) {
                    point = checkSelectPoint();
                    if (point == null) {
                        movingButNoPoint = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isSelect = false;
                isFinish = true;
                break;
        }
        //选中重复检查

        if (!isFinish && isSelect && point != null) {
            if (crossPoint(point)) {
                movingButNoPoint = true;
            } else {
                point.state = Point.State_pressed;
                pressPoint.add(point);
            }
        }

        if (isFinish) {
            //绘制不成立
            if (pressPoint.size() == 1) {
                resertPoint();
                //绘制错误
            } else if (pressPoint.size() > 0 && pressPoint.size() < 5) {
                errorPoint();
                if (mOnPatterChangeListener != null) {
                    mOnPatterChangeListener.OnPatterChange(null);
                }
            } else {
                //绘制成功
                if (mOnPatterChangeListener != null) {
                    String passWordstr = "";
                    for (int i = 0; i < pressPoint.size(); i++) {
                        passWordstr = passWordstr + pressPoint.get(i).index;
                    }
                    if (!TextUtils.isEmpty(passWordstr)) {
                        mOnPatterChangeListener.OnPatterChange(passWordstr);
                    }
                }
            }
        }
        postInvalidate();

        return true;
    }


    public boolean crossPoint(Point point) {
        if (pressPoint.contains(point)) {
            return true;
        } else {
            return false;
        }
    }

    public void resertPoint() {
        for (int i = 0; i < pressPoint.size(); i++) {
            Point point = pressPoint.get(i);
            point.state = Point.State_nomal;
        }
        pressPoint.clear();
    }

    public void errorPoint() {
        for (Point point : pressPoint) {
            point.state = Point.State_error;
        }
    }

    //检查是否选中了那个点
    private Point checkSelectPoint() {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                if (Point.with(point.x, point.y, bitmapR, moveX, moveY)) {
                    return point;
                }
            }
        }

        return null;
    }

    public static class Point {
        //正常
        public static int State_nomal = 0;
        //选中
        public static int State_pressed = 1;
        //错误
        public static int State_error = 2;
        public int index = 0, state = 0;
        public float x, y;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public static double distance(Point a, Point b) {
            return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
        }

        public static boolean with(float pointX, float pointY, float r, float movingX, float movingY) {
            return Math.sqrt(Math.abs(pointX - movingX) * Math.abs(pointX - movingX) + Math.abs(pointY - movingY) * Math.abs(pointY - movingY)) < r;
        }
    }

    public interface OnPatterChangeListener {
        void OnPatterChange(String passWorkstr);

        void OnPatterStart(boolean isStart);
    }

    private OnPatterChangeListener mOnPatterChangeListener;

    public void setOnPatterChangeListener(OnPatterChangeListener listener) {
        mOnPatterChangeListener = listener;
    }

}
