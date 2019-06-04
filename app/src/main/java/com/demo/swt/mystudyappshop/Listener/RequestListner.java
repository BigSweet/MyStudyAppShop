package com.demo.swt.mystudyappshop.Listener;


import com.demo.swt.mystudyappshop.net.IonNetInterface;
import com.demo.swt.mystudyappshop.result.ResultMessage;

import java.util.List;
import java.util.UUID;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public abstract class RequestListner<T> {

    private final Class<T> clazz;
    private Object tag;


    public RequestListner(Class<T> clazz) {
        tag = UUID.randomUUID().toString();
        this.clazz = clazz;
    }

    public RequestListner(Object tag, Class<T> clazz) {
        this.tag = tag;
        this.clazz = clazz;
    }

    public final void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 开始网络请求
     */
    public void onStart() {

    }

    /**
     * 网络请求结束
     * @param e 请求结束后的处理结果
     */
    public void onEnd(ResultMessage e) {

    }

    public final void cancel() {
        IonNetInterface.get().cancel(tag);
    }

    public final Object getTag() {
        return tag;
    }


    public boolean onSuccess(T t) throws Exception {
        return true;
    }

    public boolean onSuccess(List<T> tList) throws Exception {
        return true;
    }

    public final Class<T> getClazz() {
        return clazz;
    }

}
