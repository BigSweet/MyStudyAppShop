package com.demo.swt.mystudyappshop.greendao;

import android.content.Context;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Adapter.BaseAdapter;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;

import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/9
 */

public class RvAdapter extends BaseAdapter<User, BaseHolder> {
    public RvAdapter(Context context, List<User> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    public void binData(BaseHolder holder, User user, int position) {
        NoNullUtils.setText((TextView) holder.getView(R.id.tv_id), user.getId()+"");
        NoNullUtils.setText((TextView) holder.getView(R.id.tv_name), user.getName());
        NoNullUtils.setText((TextView) holder.getView(R.id.tv_age), user.getAge() + "");
        NoNullUtils.setText((TextView) holder.getView(R.id.tv_sex), user.getSex());
    }
}
