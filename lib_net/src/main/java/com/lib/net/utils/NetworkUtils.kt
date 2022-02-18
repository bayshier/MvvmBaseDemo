package com.lib.net.utils

import android.net.NetworkInfo
import com.lib.net.config.NetAppContext

/**
 * 检测当前请求的网络状态信息
 */
object NetworkUtils {

    @JvmStatic
    fun isConnected(): Boolean = NetworkHelper(NetAppContext.getContext()).isConnected()

    @JvmStatic
    fun isOnline(): Boolean = NetworkHelper(NetAppContext.getContext()).isOnline()

    @JvmStatic
    fun isWifiConn(): Boolean = NetworkHelper(NetAppContext.getContext()).isWifiConn()

    @JvmStatic
    fun isMobileConn(): Boolean = NetworkHelper(NetAppContext.getContext()).isMobileConn()

    @JvmStatic
    fun getNetworkInfo(): NetworkInfo? = NetworkHelper(NetAppContext.getContext()).getNetworkInfo()
}