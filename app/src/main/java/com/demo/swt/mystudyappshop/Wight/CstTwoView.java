package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
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

public class CstTwoView extends View {
    //显示的图片
    private Bitmap mImage;
    //图片的显示模式
    int mImageScale;
    //图片下方的文字
    String mTitle;
    //文字的大小
    int mTextSize;
    //文字的颜色
    int mTextColor;
    //画笔
    Paint mPaint;
    //整个布局的宽度
    int width;
    //整个布局的高度
    int height = 0;
    //静态常亮图片的模式
    private static final int IMAGE_SCALE_FITXY = 0;
    /**
     * 对文本的约束
     */
    private Rect mTextBound;
    /**
     * 控制整体布局
     */
    private Rect mRect;

    public CstTwoView(Context context) {
        this(context, null);

    }

    public CstTwoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstTwoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CstTwoView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CstTwoView_imgsrc:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CstTwoView_imgScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CstTwoView_text:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CstTwoView_textColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CstTwoView_textSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        mTextBound = new Rect();
        mRect = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int imgwidth = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
            int textWidth = mTextBound.width() + getPaddingRight() + getPaddingLeft();
            //取图片和文字的最大宽度
            width = Math.max(imgwidth, textWidth);

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            width = heightSize;
        } else {
            int des = mImage.getHeight() + getPaddingBottom() + getPaddingTop() + mTextBound.height();
            height = des;
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = width - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = height - getPaddingBottom();

        if (mRect.width() > width) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) width - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), height - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTitle, width / 2 - mTextBound.width() * 1.0f / 2, height - getPaddingBottom(), mPaint);
        }

        mRect.bottom -= mTextBound.height();
        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        } else {
            //计算居中的矩形范围
            mRect.left = width / 2 - mImage.getWidth() / 2;
            mRect.right = width / 2 + mImage.getWidth() / 2;
            mRect.top = (height - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            mRect.bottom = (height - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }

    }
}
