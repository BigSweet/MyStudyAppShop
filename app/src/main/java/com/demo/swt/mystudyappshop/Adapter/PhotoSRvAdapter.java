package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.bean.PhotoBean;

import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/17
 */

public class PhotoSRvAdapter extends BaseAdapter<String,BaseHolder> {
    public PhotoSRvAdapter(Context context, List<String> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    public void bindata(BaseHolder holder, String s, int position) {

    }

}
