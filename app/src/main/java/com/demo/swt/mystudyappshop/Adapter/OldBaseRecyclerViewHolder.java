package com.demo.swt.mystudyappshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;
import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemLongClickListener;
import com.demo.swt.mystudyappshop.R;


/**
 * 介绍：RecylerViewHolder的基类
 *
 *      废弃掉，之前的就不改变了，新写的需用最新的
 *
 * 作者：xjzhao
 * 邮箱：mr.feeling.heart@gmail.com
 * 时间: 2016-03-16  下午5:35
 */
@Deprecated
public  class OldBaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    protected View itemView;

    public OldBaseRecyclerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        if (itemView != null){
            // 添加点击效果
            itemView.setBackgroundDrawable(itemView.getResources().getDrawable(R.drawable.app_listview_item_bg));
        }
    }

    public View getItemView() {
        return itemView;
    }

    protected boolean isNeedNew(View v){
        return itemView != null && v == null;
    }

    /**
     * 点击事件
     * @param position
     * @param t
     * @param l
     */
    public void setOnItemClickListener(final int position, final T t, final OnRecyclerViewItemClickListener<T> l) {
        if (null != itemView){
            if (null != l) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        l.onClick(position, t,v);
                    }
                });
            }else {
                itemView.setOnClickListener(null);
            }
        }
    }

    /**
     * 长按事件
     * @param position
     * @param t
     * @param l
     */
    public void setOnItemLongClickLstener(final int position, final T t, final OnRecyclerViewItemLongClickListener<T> l){
        if (null != itemView){
            if (null != l) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        l.onLongClick(position, t);
                        return true;
                    }
                });
            }else {
                itemView.setOnLongClickListener(null);
            }
        }
    }


    public <K extends View> K findViewById(int resId){
        if(itemView==null){
            return null;
        }
        return (K) itemView.findViewById(resId);
    }

    public void setText(TextView t, String s){
        if (null != t){
            if (null == s){
                s = "";
            }
            t.setText(s);
        }
    }



    public void setVisable(View view,boolean visable){
        if(null!=view){
            if(visable){
                view.setVisibility(View.VISIBLE);
            }else{
                view.setVisibility(View.GONE);
            }
        }
    }
}
