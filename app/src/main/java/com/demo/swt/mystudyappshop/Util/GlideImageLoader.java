package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.swt.mystudyappshop.bean.BannerBean;

/**
 * introduce：这里写介绍
 * createBy：sunwentaoglide
 * email：wentao.sun@yintech.cn
 * time: 20/7/18
 */
public class GlideImageLoader extends com.youth.banner.loader.ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerBean.DataBean dataBean = (BannerBean.DataBean) path;
        //Glide 加载图片简单用法
        Glide.with(context).load(dataBean.getImagePath()).into(imageView);

    }
}
