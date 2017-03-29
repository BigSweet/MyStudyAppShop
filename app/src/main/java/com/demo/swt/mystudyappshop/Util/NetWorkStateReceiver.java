package com.demo.swt.mystudyappshop.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public class NetWorkStateReceiver extends BroadcastReceiver {
    private boolean update = false;


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (!update && info != null && info.isAvailable()){
                update = true;
                String name = info.getTypeName();
                if (name.equals("WIFI")) {
                    wifiConnected();
                } else {
                    mobileConnected();
                }
            } else {
                update = false;
                disConnected();
            }
        }
    }

    //wifi连接上
    private void wifiConnected(){
        Constant.isWifi = true;
        EventBus.getDefault().post(new NetwokStateEvent(true));
    }

    //移动网络连接上
    private void mobileConnected(){
        Constant.isWifi = false;
        EventBus.getDefault().post(new NetwokStateEvent(true));
    }

    //网络断开
    private void disConnected(){
        Constant.isWifi = false;
        EventBus.getDefault().post(new NetwokStateEvent(false));
    }

}
