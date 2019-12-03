package com.liushx.corelibrary.manager

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.annotation.RequiresApi
import com.liushx.corelibrary.enums.NetWorkState
import com.liushx.corelibrary.utils.NetStateUtils

/**
 * 网络状态监听客理类
 *  区分SDK 版本 低版本(< 21)用广播的形式 ，高版本(>21)用注册回调事件
 */
class NetWorkMonitorManager {
    private object NetWorkInstance {
        val sInstance = NetWorkMonitorManager()
    }

    companion object {
        fun getInstance(): NetWorkMonitorManager {
            return NetWorkInstance.sInstance
        }
    }

    private lateinit var context: Context
    private val callbacks = HashSet<NetWorkChangeCallBack>()
    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(
                    "android.net.conn.CONNECTIVITY_CHANGE",
                    ignoreCase = true
                )
            ) {
                val netWorkState = when (NetStateUtils.getAPNType(context)) {
                    0 -> NetWorkState.NONE
                    1 -> NetWorkState.WIFI
                    else -> NetWorkState.GPRS
                }
                psotCallback(netWorkState)
            }
        }
    }


    private var networkCallback: NetworkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : NetworkCallback() {
        /**
         * 网络可用的回调连接成功
         */
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            val netWorkState = when (NetStateUtils.getAPNType(context)) {
                0 -> NetWorkState.NONE
                1 -> NetWorkState.WIFI
                else -> NetWorkState.GPRS
            }
            psotCallback(netWorkState)
        }

        /**
         * 网络不可用时调用和onAvailable成对出现
         */
        override fun onLost(network: Network) {
            super.onLost(network)
            psotCallback(NetWorkState.NONE)
        }

        /**
         * 在网络连接正常的情况下，丢失数据会有回调 即将断开时
         */
        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
        }

        /**
         * 网络功能更改 满足需求时调用
         * @param network
         * @param networkCapabilities
         */
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

        }

        /**
         * 网络连接属性修改时调用
         * @param network
         * @param linkProperties
         */
        override fun onLinkPropertiesChanged(
            network: Network,
            linkProperties: LinkProperties
        ) {
            super.onLinkPropertiesChanged(network, linkProperties)
        }

        /**
         * 网络缺失network时调用
         */
        override fun onUnavailable() {
            super.onUnavailable()
        }
    }

    /**
     * 初始化 传入application
     *
     * @param application
     */
    fun init(application: Application) {

        context = application
        initMonitor()
    }

    /**
     * 初始化网络监听 根据不同版本做不同的处理
     */
    private fun initMonitor() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                //API 大于26时
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                //API 大于21时
                val builder = NetworkRequest.Builder()
                val request = builder.build()
                connectivityManager.registerNetworkCallback(request, networkCallback)
            }
            else -> {
                //低版本
                val intentFilter = IntentFilter()
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
                context.registerReceiver(receiver, intentFilter)
            }
        }
    }

    fun psotCallback(state: NetWorkState) {
        callbacks.forEach {
            it.onNetWorkChange(state)
        }


    }

    /**
     * 反注册广播
     *
     */
    private fun onDestroy() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.unregisterReceiver(receiver)
        }
    }

    /**
     * 注入
     * @param object
     */
    fun register(callback: NetWorkChangeCallBack) {
        if (callbacks.isEmpty()) {
            callbacks.add(callback)
        }
    }

    /**
     * 删除
     *
     * @param object
     */
    fun unregister(callback: NetWorkChangeCallBack) {
        if (callbacks.isNotEmpty()) {
            callbacks.remove(callback)
        }
    }

    interface NetWorkChangeCallBack {

        fun onNetWorkChange(state: NetWorkState)

    }
}