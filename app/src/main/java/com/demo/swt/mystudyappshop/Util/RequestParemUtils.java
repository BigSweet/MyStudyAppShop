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
    public static RequestParem getBanner(int locationId) {
        long time = System.currentTimeMillis() / 1000;
        RequestParem requestParem = JavaRequestParem.post(UrlAddress.getURL_COMMUNITY_BANNER())
                .putBody("client_type", Key.APP_CLIENT_TYPE + "")
                .putBody("app_version","3.1.7")
                .putBody("device_id", "868026024156875")
                .putBody("time", time + "")
                .putBody("schoolId", "1237")
                .putBody("locationId", String.valueOf(locationId));
        return requestParem;
    }
}
