package com.demo.swt.mystudyappshop.Interface;

/**
 * 介绍：RecyclerView的item点击事件处理
 * 作者：xjzhao
 * 邮箱：mr.feeling.heart@gmail.com
 * 时间: 2016-03-23  下午2:15
 */
public interface OnRecyclerViewItemClickListener<T> {

    /**
     * 点击
     * @param position
     * @param t
     */
    void onClick(int position, T t);


}
