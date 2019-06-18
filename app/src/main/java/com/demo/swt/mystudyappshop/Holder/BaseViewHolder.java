package com.demo.swt.mystudyappshop.Holder;

import androidx.annotation.NonNull;
import android.view.View;

/**
 * Created by pc on 2016/11/30.
 */

public abstract class BaseViewHolder<T> extends BaseRecyclerViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bindData(int position, @NonNull T t);
}
