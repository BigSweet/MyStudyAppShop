package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.RecordBean;

import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/6
 */

public class WebChatAdapter extends BaseAdapter<RecordBean, BaseHolder> {
    private Context mcontext;
    private int mMinItemWidth;
    private int mMaxItemWidth;

    public WebChatAdapter(Context context, List<RecordBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.mcontext = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outmetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outmetrics);

        mMinItemWidth = (int) (outmetrics.widthPixels * 0.15f);
        mMaxItemWidth = (int) (outmetrics.widthPixels * 0.7f);
    }

    @Override
    public void bindata(BaseHolder holder, RecordBean recordBean, int position) {
        setText((TextView) holder.getView(R.id.record_time), recordBean.getTime() + "");
        ViewGroup.LayoutParams lp = holder.getView(R.id.record_length).getLayoutParams();
        lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f * getItem(position).getTime()));
    }
}
