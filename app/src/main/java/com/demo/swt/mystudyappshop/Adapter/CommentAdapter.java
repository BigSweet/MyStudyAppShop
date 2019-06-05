package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;

import java.util.ArrayList;
import java.util.List;

import bean.FeedBean;

/**
 * Created by pc on 2016/12/9.
 */

public class CommentAdapter extends BaseAdapter<FeedBean.CommentsBean, BaseHolder> {
    private Context context;
    private List<TextView> mlist = new ArrayList<>();
    private List<Integer> idlist = new ArrayList<>();


    public CommentAdapter(Context context, List<FeedBean.CommentsBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void binData(BaseHolder holder, FeedBean.CommentsBean commentBean, int position) {
        NoNullUtils.setText((TextView) holder.getView(R.id.comment_name), commentBean.getUser().getName());
        NoNullUtils.setText((TextView) holder.getView(R.id.comment_content), commentBean.getContent());
    }
}
