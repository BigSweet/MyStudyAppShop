package com.demo.swt.mystudyappshop.Holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;

/**
 * baseadapter用的holder
 * Created by pc on 2016/12/7.
 */

public class BaseHolder<T> extends RecyclerView.ViewHolder {


    private SparseArray<View> views;


    public BaseHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }


    public <K extends View> K getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (K) view;
    }

    public RecyclerView getRecyclerView(int id) {
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(id);

        return recyclerView;
    }

    public void setOnItemClickListener(final int position, final T t, final OnRecyclerViewItemClickListener<T> l) {
        if (null != itemView) {
            if (null != l) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        l.onClick(position, t,v);
                    }
                });
            } else {
                itemView.setOnClickListener(null);
            }
        }
    }

}
