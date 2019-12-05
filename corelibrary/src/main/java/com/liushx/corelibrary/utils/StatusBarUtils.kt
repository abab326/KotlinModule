package com.liushx.corelibrary.utils

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

class StatusBarUtils {
    companion object {

        /**
         * 设置状态栏颜色
         */
        fun setStatusBarColor(window: Window, color: Int, colorLight: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = color
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val systemUiVisibility = window.decorView.systemUiVisibility
                if (colorLight) {
                    window.decorView.systemUiVisibility  =
                        systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility =
                        systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }
        }

        /**
         * 设置沉浸式StatusBar
         */
        fun setStatusBarTranslucent(window: Window) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags =
                (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags)
        }

    }
}