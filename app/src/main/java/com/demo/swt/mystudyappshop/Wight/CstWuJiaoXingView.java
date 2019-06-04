package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/17
 */

public class CstWuJiaoXingView extends View {
    private int width;
    private Paint mPaint = new Paint();
    private List<Point> mPointList = new ArrayList<>();
    private int centerX, centerY;

    public CstWuJiaoXingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);
        init();
    }


    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        width = Math.min(widthSize, heightSize);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        }
        centerX = width / 2;
        centerY = width / 2;
        setMeasuredDimension(width, width);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = w / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getFivePoint();
        Path path = new Path();

        for (int i = 0; i < mPointList.size() - 1; i++) {
            path.moveTo(mPointList.get(i).x, mPointList.get(i).y);
            path.lineTo(mPointList.get(i + 1).x, mPointList.get(i + 1).y);
        }
        canvas.drawPath(path, mPaint);
//            canvas.drawLine(centerX, centerY, mPointList.get(i).x, mPointList.get(i).y, mPaint);
    }


    public List<Point> getFivePoint() {
        mPointList.clear();
        for (int i = 0; i < 6; i++) {
            mPointList.add(getPoint(i));
        }
        return mPointList;
    }


    private Point getPoint(int position) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (centerX / 2);
            y = width;

        } else if (position == 1) {
            x = centerX;
            y = 0;

        } else if (position == 2) {
            x = centerX * 3 / 2;
            y = width;

        } else if (position == 3) {
            x = 0;
            y = centerY / 2;

        } else if (position == 4) {
            x = width;
            y = centerY / 2;
        } else if (position == 5) {
            x = (centerX / 2);
            y = width;
        }

        return new Point(x, y);
    }
}
