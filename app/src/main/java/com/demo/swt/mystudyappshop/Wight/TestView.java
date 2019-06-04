package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/28
 */

public class TestView extends View {
    private int maxline = 10;
    private int mpanelWidth;
    private float mLineHeight;
    private Paint mPaint = new Paint();

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        }
        //设置自定义view的宽度和高度
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mpanelWidth = w;//设置view的宽度为棋盘的宽度
        mLineHeight = mpanelWidth * 1.0f / maxline;//获取每一行的宽度=棋盘的宽度/行数
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        doawBoard(canvas);//画棋盘

    }

    private void doawBoard(Canvas canvas) {
        int w = mpanelWidth;
        int LineHeight = (int) mLineHeight;
        for (int i = 0; i < maxline; i++) {
            //不能从起点开始画，因为我们还需要放置旗子，所以这里取行宽的2分之1
            int startX = (int) (mLineHeight / 2);
            int endX = (int) (w - mLineHeight / 2);
            int y = (int) ((0.5 + i) * LineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

}
