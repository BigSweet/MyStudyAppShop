package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/5
 */
public class CstWZRYLevelProgress extends ProgressBar {
    /*xml中的自定义属性*/
    private int levelTextChooseColor;
    private int levelTextUnChooseColor;
    private int levelTextSize;
    private int progressStartColor;
    private int progressEndColor;
    private int progressBgColor;
    private int progressHeight;
    private final int EMPTY_MESSAGE = 1;

    /*代码中需要设置的属性*/
    private int levels;
    private int currentLevel;
    private int animInterval;
    private int targetProgress;
    private int mTotalWidth;
    int textHeight;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = getProgress();
            // 小于目标值时增加进度，大于目标值时减小进度
            if (progress < targetProgress) {
                setProgress(++progress);
                handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, animInterval);
            } else if (progress > targetProgress){
                setProgress(--progress);
                handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, animInterval);
            } else {
                handler.removeMessages(EMPTY_MESSAGE);
            }
        }
    };


    public CstWZRYLevelProgress(Context context) {
        this(context, null);
    }

    public CstWZRYLevelProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstWZRYLevelProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        init();
    }

    Paint mPaint;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(levelTextSize);
        mPaint.setColor(levelTextUnChooseColor);
    }
    private int dpTopx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    // 设置等级数
    public void setLevels(int levels) {
        this.levels = levels;
    }

    // 设置当前等级
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
        this.targetProgress = (int) (level * 1f / levels * getMax());
    }

    // 设置动画间隔，每隔animInterval秒进度+1或-1
    public void setAnimInterval(final int animInterval) {
        this.animInterval = animInterval;
        handler.sendEmptyMessage(EMPTY_MESSAGE);
    }
    String[] levelTexts;

    public void setLevelTexts(String[] strings) {
        this.levelTexts = strings;
        invalidate();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LevelProgressBar);
        levelTextUnChooseColor = a.getColor(R.styleable.LevelProgressBar_levelTextUnChooseColor, 0x000000);
        levelTextChooseColor = a.getColor(R.styleable.LevelProgressBar_levelTextChooseColor, 0x333333);
        levelTextSize = (int) a.getDimension(R.styleable.LevelProgressBar_levelTextSize, dpTopx(15));
        progressStartColor = a.getColor(R.styleable.LevelProgressBar_progressStartColor, 0xCCFFCC);
        progressEndColor = a.getColor(R.styleable.LevelProgressBar_progressEndColor, 0x00FF00);
        progressBgColor = a.getColor(R.styleable.LevelProgressBar_progressBgColor, 0x000000);
        progressHeight = (int) a.getDimension(R.styleable.LevelProgressBar_progressHeight, dpTopx(20));
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // layout_height为wrap_content时计算View的高度
        if (heightMode != MeasureSpec.EXACTLY) {
            textHeight = (int) (mPaint.descent() - mPaint.ascent());
            // 10dp为等级文字和进度条之间的间隔
            height = getPaddingTop() + getPaddingBottom() + textHeight + progressHeight + dpTopx(10);
        }

        setMeasuredDimension(width, height);

        mTotalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        // 绘制等级文字
        for (int i = 0; i < levelTexts.length; i++) {
            int textWidth = (int) mPaint.measureText(levelTexts[i]);
            mPaint.setColor(levelTextUnChooseColor);
            mPaint.setTextSize(levelTextSize);
            // 到达指定等级时，设置相应的等级文字颜色为深色
            if (getProgress() == targetProgress && currentLevel >= 1 && currentLevel <= levels && i == currentLevel - 1) {
                mPaint.setColor(levelTextChooseColor);
            }
            canvas.drawText(levelTexts[i], mTotalWidth / levels * (i + 1) - textWidth, textHeight, mPaint);
        }

        int lineY = textHeight + progressHeight / 2 + dpTopx(10);
            // 绘制进度条底部
            mPaint.setColor(progressBgColor);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(progressHeight);
            canvas.drawLine(0 + progressHeight / 2, lineY, mTotalWidth - progressHeight / 2, lineY, mPaint);
            // 绘制进度条
            int reachedPartEnd = (int) (getProgress() * 1.0f / getMax() * mTotalWidth);
            if (reachedPartEnd > 0) {
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                // 设置进度条的渐变色
                Shader shader = new LinearGradient(0, lineY,
                        getWidth(), lineY,
                        progressStartColor, progressEndColor, Shader.TileMode.REPEAT);
                mPaint.setShader(shader);
                canvas.drawLine(0 + progressHeight / 2, lineY, reachedPartEnd - progressHeight / 2, lineY, mPaint);
                mPaint.setShader(null);
            }

        canvas.restore();
        }
    }
