package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/25
 */
public class SWVideoView  extends VideoView{
    private int videoWidth;
    private int videoHeight;


    public SWVideoView(Context context) {
        super(context);
    }

    public SWVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SWVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }
}
