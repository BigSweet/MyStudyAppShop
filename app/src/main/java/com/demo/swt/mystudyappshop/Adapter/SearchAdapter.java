package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.CstImage;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/12/7.
 */

public class SearchAdapter extends BaseAdapter<FeedBean, BaseHolder> {
    private Context context;

    public SearchAdapter(Context context, List<FeedBean> mDatas, int layoutId) {
        super(context,mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void bindata(BaseHolder holder, FeedBean feedBean) {
        setText((TextView) holder.getView(R.id.wuyu_text), feedBean.getPost().getContent());
        setText((TextView) holder.getView(R.id.name), feedBean.getUser().getName());
        setText((TextView) holder.getView(R.id.school), feedBean.getUser().getEntity_name());
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.logo);
        simpleDraweeView.setImageURI(feedBean.getUser().getAvatar());
        CstImage cstImage = (CstImage) holder.getView(R.id.cstimage);
        cstImage.setImgs(feedBean.getPost().getImages());

        cstImage.setOnChildItemClickListener(new CstImage.OnChlidItemClickListener() {
            @Override
            public void onClick(int position, List<String> imgs) {
                Intent intent = new Intent(context, BigImageActivity.class);
                Bundle bundle = new Bundle();
                if (imgs.size() > 0) {
                    bundle.putStringArrayList("tulist", (ArrayList<String>) imgs);
                }
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}

