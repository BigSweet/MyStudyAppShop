package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by pc on 2016/12/7.
 */

public abstract class BaseAdapter<T, H extends BaseHolder> extends RecyclerView.Adapter<BaseHolder> {

    protected List<T> mDatas;
    private T mData;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int layoutResId;
    private OnRecyclerViewItemClickListener<T> onItemClickListener;

    
    public BaseAdapter(Context context, List<T> mDatas, int layoutId) {
        this.mContext = context;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
        this.layoutResId = layoutId;
    }


    public BaseAdapter(Context context, T mData, int layoutId) {
        this.mContext = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
        this.layoutResId = layoutId;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutResId, null, false);
        return new BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        H h = (H) holder;
        h.setOnItemClickListener(position, getItem(position), onItemClickListener);
        T t = getItem(position);
        binData(holder, t, position);
    }

    public abstract void binData(BaseHolder holder, T t, int position);

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<T> l) {
        this.onItemClickListener = l;
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void addData(int position, List<T> morelist) {
        mDatas.addAll(morelist);
        notifyItemChanged(position, mDatas.size());

    }


    public void addData(List<T> morelist) {
        mDatas.clear();
        mDatas.addAll(morelist);
        notifyItemChanged(0, mDatas.size());

    }

    public List<T> getdata() {
        return mDatas;
    }


    protected void setText(TextView t, String s) {
        if (null != t) {
            if (null == s) {
                s = "";
            }
            t.setText(s.trim());
        }
    }


}
