package com.demo.swt.mystudyappshop.Listener;

import java.util.List;

/**
 * 介绍：第二层继承
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public interface DataListener<T> {

    List<T> getList();

    int getTotal();

    int getCurrentpage();

    int getCurrentPageSize();


    int getRowSize();

    String getNextNt();


}
