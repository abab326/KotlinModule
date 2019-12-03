package com.liushx.corelibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager


class NetStateUtils {

    companion object {
        /**
         * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
         * 自定义
         *
         * @param context
         * @return
         */
        fun getAPNType(context: Context): Int { //结果返回值
            var netType = 0
            //获取手机所有连接管理对象
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //获取NetworkInfo对象
            val networkInfo = manager.activeNetworkInfo ?: return netType
            //NetworkInfo对象为空 则代表没有网络
            //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_WIFI) { //WIFI
                netType = 1
            } else if (nType == ConnectivityManager.TYPE_MOBILE) {

                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val nSubType = networkInfo.subtype
                //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                netType =
                    if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming) {
                        4
                    } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA || (nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                                && !telephonyManager.isNetworkRoaming)
                    ) {
                        3
                        //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                    } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS || nSubType == TelephonyManager.NETWORK_TYPE_EDGE || (nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                                && !telephonyManager.isNetworkRoaming)
                    ) {
                        2
                    } else {
                        2
                    }
            }
            return netType
        }
    }
}