package com.demo.swt.mystudyappshop.Util;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/29
 */

public class UrlAddress {

    public static String ADV_URL = "https://adsys.anlaiye.com.cn";//广告系统

    //社区banner
    public static String getURL_COMMUNITY_BANNER() {
        return ADV_URL + "/ad/app/getList.do";
    }    //社区banner
    public static String gettoken() {
        return "http://101.200.236.44:8668/api/v1/auth/authenticate?id_card=1 ";
    }

}
