package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/6
 */

public class CstManView extends View {
    private int headColor;
    int bodyColor;
    int headWidth;
    int bodyWidth;
    Paint mPaint;
    int headProgress;
    headThread headThread;

    public CstManView(Context context) {
        this(context, null);
    }

    public CstManView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstManView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CstManView, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CstManView_headColor:
                    headColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CstManView_HeadcircleWidth:
                    headWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CstManView_bodyColor:
                    bodyColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CstManView_bodycircleWidth:
                    bodyWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        // 绘图线程
        headThread = new headThread();
        headThread.start();
        if (headProgress == 360) {
            headThread.stop();
        }
    }

    class headThread extends Thread {
        @Override
        public void run() {
            while (true) {
                headProgress++;
                postInvalidate();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centreX = getWidth() / 2; // 获取圆心的x坐标
        int CenterY = getHeight() / 4;
        int radius = centreX / 8;// 半径
        mPaint.setStrokeWidth(headWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centreX - radius, CenterY - radius, centreX + radius, CenterY + radius); // 用于定义的圆弧的形状和大小的界限
//        canvas.drawCircle(getHeight() / 4, getWidth() / 2, radius, mPaint); // 画出圆环
        mPaint.setColor(headColor); // 设置圆环的颜色
        canvas.drawArc(oval, -90, headProgress, false, mPaint); //根据进度画圆弧

        //画三角形
        mPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(centreX, CenterY + radius);// 此点为多边形的起点
        path.lineTo(centreX - 2 * radius, getHeight() * 3 / 6);
        path.lineTo(centreX + 2 * radius, getHeight() * 3 / 6);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint);
    }
}
