package com.demo.swt.mystudyappshop.net;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class NetInterfaceFactory {
    public static NetInterface getNetInterface(){
        return IonNetInterface.get();
    }

}
