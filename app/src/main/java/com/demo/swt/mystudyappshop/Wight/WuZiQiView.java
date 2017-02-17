package com.demo.swt.mystudyappshop.Wight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/15
 */

/**
 * 首先画棋盘new paint 初始化画笔，
 * 根据屏幕的宽度设置自定义view的高度,在onmeasure中测量,进行特殊情况处理，unspecified的时候
 * 最后通过 setMeasuredDimension(width, width);完成大小的测量，然后绘制线条
 *
 */
public class WuZiQiView extends View {
    private int maxline = 10;
    private int mpanelWidth;
    private float mLineHeight;
    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlancPiece;
    private float ratioPieceOfLineHeight = 1.0f * 3 / 4;
    private boolean meisWhite = false;
    private ArrayList<Point> mWhiteList = new ArrayList<>();
    private ArrayList<Point> mBlackList = new ArrayList<>();
    private boolean misWhiteWinner;
    private boolean mIsGameOver;
    private int MaxCountLine = 5;

    public WuZiQiView(Context context, AttributeSet attrs) {

        super(context, attrs);
//        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlancPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
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
        setMeasuredDimension(width, width);
    }

    /**
     *
     * @param w    当前的宽度
     * @param h    当前的高度
     * @param oldw 改变之前的宽度
     * @param oldh 改变之前的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mpanelWidth = w;
        mLineHeight = mpanelWidth * 1.0f / maxline;
        int pipcewidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pipcewidth, pipcewidth, false);
        mBlancPiece = Bitmap.createScaledBitmap(mBlancPiece, pipcewidth, pipcewidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getVaildPoint(x, y);
            if (mWhiteList.contains(p) || mBlackList.contains(p)) {
                return false;
            }
            if (meisWhite) {
                mWhiteList.add(p);
            } else {
                mBlackList.add(p);
            }
            invalidate();
            meisWhite = !meisWhite;
        }
        return true;


    }

    private Point getVaildPoint(int x, int y) {

        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        doawBoard(canvas);
        drawPiece(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteList);
        boolean blackWin = checkFiveInLine(mBlackList);
        if (whiteWin || blackWin) {
            mIsGameOver = true;
            misWhiteWinner = whiteWin;
            String text = misWhiteWinner ? "白棋胜利" : "黑期胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("游戏结束").setMessage("在来一局吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reStart();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();

        }
    }

    private boolean checkFiveInLine(List<Point> points) {
        for (Point point : points) {
            int x = point.x;
            int y = point.y;
            boolean horizontalwin = checkHorizontal(x, y, points);
            boolean verticalwin = checkvertical(x, y, points);
            boolean leftwin = checkleftDiagonal(x, y, points);
            boolean rightwin = checkRightDigonal(x, y, points);
            if (horizontalwin || verticalwin || leftwin || rightwin) {
                return true;
            }
        }
        return false;
    }

    private boolean checkvertical(int x, int y, List<Point> points) {

        int count = 1;
        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }

        }

        if (count == MaxCountLine) {
            return true;
        }

        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MaxCountLine) {
            return true;
        }
        return false;
    }

    private boolean checkleftDiagonal(int x, int y, List<Point> points) {

        int count = 1;
        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }

        }

        if (count == MaxCountLine) {
            return true;
        }

        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MaxCountLine) {
            return true;
        }
        return false;
    }

    private boolean checkRightDigonal(int x, int y, List<Point> points) {

        int count = 1;
        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }

        }

        if (count == MaxCountLine) {
            return true;
        }

        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MaxCountLine) {
            return true;
        }
        return false;
    }

    /**
     * 检查横向的是否胜利
     *
     * @param x
     * @param y
     * @param points
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        //左边
        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }

        }

        if (count == MaxCountLine) {
            return true;
        }

        for (int i = 1; i < MaxCountLine; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MaxCountLine) {
            return true;
        }
        return false;
    }

    private void drawPiece(Canvas canvas) {
        for (int i = 0; i < mWhiteList.size(); i++) {
            Point whitePoint = mWhiteList.get(i);
            canvas.drawBitmap(mWhitePiece, (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
        for (int i = 0; i < mBlackList.size(); i++) {
            Point mBlackPoint = mBlackList.get(i);
            canvas.drawBitmap(mBlancPiece, (mBlackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, (mBlackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    private void doawBoard(Canvas canvas) {
        int w = mpanelWidth;
        int LineHeight = (int) mLineHeight;
        for (int i = 0; i < maxline; i++) {
            int startX = (int) (mLineHeight / 2);
            int endX = (int) (w - mLineHeight / 2);
            int y = (int) ((0.5 + i) * LineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    public void reStart() {
        mWhiteList.clear();
        mBlackList.clear();
        mIsGameOver = false;
        misWhiteWinner = false;
        invalidate();
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_ME_ISWHITE = "instance_me_iswhite";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putBoolean(INSTANCE_ME_ISWHITE, meisWhite);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteList);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackList);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mBlackList = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            mWhiteList = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            meisWhite = bundle.getBoolean(INSTANCE_ME_ISWHITE);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
