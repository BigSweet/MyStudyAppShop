package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageLoader;

import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/17
 */

public class PhotoSRvAdapter extends BaseAdapter<String, BaseHolder> {
    private String mDirPath;
    private ImageLoader mImageLoader;
    private List<String> mData;
    public PhotoSRvAdapter(Context context, List<String> mDatas, int layoutId, String mDirPath) {
        super(context, mDatas, layoutId);
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
        this.mDirPath = mDirPath;
        this.mData = mDatas;
    }

    @Override
    public void binData(BaseHolder holder, String s, int position) {
        mImageLoader.loadImage(mDirPath + "/" + mData.get(position),
                (ImageView) holder.getView(R.id.id_item_image));
    }

}
