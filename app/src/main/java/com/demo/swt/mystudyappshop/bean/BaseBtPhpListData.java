package com.demo.swt.mystudyappshop.bean;


import com.demo.swt.mystudyappshop.Listener.DataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：  针对变态的Php数据类型  如：data里是｛list1[],list2[],list3[]...｝
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */
public abstract class BaseBtPhpListData<T, K> extends BaseData<T> implements DataListener<K> {
    @Override
    public List<K> getList() {
        if (getFlag() == 1) {
            return convert(getData());
        }
        return null;
    }

    @Override
    public int getCurrentpage() {
        return 0;
    }


    @Override
    public int getTotal() {
        return 0;
    }

    public abstract List<K> convert(T data);


    @Override
    public int getCurrentPageSize() {
        if (getList() == null || getList().size() == 0) {
            return 0;
        }

        int size = getList().size();
        if (getRowSize() < 2) {
            return size;
        }

        return (size / getRowSize()) * getRowSize() + size % getRowSize();

    }

    @Override
    public int getRowSize() {
        return 1;
    }


    public static <K> List<List<K>> convertRow(List<K> data, int rowNum) {
        if (data == null || data.size() == 0) {
            return null;
        }

        if (rowNum < 1) {
            rowNum = 1;
        }

        List<List<K>> list = new ArrayList<>();
        List<K> temp = null;
        for (int i = 0; i < data.size(); i++) {

            int j = i % rowNum;

            if (j == 0) {
                temp = new ArrayList<>();
                list.add(temp);
            }
            temp.add(data.get(i));
        }
        return list;

    }

    @Override
    public String getNextNt() {
        return null;
    }
}
