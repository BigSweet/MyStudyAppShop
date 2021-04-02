package com.android.okhttp.monitor.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter


fun getPhoneWifiIpAddress(context: Context): String? {
    val mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (mWifiManager.isWifiEnabled && mWifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED) {
        val wifiInfo = mWifiManager.connectionInfo
        val wifiIp = wifiInfo?.ipAddress ?: 0
        if (wifiIp > 0) {
            return Formatter.formatIpAddress(wifiIp)
        } else {
            return null
        }
    } else {
        return null
    }
}