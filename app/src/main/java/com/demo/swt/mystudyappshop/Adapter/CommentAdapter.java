package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/9.
 */

public class CommentAdapter extends BaseAdapter<CommentBean, BaseHolder> {
    private Context context;
    private List<TextView> mlist = new ArrayList<>();
    private List<Integer> idlist = new ArrayList<>();


    public CommentAdapter(Context context, List<CommentBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void bindata(BaseHolder holder, CommentBean commentBean, int position) {
        NoNullUtils.setText((TextView) holder.getView(R.id.comment_name), commentBean.getUser().getName());
        NoNullUtils.setText((TextView) holder.getView(R.id.comment_content), commentBean.getContent());
    }
}
