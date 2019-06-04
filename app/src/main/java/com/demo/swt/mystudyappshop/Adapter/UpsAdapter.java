package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bean.FeedBean;

/**
 * Created by pc on 2016/12/9.
 */

public class UpsAdapter extends BaseAdapter<FeedBean.UpsBean, BaseHolder> {
    private Context context;


    public UpsAdapter(Context context, List<FeedBean.UpsBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void bindata(BaseHolder holder, FeedBean.UpsBean upsBean, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.up_item_adv);
        simpleDraweeView.setImageURI(upsBean.getAvatar());
    }

}
