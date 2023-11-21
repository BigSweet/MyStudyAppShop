package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.bean.BaiKeBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/20
 */

public class BaiKeAdapter extends BaseAdapter<BaiKeBean, BaseHolder> {

    private Context context;
    private List<String> imglist = new ArrayList<>();


    public BaiKeAdapter(Context context, List<BaiKeBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void binData(BaseHolder holder, BaiKeBean baiKeBean, int position) {
        LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        wuyuitem.setLayoutParams(params);
        if (baiKeBean.getContent() != null) {
            setText((TextView) holder.getView(R.id.baike_text), baiKeBean.getContent());
            setText((TextView) holder.getView(R.id.name), baiKeBean.getName());
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.logo);
//            if (baiKeBean.getTouxiang().contains("?")) {
//                String url = baiKeBean.getTouxiang().substring(0, baiKeBean.getTouxiang().indexOf("?"));
//                simpleDraweeView.setImageURI("https:" + url);
//            }
            NineGridImageView nineGridView = (NineGridImageView) holder.getView(R.id.cstimage);

            if (baiKeBean.getImgs() != null && baiKeBean.getImgs().size() > 0) {
                imglist = baiKeBean.getImgs();
                if (baiKeBean.getImgs().size() == 1) {
                    NineGridImageViewAdapter nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
                        @Override
                        protected void onDisplayImage(Context context, ImageView imageView, String s) {
                            Glide.with(context).load(s).into(imageView);
                        }

                        @Override
                        protected void onItemImageClick(Context context, int index, List list) {
                            Intent intent = new Intent(context, BigImageActivity.class);
                            Bundle bundle = new Bundle();
                            if (list.size() > 0) {
                                bundle.putStringArrayList("tulist", (ArrayList<String>) list);
                                bundle.putInt("pos", index);
                            }
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    };
                    nineGridView.setAdapter(nineGridImageViewAdapter);
                    nineGridView.setImagesData(imglist);
                } else {
                    NoNullUtils.setVisible(nineGridView, false);
                }

            }
        }
    }
}
