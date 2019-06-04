package com.demo.swt.mystudyappshop.Interface;

import android.view.View;

public interface OnRecyclerViewItemClickListener<T> {

    /**
     * 点击
     * @param position
     * @param t
     */
    void onClick(int position, T t, View view);


}
