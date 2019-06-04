package com.demo.swt.mystudyappshop.bean;


import com.demo.swt.mystudyappshop.Listener.DataListener;

import java.util.List;

/**
 * 介绍：分页数据的基类
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */
public class BaseJavaListData<T extends BaseListJavaBean<K>,K> extends BaseData<T> implements DataListener<K> {
    @Override
    public List<K> getList() {
        if(isSuccess()){
            return getData().getList();
        }
        return null;
    }

    @Override
    public int getTotal() {
        if(isSuccess()){
            return getData().getTotal();
        }
        return 0;
    }


    @Override
    public int getCurrentpage() {
        if(isSuccess()){
            return getData().getPageNo();
        }
        return 0;
    }

    @Override
    public int getCurrentPageSize() {
        return getList()==null?0:getList().size();
    }


    @Override
    public int getRowSize() {
        return 1;
    }

    @Override
    public String getNextNt() {
        if(isSuccess()){
            return getData().getNt();
        }
        return null;
    }

    protected boolean isSuccess(){
        return getFlag()==1&&null!=getData();
    }
}
