package com.demo.swt.mystudyappshop.net;

import android.content.Context;

import com.demo.swt.mystudyappshop.Listener.RequestListner;
import com.demo.swt.mystudyappshop.convert.ConvertJson;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public interface NetInterface {

    /**
     * 初始化，在application的oncreate中调用
     *
     * @param app
     */
    void start(Context app);

    /**
     * 在合适的地方取消所有接口
     * 此处需要注意：如在退出最后一个Activity调用，会清掉appliaction的对象
     * 而系统会缓存app（时间不确定），如未被系统杀死，再次启动APP，不会执行oncreat方法
     * <p/>
     * 正确处理：1，在退出app时强制杀死进程，2不调用（建议）
     */
    void stop();

    <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner);

    <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner, ConvertJson<T> convertJson);

    <T> String doSyncRequest(String tag, RequestParem requestParem);

    void cancel(Object tag);
}
