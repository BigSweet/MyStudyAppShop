package com.demo.swt.mystudyappshop.Util;

import com.demo.swt.mystudyappshop.net.JavaRequestParem;
import com.demo.swt.mystudyappshop.net.RequestParem;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/29
 */

public class RequestParemUtils {

    //banner
    public static RequestParem getBanner() {
        RequestParem requestParem = JavaRequestParem.post(UrlAddress.getURL_COMMUNITY_BANNER());
        return requestParem;
    }

    //banner
    public static RequestParem gettoken() {
        RequestParem requestParem = JavaRequestParem.post(UrlAddress.gettoken());
        return requestParem;
    }

}
